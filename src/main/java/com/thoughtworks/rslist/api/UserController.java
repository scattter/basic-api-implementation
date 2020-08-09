package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
public class UserController {
    public static List<User> users = new ArrayList<>();
    public static List<String> userNames = new ArrayList<>();


    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;

    public UserController(UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    @PostMapping("/user")
    public ResponseEntity register(@RequestBody(required = false) @Valid User user) {
        UserEntity userEntity = UserEntity.builder()
                .name(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .email(user.getEmail())
                .phone(user.getPhone())
                .vote(10)
                .build();
        userRepository.save(userEntity);
        return ResponseEntity.status(HttpStatus.OK).header("index", "ok").build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException error) {
        CommenError commenError = new CommenError();
        commenError.setError("invalid user");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commenError);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
