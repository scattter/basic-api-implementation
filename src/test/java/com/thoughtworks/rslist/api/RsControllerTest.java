package com.thoughtworks.rslist.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.rslist.domain.RsEvent;
import com.thoughtworks.rslist.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class RsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new RsController()).build();
    }

    @Test
    void shouldGetOneRsEvent() throws Exception {
        mockMvc.perform(get("/rs/1"))
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
                .andExpect(status().isOk());
    }

    @Test
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
                .andExpect(status().isOk());
    }

    @Test
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
    }

    @Test
    void shouldNotAddRsEventWhenEventNameIsNull() throws Exception {
        User user = new User("my", "male", 28, "a@b.com", "16666666666");
        RsEvent rsEvent = new RsEvent(null, "添加事件", user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotAddRsEventWhenEventKeywordIsNull() throws Exception {
        User user = new User("my", "male", 28, "a@b.com", "16666666666");
        RsEvent rsEvent = new RsEvent("第四个事件", null, user);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotAddRsEventWhenEventUserIsNull() throws Exception {

        RsEvent rsEvent = new RsEvent("第四个事件", "事件类型更改", null);
        ObjectMapper objectMapper = new ObjectMapper();
        String requestJson = objectMapper.writeValueAsString(rsEvent);

        mockMvc.perform(post("/rs/event")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/rs/list"))
                .andExpect(jsonPath("$[0].eventName", is("第一个事件")))
                .andExpect(jsonPath("$[0].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[1].eventName", is("第二个事件")))
                .andExpect(jsonPath("$[1].eventKeyword", is("无分类")))
                .andExpect(jsonPath("$[2].eventName", is("第三个事件")))
                .andExpect(jsonPath("$[2].eventKeyword", is("无分类")))
                .andExpect(status().isOk());
    }
}