package com.thoughtworks.rslist.api;


import com.thoughtworks.rslist.domain.User;
<<<<<<< HEAD
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.exception.InvalidRequestParamException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
=======
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.exception.CommenError;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
>>>>>>> jpa-2
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> jpa-2


@RestController
public class UserController {
    public static List<User> users = new ArrayList<>();
    public static List<String> userNames = new ArrayList<>();

<<<<<<< HEAD
    @PostMapping("/user")
    public ResponseEntity register(@RequestBody @Validated User user) {
        String userName = user.getName();
        if (!userNames.contains(userName)) {
            userNames.add(userName);
            users.add(user);
        }
        Integer index = users.size();
        return ResponseEntity.created(null).header("index", index.toString()).build();
=======

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
>>>>>>> jpa-2
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(users);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
<<<<<<< HEAD
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException error){
=======
    public ResponseEntity exceptionHandler(MethodArgumentNotValidException error) {
>>>>>>> jpa-2
        CommenError commenError = new CommenError();
        commenError.setError("invalid user");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(commenError);
    }
<<<<<<< HEAD
=======

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
>>>>>>> jpa-2
}
