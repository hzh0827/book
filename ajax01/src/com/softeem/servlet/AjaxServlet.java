package com.softeem.servlet;

import com.google.gson.Gson;
import com.softeem.bean.Book;
import com.softeem.bean.Person;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;

@WebServlet(name = "AjaxServlet", value = "/AjaxServlet")
public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        System.out.println("AjaxServlet.doPost....");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        Person person = new Person(Integer.parseInt(id),name);
        Gson gson = new Gson();
        String personStr = gson.toJson(person);//person对象的json字符串
        PrintWriter out = response.getWriter();

        Book book = new Book();
        book.setId(1001);
        book.setName("java21天通");
        book.setAuthor("裴杰");
        book.setPrice(new BigDecimal(100));
        book.setStock(10);
        book.setSales(10);
        book.setImgPath("d:/123.jpg");
        book.setMydate(new Date());
        String bookstr = gson.toJson(book);

        out.print(bookstr);

        //ajax是无法重定 或者 请求转发的
        //request.getRequestDispatcher("/index.html").forward(request,response);
        //response.sendRedirect("http://www.taobao.com");
    }
}
