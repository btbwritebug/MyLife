package com.itheima.dao.impl;


import com.itheima.dao.BookDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Repository
public class BookDaoImpl implements BookDao {

    @Value("${name}")
    private String name;

    @Override
    public void save() {
        System.out.println("book dao save ..." + name);
    }

    @PostConstruct
    public void init() {
        System.out.println("init");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("destroy");
    }
}
