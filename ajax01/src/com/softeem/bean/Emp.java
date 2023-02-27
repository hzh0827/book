package com.softeem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

//这种类型的类,我们统称JavaBean
//数据库中的表  与  java中的类
//相对应的一种关系
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Emp {
    private Integer id;
    private String name;
    private String gender ;
    private Integer salary;
    private Date joinDate;
    private Integer deptId ;

}
