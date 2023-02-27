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
            <td>商品名称</td>
            <td>数量</td>
            <td>单价</td>
            <td>金额</td>
            <%--<td>订单编号</td>--%>
        </tr>
        <c:forEach items="${page.items}" var="orderItem">
            <tr>
                <td>${orderItem.name}</td>
                <td>${orderItem.count}</td>
                <td>${orderItem.price}</td>
                <td>${orderItem.totalPrice}</td>
                <%--<td>${orderItem.orderId}</td>--%>
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