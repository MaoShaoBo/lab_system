package com.qfedu.labsystem.dao;

import com.qfedu.labsystem.pojo.Bookgadget;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface BookgadgetMapper {
    void addBookgadget(Bookgadget bookgadget);
    List<Bookgadget> getBookgadgetList(Bookgadget bookgadget);
    List<Bookgadget> getBookgadgetListByUsername(String bookgadget_username, Integer bookgadget_state);
    void deleteBookgadget(Integer id);
    void updateBookgadget(Bookgadget bookgadget);
}
