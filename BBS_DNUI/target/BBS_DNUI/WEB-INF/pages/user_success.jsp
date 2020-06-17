<%--
  Created by IntelliJ IDEA.
  User: 曹捷
  Date: 2020/2/29
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Login Success</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
<div>
    <strong> welcome,${sessionScope.user.username}! </strong>
</div>
this is success page!

<a href="${pageContext.request.contextPath}/user/testPage">点我跳到另一个页面</a>

<form action="${pageContext.request.contextPath}/user/outLogin">
    <table>
        <tr>
            <td><input type="submit" value="退出登录"></td>
        </tr>
    </table>
</form>
</body>
</html>
