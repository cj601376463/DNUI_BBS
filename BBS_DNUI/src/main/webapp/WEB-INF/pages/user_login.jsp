<%--
  Created by IntelliJ IDEA.
  User: 曹捷
  Date: 2020/3/1
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link href="${path}/css/article/login_bbs.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" src="${path}/js/article/login_bbs.js"></script>
    <title>登陆“东软论道”</title>
    <%
        if(request.getAttribute("message")!=null) {
            String msg = (String) request.getAttribute("message");
            out.write("<script>alert('" + msg + "');</script>");
        }
    %>
    <script type="text/javascript" language="JavaScript">
        $(document).ready(function () {
            $('#addUser').click(function () {
                if ($("#lg_name").val() == null || $("#pwd").val() == null || $("#email").val() == null || $("#username").val() == null){
                    $('#userCue').html("<span style='color: red'><b>×不可为空！</b></span>");
                }else{
                $.ajax({
                    type: "post",
                    url: "${path}/user/implAdd",
                    data: {
                        login_name: $("#lg_name").val(),
                        password: $("#pwd").val(),
                        email:$("#email").val(),
                        username:$("#username").val()
                    },
                    dataType: "JSON",
                    async: false,
                    beforeSend: function () {
                        $('#userCue').html("<span style='color: green'><b>请稍等</b></span>");
                    },
                    success: function (data) {
                        if (data == -1) {
                            alert("数据输入有误!");
                        } else if (data == 0) {
                            $('#userCue').html("<span style='color: red'><b>×学号已存在！</b></span>");

                        } else if (data == 1){
                            $('#userCue').html("<span style='color: green'><b>注册成功！</b></span>");
                            window.location.href = '${path}/user/login';
                        } else if(data == 2){
                            $('#userCue').html("<span style='color: red'><b>×邮箱已存在！</b></span>");
                        }
                    },
                    error: function () {
                        $('#userCue').html("<span style='color: red'><b>×提交失败！</b></span>");
                    }
                });}
            })
        });
    </script>
</head>
<body>
<h1>"东软论道"论坛<sup>v2020</sup></h1>

<div class="login" style="margin-top:50px;">

    <div class="header">
        <div class="switch" id="switch">
            <a class="switch_btn_focus" id="switch_qlogin" href="javascript:void(0);" tabindex="7" hidefocus="true">登录</a>
            <a class="switch_btn" id="switch_login" href="javascript:void(0);" tabindex="8">注册</a>
            <div class="switch_bottom" id="switch_bottom" style="position: absolute; width: 64px; left: 154px;"></div> <!--底部滑动条结束啦-->
        </div>
    </div><!--头部的切换按钮结束啦-->


    <div class="web_qr_login" id="web_qr_login" style="display: none; height: 235px;">
        <!--登录-->
        <div class="web_login" id="web_login">
            <div class="login-box">

                <div class="login_form">
                    <form name="loginform" accept-charset="utf-8" id="login_form" class="loginForm" method="post" action="${path}/user/login_check">
                        <input type="hidden" name="flag" value="2"/>
                        <div class="uinArea" id="uinArea">
                            <label class="input-tips" for="login_name">学号：</label>  <!--帐号标签-->
                            <div class="inputOuter" id="uArea">
                                <input type="text" id="login_name" required="required" name="login_name" class="inputstyle" placeholder="学号"/><!--用户名-->
                            </div>
                        </div>

                        <div class="pwdArea" id="pwdArea">
                            <label class="input-tips" for="password">密码：</label>
                            <div class="inputOuter" id="pArea">
                                <input type="password" id="password" required="required" name="password" class="inputstyle"/><!--密码-->
                            </div>
                        </div>

                        <div style="padding-left:50px;margin-top:20px;">
                            <label><input type="submit" value="登 录" style="width:150px;" class="button_blue"/><!--登录按钮--></label>
                        </div>
                    </form><!--表单结束啦-->
                </div>
            </div>
        </div>
        <!--登录end-->
    </div>

    <!--注册-->
    <div class="qlogin" id="qlogin" style="display: block; ">
        <div class="web_login">
            <form name="form2" id="regUser" accept-charset="utf-8" action=" " method="post">
                <input type="hidden" name="to" value="reg"/>
                <input type="hidden" name="did" value="0"/><!--两个隐藏于-->

                <ul class="reg_form" id="reg-ul">
                    <div id="userCue" class="cue">快速注册请注意格式</div>
                    <!--邮箱： -->
                    <li>
                        <label for="email" class="input-tips2">邮箱：</label>
                        <div class="inputOuter2">
                            <input type="text" id="email" required="required" name="email" maxlength="20" class="inputstyle2" placeholder="请填写准确的邮箱"/><br> <!--邮箱-->
                        </div>
                    </li>
                    <!--学号： -->
                    <li>
                        <label for="lg_name" class="input-tips2">学号：</label>
                        <div class="inputOuter2">
                            <input type="text" id="lg_name" name="sno" maxlength="11" required="required" placeholder="请填写正确的学号" class="inputstyle2"/>  <!--学号-->
                        </div>
                    </li>
                    <!--名称： -->
                    <li>
                        <label for="username" class="input-tips2">名称：</label>
                        <div class="inputOuter2">
                            <input type="text" id="username" name="username" maxlength="11" required="required" placeholder="你的名称" class="inputstyle2"/>  <!--名称-->
                        </div>
                    </li>

                    <!--密码： -->
                    <li>
                        <label for="pwd" class="input-tips2">密码：</label>
                        <div class="inputOuter2">
                            <input type="password" id="pwd" required="required" name="password" maxlength="16" minlength="6" placeholder="最少长度为6位"  class="inputstyle2"/><!-- 密码-->
                        </div>
                    </li>

                    <!--确认密码： -->
                    <li>
                        <label for="passwd2" class="input-tips2">确认密码：</label>
                        <div class="inputOuter2">
                            <input type="password" id="passwd2" name="" required="required" maxlength="16" minlength="6" class="inputstyle2"/>  <!--重复密码-->
                        </div>
                    </li>

                    <li>
                        <div class="inputArea">
                            <a style="width:150px;line-height:40px;margin-left:70px;" class="button_blue" id="addUser">注 册</a><!--登录按钮-->
                        </div>
                    </li>
                    <div class="cl"></div>
                </ul>
            </form>
        </div>
    </div><!--注册div结束啦-->
