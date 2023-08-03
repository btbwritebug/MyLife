package com.itheima.service;

import com.itheima.domain.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookService {
    /**
     * 保存
     * @param book
     * @return
     */
    boolean save(Book book);

    /**
     * 修改
     * @param book
     * @return
     */
    boolean update(Book book);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    boolean delete(Integer id);

    /**
     * 根据id获取
     * @param id
     * @return
     */
    Book getById(Integer id);

    /**
     * 获取全部
     * @return
     */
    List<Book> getAll();
}
