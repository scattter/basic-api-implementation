package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.User;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;

import javax.validation.constraints.Null;

import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

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
        mockMvc.perform(post("/user").content(userJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnInvalidUserException() throws Exception {
        User user = new User("adasdad", "male", 118, "1@th.com", "11234567890");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        mockMvc.perform(post("/user").content(userJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", org.hamcrest.Matchers.is("invalid user")));
    }

    @Test
    void shouldAddRsEventWhenUserExists() throws Exception {
        UserEntity userEntity = userRepository.save(UserEntity.builder().age(20).name("小张").gender("male")
                .email("1@a.com").phone("13423433411").vote(10).build());
        String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"userId\":" + userEntity.getId() + "}";
        mockMvc.perform(post("/rs/event").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(1, rsEventEntity.size());
        assertEquals("第四个事件", rsEventEntity.get(0).getEventName());
        assertEquals(userEntity.getId(), rsEventEntity.get(0).getUserId());
    }

    @Test
    void shouldNotAddRsEventWhenUserNotExists() throws Exception {
        String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"userId\":100}";
        mockMvc.perform(post("/rs/event").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UserEntity userEntity = userRepository.save(UserEntity.builder().age(20).name("小张").gender("male")
                .email("1@a.com").phone("13423433411").vote(10).build());
        RsEventEntity rsEventEntity = RsEventEntity.builder().eventName("kkkk")
                .eventKeyword("sdfsdfsdf").userId(userEntity.getId( )).build();
        rsEventRepository.save(rsEventEntity);

        mockMvc.perform(delete("/user/{id}",userEntity.getId()))
                .andExpect(status().isOk());
        assertEquals(0,rsEventRepository.findAll().size());
        assertEquals(0,userRepository.findAll().size());
    }
}