package com.softeem.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.softeem.bean.Book;
import com.softeem.bean.Person;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.*;

public class MyTest {
    @Test
    public void test3() {
        Map<String,Person> personList = new HashMap<>();

        personList.put("aa",new Person(1, "小明"));
        personList.put("bb",new Person(2, "张学友"));
        personList.put("cc",new Person(3, "刘德华"));
        personList.put("dd",new Person(4, "郭富城"));

        Gson gson = new Gson();

        // 把List 转换为json 字符串
        String personMapJsonString = gson.toJson(personList);
        System.out.println(personMapJsonString);

        Map<Integer,Person> personMap2 = gson.fromJson(personMapJsonString, new
                TypeToken<HashMap<String,Person>>(){}.getType());
        //System.out.println(personMap2);
        Person person = personMap2.get("dd");
        //System.out.println(person);
    }
    //	1.2.2、List 和json 的互转
    @Test
    public void test2() {
        List<Person> personList = new ArrayList<>();

        personList.add(new Person(1, "小明"));
        personList.add(new Person(2, "张学友"));
        personList.add(new Person(3, "刘德华"));
        personList.add(new Person(4, "郭富城"));

        Gson gson = new Gson();

        // 把List 转换为json 字符串
        String personListJsonString = gson.toJson(personList);
        //System.out.println(personListJsonString);

        List<Person> list = gson.fromJson(personListJsonString,new TypeToken<List<Person>>(){}.getType());
        System.out.println(list);
        Person person = list.get(0);
        System.out.println(person);
    }

    @Test
    public void test1() {
        Person person = new Person(1, "小明好帅!");
        // 创建Gson 对象实例
        Gson gson = new Gson();
        // toJson 方法可以把java 对象转换成为json 字符串
        String personJsonString = gson.toJson(person);
        //System.out.println("---------"+personJsonString);
        // fromJson 把json 字符串转换回Java 对象
        // 第一个参数是json 字符串
        // 第二个参数是转换回去的Java 对象类型
        Person person1 = gson.fromJson(personJsonString, Person.class);
        System.out.println(person1);
    }

    @Test
    public void test0() {
        Book book = new Book();
        book.setId(1001);
        book.setName("java21天通");
        book.setAuthor("裴杰");
        book.setPrice(new BigDecimal(100));
        book.setStock(10);
        book.setSales(10);
        book.setImgPath("d:/123.jpg");
        book.setMydate(new Date());

        Gson gson = new Gson();
        String bookstr = gson.toJson(book);
        System.out.println(bookstr);

    }
}
