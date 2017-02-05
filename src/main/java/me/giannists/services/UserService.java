package me.giannists.services;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.giannists.connections.TypicodeCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class UserService {

    @Autowired
    private TypicodeCallService typicodeCallService;

    public ObjectNode getUserdata(int userId) {
        CompletableFuture<ObjectNode> getUserByIdFuture =
                CompletableFuture.supplyAsync(() -> typicodeCallService.getUserById(userId));

        CompletableFuture<ArrayNode> getPostsByUserIdFuture =
                CompletableFuture.supplyAsync(() -> typicodeCallService.getPostsByUserId(userId));

        CompletableFuture allFetched = CompletableFuture
                .allOf(getUserByIdFuture, getPostsByUserIdFuture);

        fetchFutureSafe(allFetched);

        return merge(getUserByIdFuture, getPostsByUserIdFuture);
    }

    private static ObjectNode merge(CompletableFuture<ObjectNode> user,
                                               CompletableFuture<ArrayNode> posts) {
        JsonNodeFactory jsonNodeFactory = JsonNodeFactory.instance;

        ObjectNode merged = jsonNodeFactory.objectNode();
        merged.setAll(fetchFutureSafe(user));
        merged.set("posts", fetchFutureSafe(posts));

        return merged;
    }

    private static <T> T fetchFutureSafe(CompletableFuture<T> future) {
        try {
            return future.get();
        } catch (InterruptedException e) {
            throw new RuntimeException("Calls interrupted", e);
        } catch (ExecutionException e) {
            throw new RuntimeException("Execution error", e);
        }
    }
}
