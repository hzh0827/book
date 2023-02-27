package com.softeem.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.softeem.bean.Emp;
import com.softeem.bean.EmpVo;
import com.softeem.dao.EmpDao;
import com.softeem.dao.impl.EmpDaoImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "EmpPageServlet", value = "/EmpPageServlet")
public class EmpPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EmpDao empDao = new EmpDaoImpl() ;
        String pageNo = request.getParameter("pageNo");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String joinDate = request.getParameter("joinDate");
        Date mydate = null ;
        int pn = 0 ;
        try{
            pn = Integer.parseInt(pageNo);//当前页
        }catch (Exception e){
            pn = 1;
        }
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                mydate = simpleDateFormat.parse(joinDate);
            } catch (ParseException e) {
                mydate = null ;
            }

            List<EmpVo> empList = empDao.myPage(pn,name,gender,mydate); //查询的结果
            Integer record = empDao.MyPageRecord(name,gender,mydate);//总记录数据
            Integer pageTotal = 0 ;//总页数
            if(record % 4 == 0){
                pageTotal = record / 4 ;
            }else{
                pageTotal = record / 4 +1;
            }
            response.setContentType("text/htm;charset=utf-8");
            Gson gson=  new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Map map = new HashMap();
            map.put("empList" , empList);
            map.put("record" , record);
            map.put("pageTotal" , pageTotal);
            map.put("pageNo" , pn);

            String json = gson.toJson(map);
            System.out.println(json);
            response.getWriter().print(json);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
