package com.softeem.servlet;

import com.google.gson.Gson;
import com.softeem.bean.Emp;
import com.softeem.dao.EmpDao;
import com.softeem.dao.impl.EmpDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name="EmpServlet",value="/EmpServlet")
public class EmpServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmpDao empDao = new EmpDaoImpl();
        try {
            List<Emp> empList = empDao.findEmpByDeptId(Integer.parseInt(req.getParameter("deptid")));

            Gson gson = new Gson() ;
            String json = gson.toJson(empList);

            resp.setContentType("text/html;charset=utf-8");

            resp.getWriter().print(json);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
}
