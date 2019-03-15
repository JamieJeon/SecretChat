package com.jamie.sample.SOCKET;

import com.jamie.sample.SOCKET.handler.MessageHandler;
import com.jamie.sample.SOCKET.model.Test;
import com.jamie.sample.UTIL.GetUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by jeonjaebum on 2018. 1. 4..
 */
@RestController
@RequestMapping("REST")
public class RESTController {

    //private static Logger log = LoggerFactory.getLogger(RESTController.class);

    @RequestMapping(value = "/GET_USER_IP", method = RequestMethod.GET)
    public ResponseEntity<String> GET_USER_IP(HttpServletRequest request) {
        try {
            String ip = GetUserInfo.getIp(request);
            return new ResponseEntity<>(ip, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unknown", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/SEND_MESSAGE", method = RequestMethod.POST)
    public ResponseEntity<String> SEND_MESSAGE(HttpServletRequest request, @RequestBody Test t) {
        try {
            TextMessage message = new TextMessage("{\"type\":\""+t.getType()+"\",\"id\":\"admin(0:0:0:0:0:0:0:1)\",\"message\":\""+t.getMsg()+"\"}");

            Map<String, WebSocketSession> users =  MessageHandler.getUsers();

            WebSocketSession s = users.get(t.getId());

            if(s != null){
                s.sendMessage(message);
            }

            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Unknown", HttpStatus.BAD_REQUEST);
        }
    }
}
