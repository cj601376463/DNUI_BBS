<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <%
        if (request.getAttribute("message") != null) {
            System.out.println("Message is not null");
            String msg = (String) request.getAttribute("message");
            out.write("<script>alert('" + msg + "');</script>");
        }
        //从session中取出用户信息
        User user = (User) session.getAttribute("otherUser");
        User myUser = (User) session.getAttribute("user");
        if(user==null) {
            return;
        }
        String headImage = "defaultHead.png";
        if(user!=null&&user.getImgUrl()!=null) {
            headImage = user.getImgUrl();
        }
    %>

    <meta charset="UTF-8">
    <title><%=user.getUsername()%>的空间</title>
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${path}/css/myinfo/mySpace.css">
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>
    <script>
        window.onload = function () {
            var lis = document.getElementsByTagName('li');
            for (var i = 0; i < lis.length; i++) {
                lis[i].onclick = function () {
                    for (var j = 0; j < lis.length; j++) {
                        lis[j].className = '';
                    }
                    this.className = 'active';
                }
            }
            <%
               if(user.getIsLock()==1) {

            %>
               window.alert("用户拒绝他人访问");
               window.location.href = "${path}/topic/all";
            <%
            }
            %>
        }
    </script>
</head>


<body>
<div class="header">
    <img class="img img-circle" src="${path}/images/heads/<%=headImage%>"><!--头像-->
    <div class="timeInfo">
        <strong style="font-size: 30px"><%=user.getEmail()%></strong><br/><br/>
        <span>经验：<mark>4年</mark>&nbsp;&nbsp;
        累计发帖： <mark><%=user.getTopic_num()%></mark>&nbsp;&nbsp;
        所得积分：<mark><%=user.getReply_num()*20%> </mark></span><br/>
        <span>个性签名：<ins><%=user.getSign()%></ins></span>
    </div><!--用户发帖数目，积分，个性签名结束了-->

    <a class="btn btn-info" href="${path}/topic/all">返回首页</a>
    <a class="btn btn-success" href="javascript:void(0);" onclick="javascript:window.history.back()">回到上级</a>
    <%
        if (myUser != null){
    %>
    <a class="btn btn-danger" href="${path}/otherSpace/attentionUser?puser_id=<%=user.getUser_id()%>">点击关注</a>
    <%
        }
    %>
</div><!--头部信息区域结束了-->

<div class="left_nav">
    <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="${path}/otherSpace/otherInfo" target="frame_info">基本信息</a></li>
        <li class=""><a href="${path}/otherSpace/otherArticles?user_id=<%=user.getUser_id()%>" target="frame_info"><span class="badge pull-right"><%=user.getTopic_num()%></span>他的帖子</a>
        </li>
        <li class=""><a href="${path}/otherSpace/otherAttention" target="frame_info"><span class="badge pull-right"><%=user.getAttention_num()%></span>他的关注</a>
        </li>
        <li class=""><a href="${path}/otherSpace/otherCollect?user_id=<%=user.getUser_id()%>" target="frame_info"><span class="badge pull-right"><%=user.getCollect_num()%></span>他的收藏</a>
        </li>
    </ul><!--导航栏-->
</div><!--左侧用户导航区域-->

<div class="user_info">
    <iframe class="frame_info" src="${path}/otherSpace/otherInfo" name="frame_info" width="100%"
            height="100%"></iframe>
</div><!--右侧的信息展示区域结束了-->
</body>
<script>
</script>
</html>