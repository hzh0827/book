package com.softeem.service.impl;

import com.softeem.bean.Admin;
import com.softeem.dao.AdminDao;
import com.softeem.dao.impl.AdminDaoImpl;
import com.softeem.service.AdminService;


import java.sql.SQLException;

//服务
public class AdminServiceImpl implements AdminService {

    private AdminDao adminDao = new AdminDaoImpl();

    @Override
    public void registAdmin(Admin admin) throws SQLException {
        adminDao.save(admin);
    }

    /**
     *	登录
     *	@param admin
     *	@return 如果返回null，说明登录失败，返回有值，是登录成功
     */
    @Override
    public Admin login(Admin admin) throws SQLException {
        return adminDao.queryAdminByUsernameAndPassword(admin.getUsername(),admin.getPassword());
    }

    /**
     *	检查 用户名是否可用
     *	@param username
     *	@return 返回true 表示用户名已存在，返回false 表示用户名可用
     */
    @Override
    public boolean existsUsername(String username) throws SQLException {
        Admin admin = adminDao.queryAdminByUsername(username);
        return admin != null;
    }
}
