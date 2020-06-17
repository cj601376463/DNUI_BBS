<%--
  Created by IntelliJ IDEA.
  User: 曹捷
  Date: 2020/3/5
  Time: 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.DNUI.domain.TopicVO" %>
<%@ page import="java.util.List" %>
<%@ page import="com.DNUI.domain.ArticleVO" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<!-- 设置一个变量 -->
<c:set value="${pageContext.request.contextPath}" var="path" scope="page"/>
<!-- 定义一个加载编辑器的变量 -->
<c:set value="${path}" var="umPath" scope="page"/>
<head>
    <meta charset="UTF-8">
    <title>帖子详情</title>
    <link rel="stylesheet" href="${path}/css/article/box.css">
    <link rel="stylesheet" href="${path}/css/bootstrap/bootstrap.min.css">
    <link href="${path }/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <script src="${path}/js/bootstrap/jquery-3.1.1.min.js"></script>
    <script src="${path}/js/jquery.cookie.js"></script>

    <!--ueditorjs文件：-->
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="${path}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script>
        $(function () {
            $(".header_nav ul li").each(function () {
                $(this).click(function () {
                    $(this).siblings().removeClass("active");
                    $(this).addClass("active");
                });
            });

            $(".msg_content img, .ref_msg img").each(function (index) {
                if($(this).width() > 730) {
                    $(this).width(700 + 'px');
                }
            });

        });
        //回复用的函数，参数分别为被回复的人id和是否引用
        function replyArticle(puser_id, arefid) {
            $("#puser_id").val(puser_id);
            $("#arefid").val(arefid);
            console.log(arefid);
            document.getElementById('editor').style.display = 'block';
            //编辑器获得焦点
            $('html,body').animate({ scrollTop: $("#editor").offset().top - 8 }, 200);
            ue.focus();
            var replyPerson = document.getElementById("replyPerson");
            if(arefid != null){
                replyPerson.innerHTML="回复评论:";
            }else {
                replyPerson.innerHTML="回复楼主:";
            }
        }

        // function confirmReply() {
        //     if(UE.getEditor('myEditor').hasContents()==false) {
        //         alert('请输入内容');
        //         return false;
        //     }
        //     if($.trim(UE.getEditor('editor').getPlainTxt())=='') {
        //         alert('内容不能为空');
        //         return false;
        //     }
        //     return true;
        // }
    </script>


    <%
        if(request.getAttribute("message")!=null) {
            System.out.println("Message is not null");
            String msg = (String) request.getAttribute("message");
            out.write("<script>alert('" + msg + "');</script>");
        }
    %>

</head>
<%
    //首先获得属性
    TopicVO topicVO = (TopicVO) request.getAttribute("topicVO");
    System.out.println("这是articleDetail页面的主题号：" + topicVO.getTopic_id());
    System.out.println("网页的信息："+ topicVO);
    User user = (User) session.getAttribute("user");
    String hrefStr = "/user/login";
    String headImage = "defaultHead.png";
    if(user!=null&&user.getImgUrl()!=null) {
        headImage = user.getImgUrl();
    }
    if(user!=null) {
        hrefStr = "/user/" + user.getUser_id();
    }
    //头像信息
    if(user!=null&&user.getImgUrl()!=null) {
        headImage = user.getImgUrl();
    }
    //做一个时间转化的对象
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//    if(topicVO == null) {
//        return;
//    }
%>
<body>

<div class="header">

    <div class="logo">
        <div class="logo_img">
            <a href="${path}/topic/all"><img src="${path}/images/article/article_bg.png" alt="点击图片返回首页"
                                             title="点击图片返回首页"></a>
        </div><!--左边图片logo区结束了-->

        <div class="logo_info">
            <a href="${path}<%=hrefStr%>"><img class="img-circle" src="${path }/images/heads/<%=headImage%>" alt=""></a>
        </div><!--右边头像，信息区-->
    </div><!--最上面logo区结束了-->

    <div class="header_nav" style="width: 952px; margin-left: 308.5px;">
<%--        点赞和浏览区域--%>
        <ul class="nav nav-pills" style="margin-left: 50px;">
            <li class="active"><a href="${path}/topic/all">返回首页</a></li>
            <li class="active"><a href="${path}/otherSpace/<%=topicVO.getUser_id()%>">楼主信息</a></li>
        </ul><!--帖子排导航区结束了-->
        <div class="nav_btn">
            <!--回复楼主的按钮，把用户信息的值注入到应该的地方 -->
            <%
                if (user != null){
            %>
            <a class="btn btn-success" href="javascript:void(0);" onclick="replyArticle(<%=topicVO.getUser_id()%>, 0)">回复楼主</a>
            <%
                }
            %>
            <a class="btn btn-success" style="width: 80px" href="javascript:void(0);" onclick="javascript:window.history.go(0);">刷新</a>
