package me.giannists.connections;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TypicodeApi {

    @GET("/users/{id}")
    Call<ObjectNode> getUserById(@Path("id") int id);

    @GET("/posts")
    Call<ArrayNode> getPostsByUserId(@Query("userId") int id);
}
