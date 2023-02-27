<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>订单管理</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
</head>
<body>

<%
    request.setCharacterEncoding("UTF-8");
%>
<jsp:include page="header.jsp">
    <jsp:param name="msg" value="订单管理系统"/>
</jsp:include>
<%--
<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">订单管理系统</span>
    <div>
        <a href="pages/manager/book_manager.jsp">图书管理</a>
        <a href="pages/manager/order_manager.jsp">订单管理</a>
        <a href="index.jsp">返回商城</a>
    </div>
</div>
--%>

<div id="main">
    <table>
        <tr>
            <td>订单编号</td>
            <td>用户编号</td>
            <td>日期</td>
            <td>金额</td>
            <td>状态</td>
            <td>详情</td>
        </tr>

        <c:forEach items="${page.items}" var="order">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.userId}</td>
                <td>${order.createTime}</td>
                <td>${order.price}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.status == 0}">
                            未发货<br />
                            <a href="OrderServlet?action=updateByID&id=${order.orderId}&pageNo=${page.pageNo}">点击发货</a>
                        </c:when>
                        <c:when test="${order.status == 1}">
                            已发货
                        </c:when>
                        <c:otherwise>
                            已签收
                        </c:otherwise>
                    </c:choose>

                </td>
                <td><a href="OrderServlet?action=listOrderItem2&orderId=${order.orderId}">查看详情</a></td>
            </tr>
        </c:forEach>
    </table>

    <c:choose>
        <c:when test="${requestScope.page.pageTotal>1}">
            <jsp:include page="/pages/common/page.jsp"></jsp:include>
        </c:when>
        <c:otherwise>
            <span></span>
        </c:otherwise>
    </c:choose>

</div>

<%@include file="/pages/common/bottom.jsp" %>
<%--<div id="bottom">
		<span>
			网上书城.Copyright &copy;2020
		</span>
</div>--%>
</body>
</html>