<%--            <span class="views"><i class="fa fa-eye"></i><i class="viewCount">浏览数：${topicVO.view}</i></span>--%>
<%--            <span class="like">--%>
<%--            <a href="javascript:;" data-action="ding" data-id="1" title="点赞" class="favorite">--%>
<%--                <i class="fa fa-thumbs-up"></i>赞--%>
<%--                <i class="likeCount">${topicVO.tlike}</i>--%>
<%--            </a>--%>
<%--        </span>--%>
        </div>
    </div><!--导航区结束了-->

</div><!--顶部信息区结束了-->

<!--楼主的信息（必有）：-->
<div class="article_topic" style="padding-left: 30px;width: 952px;margin-left: 309px;">
    <strong>主题：</strong><%=topicVO.getTopic()%>&nbsp;
    <%
        if (user != null){
    %>
    <a class="btn btn-danger" href="${path}/topic/collectTopic?topic_id=<%=topicVO.getTopic_id()%>">收藏此贴</a>
    <%
        }
    %>
</div>

<div class="box">
    <div class="user_info">
        <img src="${path}/images/heads/<%=topicVO.getUser().getImgUrl()%>" class="img-circle" alt=""/>
        <h5 class="h5">
            <a href="${path}/otherSpace/<%=topicVO.getUser_id()%>"><%=topicVO.getUser().getUsername()%></a>
        </h5>
        <p>
            累计发帖：<%=topicVO.getUser().getTopic_num()%>
        </p>
        <p style="color: goldenrod">回帖积分：<%=topicVO.getUser().getReply_num()*20%></p>
    </div>
    <div class="message">
        <div class="msg_header">
            <mark>楼主</mark>
            <span class="bg-success time">发帖时间：<%=sdf.format(topicVO.getCreate_time())%></span>
        </div>
        <div class="msg_content">
            <%=topicVO.getContent()%>
        </div>
        <%
            if (user != null){
        %>
        <div class="msg_footer">
            <a class="btn btn-success" href="javascript:void(0);" onclick="replyArticle(<%=topicVO.getUser_id()%>,0)">回复</a>
        </div>
        <%
            }
        %>
    </div>
</div> <!--楼主的信息结束了-->

<%
    List<ArticleVO> articleList = topicVO.getOneToArticles();
    if (articleList != null){
        for(int i = 0; i < articleList.size(); i++) {
            ArticleVO articleVO = articleList.get(i);
            if(articleVO.getArefid()==0) {
%>
<!--为了下面能隔开，做一个过渡的div-->
<div style="clear: both"></div>
<div class="box">
    <div class="user_info">
        <img src="${path }/images/heads/<%=articleVO.getUser().getImgUrl()%>" class="img-circle" alt=""/>
        <h5 class="h5">
            <a href="${path}/otherSpace/<%=articleVO.getUser_id()%>"><%=articleVO.getUser().getUsername()%></a>
        </h5>
        <p>
            累计发帖：<%=articleVO.getUser().getTopic_num()%>
        </p>
        <p style="color: goldenrod">回帖积分：<%=articleVO.getUser().getReply_num()*20%></p>
    </div>
    <div class="message">
        <div class="msg_header">
            <mark><%=articleVO.getLevel()%> 楼</mark>
            <span class="bg-success time">回复时间：<%=sdf.format(articleVO.getReply_time())%></span>
        </div>
        <div class="msg_content">
            <%=articleVO.getContent()%>
        </div>
        <%
            if (user != null){
        %>
        <div class="msg_footer">
            <a class="btn btn-success" href="javascript:void(0);" onclick="replyArticle(<%=articleVO.getUser_id()%>, <%=articleVO.getArticle_id()%> )">回复</a>
        </div>
        <%
            }
        %>
    </div>
</div> <!--回复楼主的信息结束了-->

<%
    } else {
        ArticleVO refArticleVO = articleVO.getRefArticleVO();
        System.out.println("ArticleVO里面arefId的值是： " + articleVO.getArefid());
%>


<!--为了下面能隔开，做一个过渡的div-->
<div style="clear: both"></div>

<div class="box">
    <div class="user_info">
        <img src="${path}/images/heads/<%=articleVO.getUser().getImgUrl()%>" class="img-circle" alt=""/>
        <h5 class="h5">
            <a href="${path}/otherSpace/<%=articleVO.getUser_id()%>"><%=articleVO.getUser().getUsername()%></a>
        </h5>
        <p>累计发帖：<%=articleVO.getUser().getTopic_num()%></p>
        <p style="color: goldenrod">回帖积分：<%=articleVO.getUser().getReply_num()*20%></p>
    </div>
    <!--左边用户头像和基本信息-->
    <div class="message">
        <div class="msg_header">
            <mark><%=articleVO.getLevel()%> 楼</mark>
            <span class="bg-success time">回复时间：<%=sdf.format(articleVO.getReply_time())%></span>
        </div>
        <div class="ref_msg">
            <form>
                <fieldset>
                    <legend style="font-size: 18px">
                        引用 <span style="color: lightseagreen"><%=refArticleVO.getLevel()+1%>楼<a href="/otherSpace/<%=refArticleVO.getUser_id()%>"><%=refArticleVO.getUser().getUsername()%></a></span> 的回复：
                    </legend>
                    <%=refArticleVO.getContent()%>
                </fieldset>
            </form>
        </div>
        <div class="msg_content">
            <%=articleVO.getContent()%>
        </div>
        <%
            if (user != null){
        %>
        <div class="msg_footer">
            <a class="btn btn-success" href="javascript:void(0);" onclick="replyArticle(<%=articleVO.getUser_id()%>, <%=articleVO.getArticle_id()%>)">回复</a>
        </div>
        <%
            }
        %>
    </div>
</div> <!--引用别人的回复信息区结束啦-->

<%
            }
        }
    }
