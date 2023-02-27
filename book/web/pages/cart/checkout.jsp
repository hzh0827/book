<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>结算页面</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }
    </style>
</head>
<body>

<div id="header">
    <a href="index.jsp">
        <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    </a>
    <span class="wel_word">结算</span>
    <div>
        <span>欢迎<span class="um_span">${user.username},${user.id}</span>光临网上书城</span>
        <a href="OrderServlet?action=listOrder&userId=${user.id}">我的订单</a>
        <a href="UserServlet?action=logout">注销</a>&nbsp;&nbsp;
        <a href="index.jsp">返回</a>
    </div>
</div>

<div id="main">

    <h1>你的订单已结算，订单号为${orderId}</h1>

    <%--<c:remove var="orderID" scope="session"></c:remove>--%>


</div>

<%@include file="/pages/common/bottom.jsp" %>
<%--<div id="bottom">
    <span>
        网上书城.Copyright &copy;2020
    </span>
</div>--%>
</body>
</html>