package com.qfedu.labsystem;

import com.qfedu.labsystem.config.JwtConfig;
import com.qfedu.labsystem.dao.*;
import com.qfedu.labsystem.pojo.Book;
import com.qfedu.labsystem.pojo.Lab;
import com.qfedu.labsystem.pojo.Role;
import com.qfedu.labsystem.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class LabsystemApplicationTests {
//    @Autowired
//    private JwtConfig jwtConfig;
//    @Test
//    void contextLoads() throws InterruptedException {
//        String token = jwtConfig.createToken("admin");
//
//        System.out.println(token);
//
//        Thread.sleep(5000);
//        try {
//            System.out.print(jwtConfig.getSubject(token));
//        } catch (Exception e) {
////            throw new RuntimeException(e);
//            System.out.print("过期");
//        }
//    }
    @Autowired
    private GadgetMapper gadgetMapper;
    @Test
    void myTest(){
        System.out.println(gadgetMapper.getGadgetList());
    }

}