%>


<!--umeditor: -->
<!--style给定宽度可以影响编辑器的最终宽度-->
<div class="umeditor" id="editor" style="display: none">
    <form class="form-group-lg"  action="">
        <%if(user!=null){%>
        <input type="hidden"  name="user_id" value="<%=user.getUser_id()%>" id="user_id"/>
        <%} else {%>
        <input type="hidden"  name="user_id"/>
        <%}%>
        <input type="hidden" name="puser_id" id="puser_id" value="<%=topicVO.getUser_id()%>"/>
        <input type="hidden" name="arefid" id="arefid">
        <input type="hidden" value="<%=topicVO.getTopic_id()%>" name="topic_id" id="topic_id">
        <%
            if (user != null && user.getStatus() == 1){
        %>
        <p id="replyPerson">回复楼主：</p>
        <script type="text/plain" id="myEditor" name="myEditor" style="width:955px;min-height:210px;">
            <p>在此写上您的内容</p>
        </script>
        <a type="submit" class="btn btn-info" onclick="sensitive()" href="javascript:void(0);" >确认回复</a>
        <%
            }else {
        %>
    </form>
    <p style="float: left">当前为禁言状态，无法发言！</p>
    <%
        }
    %>
</div>

<!--编辑器结束了-->

<!--实例化编辑器的代码-->
<script type="text/javascript">
    // 实例化编辑器
    var ue = UE.getEditor('myEditor');
</script>
<script>
    function sensitive() {
        var content = UE.getEditor('myEditor').getContentTxt();
        var realContent = UE.getEditor('myEditor').getContent();
        var topic_id = $.trim($("#topic_id").val());
        var arefid = $.trim($("#arefid").val());
        var puser_id = $.trim($("#puser_id").val());
        var user_id = $.trim($("#user_id").val());
        if (content != null || content.length != 0){
            console.log(content);
            $.ajax({
                url:"${path}/sensitive/validateContent",
                data:{"content":realContent},
                type:"post",
                success:function (dataMap) {
                    console.log(dataMap);
                    if (dataMap.result != "allow"){
                        alert("输入框中包含敏感词汇：" + dataMap.sensitiveWords);
                        $("#myEditor").val(dataMap.result);
                    } else {
                        <%--location.href = '${path}/article/postArticle?content='+content+'&topic_id='+topic_id+'&arefid='+arefid+'&puser_id='+puser_id+'&user_id='+user_id;--%>
                        $.ajax({
                            url:"${path}/article/postArticle",
                            data:{"content":realContent,"topic_id":topic_id,"arefid":arefid,"puser_id":puser_id,"user_id":user_id}
                        });
                        location.href = '${path}/topic/detail/'+topic_id;
                    }
                }
            })
        }
    }
</script>
</body>
</html>