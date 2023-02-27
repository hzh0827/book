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

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User user = new User(null, username, password, null);
        UserService userService = new UserServiceImpl();
        try {
            if (userService.existsUsername(username)) {
                if (userService.login(user) != null) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user" , user);
                    req.setAttribute("msg","欢迎回来! ");
                    req.getRequestDispatcher("pages/user/success.jsp").forward(req, resp);
                } else {
                    req.setAttribute("msg","用户名或密码错误！请重新输入");
                    req.setAttribute("username",username);
                    req.setAttribute("password",password);
                    System.out.println("用户名或密码错误！！！请重新输入");
                    req.getRequestDispatcher("pages/user/login.jsp").forward(req, resp);
                }
            } else {
                req.setAttribute("msg","用户名不存在！请重新输入或注册新用户");
                req.setAttribute("username",username);
                req.setAttribute("password",password);
                System.out.println("用户名不存在！！！请重新输入或注册新用户");
                req.getRequestDispatcher("pages/user/login.jsp").forward(req, resp);
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
