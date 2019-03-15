package com.jamie.sample.SOCKET.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseOverlays<T> {

    @SuppressWarnings({"unused", "WeakerAccess"}) @JsonProperty("CODE") public int CODE;
    @SuppressWarnings({"unused", "WeakerAccess"}) @JsonProperty("IDEA") public String MESG;
    @SuppressWarnings({"unused", "WeakerAccess"}) @JsonProperty("BODY") public T BODY;

    public ResponseOverlays(int CODE) {

        this.CODE = CODE;
    }

    public ResponseOverlays(int CODE, T BODY) {

        this.CODE = CODE;
        this.BODY = BODY;
    }

    public ResponseOverlays(int CODE, String MESG) {

        this.CODE = CODE;
        this.MESG = MESG;
    }

    public ResponseOverlays(int CODE, String MESG, T BODY) {

        this.CODE = CODE;
        this.MESG = MESG;
        this.BODY = BODY;
    }
}
