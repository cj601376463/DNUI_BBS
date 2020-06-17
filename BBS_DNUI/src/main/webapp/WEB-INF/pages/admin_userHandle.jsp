<%@ page import="com.DNUI.domain.User" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!DOCTYPE html>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title></title>
    <link rel="stylesheet" href="${path}/css/admin/pintuer.css">
    <link rel="stylesheet" href="${path}/css/admin/admin.css">
    <script src="${path}/js/admin/jquery.js"></script>
    <script src="${path}/js/admin/pintuer.js"></script>
    <%
        if(request.getAttribute("message")!=null) {
            System.out.println("Message is not null");
            String msg = (String) request.getAttribute("message");
            out.write("<script>alert('" + msg + "');</script>");
        }
        List<User> userList = (List<User>) request.getAttribute("userList");
        if(userList==null) {
            return;
        }
    %>
</head>
<body>
<form method="post" action="${path}/admin/usersStatusUpdate" id="form">
    <input type="hidden" value="0" name="status"/>
    <div class="panel admin-panel">
        <div class="panel-head"><strong class="icon-reorder"> 用户操作</strong></div>
        <div class="padding border-bottom">
            <ul class="search">
                <li>
                    <button type="button" class="button border-green" id="checkall"><span class="icon-check"></span> 全选
                    </button>
                    <button type="submit" class="button border-red" id="shutupBatch"><span class="icon-trash-o"></span>
                        禁言
                    </button>
                </li>
                <li>
                    <%
                        String username = "";
                        if(request.getAttribute("username")!=null) {
                            username = (String) request.getAttribute("username");
                        }
                    %>
                    <label><input type="text" placeholder="请输入用户名关键字" value="<%=username%>" name="keywords" class="input"
                           style="width:250px; line-height:19px;display:inline-block"/></label>
                    <a href="javascript:void(0)" class="button border-main icon-search" onclick="javascript:searchUsers();"> 搜索</a>
                </li>
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th >ID</th>
                <th>用户名</th>
                <th>电话</th>
                <th>邮箱</th>
                <th>学院</th>
                <th>状态</th>
                <th>学号</th>
                <th>发帖数</th>
            </tr> <!--表格头结束啦-->
            <c:set value="0" var="i"/>
            <c:forEach begin="0" end="${fn:length(userList)}" items="${userList}" var="userList">
                <c:set var="i" value="${i+1}"/>
            <tr>
                <td><label><input type="checkbox" name="id[]" value="${userList.user_id}"/>
                    ${i}</label>
                </td>
                <td><a href="${path}/user/${userList.user_id}" target="_blank">${userList.username}</a></td>
                <td>${userList.phone}</td>
                <td>${userList.email}</td>
                <td>${userList.sdept}</td>
                <td>${userList.status==0? "禁言中" : "正常"}</td>
                <td>${userList.sno}</td>
                <td>${userList.topic_num}</td>
            </tr> <!--一个用户结束啦-->
            </c:forEach>

        </table>
    </div>
</form>
<script type="text/javascript">

    $("body").keydown(function() {
        if (event.keyCode === "13") {//keyCode=13是回车键；数字不同代表监听的按键不同
            searchUsers();
        }
    });
    function searchUsers() {
        $("#form").attr("action", "${path}/admin/findUsersByLikeName");
        $("#form").submit();
    }

    $("#checkall").click(function () {
        $("input[name='id[]']").each(function () {
            if (this.checked) {
                this.checked = false;
            }
            else {
                this.checked = true;
            }
        });
    })

    $("#shutupBatch").click(function() {
        var Checkbox = false;
        $("input[name='id[]']").each(function () {
            if (this.checked == true) {
                Checkbox = true;
            }
        });
        if (Checkbox) {
            var t = confirm("您确认要禁言选中的用户吗？");
            if (t == false) return false;
            $("form").submit();
        }
        else {
            alert("请选择您要禁言的用户!");
            return false;
        }
    });

</script>
</body>
</html>