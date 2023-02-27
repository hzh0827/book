package com.softeem.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//这种类型的类,我们统称JavaBean
//数据库中的表  与  java中的类
//相对应的一种关系
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Dept {
    private Integer id;
    private String name;
}
