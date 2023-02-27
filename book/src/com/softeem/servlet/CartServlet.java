package com.softeem.servlet;

import com.softeem.bean.Book;
import com.softeem.bean.CartItem;
import com.softeem.service.Cart;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet", value = "/CartServlet")
public class CartServlet extends BaseServlet {
    /**
     *	加入购物车
     *	@param request
     *	@param response
     *	@throws ServletException
     *	@throws IOException
     */
    protected void addItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookServiceImpl bookService = new BookServiceImpl();
        HttpSession session = request.getSession();
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        try {
            Book book = bookService.queryBookById(id);
            CartItem cartItem = new CartItem(book.getId(), book.getName(), 1, book.getPrice(), book.getPrice());

            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }

            cart.addItem(cartItem);

            System.out.println("cart = " + cart);

            System.out.println("请求头 Referer 的值：" + request.getHeader("Referer"));

            session.setAttribute("lastname", cartItem.getName());

            response.sendRedirect(request.getHeader("Referer"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     *	删除商品项
     *	@param request
     *	@param response
     *	@throws ServletException
     *	@throws IOException
     */
    protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null){
            cart.deleteItem(id);
            response.sendRedirect(request.getHeader("Referer"));
        }
    }
    /**
     *	清空购物车
     *	@param request
     *	@param response
     *	@throws ServletException
     *	@throws IOException
     */
    protected void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取购物车对象
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            // 清空购物车
            cart.clear();
            // 重定向回原来购物车展示页面
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = WebUtils.parseInt(request.getParameter("id"), 0);
        int count = WebUtils.parseInt(request.getParameter("count"), 1);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {

            cart.updateCount(id,count);
        }
        // 重定向回原来购物车展示页面
        response.sendRedirect(request.getHeader("Referer"));
    }
}
