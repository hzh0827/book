package com.softeem.servlet;

import com.softeem.bean.User;
import com.softeem.service.UserService;
import com.softeem.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RegistServlet", value = "/RegistServlet")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String code = req.getParameter("code");

        req.setAttribute("username", username);
        req.setAttribute("password", password);
        req.setAttribute("email", email);
        req.setAttribute("code", code);

        UserService userService = new UserServiceImpl();
        try {
            if ("1234".equalsIgnoreCase(code)) {
                if (!userService.existsUsername(username)) {
                    User user = new User(null, username, password, email);
                    userService.registUser(user);
                    HttpSession session = req.getSession();
                    session.setAttribute("user" , user);
                    req.setAttribute("msg","注册成功! ");
                    req.getRequestDispatcher("pages/user/success.jsp").forward(req, resp);
                } else {

                    req.setAttribute("msg" , "用户名已存在，请更换！");
                    req.getRequestDispatcher("pages/user/regist.jsp").forward(req, resp);
                }
            } else {

                req.setAttribute("msg" , "验证码不正确！");
                req.getRequestDispatcher("pages/user/regist.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
