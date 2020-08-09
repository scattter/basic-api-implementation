package com.thoughtworks.rslist.api;

<<<<<<< HEAD
<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
>>>>>>> jpa-2
import com.thoughtworks.rslist.entity.RsEventEntity;
import com.thoughtworks.rslist.entity.UserEntity;
import com.thoughtworks.rslist.repository.RsEventRepository;
import com.thoughtworks.rslist.repository.UserRepository;
<<<<<<< HEAD
import org.junit.jupiter.api.AfterEach;
>>>>>>> jpa-2
=======
import com.thoughtworks.rslist.service.RsService;
>>>>>>> jpa-2
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
<<<<<<< HEAD
<<<<<<< HEAD
import org.springframework.test.web.servlet.MockMvcBuilder;
=======
>>>>>>> jpa-2
=======
>>>>>>> jpa-2
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;
<<<<<<< HEAD
<<<<<<< HEAD
=======
import java.util.List;
>>>>>>> jpa-2
=======
import java.util.List;
>>>>>>> jpa-2

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

<<<<<<< HEAD
<<<<<<< HEAD
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
=======
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

>>>>>>> jpa-2
=======
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

>>>>>>> jpa-2
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {

    @Autowired
    MockMvc mockMvc;
<<<<<<< HEAD
<<<<<<< HEAD

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
=======
=======
>>>>>>> jpa-2
    @Autowired
    RsEventRepository rsEventRepository;
    @Autowired
    UserRepository userRepository;
<<<<<<< HEAD

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController(rsEventRepository, userRepository)).build();
=======
    @Autowired
    RsService rsService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController(rsEventRepository, userRepository, rsService)).build();
>>>>>>> jpa-2
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

