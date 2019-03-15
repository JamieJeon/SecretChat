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
    @RequestMapping(value="/socket", method=RequestMethod.GET)
    public String socket(String test){

        return "socket/socket_chat";
    }
/*
    @SuppressWarnings("unused")
    @RequestMapping(value = "socket_count", method = RequestMethod.GET)
    @ResponseBody
    public ResponseOverlays socket_count(){

        int result = MessageHandler.getUsers().size();

        if(result != 0){
            return new ResponseOverlays<>(HttpServletResponse.SC_OK, null,result);
        }
        return new ResponseOverlays<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null,null);
    }

    @SuppressWarnings("unused")
    @RequestMapping(value = "socket_push", method = RequestMethod.POST)
    @ResponseBody
    public ResponseOverlays socket_push(@RequestBody MESSAGE message, HttpServletRequest request){

        String jsondata = "";
        jsondata += "{";
        jsondata += "\"type\" : \""+message.getTYPE()+"\",";
        jsondata += "\"id\" : \""+message.getID()+"\",";
        jsondata += "\"ip\" : \""+ GetUserInfo.getIp(request)+"\",";
        jsondata += "\"message\" : \""+message.getMESSAGE()+"\"";
        jsondata += "}";

        Map<String, WebSocketSession> result = MessageHandler.getUsers();

        if(result.size() != 0){
            for (WebSocketSession s : result.values()) {
                try {
                    s.sendMessage(new TextMessage(jsondata.getBytes()));
                    logger.info(s.getId() + "에 json 발송: " + jsondata);
                } catch(Exception e){
                    return new ResponseOverlays<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null,"IOException");
                }
            }
            return new ResponseOverlays<>(HttpServletResponse.SC_OK, null,null);
        }
        return new ResponseOverlays<>(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, null,null);
    }*/
}
