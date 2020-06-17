<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 曹捷
  Date: 2020/3/17
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<html>
<head>
    <title>file upload</title>
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="${path}/css/myinfo/mySpace.css">
    <script type="text/javascript" language="JavaScript">
        $(document).ready(function () {
            $('#login').click(function () {
                $.ajax({
                    type: "post",
                    url: "${path}/user/login_check",
                    data: {
                        "login_name": $("#login_name").val(),
                        "password": $("#password").val(),
                    },
                    dataType: "JSON",
                    async: false,
                    beforeSend: function () {
                        alert("正在提交");
                    },
                    success: function (data) {
                        if (data == 1) {
                            alert("登录成功")

                        } else {
                            alert("用户名或者密码错误!");
                        }
                    },
                    error: function () {
                        alert("提交失败");
                    }
                });
            })
        })
    </script>
    <script type="text/javascript" language="JavaScript">
        $(document).ready(function () {
            $('#addUser').click(function () {
                $.ajax({
                    type: "post",
                    url: "${path}/user/implAdd",
                    data: {
                        login_name: $("#lg_name").val(),
                        password: $("#pwd").val(),
                        user_name: $("#user_name").val(),
                    },
                    dataType: "JSON",
                    async: false,
                    beforeSend: function () {
                        alert("正在提交");
                    },
                    success: function (data) {
                        if (data == -1) {
                            alert("数据输入有误!");
                        } else if (data == 0) {
                            alert("用户以存在!");

                        } else {
                            alert("注册成功")
                        }
                    },
                    error: function () {
                        alert("提交失败");
                    }
                });
            })

        })
    </script>
</head>
<body>
<form>
    <h1>登录页面</h1>
    <form class="bs-example bs-example-form" method="post">
        <div class="input-group">
            <span class="input-group-addon">username</span>
            <label><input type="text" id="login_name" class="form-control" ></label>
        </div>
        <div class="input-group">
            <span class="input-group-addon">password</span>
            <label><input type="text" id="password" class="form-control" ></label>
        </div>
        <label><a class="btn btn-info" style="align-content: center" id="login">登陆</a></label>
    </form>
</form>
<h1>注册页面</h1>
<form class="bs-example bs-example-form" role="form" method="post">
    <div class="input-group">
        <span class="input-group-addon">用户名:</span>
        <label><input type="text" id="lg_name" class="form-control" placeholder="输入你的登录名"></label>
    </div>
    <div class="input-group">
        <span class="input-group-addon">密码:</span>
        <label><input type="text" id="pwd" class="form-control" placeholder="输入你的密码"></label>
    </div>
    <div class="input-group">
        <span class="input-group-addon">昵称:</span>
        <label><input type="text" id="user_name" class="form-control" placeholder="输入你的昵称"></label>
    </div>
    <label><a class="btn btn-info" style="align-content: center" id="addUser">提交</a> </label>
</form>
</body>
</html>
