package com.project;

import com.project.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//解决@Autowired在test中失效的问题
@SpringBootTest
public class MyApplicationTests {
    @Autowired
    private UserService userService;
    @Test
    public void DBTest(){
        System.out.println(userService.list());
    }

}
