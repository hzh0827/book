package com.softeem.servlet;

import com.softeem.bean.User;
import com.softeem.service.UserService;
import com.softeem.service.impl.UserServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.CookieUtils;
import com.softeem.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
//静态导入
import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends BaseServlet {

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //不用自己手动的一个一个的获取用户信息
        /*String username = request.getParameter("username");
        String password = request.getParameter("password");*/
        Map<String, String[]> parameterMap = request.getParameterMap();
        /*for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            System.out.println(entry.getKey() +":"+ entry.getValue()[0]);
        }*/
        User user = new User();
        WebUtils.copyParamToBean(parameterMap,user);
        //User user = new User(null, username, password, null);

        UserService userService = new UserServiceImpl();

        try {
            User myuser = userService.login(user);
            if (userService.existsUsername(user.getUsername())) {
                if (userService.login(user) != null) {
                    Cookie namecookie = new Cookie("username", myuser.getUsername());
                    Cookie passcookie = new Cookie("password", myuser.getPassword());
                    namecookie.setMaxAge(60*60*24*7);
                    passcookie.setMaxAge(60*60*24*7);
                    response.addCookie(namecookie);
                    response.addCookie(passcookie);

                    HttpSession session = request.getSession();
                    session.setAttribute("user" , myuser);
                    request.setAttribute("msg","欢迎回来! ");
                    if (request.getParameter("oyxurl") != null && !request.getParameter("oyxurl").equals("")){
                        request.getRequestDispatcher(request.getParameter("oyxurl")).forward(request,response);
                    }else {
                        request.getRequestDispatcher("pages/user/success.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("msg","用户名或密码错误！请重新输入");
                    request.setAttribute("username",user.getUsername());
                    request.setAttribute("password",user.getPassword());
                    System.out.println("用户名或密码错误！！！请重新输入");
                    request.getRequestDispatcher("pages/user/login.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg","用户名不存在！请重新输入或注册新用户");
                request.setAttribute("username",user.getUsername());
                request.setAttribute("password",user.getPassword());
                System.out.println("用户名不存在！！！请重新输入或注册新用户");
                request.getRequestDispatcher("pages/user/login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");*/
        HttpSession session = request.getSession();

        // 获取Session 中的验证码
        String token = (String) request.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        // 删除 Session 中的验证码
        request.getSession().removeAttribute(KAPTCHA_SESSION_KEY);


        String code = request.getParameter("code");//验证码
        System.out.println("用户提交的验证码 = " + code);
        System.out.println("session中的验证码 = " + token);
        /*request.setAttribute("username", username);
        request.setAttribute("password", password);
        request.setAttribute("email", email);
        request.setAttribute("code", code);*/

        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        WebUtils.copyParamToBean(parameterMap,user);

        request.setAttribute("u", user);//为了回显
        UserService userService = new UserServiceImpl();
        try {
            if (token.equalsIgnoreCase(code)) {
                if (!userService.existsUsername(user.getUsername())) {
                    //User user = new User(null, username, password, email);
                    userService.registUser(user);
                    session.setAttribute("user" , user);
                    request.setAttribute("msg","注册成功! ");
                    request.getRequestDispatcher("pages/user/success.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg" , "用户名已存在，请更换！");
                    request.getRequestDispatcher("pages/user/regist.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("msg" , "验证码不正确！");
                request.getRequestDispatcher("pages/user/regist.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();//session立刻失效
        //session.removeAttribute("user");
        Cookie nameCookie = CookieUtils.findCookie("username", request.getCookies());
        Cookie passCookie = CookieUtils.findCookie("password", request.getCookies());
        if (nameCookie != null){
            nameCookie.setMaxAge(0);
            response.addCookie(nameCookie);
        }
        if (passCookie != null){
            passCookie.setMaxAge(0);
            response.addCookie(passCookie);
        }
        response.sendRedirect("index.jsp");
    }

    /*@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String methodName = request.getParameter("action");
        System.out.println("methodName = " + methodName);
        *//*if ("login".equals(action)){
            this.login(request,response);
        }else if ("regist".equals(action)){
            this.regist(request,response);

        }*//*
        Class claxx = this.getClass();
        try {
            Method method = claxx.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }*/




    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }*/
}
