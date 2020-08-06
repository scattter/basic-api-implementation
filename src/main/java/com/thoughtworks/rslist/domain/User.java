package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.*;

@Data
@RestController
//@JsonView(RsEvent.PrivateView.class)
//@NoArgsConstructor
public class User {

    public User(@Size(max = 8) @NotNull String name, @NotNull String gender, @Max(100) @Min(18) @NotNull Integer age, @Email String email, @Pattern(regexp = "1\\d{10}") String phone) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }


    @Size(max = 8)
    @NotNull
    private String name;

    @NotNull
    private String gender;

    @Max(100)
    @Min(18)
    @NotNull
    private Integer age;

    @Email
    private String email;

    @Pattern(regexp = "1\\d{10}")
    private String phone;

    private int vote = 10;

    public User() {

    }
}
