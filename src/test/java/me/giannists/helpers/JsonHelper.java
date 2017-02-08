package me.giannists.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;

public abstract class JsonHelper {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static ObjectNode getMockUserById() {
        File mockUserResponse = new File("src/test/resources/mockUserResponse.json");
        try {
            return (ObjectNode) mapper.readTree(mockUserResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayNode getMockPostsByUserId() {
        File mockUserResponse = new File("src/test/resources/mockPostsResponse.json");
        try {
            return (ArrayNode) mapper.readTree(mockUserResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectNode getMockMergedResponse() {
        ObjectNode mergedResponse = JsonNodeFactory.instance.objectNode();
        mergedResponse.setAll(getMockUserById());
        mergedResponse.set("posts", getMockPostsByUserId());
        return  mergedResponse;
    }
}
