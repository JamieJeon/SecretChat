var sock = null;
var admin = false;

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
    if(type == 'alert'){
        if(!admin){
            return;
        }
    }
    var data = JSON.stringify({
        type : type,
        id : id,
        message : message
    });
    sock.send(data);
    $("#word").val('');
}

function appendMessage(msg) {
    var time = new Date();
    $("#wordList").append('<p>' + msg + '<em> '+getTime()+'</em></p>');
    var objDiv = document.getElementById("wordList");
    objDiv.scrollTop = objDiv.scrollHeight;
}

function appendImage(id, fileName) {
    if(fileName != ''){
        $("#wordList").append('<p>' + id + ' : <img src=/file' + fileName + ' class="file_send_image"/><em> '+getTime()+'</em></p>');
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

function getTime(){
    var result = '';
    
    var d = new Date();
    var hr = d.getHours();
    var min = d.getMinutes();
    if (min < 10) {
        min = "0" + min;
    }
    var ampm = "오전";
    if( hr > 12 ) {
        hr -= 12;
        ampm = "오후";
    }
    
    result = ampm + ' ' + hr + ":" + min;
    
    return result;
}