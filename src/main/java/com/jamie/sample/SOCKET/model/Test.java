package com.jamie.sample.SOCKET.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
    @JsonProperty("type")
    private String type;
    @JsonProperty("id")
    private String id;
    @JsonProperty("msg")
    private String msg;

    @SuppressWarnings("unused")
    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public void setType(String type) {
        this.type = type;
    }

    @SuppressWarnings("unused")
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(String id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @SuppressWarnings("unused")
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
