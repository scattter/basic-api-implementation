package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonView;
<<<<<<< HEAD
<<<<<<< HEAD
import lombok.Data;

import javax.validation.constraints.*;

@JsonView(RsEvent.PrivateView.class)
public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(String name, String gender, Integer age, String email, String phone) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }
=======
=======
>>>>>>> jpa-2
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.*;

@Data
@RestController
//@JsonView(RsEvent.PrivateView.class)
//@NoArgsConstructor
@AllArgsConstructor
public class User {

    public User(@Size(max = 8) @NotNull String name, @NotNull String gender, @Max(100) @Min(18) @NotNull Integer age, @Email String email, @Pattern(regexp = "1\\d{10}") String phone) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.phone = phone;
    }

    private Integer id;
<<<<<<< HEAD
>>>>>>> jpa-2
=======
>>>>>>> jpa-2

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
