package com.thoughtworks.rslist.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

//@NoArgsConstructor
//@AllArgsConstructor
public class RsEvent implements Serializable {

    public interface PublicView {
    }

    public interface PrivateView extends PublicView {
    }

    @NotNull
    //@JsonView(PublicView.class)
    private String eventName;

    @NotNull
    //@JsonView(PublicView.class)
    private String eventKeyword;

    @NotNull
    //@JsonView(PrivateView.class)
    private User eventUser;

    @JsonIgnore
    public User geteventUser() {
        return eventUser;
    }

    @JsonProperty
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
