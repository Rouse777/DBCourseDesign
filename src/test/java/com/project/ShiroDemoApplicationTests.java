package com.project;

import com.project.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroDemoApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    void DBTest() {
        System.out.println(userService.list());
    }
    @Test
    void DBSettings(){

    }
    @Test
    void contextLoads() {
    }

}
