package com.jamie.sample.SOCKET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by jeonjaebum on 2017. 4. 25..
 */

@Controller
public class SocketController {

    private Logger logger = LoggerFactory.getLogger(SocketController.class);

    /**
     * chatting
     *
     * */
    @RequestMapping(value="/mqnic", method=RequestMethod.GET)
    public String socket(String test){

        return "socket/socket_chat";
    }
}
