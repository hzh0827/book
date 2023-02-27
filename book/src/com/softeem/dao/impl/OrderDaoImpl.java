package com.softeem.dao.impl;

import com.softeem.bean.Order;
import com.softeem.dao.OrderDao;
import com.softeem.utils.BaseDao;
import com.softeem.utils.JdbcUtils;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {
    @Override
    public List<Order> findAll() throws SQLException {
        return null;
    }

    @Override
    public void save(Order order) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into t_order(`order_id`,`create_time`,`price`,`status`,`user_id`) values(?,?,?,?,?)";
        queryRunner.update(connection,sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),order.getStatus(),order.getUserId());
    }

    @Override
    public void updateById(Order order) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        queryRunner.update(connection,"update t_order set status=? where order_id = ?",order.getStatus(),order.getOrderId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {

    }

    @Override
    public Order findById(Integer id) throws SQLException {
        return null;
    }

    @Override
    public List<Order> page(Integer pageNumber) throws SQLException {
        String sql = "select * from t_order order by create_time desc limit ? , ?";
        return queryRunner.query(sql, new BeanListHandler<Order>(Order.class,getRowProcessor()), (pageNumber-1)*pageSize, pageSize);
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_order";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }

    @Override
    public List<Order> page(Integer pageNumber, Integer userId) throws SQLException {
        String sql = "select * from t_order where user_id = ? order by create_time desc limit ? , ?";
        return queryRunner.query(sql, new BeanListHandler<Order>(Order.class,getRowProcessor()),  userId,(pageNumber-1)*pageSize, pageSize);
    }

    @Override
    public Integer pageRecord(Integer userId) throws SQLException {
        String sql = "select count(*) from t_order where user_id = ?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler, userId);
        return i.intValue();
    }
}