<<<<<<< HEAD
>>>>>>> jpa-2
=======
>>>>>>> jpa-2
    }

    @Test
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/1"))
<<<<<<< HEAD
<<<<<<< HEAD
                .andExpect(jsonPath("$.eventName", is("第一个事件")))
                .andExpect(jsonPath("$.eventKeyword", is("无分类")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/2"))
                .andExpect(jsonPath("$.eventName", is("第二个事件")))
                .andExpect(jsonPath("$.eventKeyword", is("无分类")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/3"))
                .andExpect(jsonPath("$.eventName", is("第三个事件")))
                .andExpect(jsonPath("$.eventKeyword", is("无分类")))
=======
=======
>>>>>>> jpa-2
                .andExpect(jsonPath("$.eventName", is("小热搜")))
                .andExpect(jsonPath("$.eventKeyword", is("小分类")))
                .andExpect(jsonPath("$.eventId", is(1)))
                .andExpect(jsonPath("$.voteNum", is(0)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/2"))
                .andExpect(jsonPath("$.eventName", is("中热搜")))
                .andExpect(jsonPath("$.eventKeyword", is("中分类")))
                .andExpect(jsonPath("$.eventId", is(2)))
                .andExpect(jsonPath("$.voteNum", is(0)))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/3"))
                .andExpect(jsonPath("$.eventName", is("大热搜")))
                .andExpect(jsonPath("$.eventKeyword", is("大分类")))
                .andExpect(jsonPath("$.eventId", is(3)))
                .andExpect(jsonPath("$.voteNum", is(0)))
<<<<<<< HEAD
>>>>>>> jpa-2
=======
>>>>>>> jpa-2
                .andExpect(status().isOk());
    }

    @Test
<<<<<<< HEAD
<<<<<<< HEAD
    void shouldGetRsEventBetween() throws Exception {
        mockMvc.perform(get("/rs/list?start=1&end=2"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=2&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/rs/list?start=1&end=3"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
=======
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
>>>>>>> jpa-2
                .andExpect(status().isOk());
    }

    @Test
<<<<<<< HEAD
    void shouldAddRsEventIfNormaliz() throws Exception {
        //String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"user\":" + "{\"name\":\"my\",\"gender\":\"male\",\"age\":\"28\",\"email\":\"a@b.com\",\"phone\":\"166666666666\"}}";
        User user = new User("my", "male", 28, "a@b.com", "16666666666");
        RsEvent rsEvent = new RsEvent("第四个事件", "事件类型已更改", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[0]", not(hasKey("eventUser"))))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1]", not(hasKey("eventUser"))))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2]", not(hasKey("eventUser"))))
                .andExpect(jsonPath("$[3].eventName", is("第四个事件")))
                .andExpect(jsonPath("$[3].eventKeyword", is("事件类型已更改")))
                .andExpect(jsonPath("$[3]", not(hasKey("eventUser"))))
                .andExpect(status().isOk());
    }

    @Test
    void shouldModifyRsEventName() throws Exception {
        RsEvent rsEvent = new RsEvent("事件名称更改", null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/list/modifyName/1").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("事件名称更改")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldModifyRsEventKeyword() throws Exception {
        RsEvent rsEvent = new RsEvent(null, "事件类型更改", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/list/modifyKeyword/1").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("事件类型更改")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldModifyRsEventNameAndKeyword() throws Exception {
        RsEvent rsEvent = new RsEvent("事件名称更改", "事件类型更改", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/list/modify/1").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("事件名称更改")))
                .andExpect(jsonPath("$[0].eventKeyword", is("事件类型更改")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteOneRsevent() throws Exception {
        mockMvc.perform(delete("/rs/list/delete/1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
=======
    void shouldAddRsEventWhenUserExists() throws Exception {
        String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"userId\":\"1\"}";
        mockMvc.perform(post("/rs/event").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(5, rsEventEntity.size());
        assertEquals("小热搜", rsEventEntity.get(0).getEventName());
    }

    @Test
    void shouldNotAddRsEventWhenUserNotExists() throws Exception {
        String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"userId\":100}";
        mockMvc.perform(post("/rs/event").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
=======
    void shouldAddRsEventWhenUserExists() throws Exception {
        String requestJson = "{\"eventName\":\"第四个事件\",\"eventKeyword\":\"添加事件\",\"userId\":\"1\"}";
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
>>>>>>> jpa-2
                .andExpect(status().isBadRequest());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
    }

    @Test
    void shouldUpdateRsEventWhenUserIdCampareToEventId() throws Exception {
        String requestJson = "{\"eventName\":\"新的热搜事件名称\",\"eventKeyword\":\"新的关键字\",\"userId\":\"1\"}";
        mockMvc.perform(post("/rs/2/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals("新的热搜事件名称", rsEventEntity.get(1).getEventName());//
    }

    @Test
<<<<<<< HEAD
        // 传入的rsEventId是错误的
    void shouldNotUpdateRsEventWhenUserIdNotCampareToEventId() throws Exception {
        String requestJson = "{\"eventName\":\"新的热搜事件名称\",\"eventKeyword\":\"新的关键字\",\"userId\":\"2\"}";
        mockMvc.perform(post("/rs/5/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
=======
>>>>>>> jpa-2
    void shouldUpdateRsEventNameWhenRsEventKeywordNotExists() throws Exception {
        String requestJson = "{\"eventName\":\"新的热搜事件名称2\",\"eventKeyword\":\"\",\"userId\":\"1\"}";
        mockMvc.perform(post("/rs/2/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals("新的热搜事件名称2", rsEventEntity.get(1).getEventName());
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
<<<<<<< HEAD
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


=======
        // 传入的rsEventId是错误的
    void shouldNotUpdateRsEventWhenUserIdNotCampareToEventId() throws Exception {
        String requestJson = "{\"eventName\":\"新的热搜事件名称\",\"eventKeyword\":\"新的关键字\",\"userId\":\"2\"}";
        mockMvc.perform(post("/rs/5/update").content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

>>>>>>> jpa-2
    @Test
    void shouldDeleteOneRsevent() throws Exception {
        mockMvc.perform(delete("/rs/list/1/delete"))
                .andExpect(status().isOk());
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(3, rsEventEntity.size());
<<<<<<< HEAD
>>>>>>> jpa-2
    }

    @Test
    void shouldNotAddRsEventWhenEventNameIsNull() throws Exception {
<<<<<<< HEAD
        User user = new User("my", "male", 28, "a@b.com", "16666666666");
        RsEvent rsEvent = new RsEvent(null, "添加事件", user);
=======
        RsEvent rsEvent = new RsEvent(null, "添加事件", 2);
>>>>>>> jpa-2
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
<<<<<<< HEAD

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
=======
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
>>>>>>> jpa-2
    }

    @Test
    void shouldNotAddRsEventWhenEventKeywordIsNull() throws Exception {
<<<<<<< HEAD
        User user = new User("my", "male", 28, "a@b.com", "16666666666");
        RsEvent rsEvent = new RsEvent("第四个事件", null, user);
=======
        RsEvent rsEvent = new RsEvent("第四个事件", null, 2);
>>>>>>> jpa-2
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
<<<<<<< HEAD

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
=======
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
>>>>>>> jpa-2
    }

    @Test
    void shouldNotAddRsEventWhenEventUserIsNull() throws Exception {
<<<<<<< HEAD

=======
>>>>>>> jpa-2
        RsEvent rsEvent = new RsEvent("第四个事件", "事件类型更改", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
<<<<<<< HEAD

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
=======
        List<RsEventEntity> rsEventEntity = rsEventRepository.findAll();
        assertEquals(4, rsEventEntity.size());
>>>>>>> jpa-2
=======
>>>>>>> jpa-2
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
<<<<<<< HEAD
<<<<<<< HEAD
        User user = new User("my", "male", 128, "a@b.com", "16666666666");
        RsEvent rsEvent = new RsEvent("测试事件", "添加事件", user);
=======
        RsEvent rsEvent = new RsEvent("测试事件", "添加事件", 2);
>>>>>>> jpa-2
=======
        RsEvent rsEvent = new RsEvent("测试事件", "添加事件", 2);
>>>>>>> jpa-2
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error", is("invalid param")));
    }
}