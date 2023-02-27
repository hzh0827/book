package com.softeem.dao.impl;

import com.softeem.bean.Dept;
import com.softeem.dao.DeptDao;
import com.softeem.uitl.BaseDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class DeptDaoImpl extends BaseDao implements DeptDao {

    @Override
    public List<Dept> findAll() throws SQLException {
        BeanListHandler<Dept> handler = new BeanListHandler<>(Dept.class);
        List<Dept> deptList = queryRunner.query("select * from dept", handler);
        return deptList;
    }

    @Override
    public void save(Dept dept) throws SQLException {
       queryRunner.update("insert into dept values(null,?)",dept.getName());
    }

    @Override
    public void updateById(Dept dept) throws SQLException {
        queryRunner.update("update dept set name=? where id = ?",dept.getName(),dept.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        queryRunner.update("delete from dept where id = ? " , id);
    }

    @Override
    public Dept findById(Integer id) throws SQLException {
        BeanHandler<Dept> handler = new BeanHandler<>(Dept.class);
        Dept dept = queryRunner.query("select * from dept", handler);
        return dept;
    }

    @Override
    public List<Dept> page(Integer pageNumber) throws SQLException {
        String sql = "select * from dept limit ? , ?";
        BeanListHandler<Dept> handler = new BeanListHandler<>(Dept.class);
        List<Dept> deptList = queryRunner.query(sql, handler, (pageNumber - 1) * pageSize, pageSize);
        return deptList;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from dept";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }
}
