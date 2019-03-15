<%--
  Created by IntelliJ IDEA.
  User: jeonjaebum
  Date: 2017. 4. 25.
  Time: 오후 2:44
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <title>Socket Home</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/socket/sockjs-1.0.3.min.js"></script>
    <script>
        var sock = null;
        var contextPath = '${pageContext.request.contextPath}';

        $(document).ready(function(){

            $("#sendWord").on("click", function(){
                if($("#word").val() == ''){
                    alert("Input the word!");
                } else {
                    message_send('chatting',$("#id").val(),$("#word").val());
                }
            });

            //
            $("#connect").on("click", function(){
                if($("#id").val() == ''){
                    alert("Input the ID!");
                } else {
                    sock = new SockJS(contextPath+"/chat");
                    sock.onopen = onOpen;
                    sock.onmessage = onMessage;
                    sock.onclose = onClose;

                    $("#disconn").hide();
                    $("#conn").show();
                }
            });

            $("#disconnect").on("click", function(){
                disconnect();

                $("#conn").hide();
                $("#disconn").show();
            });

        });

        function onOpen(evt) {
            $.ajax({
                type: "GET",
                url: "../REST/GET_USER_IP",
                data: {},
                dataType: "text",
                success: function (data) {
                    $("#id").val($("#id").val() + "(" + data + ")");
                    message_send('chatting',$("#id").val(),"채팅방에 들어오셨습니다.");
                },
                error: function (xhr, status, error) {
                    console.log(xhr + " " + status + " " + error);
                }
            });
        }
        function onClose(evt) {
            //socket이 종료되고 나서 실행됨
            $("#id").val('');
            $("#wordList").html('<h2>List Words</h2>');
        }

        function onMessage(evt) {
            var data = JSON.parse(evt.data);
            var id = data.id;
            var type = data.type;
            var message = data.message;
            console.log(type);
            if(type == 'alert'){
                alert(message)
            } else {
                appendMessage(id+" : "+message);
            }
        }
        //소켓 종료
        function disconnect() {
            message_send('chatting',$("#id").val(),"채팅방을 나가셨습니다.");
            sock.close();
        }

        function message_send(type, id, message) {
            var data = JSON.stringify({
                type : type,
                id : id,
                message : message
            });
            sock.send(data);
            $("#word").val('');
        }

        function appendMessage(msg) {
            $("#wordList").append('<p>'+msg+'</p>');
        }
    </script>
</head>
<body>
    <h1>
        Hello Socket!
    </h1>

    <div id="inputWord">
        <h2>Please Input the word!</h2>
        <div id="disconn">
            <p>ID <input type="text" name="id" id="id" /><input type="button" id="connect" value="Connect" /></p>
        </div>
        <div id="conn" style="display: none;">
            <p>Input <input type="text" name="word" id="word" /></p>
            <p><input type="button" id="sendWord" value="Send" /><input type="button" id="disconnect" value="Disconnect" /></p>
        </div>
    </div>

    <div id="wordList">
        <h2>List Words</h2>

    </div>
</body>