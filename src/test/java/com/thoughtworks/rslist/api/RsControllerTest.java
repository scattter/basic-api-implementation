package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.service.RsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RsService rsService;

    UserEntity userEntity;

    @BeforeEach
    void setup() {
        rsEventRepository.deleteAll();
        userRepository.deleteAll();
        userEntity = userRepository.save(UserEntity.builder().age(20).name("小红").gender("male")
                .email("1@a.com").phone("13423433411").vote(10).build());
        UserEntity userEntity1 = userRepository.save(UserEntity.builder().age(20).name("小张").gender("male")
                .email("1@a.com").phone("13423433411").vote(10).build());
        UserEntity userEntity2 = userRepository.save(UserEntity.builder().age(21).name("小王").gender("male")
                .email("2@a.com").phone("13423433412").vote(10).build());
        UserEntity userEntity3 = userRepository.save(UserEntity.builder().age(22).name("小李").gender("male")
                .email("3@a.com").phone("13423433413").vote(10).build());
        List<UserEntity> users = new ArrayList<>();
        users.add(userEntity);
        users.add(userEntity1);
        users.add(userEntity2);
        users.add(userEntity3);
        String[] eventName = {"小热搜", "中热搜", "大热搜", "超大热搜"};
        String[] eventKeyword = {"小分类", "中分类", "大分类", "超大分类"};
        for (int i = 0; i < 4; i++) {
            rsEventRepository.save(RsEventEntity.builder()
                    .eventName(eventName[i])
                    .eventKeyword(eventKeyword[i])
                    .userEntity(users.get(i))
                    .voteNum(0)
                    .build());
        }

    }

    @Test
    void shouldGetOneRsEvent() throws Exception {
        int a,b,c;
        if (userEntity.getId()!=1){
            a = 1;
            b = 2;
            c = 3;
        }else {
            a = 0;
            b = 1;
            c = 2;
        }
        String uri = String.valueOf(userEntity.getId()+a);
        String uri2 = String.valueOf(userEntity.getId()+b);
        String uri3 = String.valueOf(userEntity.getId()+c);
        mockMvc.perform(get("/rs/"+uri))
                .andExpect(jsonPath("$.eventName", is("小热搜")))
                .andExpect(jsonPath("$.eventKeyword", is("小分类")))
                .andExpect(jsonPath("$.eventId", is(userEntity.getId()+a)))
                .andExpect(jsonPath("$.voteNum", is(0)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/"+uri2))
                .andExpect(jsonPath("$.eventName", is("中热搜")))
                .andExpect(jsonPath("$.eventKeyword", is("中分类")))
                .andExpect(jsonPath("$.eventId", is(userEntity.getId()+b)))
                .andExpect(jsonPath("$.voteNum", is(0)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/"+uri3))
                .andExpect(jsonPath("$.eventName", is("大热搜")))
                .andExpect(jsonPath("$.eventKeyword", is("大分类")))
                .andExpect(jsonPath("$.eventId", is(userEntity.getId()+c)))
                .andExpect(jsonPath("$.voteNum", is(0)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldGetRsEventBetween() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName", is("小热搜")))
                .andExpect(jsonPath("$[0].eventKeyword", is("小分类")))
                .andExpect(jsonPath("$[1].eventName", is("中热搜")))
                .andExpect(jsonPath("$[1].eventKeyword", is("中分类")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=2&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("中热搜")))
                .andExpect(jsonPath("$[0].eventKeyword", is("中分类")))
                .andExpect(jsonPath("$[1].eventName", is("大热搜")))
                .andExpect(jsonPath("$[1].eventKeyword", is("大分类")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("小热搜")))
                .andExpect(jsonPath("$[0].eventKeyword", is("小分类")))
                .andExpect(jsonPath("$[1].eventName", is("中热搜")))
                .andExpect(jsonPath("$[1].eventKeyword", is("中分类")))
                .andExpect(jsonPath("$[2].eventName", is("大热搜")))
                .andExpect(jsonPath("$[2].eventKeyword", is("大分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldAddRsEventWhenUserExists() throws Exception {
        String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"userId\":" + userEntity.getId() + "}";
        mockMvc.perform(post("/rs/event").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(5, rsEventEntity.size());
        assertEquals("小热搜", rsEventEntity.get(0).getEventName());
    }

    @Test
    void shouldNotAddRsEventWhenUserIdInvalid() throws Exception {
        String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"userId\":100}";
        mockMvc.perform(post("/rs/event").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
    }

    @Test
    void shouldNotAddRsEventWhenEventNameIsNull() throws Exception {
        RsEvent rsEvent = new RsEvent(null, "添加事件", 2);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);
        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
    }

    @Test
    void shouldNotAddRsEventWhenEventKeywordIsNull() throws Exception {
        RsEvent rsEvent = new RsEvent("第四个事件", null, 2);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
    }

    @Test
    void shouldNotAddRsEventWhenEventUserIdIsNull() throws Exception {
        RsEvent rsEvent = new RsEvent("第四个事件", "事件类型更改", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
    }

    @Test
    void shouldUpdateRsEventWhenUserIdCampareToEventId() throws Exception {
        String requestJson = "{\"eventName\":\"新的热搜事件名称\",\"eventKeyword\":\"新的关键字\",\"userId\":"+ userEntity.getId() +"}";
        String uri = String.valueOf(userEntity.getId()+1);
        //"/rs/"+uri+"/update"
        mockMvc.perform(post("/rs/"+uri+"/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals("新的热搜事件名称", rsEventEntity.get(0).getEventName());//
    }

    @Test
    void shouldUpdateRsEventNameWhenRsEventKeywordNotExists() throws Exception {
        System.out.println(userEntity.getId());
        String requestJson = "{\"eventName\":\"新的热搜事件名称2\",\"eventKeyword\":\"\",\"userId\":" + userEntity.getId() + "}";
        String uri = String.valueOf(userEntity.getId()+1);
        mockMvc.perform(post("/rs/"+uri+"/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals("新的热搜事件名称2", rsEventEntity.get(0).getEventName());
    }

    @Test
    void shouldUpdateRsEventKeywordWhenRsEventNameNotExists() throws Exception {
        String requestJson = "{\"eventName\":\"\",\"eventKeyword\":\"新的热搜类型\",\"userId\":\"1\"}";
        System.out.println(requestJson);
        mockMvc.perform(post("/rs/2/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals("新的热搜类型", rsEventEntity.get(1).getEventKeyword());
    }

    @Test
        // 传入的rsEventId是错误的
    void shouldNotUpdateRsEventWhenUserIdNotCampareToEventId() throws Exception {
        String requestJson = "{\"eventName\":\"新的热搜事件名称\",\"eventKeyword\":\"新的关键字\",\"userId\":\"2\"}";
        mockMvc.perform(post("/rs/5/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteOneRsevent() throws Exception {
        System.out.println("/rs/list/" + userEntity.getId() + "/delete");
        String uri = String.valueOf(userEntity.getId()+1);
        mockMvc.perform(delete("/rs/list/" + uri + "/delete"))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(3, rsEventEntity.size());
    }

    @Test
    void shouldReturnBadRequestWhenIndexOutOfBound() throws Exception {
        mockMvc.perform(get("/rs/10"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid index")));
    }

    @Test
    void shouldReturnBadRequestWhenRequestParamOutOfBound() throws Exception {
        mockMvc.perform(get("/rs/list?start=0&end=2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid request param")));
    }

    @Test
    void shouldReturnInvalidParamException() throws Exception {
        RsEvent rsEvent = new RsEvent("测试事件", "添加事件", 2);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
    }
}