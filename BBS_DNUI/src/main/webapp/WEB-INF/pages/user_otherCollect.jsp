<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我收藏的帖子</title>
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.css">
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>
    <style>
        .menu {
            position: absolute;
            display: none;
            width: 100px;
        }
    </style>
</head>

<%
    if(request.getAttribute("message")!=null) {
        System.out.println("Message is not null");
        String msg = (String) request.getAttribute("message");
        out.write("<script>alert('" + msg + "');</script>");
    }
    //从session中取出用户信息
    User user = (User) session.getAttribute("otherUser");
    if(user==null) {
        return;
    }
%>
<body>
<!--右击鼠标弹出取消收藏-->

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">我的收藏</h3>
    </div>
    <div class="panel-body">
        <c:forEach items="${otherCollectList}" var="otherCollectList">
        <div style="margin: 25px; height: 90px; border: 1px solid darkcyan">
            <legend>主题：
                <mark><a href="${path}/topic/detail/${otherCollectList.topic_id}" target="_blank" class="mark_a" onmousedown="mouseDown(window.event, ${otherCollectList.topicVO.topic_id})">${otherCollectList.topicVO.topic}</a></mark>
                <strong style="float: right; margin-right: 35px">收藏时间： <fmt:formatDate value="${otherCollectList.create_time}" pattern="yyyy-MM-dd"/></strong>
            </legend>
            <div class="text-info" style="margin-left: 25px">
                <a href="${path}/otherSpace/${otherCollectList.topicVO.user.user_id}" target="_blank"> <h4 style="display: inline; float: right; margin-right: 25px">-----&nbsp;&nbsp;${otherCollectList.topicVO.user.username}</h4></a></div>
        </div>
        </c:forEach>


    </div>
</div>
</body>
<script>
    //屏蔽默认事件
    $(".mark_a").bind("contextmenu", function (e) {
        return false;
    });

    function mouseDown(e, topic_id) {
        if(e.which == 3) {
            $(".menu").css({
                "left" : e.clientX,
                "top"  : e.clientY + 20,
                "background" : "#eaf0a9",
                "display" : "block"
            })
            $("input[name='topic_id']").val(topic_id);
        }
    }


    //点击事件
    $(document).click(function () {
        $(".menu").css("display","none");
    })

</script>
</html>