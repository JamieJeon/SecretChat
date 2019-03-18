package com.jamie.sample.SOCKET.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamie.sample.SOCKET.model.MESSAGE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jeonjaebum on 2017. 4. 25..
 * servlet-context.xml에 빈과 웹소켓을 설정했는지 확인
 * Chatting 을 위한 MessageHandler
 */
public class MessageHandler extends TextWebSocketHandler {

    private final static ObjectMapper JSON = new ObjectMapper();
    //observers
    private static Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();
    private static Map<String, String> ids = new ConcurrentHashMap<String, String>();

    /**
     * 클라이언트 연결 이후에 실행되는 메소드
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        users.put(session.getId(), session);
    }

    /**
     * 클라이언트가 웹소켓서버로 메시지를 전송했을 때 실행되는 메소드
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        MESSAGE data = JSON.readValue(
                message.getPayload(), MESSAGE.class
        ); //MESSAGE에 맵핑

        if("on".equals(data.getTYPE())){
            ids.put(session.getId(), data.getID());
        }

        for (WebSocketSession s : users.values()) {
            s.sendMessage(message);
        }
    }

    /**
     * 클라이언트가 연결을 끊었을 때 실행되는 메소드
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        users.remove(session.getId());

        TextMessage message = new TextMessage("{\"type\":\"off\",\"id\":\""+ids.get(session.getId())+"\",\"message\":\"채팅방에 나가셨습니다.\"}");

        for (WebSocketSession s : users.values()) {
            s.sendMessage(message);
        }

        ids.remove(session.getId());
    }

    /**
     * Exception 발생
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        TextMessage error = new TextMessage("Throws Exception!!!".getBytes());
        session.sendMessage(error);
    }

    public static Map<String, WebSocketSession> getUsers() {
        return users;
    }

    public static Map<String, String> getIds() {
        return ids;
    }
}
