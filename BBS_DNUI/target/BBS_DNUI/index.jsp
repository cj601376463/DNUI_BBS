<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 曹捷
  Date: 2020/2/29
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<head>
    <c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
    <title>“东软论道”</title>
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.min.css">
    <link href="${path}/css/article/login_bbs.css" rel="stylesheet" type="text/css"/>
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js" type="javascript"></script>
    <style type="text/css">
        body{
            background-image: url("images/article/login_user_background.jfif");
            background-size: cover;
            margin: 0;
        }
        .back_img{
            background-color: rgba(93, 93, 93, 0.6);
            margin-top:220px;
            margin-left: 450px;
            width: 700px;
            height: 180px;
        }
    </style>
</head>
<body>

<div align="center" class="back_img">
    <h1 style="margin: 10px auto 10px auto;">"东软论道"论坛</h1>
    <h1 style="margin: 10px auto 40px auto;">欢迎您</h1>
    <a class="btn btn-success" style="width: 150px;margin-right: 10px" href="${pageContext.request.contextPath}/user/login">登陆</a>
    <a class="btn btn-success" style="width: 150px;margin-right: 10px" href="${pageContext.request.contextPath}/topic/all?page=1">游客浏览</a>
    <a class="btn btn-success" style="width: 150px;" href="${pageContext.request.contextPath}/admin/login">管理员界面</a>
</div>
</body>
</html>
