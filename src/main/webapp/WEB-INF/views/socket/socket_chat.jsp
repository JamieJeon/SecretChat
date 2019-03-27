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
    <link href="${pageContext.request.contextPath}/resources/css/jamie.css" rel="stylesheet">
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
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
            <p><form id="fileForm" method="POST" enctype="multipart/form-data"><input type="file" name="file" id="file" accept="image/*"/><input type="button" id="fileSend" value="Image Send" /></form></p>
            <div id="user_list">

            </div>
        </div>
    </div>

    <div id="wordList">
        <h2>List Words</h2>
    </div>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-3.1.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/socket/sockjs-1.0.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/socket/jamie.min.js"></script>
</body>
</html>