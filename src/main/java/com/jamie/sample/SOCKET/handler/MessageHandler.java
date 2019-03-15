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
    private Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    //observers
    private static Map<String, WebSocketSession> users = new ConcurrentHashMap<String, WebSocketSession>();

    /**
     * 클라이언트 연결 이후에 실행되는 메소드
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info(session.getId() + " 연결 됨!!");
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

        logger.info(session.getId() + "로부터 json 수신: " + message.getPayload());
        for (WebSocketSession s : users.values()) {
            s.sendMessage(message);
            logger.info(s.getId() + "에 json 발송: " + message.getPayload());
        }
    }

    /**
     * 클라이언트가 연결을 끊었을 때 실행되는 메소드
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info(session.getId() + " 연결 종료됨");
        users.remove(session.getId());
    }

    /**
     * Exception 발생
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        TextMessage error = new TextMessage("Throws Exception!!!".getBytes());
        session.sendMessage(error);

        logger.info(session.getId() + " 익셉션 발생: " + exception.getMessage());
    }

    public static Map<String, WebSocketSession> getUsers() {
        return users;
    }
}
