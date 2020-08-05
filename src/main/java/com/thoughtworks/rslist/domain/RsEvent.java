package com.thoughtworks.rslist.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

public class RsEvent {

    @NotNull
    private String eventName;
    @NotNull
    private String eventKeyword;
    @NotNull
    private User eventUser;


    public User geteventUser() {
        return eventUser;
    }

    public void seteventUser(User eventUser) {
        this.eventUser = eventUser;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventKeyword() {
        return eventKeyword;
    }

    public void setEventKeyword(String eventKeyword) {
        this.eventKeyword = eventKeyword;
    }

    public RsEvent() {
    }

    public RsEvent(String eventName, String eventKeyword, User eventUser) {
        this.eventName = eventName;
        this.eventKeyword = eventKeyword;
        this.eventUser = eventUser;
    }
}
