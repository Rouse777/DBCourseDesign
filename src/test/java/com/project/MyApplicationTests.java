package com.project;

import com.project.service.CellService;
import com.project.service.impl.CellServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyApplicationTests {
    @Autowired
    private CellService cellService;
    @Test
    public void test(){
        System.out.println(cellService.list());
    }
}
