package com.softeem.service.impl;

import com.softeem.bean.Book;
import com.softeem.dao.BookDao;
import com.softeem.dao.impl.BookDaoImpl;
import com.softeem.service.BookService;
import com.softeem.utils.Page;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl implements BookService {

    private BookDao bookDao = new BookDaoImpl();

    @Override
    public void addBook(Book book) throws SQLException {
        bookDao.save(book);
    }

    @Override
    public void deleteBookById(Integer id) throws SQLException {
        bookDao.deleteById(id);
    }

    @Override
    public void updateBook(Book book) throws SQLException {
        bookDao.updateById(book);
    }

    @Override
    public Book queryBookById(Integer id) throws SQLException {
        return bookDao.findById(id);
    }

    @Override
    public List<Book> queryBooks() throws SQLException {
        return bookDao.findAll();
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize) throws SQLException {
        Page<Book> page = new Page<>();
        Integer totalCount = bookDao.queryForPageTotalCount();
        page.setPageTotalCount(totalCount);
        //21 % 4 == 0
        /*if (totalCount % pageSize == 0){
            page.setPageTotal(totalCount / pageSize);//能整除
        }else {
            page.setPageTotal(totalCount / pageSize +1);//不能整除就加1
        }*/
        page.setPageTotal((totalCount + pageSize -1 ) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(bookDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize));
        return page;
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize, int min, int max) throws SQLException {
        Page<Book> page = new Page<>();
        Integer totalCount = bookDao.queryForPageByPriceTotalCount(min,max);
        page.setPageTotalCount(totalCount);
        page.setPageTotal((totalCount + pageSize -1 ) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(bookDao.queryForPageByPriceItems((page.getPageNo()-1)*pageSize,pageSize,min,max));
        return page;
    }

    @Override
    public Page<Book> page(int pageNo, int pageSize, String name, String author, BigDecimal min, BigDecimal max) throws SQLException {
        Page<Book> page = new Page<>();
        Integer totalCount = bookDao.queryForPageTotalCount(name, author,min,max);
        page.setPageTotalCount(totalCount);
        page.setPageTotal((totalCount + pageSize -1 ) / pageSize);
        page.setPageNo(pageNo);
        page.setItems(bookDao.queryForPageItems((page.getPageNo()-1)*pageSize,pageSize,name,author,min,max));
        return page;
    }

    /*public static void main(String[] args) throws SQLException {
        BookServiceImpl bookService = new BookServiceImpl();
        Page<Book> page = bookService.page(1,4,10,333);
        System.out.println("page = " + page);
    }*/
}
