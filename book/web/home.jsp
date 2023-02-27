<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>书城首页</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style>
        ::-webkit-scrollbar{
            display: none;
        }
    </style>
</head>
<body>

<div id="header">
    <a href="index.jsp">
        <img class="logo_img" alt="" src="static/img/logo.gif" width="230px" height="80px">
    </a>
    <span class="wel_word">网上书城</span>
    <div>
        <c:if test="${empty user }">
            <a href="pages/user/login.jsp">登录</a> |
            <a href="pages/user/regist.jsp">注册</a> &nbsp;&nbsp;
        </c:if>
        <c:if test="${not empty user}">
            <span>欢迎<span class="um_span">${user.username}</span>光临书城</span>
            <a href="OrderServlet?action=listOrder&userId=${user.id}">我的订单</a>
            <a href="UserServlet?action=logout">注销</a>
        </c:if>

        <a href="javascript:void(0)" onclick="toCart()">购物车</a>
        <%--<a href="pages/manager/login.jsp">后台管理</a>--%>
        <a href="login.jsp">后台管理</a>
    </div>
</div>
<div id="main">
    <div id="book">
        <div class="book_cond">
            <form action="BookServlet" method="get">
                <input type="hidden" name="action" value="searchPage">
                书名：<input type="text" name="name" value="${param.name}">
                作者：<input type="text" name="author" value="${param.author}">
                价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
                <input id="max" type="text" name="max" value="${param.max}"> 元
                <input type="submit" value="查询"/>
            </form>
            <%--<form action="BookServlet" method="get">
                <input name="action" type="hidden" value="searchPrice">
                价格：<input id="min" type="text" name="min" value="${param.min}"> 元 -
                <input id="max" type="text" name="max" value="${param.max}"> 元
                <input type="submit" value="查询"/>
            </form>--%>
        </div>
        <div style="text-align: center">
            <c:if test="${empty user}">
                <span></span><br>
                <div>
                    <span></span><br>
                </div>
            </c:if>
            <c:if test="${not empty user}">
                <c:if test="${empty sessionScope.cart.items}">
                    <span></span><br>
                    <div>
                        <span>当前购物车为空</span>
                    </div>
                </c:if>
                <c:if test="${not empty sessionScope.cart.items}">
                    <span>您的购物车中有${sessionScope.cart.totalCount}件商品</span>
                    <div>
                        您刚刚将《<span style="color: red">${sessionScope.lastname}</span>》加入到了购物车中
                        <%--<c:remove var="lastName" scope="session"></c:remove>--%>
                    </div>
                </c:if>
            </c:if>
        </div>

        <c:choose>
            <c:when test="${empty page.items}">
                <h1 style="color: red; text-align: center;">未找到相关数据!!!请重新查询!</h1>
            </c:when>
            <c:otherwise>
                <c:forEach items="${page.items}" var="book">
                    <div class="b_list">
                        <div class="img_div">
                            <img class="book_img" alt="" src="${book.imgPath}"/>
                        </div>
                        <div class="book_info" style="margin-left: 50px; ">
                            <div class="book_name">
                                <span class="sp1">书名:</span>
                                <span class="sp2">${book.name}</span>
                            </div>
                            <div class="book_author">
                                <span class="sp1">作者:</span>
                                <span class="sp2">${book.author}</span>
                            </div>
                            <div class="book_price">
                                <span class="sp1">价格:</span>
                                <span class="sp2">￥${book.price}</span>
                            </div>
                            <div class="book_sales">
                                <span class="sp1">销量:</span>
                                <span class="sp2">${book.sales}</span>
                            </div>
                            <div class="book_amount">
                                <span class="sp1">库存:</span>
                                <span class="sp2">${book.stock}</span>
                            </div>
                            <div class="book_add">
                                <%--<button>加入购物车</button>--%>
                                <input onclick="addToCart('${book.id}','${book.name}')" type="button" value="加入购物车"/>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>

    <jsp:include page="/pages/common/page.jsp"></jsp:include>

</div>

<%@include file="/pages/common/bottom.jsp"%>
<%--<jsp:forward page="pages/common/bottom.jsp"></jsp:forward>--%>
<%--<div id="bottom">
		<span>
			网上书城.Copyright &copy;2020
		</span>
</div>--%>
</body>
</html>
<script>
    function addToCart(bookId,bookname){
        /*

        alert("你确定要添加《" +bookname+"》到购物车吗?");*/
        if ('${sessionScope.user}' != ''){
            window.location.href = "CartServlet?action=addItem&id="+bookId;
        }else {
            alert("先登录在添加购物车");
            /*window.location.href = "pages/user/login.jsp?msg=先登录在添加购物车";*/
        }

    }

    function toCart(){
        /*console.log("开始");*/
        /*console.log('${sessionScope.user}');*/
        /*console.log("结束");*/

        if ('${sessionScope.user}' != ''){
            window.location.href = "pages/cart/cart.jsp";
        }else{
            <%--<c:set scope="request" var="msg" value="先登录在访问购物车"></c:set>--%>
            /*不支持*/
            <%--<jsp:forward page="pages/user/login.jsp"></jsp:forward>--%>
            window.location.href = "pages/user/login.jsp?msg=先登录在访问购物车";
        }
    }

</script>