package me.giannists.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import me.giannists.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path = "{id}/data", method = RequestMethod.GET)
    public ResponseEntity findUserdataByUserId(@PathVariable int id) {
        return ResponseEntity.ok(userService.getUserdata(id));
    }
}
