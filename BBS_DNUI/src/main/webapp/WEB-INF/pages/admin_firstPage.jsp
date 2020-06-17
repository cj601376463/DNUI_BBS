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
    <script src="${path}/js/echarts/echarts.min.js"></script>
</head>
<%
    if (request.getAttribute("message") != null) {
        System.out.println("Message is not null");
        String msg = (String) request.getAttribute("message");
        out.write("<script>alert('" + msg + "');</script>");
    }

%>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="bar" style="width: 400px; height: 300px"></div>
<div id="angle" style="width: 400px; height: 300px; position: absolute; top: 10px; left: 500px"></div>
</body>
<script>
    var barChart = echarts.init(document.getElementById('bar'),'light');
    barChart.setOption({
        title:{
            text:'各类型帖子发帖数量'
        },
        tooltip: {},
        legend: {
            data: [{
                name: ['板块帖子数量']
            }]
        },
        xAxis: {
            data: []
        },
        yAxis:{},
        series: [{
            type: 'bar',  // 系列图表类型
            itemStyle: {
                normal:{
                    color: {
                        type:'linear',
                        x:0,
                        y:0,
                        x2:0,
                        y2:1,
                        colorStops:[
                            {
                                offset:0,color:'#26c4dd'//0处的颜色
                            },{
                            offset: 1,color: '#ffffff'//100处的颜色
                            }
                        ],
                        toGlobalCoord:false
                    }
                }
            },
            data: []  // 系列中的数据内容
        }]
    });
    var typeNames = [];
    var typeNameNums = [];
    $.ajax({
        type:"post",
        async:true,//异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url:'${path}/chart/typeBarChart',
        data:{},
        dataType:"json",//返回数据形式为json
        success:function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result){
                // console.log(Object.keys(result));
                var fData = formBarData(result);
                console.log(result);
                barChart.setOption({//加载数据图表
                    xAxis: {
                        data:fData.xAxData
                    },
                    series:[{
                        // 根据名字对应到相应的系列
                        data:fData.serData
                    }]
                });
            }
        },
        error:function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
        }
    });
    var angleChart = echarts.init(document.getElementById('angle'));
    angleChart.setOption({
        title:{
            text:'各类型帖子占比'
        },
        series:[
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                data: [
                ],
                roseType: 'angle',
                itemStyle: {
                    normal: {
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    });
    $.ajax({
        type:"post",
        async:true,//异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url:'${path}/chart/typeAngleChart',
        data:{},
        dataType:"json",//返回数据形式为json
        success:function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result){
                console.log("result",result);
                // var jsonData = [];
                // for (var x = 0;x<result.list.length;x++){
                //     jsonData.push(result.list[x]);
                // }
                // console.log("jsonData",jsonData);
                angleChart.setOption({
                    series:[{
                        data:result
                    }]
                })
            }
        },
        error:function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
        }
    });
    // angleChart.showLoading();

    function formBarData(data) {
        var xAxData = [];
        var serData = [];
        for (var i = 0; i<data.length; i++){
            var content = eval(data[i]);
            xAxData.push(Object.keys(content) || "");
            serData.push({
                name:Object.keys(content),
                value:content[Object.keys(content)] || 0
            })
        }
        return{
            xAxData:xAxData,
            serData:serData,
            result:eval(data)
        };
    }
</script>

</html>