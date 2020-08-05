package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {
    public static List<User> users = new ArrayList<>();
    public static List<String> userNames = new ArrayList<>();

    @PostMapping("/user")
    public ResponseEntity register(@RequestBody @Validated User user) {
        String userName = user.getName();
        if (!userNames.contains(userName)) {
            userNames.add(userName);
            users.add(user);
        }
        users.add(user);
        Integer index = users.size();
        return ResponseEntity.created(null).header("index", index.toString()).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }
}
