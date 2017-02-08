package me.giannists.rest;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import me.giannists.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval")
    })
    @RequestMapping(path = "{id}/data", method = RequestMethod.GET)
    public ResponseEntity findUserdataByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserEnriched(id));
    }
}
