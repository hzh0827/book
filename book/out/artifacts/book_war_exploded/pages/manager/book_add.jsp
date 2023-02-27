<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑图书</title>
    <base href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <link type="text/css" rel="stylesheet" href="static/css/style.css">
    <style type="text/css">
        h1 {
            text-align: center;
            margin-top: 200px;
        }

        h1 a {
            color: red;
        }

        input {
            text-align: center;
        }
    </style>
</head>
<body>
<%
    request.setCharacterEncoding("utf-8");
%>
<jsp:include page="header.jsp">
    <jsp:param name="msg" value="添加图书"/>
</jsp:include>

<div id="main">
    <form action="BookServlet?action=add" enctype="multipart/form-data" method="post">
        <table>
            <tr>
                <td>名称</td>
                <td><input name="name" type="text" /></td>
            </tr>
            <tr>
                <td>价格</td>
                <td><input name="price" type="text" id="price" onchange="decide_price()" /></td>
            </tr>
            <tr>
                <td>作者</td>
                <td><input name="author" type="text" /></td>
            </tr>
            <tr>
                <td>销量</td>
                <td><input name="sales" type="text" id="sales" onchange="decide_sales()" /></td>
            </tr>
            <tr>
                <td>库存</td>
                <td><input name="stock" type="text" id="stock" onchange="decide_stock()" /></td>
            </tr>
            <tr>
                <td>封面</td>
                <td>
                    <input name="imgPath" type="file" id="file_input" onchange="show_image()" />
                    <img src="" alt="" id="show_img" width="100px" height="100px" style="display: none;" />
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="提交"/></td>
            </tr>

        </table>
    </form>


</div>
<%@include file="/pages/common/bottom.jsp"%>
</body>
</html>
<script>
    function show_image() {
        //抓取到上传图片的input标签的信息
        file_input = document.getElementById("file_input");
        //抓取到需要展示预览图的img标签信息
        show_img = document.getElementById("show_img");
        //回去预览图的src属性信息
        show_img.src = window.URL.createObjectURL(file_input.files[0]);
        //改变style属性中block的值
        show_img.style.display = 'block';
    }

    function decide_price() {
        var pricev = document.getElementById("price").value;
        var ppattern = /^[0-9]*$/;
        if (!ppattern.test(pricev)){
            alert("价格输入错误!必须为全数字!");
            return false;
        }
        return true;
    }

    function decide_sales() {
        var salesv = document.getElementById("sales").value;
        var spattern = /^[0-9]*$/;
        if (!spattern.test(salesv)){
            alert("销量输入错误!必须为全数字!");
            return false;
        }
        return true;
    }

    function decide_stock() {
        var stockv = document.getElementById("stock").value;
        var stpattern = /^[0-9]*$/;
        if (!stpattern.test(stockv)){
            alert("库存输入错误!必须为全数字!");
            return false;
        }
        return true;
    }
</script>
