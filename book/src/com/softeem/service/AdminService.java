package com.softeem.service;

import com.softeem.bean.Admin;

import java.sql.SQLException;

public interface AdminService {

    /**
     *	注册用户
     *	@param admin
     */
    public void registAdmin(Admin admin) throws SQLException;

    /**
     *	登录
     *	@param admin
     *	@return 如果返回null，说明登录失败，返回有值，是登录成功
     */
    public Admin login(Admin admin) throws SQLException;

    /**
     *	检查 用户名是否可用
     *	@param username
     *	@return 返回true 表示用户名已存在，返回false 表示用户名可用
     */
    public boolean existsUsername(String username) throws SQLException;

}
