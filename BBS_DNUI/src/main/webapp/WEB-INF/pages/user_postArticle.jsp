<%@ page import="com.DNUI.domain.User" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发表新帖</title>
    <link rel="stylesheet" href="${path }/css/article/box.css">
    <link rel="stylesheet" href="${path }/css/bootstrap/bootstrap.min.css">
    <script src="${path }/js/bootstrap/jquery-3.1.1.min.js"></script>
    <link rel="stylesheet" href="${path }/css/article/postArticle.css">


    <!--ueditorjs文件：-->
    <link href="${path }/ueditor/themes/default/css/ueditor.css" type="text/css" rel="stylesheet">
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${path}/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="${path}/ueditor/lang/zh-cn/zh-cn.js"></script>
    <!--敏感词过滤-->
    <script>

    </script>
</head>
<body>
<%
    //查看是否登录，将session中数据取出来
    User user = (User)session.getAttribute("user");
    String headImage = "defaultHead.png";
    if(user!=null&&user.getImgUrl()!=null) {
        headImage = user.getImgUrl();
    }
%>
<div class="header">

    <div class="logo">
        <div class="logo_img">
            <a href="${path}/topic/all"><img style="height: 110px;width: auto;margin-right: 100px;margin-left: 100px;"  src="${path}/images/article/article_bg.png" alt="点击图片返回首页" title="点击图片返回首页"></a>
        </div><!--左边图片logo区结束了-->

        <div class="logo_info">
            <img class="img-circle" src="${path}/images/heads/<%=headImage%>" alt="">
        </div><!--右边头像，信息区-->
    </div><!--最上面logo区结束了-->

    <div class="header_nav" style="width: 1002px">
        <ul class="nav nav-pills">
            <li class="active"><a href="javascript:void(0);" onclick="window.history.back();">返回上级</a></li>
            <li><a href="${path}/topic/postTopic">清除内容</a></li>
        </ul><!--帖子排导航区结束了-->
        <div class="nav_btn">
        </div>
    </div><!--导航区结束了-->

</div><!--顶部信息区结束了-->

<form>
    <%
        if(user.getStatus() == 1){
    %>
    <div class="form-group-lg has-success">
        <label><strong style="margin-left: 100px">主题：</strong>
        <input type="text" class="form-inline" name="topic" maxlength="40" width="250px" placeholder="请输入标题" id="topic"></label>
    </div>
    <div class="form-group-lg has-success">
        <strong style="margin-left: 100px">请选择栏目：</strong>
        <label><select name="typeName" class="selectpicker" data-style="btn-primary" id="typeName">
        <option>---请选择栏目---</option>
        <c:forEach items="${type}" var="type">
            <option value="${type.type_name}">${type.type_name} </option>
        </c:forEach>
    </select></label>
    </div>
    <strong style="margin-left: 100px">内容：</strong>
    <!--umeditor: -->
    <!--style给定宽度可以影响编辑器的最终宽度-->
    <script type="text/plain" id="myEditor" style="width:auto;min-height:500px;margin-right: 100px;margin-left: 100px">
    <p>在此写上您的内容</p>
    </script>
    <a  class="btn btn-info" style="margin-left: 100px" onclick="sensitive()" href="javascript:void(0);" >确认发帖</a>
    <%
        }else {
    %>
    <p>当前为禁言状态，无法发言！</p>
    <%
        }
    %>
    <script type="text/javascript">
        //实例化编辑器
        var ue = UE.getEditor('myEditor');
    </script>
    <script>
        function sensitive() {
            var content = UE.getEditor('myEditor').getContentTxt();
            var realContent = UE.getEditor('myEditor').getContent();
            var typeName = $.trim($("#typeName").val());
            var topic = $.trim($("#topic").val());
            if (content != null || content.length != 0){
                console.log(content);
                $.ajax({
                    url:"${path}/sensitive/validateContent",
                    data:{"content":content,"typeName":typeName,"topic":topic},
                    success:function (dataMap) {
                        console.log(dataMap);
                        if (dataMap.result != "allow"){
                            alert("输入框中包含敏感词汇：" + dataMap.sensitiveWords);
                            $("#myEditor").val(dataMap.result);
                        } else {
                            <%--location.href = '${path}/topic/postTopicInfo?content='+content+'&typeName='+typeName+'&topic='+topic;--%>
                            $.ajax({
                                url:"${path}/topic/postTopicInfo",
                                data:{"content":realContent,"typeName":typeName,"topic":topic}
                            });
                            location.href = '${path}/topic/all'
                        }
                    }
                })
            }
        }
    </script>
</form>
</body>
</html>