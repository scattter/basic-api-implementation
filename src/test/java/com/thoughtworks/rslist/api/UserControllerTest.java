package com.thoughtworks.rslist.api;

<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
<<<<<<< HEAD
=======
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
>>>>>>> jpa-2
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
>>>>>>> jpa-2
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

<<<<<<< HEAD
import static org.hamcrest.Matchers.*;

import javax.validation.constraints.Null;

<<<<<<< HEAD
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
=======
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
>>>>>>> jpa-2
=======
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
>>>>>>> jpa-2
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

<<<<<<< HEAD
    @Autowired
    MockMvc mockMvc;

<<<<<<< HEAD
    @BeforeEach
    void setup() {
        UserController.users.clear();
    }

    @Test
    void shouldRegisterUser() throws Exception {
        User user = new User("adasdad", "male", 18, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertEquals(1, UserController.users.size());
    }
=======
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsEventRepository rsEventRepository;

    @BeforeEach
    void setup() {
        UserController.users.clear();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
    }

>>>>>>> jpa-2
=======
    private final MockMvc mockMvc;
    private final UserRepository userRepository;
    private final RsEventRepository rsEventRepository;

    UserControllerTest(MockMvc mockMvc, UserRepository userRepository, RsEventRepository rsEventRepository) {
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        this.rsEventRepository = rsEventRepository;
    }

    @BeforeEach
    void setup() {
        //UserController.users.clear();
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        UserEntity userEntity = userRepository.save(UserEntity.builder().age(20).name("小红").gender("male")
                .email("1@a.com").phone("13423433411").vote(10).build());
        UserEntity userEntity1 = userRepository.save(UserEntity.builder().age(20).name("小张").gender("male")
                .email("1@a.com").phone("13423433411").vote(10).build());
        UserEntity userEntity2 = userRepository.save(UserEntity.builder().age(21).name("小王").gender("male")
                .email("2@a.com").phone("13423433412").vote(10).build());
        UserEntity userEntity3 = userRepository.save(UserEntity.builder().age(22).name("小李").gender("male")
                .email("3@a.com").phone("13423433413").vote(10).build());
        userRepository.save(userEntity);
        userRepository.save(userEntity1);
        userRepository.save(userEntity2);
        userRepository.save(userEntity3);
    }

>>>>>>> jpa-2

    @Test
    void nameShouldLessThan8() throws Exception {
        User user = new User("adasdadsa", "male", 18, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void nameShouldNotNull() throws Exception {
        User user = new User(null, "male", 18, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void genderShouldNotNull() throws Exception {
        User user = new User("adasdad", null, 18, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldNotNull() throws Exception {
        User user = new User("adasdad", "male", null, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldMoreThan18() throws Exception {
        User user = new User("adasdad", "male", 17, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void ageShouldLessThan100() throws Exception {
        User user = new User("adasdad", "male", 101, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void emailShouldNormalize() throws Exception {
        User user = new User("adasdad", "male", 18, "1th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void phoneShouldCount10() throws Exception {
        User user = new User("adasdad", "male", 18, "1@th.com", "1123456789");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
<<<<<<< HEAD
<<<<<<< HEAD
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
=======
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
>>>>>>> jpa-2
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
=======
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

>>>>>>> jpa-2

    @Test
    void shouldReturnInvalidUserException() throws Exception {
        User user = new User("adasdad", "male", 118, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", org.hamcrest.Matchers.is("invalid user")));
    }
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> jpa-2

    @Test
    void shouldRegister() throws Exception {
        User user = new User("adasdad", "male", 18, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user")
                .content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
<<<<<<< HEAD
        assertEquals(1,userRepository.findAll().size());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UserEntity userEntity_1 = userRepository.save(UserEntity.builder().age(20).name("小张").gender("male")
                .email("1@a.com").phone("13423433411").vote(10).build());
        RsEventEntity rsEventEntity = RsEventEntity.builder().eventName("kkkk")
                .eventKeyword("sdfsdfsdf").userEntity(userEntity_1).build();
        rsEventRepository.save(rsEventEntity);

        mockMvc.perform(delete("/user/delete/{id}",userEntity_1.getId()))
                .andExpect(status().isOk());
        assertEquals(0,rsEventRepository.findAll().size());
        assertEquals(0,userRepository.findAll().size());
    }
>>>>>>> jpa-2
=======
    }

    @Test
    void shouldGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(jsonPath("$[0].name", Matchers.is("小红")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/1/delete"))
                .andExpect(status().isOk());
    }
>>>>>>> jpa-2
}