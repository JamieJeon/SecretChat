package com.jamie.sample.SOCKET.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by jeonjaebum on 2017. 4. 28..
 */
public class MESSAGE {

    private String TYPE;
    private String ID;
    private String IP;
    private String MESSAGE;

    @SuppressWarnings("unused")
    @JsonProperty("type")
    public String getTYPE() {
        return TYPE;
    }

    @SuppressWarnings("unused")
    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    @SuppressWarnings("unused")
    @JsonProperty("message")
    public String getMESSAGE() {
        return MESSAGE;
    }

    @SuppressWarnings("unused")
    public void setMESSAGE(String MESSAGE) {
        this.MESSAGE = MESSAGE;
    }

    @SuppressWarnings("unused")
    @JsonProperty("id")
    public String getID() {
        return ID;
    }

    @SuppressWarnings("unused")
    public void setID(String ID) {
        this.ID = ID;
    }

    @SuppressWarnings("unused")
    @JsonProperty("ip")
    public String getIP() {
        return IP;
    }

    @SuppressWarnings("unused")
    public void setIP(String IP) {
        this.IP = IP;
    }
}
