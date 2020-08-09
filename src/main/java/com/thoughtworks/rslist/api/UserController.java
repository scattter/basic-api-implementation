package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
public class UserController {

    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;
    private final UserService userService;

    public UserController(UserRepository userRepository, RsEventRepository rsEventRepository, UserService userService) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
        this.userService = userService;
    }

    @PostMapping("/user")
    public ResponseEntity register(@RequestBody(required = false) @Valid User user) {
        userService.register(user);
        return ResponseEntity.status(HttpStatus.OK).header("index", "ok").build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        List<UserEntity> result = userService.getUsers();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/user/{id}/delete")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException error) {
        CommenError commenError = new CommenError();
        commenError.setError("invalid user");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commenError);
    }
}
