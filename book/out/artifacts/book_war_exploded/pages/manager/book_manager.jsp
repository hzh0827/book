<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>图书管理</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
</head>
<body>

<%
    request.setCharacterEncoding("utf-8");
%>
<jsp:include page="header.jsp">
    <jsp:param name="msg" value="图书管理系统"/>
</jsp:include>
<%--<div id="header">
    <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    <span class="wel_word">图书管理系统</span>
    <div>
        <a href="pages/manager/book_manager.jsp">图书管理</a>
        <a href="pages/manager/order_manager.jsp">订单管理</a>
        <a href="index.jsp">返回商城</a>
    </div>
</div>--%>

<div id="main">
    <table style="margin-top: 5px">
        <tr>
            <td>名称</td>
            <td>价格</td>
            <td>作者</td>
            <td>销量</td>
            <td>库存</td>
            <td>封面</td>
            <td colspan="2">操作</td>
        </tr>

        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td><a href="pages/manager/book_add.jsp">添加图书</a></td>
        </tr>

        <c:forEach items="${page.items}" var="book">
            <tr>
                <td>${book.name}</td>
                <td>${book.price}</td>
                <td>${book.author}</td>
                <td>${book.sales}</td>
                <td>${book.stock}</td>
                <td><img src="${book.imgPath}" width="50px" height="50px"></td>
                <td><a href="BookServlet?action=queryByID&id=${book.id}&pageNo=${page.pageNo}">修改</a></td>
                <td><a href="javascript:void(0)" onclick="mydelete('${book.id}','${book.name}','${page.pageNo}')">删除</a></td>
            </tr>
        </c:forEach>
    </table>


    <jsp:include page="/pages/common/page.jsp"></jsp:include>

    <%--a--%>
    <%--页码输出的开始--%>
<%--    <c:choose>
        &lt;%&ndash;情况1：如果总页码小于等于5 的情况，页码的范围是：1-总页码&ndash;%&gt;
        <c:when test="${ requestScope.page.pageTotal <= 5 }">
            <c:set var="begin" value="1"/>
            <c:set var="end" value="${requestScope.page.pageTotal}"/>
        </c:when>
        &lt;%&ndash;情况2：总页码大于5 的情况&ndash;%&gt;
        <c:when test="${requestScope.page.pageTotal > 5}">
            <c:choose>
                &lt;%&ndash;小情况1：当前页码为前面3 个：1，2，3 的情况，页码范围是：1-5.&ndash;%&gt;
                <c:when test="${requestScope.page.pageNo <= 3}">
                    <c:set var="begin" value="1"/>
                    <c:set var="end" value="5"/>
                </c:when>
                &lt;%&ndash;小情况2：当前页码为最后3 个，8，9，10，页码范围是：总页码减4 - 总页码&ndash;%&gt;
                <c:when test="${requestScope.page.pageNo > requestScope.page.pageTotal-3}">
                    <c:set var="begin" value="${requestScope.page.pageTotal-4}"/>
                    <c:set var="end" value="${requestScope.page.pageTotal}"/>
                </c:when>
                &lt;%&ndash;小情况3：4，5，6，7，页码范围是：当前页码减2 - 当前页码加2&ndash;%&gt;
                <c:otherwise>
                    <c:set var="begin" value="${requestScope.page.pageNo-2}"/>
                    <c:set var="end" value="${requestScope.page.pageNo+2}"/>
                </c:otherwise>
            </c:choose>
        </c:when>
    </c:choose>


    &lt;%&ndash;页码输出的结束&ndash;%&gt;

            &lt;%&ndash;a&ndash;%&gt;



    <div id="page_nav" style="margin-top: 20px">
        &lt;%&ndash;<c:if test="${page.pageNo>1}">
            <a href="BookServlet?action=page&pageNo=1">首页</a>
            <a href="BookServlet?action=page&pageNo=${page.pageNo-1}">上一页</a>
        </c:if>&ndash;%&gt;

        <c:choose>
            <c:when test="${page.pageNo>1}">
                <a href="${page.url}pageNo=1">首页</a>
                <a href="${page.url}pageNo=${page.pageNo-1}">上一页</a>
            </c:when>
            <c:otherwise>
                <a>首页</a>
                <a>上一页</a>
            </c:otherwise>
        </c:choose>


        <c:forEach begin="${begin}" end="${end}" var="i">
            <c:if test="${i == requestScope.page.pageNo}">
                【${i}】
            </c:if>
            <c:if test="${i != requestScope.page.pageNo}">
                <a href="${page.url}pageNo=${i}">${i}</a>
            </c:if>
        </c:forEach>

        &lt;%&ndash;<a href="#">3</a>&ndash;%&gt;
        &lt;%&ndash;【${ requestScope.page.pageNo }】&ndash;%&gt;
        &lt;%&ndash;<a href="#">5</a>&ndash;%&gt;

        <c:choose>
            <c:when test="${page.pageNo < page.pageTotal}">
                <a href="${page.url}pageNo=${page.pageNo+1}">下一页</a>
                <a href="${page.url}pageNo=${requestScope.page.pageTotal}">末页</a>
            </c:when>
            <c:otherwise>
                <a>下一页</a>
                <a>末页</a>
            </c:otherwise>
        </c:choose>

        &lt;%&ndash;<c:if test="${page.pageNo < page.pageTotal}">
            <a href="BookServlet?action=page&pageNo=${page.pageNo+1}">下一页</a>
            <a href="BookServlet?action=page&pageNo=${requestScope.page.pageTotal}">末页</a>
        </c:if>&ndash;%&gt;

        共${ requestScope.page.pageTotal }页，${ requestScope.page.pageTotalCount }条记录
        到第<input value="${page.pageNo}" name="pn" id="pn_input"/>页
        <input type="button" value="确定" onclick="jump()">
    </div>
</div>--%>

<%@include file="/pages/common/bottom.jsp" %>
<%--<div id="bottom">
		<span>
			网上书城.Copyright &copy;2020
		</span>
</div>--%>
</body>
</html>

<script>
    /*function jump() {
        var inputobj = document.getElementById("pn_input")
        var pattern = /^[1-9][0-9]*$/;
        if (!pattern.test(inputobj.value)){
            alert("页码输入错误!必须为大于0的数字!" + inputobj.value);
            return false;
        }
        window.location.href = "${page.url}pageNo=" + inputobj.value;
        return true;
    }*/

    function mydelete(id, name, pageNo) {
        if (window.confirm("你确定删除" + name + "吗?")) {
            window.location.href = "BookServlet?action=deleteByID&id=" + id +"&pageNo=" + pageNo ;
        }
    }
</script>