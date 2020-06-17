<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>我的空间</title>
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${path}/css/myinfo/mySpace.css">
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>
    <script>
        window.onload = function () {
            var lis = document.getElementsByTagName('li');
            for(var i = 0; i < lis.length; i++) {
                lis[i].onclick = function () {
                    for(var j = 0; j < lis.length; j++) {
                        lis[j].className = '';
                    }
                   this.className = 'active';
                }
            }
        }
    </script>

    <%
        if(request.getAttribute("message")!=null) {
            System.out.println("Message is not null");
            String msg = (String) request.getAttribute("message");
            out.write("<script>alert('" + msg + "');</script>");
        }
        //从session中取出用户信息
        User user = (User) session.getAttribute("user");
        if(user==null) {
            return;
        }
        String headImage = "defaultHead.png";
        if(user!=null&&user.getImgUrl()!=null) {
            headImage = user.getImgUrl();
        }
        String isLock = user.getIsLock() == 0? "禁止访问":"开放访问";
        Integer varLock = user.getIsLock()==1? 0 : 1;
    %>
</head>
<body>
<div class="header">
    <img class="img-circle" src="${path}/images/heads/<%=headImage%>" alt=""><!--头像-->
    <div class="timeInfo">
        <strong style="font-size: 30px"><%=user.getEmail()%></strong><br/><br/>
        累计发帖： <mark><%=user.getTopic_num()%></mark>&nbsp;&nbsp;
<%--        所得积分：<mark><%=user.getReply_num()*20%> </mark></span><br/>--%>
        <span>个性签名：<ins><%=user.getSign()%></ins></span>
    </div><!--用户发帖数目，积分，个性签名结束了-->

    <a class="btn btn-info" href="${path}/topic/all">返回首页</a>
    <a class="btn btn-success" href="javascript:void(0);" onclick="javascript:window.history.back()">回到上级</a>
    <a class="btn btn-danger" href="${path}/user/outLogin">安全注销</a>
    <a class="btn btn-danger"  href="javascript:void(0);" onclick="lock(<%=varLock%>);"><%=isLock%></a>
</div><!--头部信息区域结束了-->

<div class="left_nav">
    <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="${path}/user/myInfo" target="frame_info">基本信息</a></li>
        <li class=""><a href="${path}/user/editMyInfo" target="frame_info">编辑信息</a></li>
        <li class=""><a href="${path}/user/myTopics?user_id=<%=user.getUser_id()%>" target="frame_info"><span class="badge pull-right"><%=user.getTopic_num()%></span>我的帖子</a></li>
        <li class=""><a href="${path}/user/myAttention" target="frame_info"><span class="badge pull-right"><%=user.getAttention_num()%></span>我的关注</a></li>
        <li class=""><a href="${path}/user/myCollect?user_id=<%=user.getUser_id()%>" target="frame_info"><span class="badge pull-right"><%=user.getCollect_num()%></span>我的收藏</a></li>
        <li class=""><a href="${path}/user/changePwd" target="frame_info">修改密码</a></li>
    </ul><!--导航栏-->
</div><!--左侧用户导航区域-->

<div class="user_info">
    <iframe class="frame_info" src="${path}/user/myInfo" name="frame_info" width="100%"
            height="100%"></iframe>
</div><!--右侧的信息展示区域结束了-->

<form method="post" class="form-group" action="${path}/user/setIsLock" id="lockForm">
    <input type="hidden" value="<%=user.getUser_id()%>" name="user_id">
    <input type="hidden" name="isLock">
</form><!--用户修改用户权限的一个隐藏的form表单-->
</body>
<script>
    function lock(isLock) {
        $("input[name='isLock']").val(isLock + '');
        $("#lockForm").submit();
    }
</script>
</html>