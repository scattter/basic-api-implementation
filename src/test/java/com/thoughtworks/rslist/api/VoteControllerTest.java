package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.Vote;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.entity.VoteEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VoteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;

    UserEntity userEntity;
    RsEventEntity rsEventEntity;

    @BeforeEach
    void setUp() {
        userEntity = UserEntity.builder().name("idolice").age(19).email("a@b.com").gender("female")
                .phone("18888888888").vote(10).build();
        userEntity = userRepository.save(userEntity);
        rsEventEntity = RsEventEntity.builder()
                .userEntity(userEntity)
                .eventName("event name")
                .eventKeyword("keyword")
                .voteNum(0)
                .build();
        rsEventEntity = rsEventRepository.save(rsEventEntity);
    }

    @Test
    void shouldSuccessVote() throws Exception {
        Vote vote = new Vote(1, null, 6);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/1/vote").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
        // 当request信息中的用户ID不存在时存入失败
    void shouldFailWhenUserIdNotValid() throws Exception {
        // String requestJson = "{\"userId\":\"1\", \"rsEventId\":\"2\",\"time\":\"\",\"voteNum\":\"8\"}";
        Vote vote = new Vote(1, null, 6);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/2/vote").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
        // 当request信息中投票的票数超过用户剩余票数时失败
    void shouldFailWhenVoteNumNotValid() throws Exception {
        // String requestJson = "{\"userId\":\"1\", \"rsEventId\":\"2\",\"time\":\"\",\"voteNum\":\"8\"}";
        Vote vote = new Vote(1, null, 16);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(vote);
        mockMvc.perform(post("/rs/1/vote").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}