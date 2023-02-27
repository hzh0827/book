package com.softeem.dao;

import com.softeem.bean.Order;
import com.softeem.utils.BaseInterface;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao extends BaseInterface<Order> {
    public List<Order> page(Integer pageNumber, Integer userId) throws SQLException;

    public Integer pageRecord(Integer userId) throws SQLException;
}
