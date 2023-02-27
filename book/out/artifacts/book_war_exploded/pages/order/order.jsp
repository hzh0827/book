<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>我的订单</title>
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
    <span class="wel_word">我的订单</span>
    <div>
        <span>欢迎<span class="um_span">${user.username},${user.id}</span>光临网上书城</span>
        <a href="OrderServlet?action=listOrder&userId=${user.id}">我的订单</a>
        <a href="UserServlet?action=logout">注销</a>&nbsp;&nbsp;
        <a href="index.jsp">返回</a>
    </div>
</div>

<div id="main">

    <table>
        <tr>
            <td>订单编号</td>
            <td>日期</td>
            <td>金额</td>
            <td>状态</td>
            <td>详情</td>
        </tr>
        <c:if test="${empty page.items}">
            <%--如果订单为空的情况--%>
            <tr>
                <td colspan="5"><a href="index.jsp">亲，当前订单为空！快跟小伙伴们去购物下单吧！！！</a></td>
            </tr>
        </c:if>


        <c:forEach items="${page.items}" var="order">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.createTime}</td>
                <td>${order.price}</td>
                <td>
                    <c:choose>
                        <c:when test="${order.status == 0}">
                            未发货
                        </c:when>
                        <c:when test="${order.status == 1}">
                            已发货
                        </c:when>
                        <c:otherwise>
                            已签收
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><a href="OrderServlet?action=listOrderItem&orderId=${order.orderId}">查看详情</a></td>
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