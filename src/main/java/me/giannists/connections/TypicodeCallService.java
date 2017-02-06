package me.giannists.connections;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;

@Service
public class TypicodeCallService {

    @Autowired
    private TypicodeApi typicodeApi;

    public ObjectNode getUserById(int userId) {
        return performCallSafe(typicodeApi.getUserById(userId));
    }

    public ArrayNode getPostsByUserId(int userId) {
        return performCallSafe(typicodeApi.getPostsByUserId(userId));
    }

    private static <T> T performCallSafe(Call<T> call) {
        try {
            return call.execute().body();
        } catch (IOException e) {
            throw new RuntimeException("Communication Error", e);
        }
    }

}
