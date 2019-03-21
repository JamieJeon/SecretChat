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
    <style>
        #inputWord {
            float: left;
            width:30%;
            height: 100%;
        }

        #wordList {
            overflow:scroll;
            float: left;
            width: 70%;
            height: 100%;
        }

        input[type=button]{
            margin: 5px;
        }

        #user_list {
            overflow:scroll;
            width: 200px;
            height: 300px;
            background-color: aliceblue;
            padding: 5px;
            margin: 10px;
        }

        .file_send_image {
            width: 100px;
            height: 100px;
        }
    </style>
</head>
<body>
    <div id="inputWord">
        <h1>
            Hello Socket!
        </h1>
        <h2>Please Input the word!</h2>

        <div id="disconn">
            <p> ID <input type="text" name="id" id="id" /><input type="button" id="connect" value="Connect"/></p>
        </div>
        <div id="conn" style="display: none;">
            <p> Input <input type="text" name="word" id="word" onkeypress="if( event.keyCode==13 ){document.getElementById('sendWord').click() }"/><input type="button" id="sendWord" value="Send" /></p>
            <p><form id="fileForm" method="POST" enctype="multipart/form-data"><input type="file" name="file" id="file"/><input type="button" id="fileSend" value="Image Send" /></form></p>
            <div id="user_list">

            </div>
        </div>
    </div>

    <div id="wordList">
        <h2>List Words</h2>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/socket/sockjs-1.0.3.min.js"></script>
    <script>
        var sock = null;
        var contextPath = '${pageContext.request.contextPath}';

        $(document).ready(function(){

            $("#sendWord").on("click", function(){
                if($("#word").val() != ''){
                    message_send('chat',$("#id").val(),$("#word").val());
                }
            });

            $("#fileSend").on("click", function(){
                if($("#file").val() != ''){
                    var data = new FormData(document.getElementById('fileForm'));
                    data.append('id', $("#id").val());

                    $.ajax({
                        url: contextPath + "/REST/IMAGE_SEND",
                        processData: false,  // file전송시 필수
                        contentType: false,  // file전송시 필수
                        data: data,
                        type: "POST",
                        dataType: "json"
                    }).done(function(json) {
                        var fileName = json.BODY;
                        message_send('img',$("#id").val(),fileName);
                        document.getElementById("fileForm").reset();
                    }).fail(function(xhr, status, errorThrown) {
                        console.log("오류명: " + errorThrown);
                        console.log("상태: " + status);
                    }).always(function(xhr, status) {
                        console.log("done")
                    });
                }
            });

            $("#connect").on("click", function(){
                if($("#id").val() == ''){
                    alert("Input the ID!");
                } else {
                    var check = true;
                    $.getJSON(contextPath+'/REST/GET_USERS_ID', function(data){
                        for(var i=0; i<data.length; i++){
                            if($("#id").val() == data[i]){
                                check = false;
                            }
                        }
                        if(check){
                            sock = new SockJS(contextPath+"/chat");
                            sock.onopen = onOpen;
                            sock.onmessage = onMessage;
                            sock.onclose = onClose;

                            $("#disconn").hide();
                            $("#conn").show();
                        } else {
                            alert("이미 로그인한 아이디입니다.");
                        }
                    });
                }
            });

            $("#disconnect").on("click", function(){
                disconnect();

                $("#conn").hide();
                $("#disconn").show();
            });

            $("#wordList").on("change", function(){
                console.log('fff');
            });
        });

        function onOpen(evt) {
            message_send('on',$("#id").val(),"채팅방에 들어오셨습니다.");
            document.getElementById('word').onfocus = true;
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
            if(type == 'alert'){
                alert(message)
            } else if(type == 'chat') {
                appendMessage(id+" : "+message);
            } else if(type == 'img'){
                appendImage(id, message);
            } else{ //on, off경우
                refreshId();
                appendMessage(id+" : "+message);
            }
        }
        //소켓 종료
        function disconnect() {
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
            $("#wordList").append('<p>' + msg + '</p>');
            var objDiv = document.getElementById("wordList");
            objDiv.scrollTop = objDiv.scrollHeight;
        }

        function appendImage(id, fileName) {
            if(fileName != ''){
                $("#wordList").append('<p>' + id + ' : <img src=/file' + fileName + ' class="file_send_image"/></p>');
                var objDiv = document.getElementById("wordList");
                objDiv.scrollTop = objDiv.scrollHeight;
            }
        }

        function refreshId(){
            $.getJSON(contextPath+'/REST/GET_USERS_ID', function(data){
                $("#user_list").html("");
                for(var i=0; i<data.length; i++){
                    $("#user_list").append('<p>' + data[i] + '</p>');
                }
            });
        }
    </script>
</body>
</html>