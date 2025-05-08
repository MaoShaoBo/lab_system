package com.qfedu.labsystem.service;

import com.qfedu.labsystem.pojo.Book;

import java.util.List;

public interface BookService {
    public void addBookList(Book book);

    List<Book> getBookList(Book book);

    List<Book> getBookListByLabId(Integer labId);

    void deleteBookList(Integer id);

    void updateBookList(Book book);
}
