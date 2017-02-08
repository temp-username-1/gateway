package me.giannists.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import me.giannists.connections.TypicodeCallService;
import me.giannists.helpers.JsonHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        when(typicodeCallService.getUserById(anyInt()))
                .thenReturn(JsonHelper.getMockUserById());
        when(typicodeCallService.getPostsByUserId(anyInt()))
                .thenReturn(JsonHelper.getMockPostsByUserId());

        // when
        ObjectNode mergedResult = userService.getUserEnriched(2);

        // then
        assertNotNull(mergedResult.get("posts"));
        assertEquals(mergedResult.get("posts"), JsonHelper.getMockPostsByUserId());
        assertIncludes(mergedResult, JsonHelper.getMockUserById());
    }

    // Method that asserts that all object b's values and attributes are
    // included in object a
    private void assertIncludes(ObjectNode a, ObjectNode b) {
        b.fieldNames().forEachRemaining(
                fieldName -> assertEquals(a.get(fieldName), b.get(fieldName))
        );
    }
}
