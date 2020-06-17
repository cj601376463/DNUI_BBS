<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<c:set value="${pageContext.request.contextPath }" var="path" scope="page"/>
<!DOCTYPE html>
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
</head>
<%
    if (request.getAttribute("message") != null) {
        System.out.println("Message is not null");
        String msg = (String) request.getAttribute("message");
        out.write("<script>alert('" + msg + "');</script>");
    }

%>
<body>
<form method="post" action="${path}/admin/updateTopicStatus" id="updateForm">
    <input id="sort" type="hidden" name="sort">
    <input type="hidden" name="type" id="type">
    <input type="hidden" name="st" id="status">
    <div class="panel admin-panel">
        <div class="panel-head"><strong class="icon-reorder"> 所有帖子</strong>
        </div>
        <div class="padding border-bottom">
            <ul class="search" style="padding-left:10px;">
                <li>
                    <label><input type="text" placeholder="请输入帖子关键字" required name="topicName" class="input"
                           style="width:250px; line-height:17px;display:inline-block"/></label>
                    <a class="button border-main icon-search" href="javascript:void(0);" onclick="changesearch()"> 搜索</a>
                </li>  <!--搜索帖子结束啦-->
            </ul>
        </div>
        <table class="table table-hover text-center">
            <tr>
                <th width="6%" style="text-align:left; padding-left:20px;">序号</th>
                <th width="8%">作者</th>
                <th width="6%">回复数</th>
                <th width="33%">主题</th>
                <th width="8%">属性</th>
                <th width="8%">状态</th>
                <th width="20%">发帖时间</th>
            </tr>
            <volist name="list" id="vo">
                <c:set value="0" var="i"/>
                <c:forEach begin="0" end="${fn:length(adminAllTopic)}" items="${adminAllTopic}" var="adminAllTopic">
                    <c:set var="i" value="${i+1}"/>
                    <tr>
                        <td style="text-align:left; padding-left:20px;">
                            <label><input type="hidden" name="soloId" value="${adminAllTopic.topic_id}"></label>
                            <label><input type="hidden" name="soloIdUserId" value="${adminAllTopic.user.user_id}"></label>
                            <label><input type="checkbox" name="id[]" value="${adminAllTopic.topic_id}">
                            ${i}</label>
                        </td>
                        <td>${adminAllTopic.user.username}
                        </td>
                        <td width="10%">${adminAllTopic.reply_number}
                        </td>
                        <td><a href="${path}/topic/detail/${adminAllTopic.topic_id}" target="_blank">${adminAllTopic.topic}
                        </a></td>
                        <td><a href="#">${adminAllTopic.isindex == 1 ? "置顶" : "普通"}</a></td>
                        <td><a href="#">${adminAllTopic.flag == 1 ? "正常" : "被删除"}</a></td>
                        <td><fmt:formatDate value="${adminAllTopic.create_time}" pattern="yyyy-MM-dd"/></td>

                    </tr> <!--一条帖子结束啦-->
                </c:forEach>

                <tr>
                    <td style="text-align:left; padding:19px 0;padding-left:20px;">
                        <label><input type="checkbox" id="checkall"/>全选</label>
                    </td>
                    <td colspan="7" style="text-align:left;padding-left:20px;">
                        <a href="javascript:void(0)" class="button border-red icon-trash-o" style="padding:5px 15px;"
                           onclick="delBatch()"> 删除</a>
                        <a href="javascript:void(0)" style="padding:5px 15px; margin:0 10px;"
                           class="button border-blue icon-edit" onclick="indexBatch()"> 置顶</a>
                        <a href="javascript:void(0)" style="padding:5px 15px; margin:0 10px;"
                           class="button border-blue icon-edit" onclick="indexNoBatch()"> 取消置顶</a></td>
                </tr> <!--批量操作结束啦-->

            </volist>
        </table>
    </div>
</form>
<script type="text/javascript">
    function optionChange() {
        $("#typeName").val($("#typeList").val());
        $("#updateForm").attr("action", "${path}/admin/allTopic");
        $("#updateForm").submit();
    }

    //搜索
    function changesearch() {
        $("#updateForm").attr("action", "${path}/admin/allTopic");
        $("#updateForm").submit();
    }

    //单个删除
    function del() {
        if (confirm("您确定要删除吗?")) {
            $("#type").val("flag");
            $("#status").val("0");
            $("#updateForm").submit();
        }
    }

    //批量置顶
    function indexBatch() {
        var Checkbox = false;
        $("input[name='id[]']").each(function () {
            if (this.checked == true) {
                Checkbox = true;
            }
        });
        if (Checkbox) {
            var t = confirm("您确认要置顶所有内容吗？");
            if (t == false) return false;
            $("#type").val("index");
            $("#status").val("1");
            $("#updateForm").submit();
        }
        else {
            alert("请选择要操作的内容!");
            return false;
        }
    }
    //批量取消置顶
    function indexNoBatch() {
        var Checkbox = false;
        $("input[name='id[]']").each(function () {
            if (this.checked == true) {
                Checkbox = true;
            }
        });
        if (Checkbox) {
            var t = confirm("您确认取消置顶所有内容吗？");
            if (t == false) return false;
            $("#type").val("index");
            $("#status").val("0");
            $("#updateForm").submit();
        }
        else {
            alert("请选择要操作的内容!");
            return false;
        }
    }

    //批量删除
    function delBatch() {
        var Checkbox = false;
        $("input[name='id[]']").each(function () {
            if (this.checked == true) {
                Checkbox = true;
            }
        });
        if (Checkbox) {
            var t = confirm("您确认要删除选中的内容吗？");
            if (t == false) return false;
            $("#type").val("flag");
            $("#status").val("0");
            $("#updateForm").submit();
        }
        else {
            alert("请选择您要删除的内容!");
            return false;
        }
    }

    //全选
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


</script>
</body>
</html>