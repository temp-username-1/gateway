package me.giannists.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import me.giannists.connections.TypicodeCallService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private TypicodeCallService typicodeCallService;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUserEnrichedShouldMergeContents() {
        // given
        when(typicodeCallService.getUserById(anyInt())).thenReturn(getUserById());
        when(typicodeCallService.getPostsByUserId(anyInt())).thenReturn(getPostsByUserId());

        // when
        ObjectNode mergedResult = userService.getUserEnriched(2);

        // then
        JSONAssert.assertEquals(mergedResult, getUserById());
    }

    private ObjectNode getUserById() {
        ObjectMapper mapper = new ObjectMapper();
        FileInputStream mockUserResponse = null;
        try {
            mockUserResponse = new FileInputStream("src/test/resources/mockUserResponse.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return mapper.valueToTree(mockUserResponse);
    }

    private ArrayNode getPostsByUserId() {
        ObjectMapper mapper = new ObjectMapper();
        FileInputStream mockUserResponse = null;
        try {
            mockUserResponse = new FileInputStream("src/test/resources/mockPostsResponse.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return mapper.valueToTree(mockUserResponse);
    }

}
