<%--
  Created by IntelliJ IDEA.
  User: 曹捷
  Date: 2020/3/2
  Time: 10:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>所有帖子</title>
    <!-- 设置一个变量 -->
    <c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
    <link rel="stylesheet" href="${path}/css/article/showTopics.css">
    <link rel="stylesheet" href="${path}/css/article/loginDialog.css">
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.min.css">
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js" type="javascript"></script>
    <script src="${path}/js/article/loginDialog.js" type="javascript"></script>

</head>
<body>
<%
    //查看是否登录，将session中数据取出来
    User user = (User)session.getAttribute("user");
    String loginStr = "注册和登录";
    String login = "login";
    String hrefStr = "/user/login";
    String headImage = "defaultHead.png";
    if(user!=null&&user.getImgUrl()!=null) {
        headImage = user.getImgUrl();
    }
    if(user!=null) {
        loginStr = "欢迎" + user.getUsername();
        login = "otherLogin";
        hrefStr = "/user/" + user.getUser_id();
    }
%>

<div class="header">
    <div class="logo">
        <div class="logo_img">
            <a href="${pageContext.request.contextPath}/topic/all">
                <img src="${path}/images/article/article_bg.png" alt="">
            </a>
        </div>
        <!--左边图片logo区结束了-->

        <div class="logo_info">
            <a href="${path}<%=hrefStr%>">
                <img class="img-circle" src="${path}/images/heads/<%=headImage%>" style="margin-left: 590px" title="点击查看个人信息" alt="">
            </a>
        </div>
        <!--右边头像，信息区-->
    </div>
    <!--最上面logo区结束了-->
    <%
        String pageType =request.getParameter("type");
        String all = "active";
        String normal1 = "";
        String normal2 = "";
        String normal3 = "";
        String normal4 = "";
        if(pageType != null){
            switch (pageType){
                case "java":
                    all = "";
                    normal1 = "active";
                    break;
                case "python":
                    all = "";
                    normal2 = "active";
                    break;
                case "c":
                    all = "";
                    normal3 = "active";
                    break;
                case "html":
                    all = "";
                    normal4 = "active";
                    break;
                default:
                    all="active";
            }
        }
    %>
    <div class="header_nav">
        <ul class="nav nav-pills" id="sort">
            <li class="<%=all%>"><a href="${pageContext.request.contextPath}/topic/all">全部帖子</a></li>
            <li class="<%=normal1%>>"><a href="${path}/topic/all?type=java">Java专区</a></li>
            <li class="<%=normal2%>>"><a href="${path}/topic/all?type=python">Python专区</a></li>
            <li class="<%=normal3%>>"><a href="${path}/topic/all?type=c">C专区</a></li>
            <li class="<%=normal4%>>"><a href="${path}/topic/all?type=html">Html专区</a></li>
        </ul>
        <!--帖子排导航区结束了-->
        <div class="nav_btn">
            <a class="btn btn-success" href="${path}/topic/postTopic">我要发帖</a>
            <a class="btn btn-success" style="width: 80px" href="${path}/topic/all">刷新</a>
        </div>
    </div>
    <!--导航区结束了-->

    <!--分页区-->
    <div class="page_header">
        <ul class="pagination">
            <li><a href="${path}/topic/all?page=1">首页</a></li>
            <%--<li><a href="${pageContext.request.contextPath}/topic/all?page=${pageInfo.pageNum-1}&size=5">&laquo;</a></li>--%>
            <c:forEach begin="1" end="${pageInfo.pages}" var="pageNum">
                <li><a href="${path}/topic/all?page=${pageNum}">${pageNum}</a></li>
            </c:forEach>
            <%--<li><a href="${pageContext.request.contextPath}/topic/all?page=${pageInfo.pageNum+1}">&raquo;</a></li>--%>
            <li><a href="${path}/topic/all?page=${pageInfo.pages}">尾页</a></li>
        </ul>

        <!--显示用户信息区域-->
        <a class="btn btn-info rigister" id="<%=login%>" href="${path}/<%=hrefStr%>">
            <span><%=loginStr%></span>
        </a>

        <!--模糊查找区域-->
        <form class="form-inline article_search" method="post" action="${pageContext.request.contextPath}/topic/all">
            <input class="form-control" placeholder="查找帖子" name="topicName" style="width: 250px;">
            <input class="btn btn-danger" type="submit" value="搜索" style="width: 80px">
        </form>
        <!--查找帖子区结束了-->
    </div>
    <!--分页区结束了-->

</div>
<!--顶部信息区结束了-->

<div class="main">
    <table class="table table-hover table-striped">
        <thead class="bg-info">
        <th style="width: 8%">状态</th>
        <th style="width: 25%">主题</th>
        <th style="width: 8%">作者</th>
        <th style="width: 8%">回复数</th>
        <th style="width: 15%">发帖时间</th>
        <th style="width: 10%">栏目</th>
        </thead>
        <tbody>
        <c:forEach items="${pageInfo.list}" var="topicVO">
            <c:if test="${topicVO.isindex == 1}">
                <tr class="table-primary" style="background-color: #bff3ff;">
                    <td>置顶</td>
                    <td><a href="${path}/topic/detail/${topicVO.topic_id}">${topicVO.topic}</a></td>
                    <td>${topicVO.user.username}</td>
                    <td>${topicVO.reply_number}</td>
                    <td><fmt:formatDate value="${topicVO.create_time}" pattern="yyyy-MM-dd"/></td>
                    <td>${topicVO.type}</td>
                </tr>
            </c:if>
            <c:if test="${topicVO.isindex == 0}">
                <tr class="table-primary">
                    <td></td>
                    <td><a href="${path}/topic/detail/${topicVO.topic_id}">${topicVO.topic}</a></td>
                    <td>${topicVO.user.username}</td>
                    <td>${topicVO.reply_number}</td>
                    <td><fmt:formatDate value="${topicVO.create_time}" pattern="yyyy-MM-dd"/></td>
                    <td>${topicVO.type}</td>
                </tr>
            </c:if>
        </c:forEach>

        </tbody>
    </table>
    <ul class="pagination" style="margin: 0 0">
        <li><a href="${pageContext.request.contextPath}/topic/all?page=1">首页</a></li>
        <%--<li><a href="${pageContext.request.contextPath}/topic/all?page=${pageInfo.pageNum-1}&size=5">&laquo;</a></li>--%>
        <c:forEach begin="1" end="${pageInfo.pages}" var="pageNum">
            <li><a href="${pageContext.request.contextPath}/topic/all?page=${pageNum}">${pageNum}</a></li>
        </c:forEach>
        <%--<li><a href="${pageContext.request.contextPath}/topic/all?page=${pageInfo.pageNum+1}">&raquo;</a></li>--%>
        <li><a href="${pageContext.request.contextPath}/topic/all?page=${pageInfo.pages}">尾页</a></li>
    </ul>
</div>
<!--中间帖子展示区结束了-->
<div class="footer">
    <div class="footer_main">
        <p>大连东软信息学院 16180600811 曹捷 2020</p>
    </div>
</div>
<!--底部信息展示区结束了-->
</body>
</html>