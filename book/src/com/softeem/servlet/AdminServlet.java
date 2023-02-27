package com.softeem.servlet;

import com.softeem.bean.Admin;
import com.softeem.service.AdminService;
import com.softeem.service.impl.AdminServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name="AdminServlet" , value="/AdminServlet")
public class AdminServlet extends BaseServlet {

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Admin admin = WebUtils.copyParamToBean(request.getParameterMap(), new Admin());
        AdminService adminService = new AdminServiceImpl() ;
        try {
            Admin myadmin = adminService.login(admin);
            if(myadmin != null){
                HttpSession session = request.getSession();
                session.setAttribute("admin" , myadmin);
                request.getRequestDispatcher("/pages/manager/manager.jsp").forward(request,response);
            }else{
                request.getRequestDispatcher("/login.jsp").forward(request,response);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
