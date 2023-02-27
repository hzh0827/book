package com.softeem.service.impl;

import com.softeem.bean.Book;
import com.softeem.bean.CartItem;
import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.dao.BookDao;
import com.softeem.dao.OrderDao;
import com.softeem.dao.OrderItemDao;
import com.softeem.dao.impl.BookDaoImpl;
import com.softeem.dao.impl.OrderDaoImpl;
import com.softeem.dao.impl.OrderItemDaoImpl;
import com.softeem.service.Cart;
import com.softeem.service.OrderService;
import com.softeem.utils.Page;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    //订单dao
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private BookDao bookDao = new BookDaoImpl();


    @Override
    public void updateOrder(Order order) throws SQLException {
        orderDao.updateById(order);
    }

    /**
     * 生成一个订单
     * 1.添加一个订单数据到数据库中的order不中
     * 2.此订单中至少有一个订单,至多会有N个..所以要将订单项目都添加到orderItem表中
     * 3.清空掉购物车中的数据
     *
     * @param cart 购物车对象
     * @param userId 用户id
     * @return 返回此订单的id
     */
    @Override
    public String createOrder(Cart cart, Integer userId) throws SQLException {
        //1.添加一个订单数据到数据库中的order不中
        String orderId = "" + System.currentTimeMillis() + userId;
        Order order = new Order();
        order.setOrderId(orderId);//生成的订单编号
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));//当前的系统时间
        order.setPrice(cart.getTotalPrice());//订单的总价
        order.setStatus(0);//设置0表达式,未发货
        order.setUserId(userId);//设置用户编号,因为这个订单要知道是谁下的订单
        orderDao.save(order);
        //2.此订单中至少有一个订单,至多会有N个..所以要将订单项目都添加到orderItem表中
        Map<Integer, CartItem> items = cart.getItems();
        for (Map.Entry<Integer, CartItem> entry : items.entrySet()) {
            OrderItem item = new OrderItem();
            item.setName(entry.getValue().getName());//设置订单项的名字
            item.setCount(entry.getValue().getCount());//设置订单项的数量
            item.setPrice(entry.getValue().getPrice());//设置订单项的单价
            item.setTotalPrice(entry.getValue().getTotalPrice());//设置订单项的总价
            item.setOrderId(orderId);//设置订单项的名字
            orderItemDao.save(item);
            //更新库存和销量
            Book book = bookDao.findById(entry.getValue().getId());//通过图书的id返回一个图书对象:book
            book.setSales(book.getSales()+entry.getValue().getCount());//设置销量
            book.setStock(book.getStock()-entry.getValue().getCount());//设置库存
            bookDao.updateById(book);//修改book的销量与库存
        }
        //3.清空掉购物车中的数据
        cart.clear();
        return orderId;
    }

    @Override
    public Page<Order> page(Integer pageNo, Integer pageSize) throws SQLException {
        Page<Order> page = new Page<>();
        Integer totalCount = orderDao.pageRecord();
        page.setPageTotalCount(totalCount);
        page.setPageTotal((totalCount + pageSize - 1 ) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(orderDao.page(page.getPageNo()));
        return page;
    }

    @Override
    public Page<Order> page(Integer pageNo, Integer pageSize, Integer userid) throws SQLException {
        Page<Order> page = new Page<>();
        Integer totalCount = orderDao.pageRecord(userid);
        page.setPageTotalCount(totalCount);
        page.setPageTotal((totalCount + pageSize - 1 ) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(orderDao.page(page.getPageNo(),userid));
        return page;
    }

    @Override
    public Page<OrderItem> page(Integer pageNo, Integer pageSize, String orderId) throws SQLException {
        Page<OrderItem> page = new Page<>();
        Integer totalCount = orderItemDao.pageRecord(orderId);
        page.setPageTotalCount(totalCount);
        page.setPageTotal((totalCount + pageSize - 1 ) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(orderItemDao.page(page.getPageNo(),orderId));
        return page;
    }
}
