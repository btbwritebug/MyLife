package com.itheima.service.impl;

import com.itheima.dao.BookDao;
import com.itheima.domain.Book;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    public static void main(String[] args) {
        Book book1 = new Book(1,"sad");
        Book book2 = new Book(2,"kjqwh");
        Book book3 = new Book(3,"menwrr");
        Book book4 = new Book(4,"xzicj");

        List<Book> list1 = new ArrayList<Book>();
        final List<Book> list2 = new ArrayList<Book>();

        list1.add(book1);
        list1.add(book2);
        list1.add(book3);

        list2.add(book2);
        list2.add(book3);
        list2.add(book4);



        Set<Book> hashSet = new HashSet();
        hashSet.addAll(list1);
        hashSet.addAll(list2);
        for(Book book:hashSet){
            System.out.println(book);
        }

    }
    @Autowired
    private BookDao bookDao;

    public boolean save(Book book) {
        bookDao.save(book);
        return true;
    }

    public boolean update(Book book) {
        bookDao.update(book);
        return true;
    }

    public boolean delete(Integer id) {
        bookDao.delete(id);
        return true;
    }

    public Book getById(Integer id) {
        return bookDao.getById(id);
    }

    public List<Book> getAll() {
        return bookDao.getAll();
    }
}
