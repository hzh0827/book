package com.softeem.servlet;

import com.google.gson.Gson;
import com.softeem.bean.Dept;
import com.softeem.dao.DeptDao;
import com.softeem.dao.impl.DeptDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="DeptServlet" , value = "/DeptServlet")
public class DeptServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeptDao deptDao = new DeptDaoImpl() ;
        try {
            List<Dept> deptList = deptDao.findAll();
            resp.setContentType("text/html;charset=utf-8");
            Gson gson = new Gson();
            String s = gson.toJson(deptList);
            resp.getWriter().print(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
