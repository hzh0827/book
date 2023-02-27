package com.softeem.dao.impl;

import com.softeem.bean.Emp;
import com.softeem.bean.EmpVo;
import com.softeem.dao.EmpDao;
import com.softeem.uitl.BaseDao;
import com.softeem.uitl.MyDataSource;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
public class EmpDaoImpl extends BaseDao implements EmpDao {

    @Override
    public List<Emp> findEmpByDeptId(Integer deptId) throws SQLException {
        BeanProcessor bean = new GenerousBeanProcessor();
        BasicRowProcessor processor = new BasicRowProcessor(bean);
        return queryRunner.query("select * from emp where dept_id =?", new BeanListHandler<>(Emp.class, processor), deptId);
    }

    @Override
    public List<Emp> findAll() {
        return null;
    }

    @Override
    public void save(Emp emp) {

    }

    @Override
    public void updateById(Emp emp) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Emp findById(Integer id) {
        return null;
    }

    @Override
    public List<Emp> page(Integer pageNumber) throws SQLException {
        String sql = "select * from emp limit ? , ?";
        BeanProcessor bean = new GenerousBeanProcessor();
        BasicRowProcessor processor = new BasicRowProcessor(bean);
        return queryRunner.query(sql, new BeanListHandler<>(Emp.class, processor), (pageNumber - 1) * pageSize, pageSize);
    }

    public List<EmpVo> myPage(Integer pageNumber, String name, String gender, Date joinDate) throws SQLException {
        String sql = "select e.*,d.name as deptName from emp e, dept d where 1=1 ";
        ArrayList list = new ArrayList();
        if (name != null && !"".equals(name)) {
            sql += " and e.name like ?";
            list.add("%" + name + "%");
        }
        if (gender != null && !"".equals(gender)) {
            sql += " and e.gender = ?";
            list.add(gender);
        }
        if (joinDate != null) {
            sql += " and e.join_date = ?";
            list.add(joinDate);
        }
        String end = " and e.dept_id = d.id  limit ? , ?";
        sql += end;
        list.add((pageNumber - 1) * pageSize);
        list.add(pageSize);
        BeanProcessor bean = new GenerousBeanProcessor();
        BasicRowProcessor processor = new BasicRowProcessor(bean);
        return queryRunner.query(sql, new BeanListHandler<>(EmpVo.class, processor), list.toArray());
    }

    public Integer MyPageRecord(String name, String gender, Date joinDate) throws SQLException {
        String sql = "select count(*) from emp e, dept d where 1=1 ";
        ArrayList list = new ArrayList();
        if (name != null && !"".equals(name)) {
            sql += " and e.name like ?";
            list.add("%" + name + "%");
        }
        if (gender != null && !"".equals(gender)) {
            sql += " and e.gender = ?";
            list.add(gender);
        }
        if (joinDate != null) {
            sql += " and e.join_date = ?";
            list.add(joinDate);
        }
        String end = " and e.dept_id = d.id";
        sql += end;
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler, list.toArray());
        return i.intValue();
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from emp e, dept d where e.dept_id = d.id ";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }

}
