package com.softeem.dao;

import com.softeem.bean.Emp;
import com.softeem.bean.EmpVo;
import com.softeem.uitl.BaseInterface;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface EmpDao extends BaseInterface<Emp> {
    public List<Emp> findEmpByDeptId(Integer deptId) throws SQLException;

    public List<EmpVo> myPage(Integer pageNumber, String name, String gender, Date joinDate) throws SQLException;
    public Integer MyPageRecord(String name, String gender, Date joinDate) throws SQLException ;
}
