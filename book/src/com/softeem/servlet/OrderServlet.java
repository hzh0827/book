package com.softeem.servlet;

import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.bean.User;
import com.softeem.service.Cart;
import com.softeem.service.OrderService;
import com.softeem.service.impl.OrderServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.Page;
import com.softeem.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OrderServlet", value = "/OrderServlet")
public class OrderServlet extends BaseServlet {

    protected void updateByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id") == null ? "" : request.getParameter("id");
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        OrderService orderService = new OrderServiceImpl();
        Order order = new Order();
        order.setOrderId(String.valueOf(id));
        order.setStatus(1);
        try {
            orderService.updateOrder(order);
            response.sendRedirect("OrderServlet?action=listOrder2&pageNo=" + pageNo);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    //生成订单
    protected void createOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (user == null){
            request.setAttribute("msg","亲,您登录超时,请重新登录!");
            request.getRequestDispatcher("/pages/user/login.jsp").forward(request,response);
        }

        OrderService orderService = new OrderServiceImpl();
        try {
            String orderId = orderService.createOrder(cart, user.getId());
            //
            session.setAttribute("orderId",orderId);
            response.sendRedirect(request.getContextPath()+"/pages/cart/checkout.jsp");

            /*response.sendRedirect("pages/cart/checkout.jsp");*/

        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

    protected void listOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int userId = WebUtils.parseInt(request.getParameter("userId"), 0);
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);

        try {
            Page<Order> page = orderService.page(pageNo, pageSize,userId);
            page.setUrl("OrderServlet?action=listOrder&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/order/order.jsp").forward(request,response);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void listOrder2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        try {
            Page<Order> page = orderService.page(pageNo, pageSize);
            page.setUrl("OrderServlet?action=listOrder2&");
            request.setAttribute("page", page);
            request.getRequestDispatcher("/pages/manager/order_manager.jsp").forward(request,response);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void listOrderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        String orderId = request.getParameter("orderId");
        try {
            Page<OrderItem> page = orderService.page(pageNo, pageSize,orderId);
            page.setUrl("OrderServlet?action=listOrderItem&orderId="+orderId+"&");
            request.setAttribute("page", page);
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/pages/order/order_message.jsp").forward(request,response);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected void listOrderItem2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), 4);
        String orderId = request.getParameter("orderId");
        try {
            Page<OrderItem> page = orderService.page(pageNo, pageSize,orderId);
            page.setUrl("OrderServlet?action=listOrderItem2&orderId="+orderId+"&");
            request.setAttribute("page", page);
            request.setAttribute("orderId", orderId);
            request.getRequestDispatcher("/pages/manager/order_manage.jsp").forward(request,response);
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
