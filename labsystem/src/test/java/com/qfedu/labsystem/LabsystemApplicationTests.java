package com.qfedu.labsystem;

import com.qfedu.labsystem.dao.*;
import com.qfedu.labsystem.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LabsystemApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    void myTest(){
        System.out.println(bookService.getBookListByLabId(6));
    }

}
