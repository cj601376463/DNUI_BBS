<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>所发帖子</title>
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.css">
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>
</head>
<style>
    thead, th, tr {
        text-align: center;
    }
</style>
<body>
<%
    if (request.getAttribute("message") != null) {
        System.out.println("Message is not null");
        String msg = (String) request.getAttribute("message");
        out.write("<script>alert('" + msg + "');</script>");
    }
//从session中取出用户信息
    User user = (User) session.getAttribute("otherUser");
    if (user == null) {
        return;
    }
%>

<div class="panel panel-success">
    <div class="panel-heading">
        <h3 class="panel-title" style="font-size: 21px; margin-left: 5px">我的帖子</h3>
    </div>
    <div class="panel-body">
        <table class="table table-hover table-striped">
            <thead class="bg-info">
            <th style="width: 25%">主题</th>
            <th style="width: 8%">回复数</th>
            <th style="width: 15%">发帖时间</th>
            <th style="width: 15%">栏目</th>
            </thead>
            <tbody class="">
            <c:forEach items="${otherTopicList}" var="otherTopicList">
                <tr>
                    <td><a href="${path}/topic/detail/${otherTopicList.topic_id}">${otherTopicList.topic}</a></td>
                    <td>${otherTopicList.reply_number}</td>
                    <td><fmt:formatDate value="${otherTopicList.create_time}" pattern="yyyy-MM-dd"/></td>
                    <td>${otherTopicList.type}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <!--删除帖子的form -->
    <form action="/user/delMyArticles" id="delForm" method="post">
        <input type="hidden" id="topic_id" name="id[]">
        <input type="hidden" id="type" name="type" value="flag">
        <input type="hidden" id="st" name="st" value="0">
        <input type="hidden" name="user_id" value="<%=user.getUser_id()%>">
        <input type="hidden" name="initUrl" value="myArticles">
    </form>
</div>

</body>
</html>