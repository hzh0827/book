package com.softeem.service;

import com.softeem.bean.Order;
import com.softeem.bean.OrderItem;
import com.softeem.utils.Page;

import java.sql.SQLException;

public interface OrderService {
    public void updateOrder(Order order) throws SQLException;

    public String createOrder(Cart cart,Integer userId) throws SQLException;
    public Page<Order> page(Integer pageNo, Integer pageSize) throws SQLException;
    public Page<Order> page(Integer pageNo, Integer pageSize,Integer userid) throws SQLException;
    public Page<OrderItem> page(Integer pageNo, Integer pageSize, String orderId) throws SQLException;
}
