package com.softeem.dao.impl;

import com.softeem.bean.Book;
import com.softeem.dao.BookDao;
import com.softeem.utils.BaseDao;
import com.softeem.utils.JdbcUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.GenerousBeanProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class BookDaoImpl extends BaseDao implements BookDao {
    @Override
    public List<Book> findAll() throws SQLException {
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class,getRowProcessor());
        List<Book> bookList = queryRunner.query("select * from t_book order by id desc", handler);
        return bookList;
    }

    @Override
    public void save(Book book) throws SQLException {
        queryRunner.update("insert into t_book values(null,?,?,?,?,?,?)",
                book.getName(), book.getPrice(), book.getAuthor(),
                book.getSales(), book.getStock(), book.getImgPath());
    }

    @Override
    public void updateById(Book book) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        queryRunner.update(connection,"update t_book set name=?, price=?, author=?, sales=?, stock=?, img_path=? where id = ?",
                book.getName(), book.getPrice(), book.getAuthor(),
                book.getSales(), book.getStock(), book.getImgPath(),
                book.getId());
    }

    @Override
    public void deleteById(Integer id) throws SQLException {
        queryRunner.update("delete from t_book where id = ? " , id);
    }

    @Override
    public Book findById(Integer id) throws SQLException {
        BeanHandler<Book> handler = new BeanHandler<>(Book.class,getRowProcessor());
        Book book = queryRunner.query("select * from t_book where id = ?", handler,id);
        return book;
    }

    @Override
    public List<Book> page(Integer pageNumber) throws SQLException {
        String sql = "select * from t_book limit ? , ?";
        BeanListHandler<Book> handler = new BeanListHandler<>(Book.class,getRowProcessor());
        List<Book> bookList = queryRunner.query(sql, handler, (pageNumber - 1) * pageSize, pageSize);
        return bookList;
    }

    @Override
    public Integer pageRecord() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }

    @Override
    public List<Book> findByKeyword(String keyword) throws SQLException {
        String sql = "select * from t_book where name like '%" + keyword + "%'";
        return queryRunner.query(sql,new BeanListHandler<>(Book.class,getRowProcessor()));
    }

    //查询book表的总记录数
    @Override
    public Integer queryForPageTotalCount() throws SQLException {
        String sql = "select count(*) from t_book";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler);
        return i.intValue();
    }

    //分页查询
    @Override
    public List<Book> queryForPageItems(int begin, int pageSize) throws SQLException {
        String sql = "select * from t_book order by id desc limit ?,?";
        return queryRunner.query(sql,new BeanListHandler<Book>(Book.class,getRowProcessor()),begin,pageSize);
    }

    @Override
    public Integer queryForPageByPriceTotalCount(int min, int max) throws SQLException {
        String sql = "select count(*) from t_book where price between ? and ?";
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql, handler, min, max);
        return i.intValue();
    }

    @Override
    public List<Book> queryForPageByPriceItems(int begin, int pageSize, int min, int max) throws SQLException {
        String sql = "select * from t_book where price between ? and ? order by price desc limit ?,?";
        return queryRunner.query(sql,new BeanListHandler<Book>(Book.class,getRowProcessor()),min,max,begin,pageSize);
    }

    @Override
    public Integer queryForPageTotalCount(String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        StringBuilder sql = new StringBuilder("select count(*) from t_book where 1 = 1 ");
        ArrayList list = new ArrayList();
        if ( name != null && !"".equals(name)){
            sql.append(" and name like ?");
            list.add("%"+name+"%");
        }
        if ( author != null && !"".equals(author)){
            sql.append(" and author like ?");
            list.add("%"+author+"%");
        }
        if ( (min != null && min.signum() ==1) &&(max != null && max.signum() ==1) ){
            if (min.compareTo(max) == 1){
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price between ? and ? ");
            list.add(min);
            list.add(max);
        }else if(min != null && min.signum() ==1){
            sql.append(" and price > ? ");
            list.add(min);
        }else if(max != null && max.signum() ==1){
            sql.append(" and price < ? ");
            list.add(max);
        }
        ScalarHandler<Long> handler = new ScalarHandler<>();
        Long i = queryRunner.query(sql.toString(), handler, list.toArray());
        return i.intValue();
    }

    @Override
    public List<Book> queryForPageItems(int begin, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        StringBuilder sql = new StringBuilder("select * from t_book where 1=1 ");
        ArrayList list = new ArrayList();
        if ( name != null && !"".equals(name)){
            sql.append(" and name like ?");
            list.add("%"+name+"%");
        }
        if ( author != null && !"".equals(author)){
            sql.append(" and author like ?");
            list.add("%"+author+"%");
        }
        if ( (min != null && min.signum() ==1) &&(max != null && max.signum() ==1) ){
            if (min.compareTo(max) == 1){
                BigDecimal temp = min;
                min = max;
                max = temp;
            }
            sql.append(" and price between ? and ? ");
            list.add(min);
            list.add(max);
        }else if(min != null && min.signum() ==1){
            sql.append(" and price > ? ");
            list.add(min);
        }else if(max != null && max.signum() ==1){
            sql.append(" and price < ? ");
            list.add(max);
        }
        String end = " order by id desc limit ?,?";
        sql.append(end);
        list.add(begin);
        list.add(pageSize);
        return queryRunner.query(sql.toString(),new BeanListHandler<Book>(Book.class,getRowProcessor()),list.toArray());
    }
}
