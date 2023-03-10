package com.softeem.servlet;

import com.softeem.bean.Book;
import com.softeem.service.BookService;
import com.softeem.service.impl.BookServiceImpl;
import com.softeem.utils.BaseServlet;
import com.softeem.utils.Page;
import com.softeem.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "BookServlet", value = "/BookServlet")
public class BookServlet extends BaseServlet {

    protected void searchPrice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        int min = WebUtils.parseInt(request.getParameter("min"), 0);
        int max = WebUtils.parseInt(request.getParameter("max"), Integer.MAX_VALUE);
        try {
            Page<Book> page = bookService.page(pageNo,pageSize,min,max);
            page.setUrl("BookServlet?action=searchPrice&min="+min+"&max="+max+"&");
            request.setAttribute("page" , page);
            request.getRequestDispatcher("/home.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    /*StringBuilder strb = new StringBuilder("BookServlet?action=searchPrice&");
            if (request.getParameter("min") != null){
                strb.append("min=" + request.getParameter("min")+"&");
            }
            if (request.getParameter("max") != null){
                strb.append("max=" + request.getParameter("max")+"&");
            }
            page.setUrl(strb.toString());*/

    protected void searchPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        String name = request.getParameter("name") == null ? "" : request.getParameter("name");
        System.out.println("name = " + name);
        String author = request.getParameter("author")== null ? "" : request.getParameter("author");
        System.out.println("author = " + author);
        Integer min = WebUtils.parseInt(request.getParameter("min"), 0);
        Integer max = WebUtils.parseInt(request.getParameter("max"), 0);
        request.setAttribute("name", name);
        request.setAttribute("author", author);
        request.setAttribute("min", request.getParameter("min"));
        request.setAttribute("max", request.getParameter("max"));
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        try {
            Page<Book> page = bookService.page(pageNo,pageSize,name,author,new BigDecimal(min),new BigDecimal(max));
            page.setUrl("BookServlet?action=searchPage&name="+name+"&author="+author+"&min="+(min == 0?"":min)+"&max="+(max == 0?"":max)+"&");
            request.setAttribute("page" , page);
            request.getRequestDispatcher("/home.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void deleteByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = WebUtils.parseInt(request.getParameter("id") , 0);
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"), 1);
        BookService bookService = new BookServiceImpl();

        try {

            Book book = bookService.queryBookById(id);
            String path = "D:/" + book.getImgPath();
            File file = new File(path);
            file.delete();
            bookService.deleteBookById(id);
            //request.getRequestDispatcher("/BookServlet?action=list").forward(request,response);
            response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void queryByID(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String pageNo = request.getParameter("pageNo");
        BookService bookService = new BookServiceImpl();
        try {
            Book book = bookService.queryBookById(Integer.valueOf(id));
            request.setAttribute("book" , book);
            request.setAttribute("pageNo",pageNo);
            request.getRequestDispatcher("/pages/manager/book_edit.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        try {
            List<Book> bookList = bookService.queryBooks();
            request.setAttribute("booklist" , bookList);
            request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BookService bookService = new BookServiceImpl();
        int pageNo = WebUtils.parseInt(request.getParameter("pageNo"),1);
        int pageSize = WebUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        try {
            Page<Book> page = bookService.page(pageNo,pageSize);
            page.setUrl("BookServlet?action=page&");
            request.setAttribute("page" , page);
            request.getRequestDispatcher("/pages/manager/book_manager.jsp").forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Book book = new Book();
        BookService bookService = new BookServiceImpl();
        if(ServletFileUpload.isMultipartContent(request)){
            //??????FileItemFactory ???????????????
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            // ??????????????????????????????????????????ServletFileUpload ???
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                // ????????????????????????????????????????????????FileItem
                List<FileItem> list = servletFileUpload.parseRequest(request);
                //?????????6????????????????????????
                for (FileItem fileItem : list) {
                    //??????????????????????????????,???????????????????????????
                    if(fileItem.isFormField()){
                        //?????????????????????
                        //System.out.println(fileItem.getFieldName() +" = " + MyUtils.parseString(fileItem.getString()));
                        if("name".equals(fileItem.getFieldName())){
                            book.setName(fileItem.getString("utf-8"));//?????????
                        }else if("author".equals(fileItem.getFieldName())){
                            book.setAuthor(fileItem.getString("utf-8"));//????????????
                        }else if("price".equals(fileItem.getFieldName())){
                            book.setPrice(new BigDecimal(fileItem.getString()));//????????????
                        }else if("sales".equals(fileItem.getFieldName())){
                            book.setSales(Integer.valueOf(fileItem.getString()));//????????????
                        }else if("stock".equals(fileItem.getFieldName())){
                            book.setStock(Integer.parseInt(fileItem.getString()));//????????????
                        }
                    }else{
                        //??????????????????(????????????)
                        String filename = fileItem.getName();//???????????????
                        //????????? = 123.jpg       suffix = .jpg
                        String suffix = filename.substring(filename.lastIndexOf("."));
                        //?????????????????? + ?????? = ????????????
                        String newfilename =  System.currentTimeMillis() + suffix;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String format = simpleDateFormat.format(new Date());
                        File file = new File("D:/bookimg/"+format+"/");
                        if(!file.exists()){//??????????????????????????????????????????
                            file.mkdirs();//????????????
                        }
                        System.out.println(file.getAbsolutePath());
                        fileItem.write(new File(file,newfilename));//????????????
                        book.setImgPath("/bookimg/"+format+"/"+newfilename);//????????????
                    }
                }
                bookService.addBook(book);//?????????????????????????????????
                //request.getRequestDispatcher("/BookServlet?action=list").forward(request,response);
                response.sendRedirect("BookServlet?action=page");
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            out.println("??????????????????..??????????????????!");
        }
    }

    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String id = request.getParameter("id");
        String pageNo = request.getParameter("pageNo");
        PrintWriter out = response.getWriter();
        Book book = new Book();
        BookService bookService = new BookServiceImpl();
        if(ServletFileUpload.isMultipartContent(request)){
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                for (FileItem fileItem : list) {
                    if(fileItem.isFormField()){
                        if("id".equals(fileItem.getFieldName())) {
                            book.setId(Integer.valueOf(fileItem.getString()));
                            book = bookService.queryBookById(book.getId());
                        }else if("name".equals(fileItem.getFieldName())){
                            book.setName(fileItem.getString("utf-8"));
                        }else if("author".equals(fileItem.getFieldName())){
                            book.setAuthor(fileItem.getString("utf-8"));
                        }else if("price".equals(fileItem.getFieldName())){
                            book.setPrice(new BigDecimal(fileItem.getString()));
                        }else if("sales".equals(fileItem.getFieldName())){
                            book.setSales(Integer.valueOf(fileItem.getString()));
                        }else if("stock".equals(fileItem.getFieldName())){
                            book.setStock(Integer.parseInt(fileItem.getString()));
                        }/*else if ("oldPath".equals(fileItem.getFieldName())){
                            book.setImgPath(fileItem.getString());
                        }*/
                    }else{
                        //String imgpath = request.getParameter("imgpath");
                        String filename = fileItem.getName();
                        if (!filename.equals("")) {
                            String suffix = filename.substring(filename.lastIndexOf("."));
                            String newfilename = System.currentTimeMillis() + suffix;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String format = simpleDateFormat.format(new Date());
                            File file = new File("D:/bookimg/" + format + "/");
                            if (!file.exists()) {
                                file.mkdirs();
                            }
                            System.out.println(file.getAbsolutePath());
                            fileItem.write(new File(file, newfilename));
                            String path = "D:/" + book.getImgPath();
                            File temp = new File(path);
                            temp.delete();
                            book.setImgPath("/bookimg/" + format + "/" + newfilename);
                        }/*else{
                            book.setImgPath(imgpath);
                        }*/
                    }
                }
                bookService.updateBook(book);
                //request.getRequestDispatcher("/BookServlet?action=list").forward(request,response);
                response.sendRedirect("BookServlet?action=page&pageNo=" + pageNo);
            } catch (FileUploadException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            out.println("??????????????????..??????????????????!");
        }
    }
}