</div>
</body>

<script type="text/javascript">
    $("#lg_name").blur(function () {
        if($("#lg_name").val().length!=11) {
            $('#userCue').html("<span style='color: red'><b>×学号格式错误！</b></span>");
            $('#reg').attr('disabled', true);
            $("#lg_name").focus();
            document.getElementById('addUser').style.display = 'none';
            // $('#addUser').html("<span style='color: red'><b>请填写正确的信息</b></span>");
        }else {
            $('#userCue').html("<span style='color: green'><b>正确</b></span>");
            document.getElementById('addUser').style.display = 'block';
            // $('#addUser').html("<a style=\"width:150px;line-height:40px;margin-left:70px;\" class=\"button_blue\" id=\"addUser\">注 册</a>");
        }
    });
    $("#email").blur(function () {
        var regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        if (!regEmail.test($('#email').val())) {
            $('#userCue').html("<span style='color: red'><b>×邮箱格式不正确</b></span>");
            $('#reg').attr('disabled', true);
            $("#email").focus();
            document.getElementById('addUser').style.display = 'none';
        }else {
            $('#userCue').html("<span style='color: green'><b>正确</b></span>");
            document.getElementById('addUser').style.display = 'block';
        }
    });
    $("#passwd2").blur(function () {
        var password = $("#passwd2").val();
        if (password != $("#pwd").val()) {
            $('#userCue').html("<span style='color: red'><b>×两次密码不相同</b></span>");
            $('#reg').attr('disabled', true);
            document.getElementById('addUser').style.display = 'none';
        }else{
            $('#userCue').html("<span style='color: green'><b>正确</b></span>");
            document.getElementById('addUser').style.display = 'block';
        }
    });
    $("#pwd").blur(function () {
        var password = $("#pwd").val();
        if (password.length < 6) {
            $('#userCue').html("<span style='color: red'><b>×密码长度不可小于6位</b></span>");
            $('#reg').attr('disabled', true);
            document.getElementById('addUser').style.display = 'none';
        }else{
            $('#userCue').html("<span style='color: green'><b>正确</b></span>");
            document.getElementById('addUser').style.display = 'block';
        }
    })
</script>

</html>
