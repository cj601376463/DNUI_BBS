<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>他关注的人</title>
    <link rel="stylesheet" href="${path}/css/myinfo/myAttention.css">
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.css">
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>

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
<div class="panel panel-primary">
    <div class="panel-heading">
        他的关注
    </div>
    <div class="panel-body" style="padding-left: 55px">
        <c:forEach items="${otherAttentionList}" var="otherAttentionList">
        <div class="attention">
            <ul class="nav_att">
                <li><img class="img-circle" src="${path}/images/heads/${otherAttentionList.PUser.imgUrl}" width="120px" height="120px" alt=""></li>
                <li><a href="${path}/otherSpace/${otherAttentionList.PUser.user_id}" target="_blank"><strong style="font-size: 20px">${otherAttentionList.PUser.username}</strong></a></li>
            </ul>
        </div><!--一个关注用户的信息结束了-->
        </c:forEach>

    </div>
</div>
</body>
</html>