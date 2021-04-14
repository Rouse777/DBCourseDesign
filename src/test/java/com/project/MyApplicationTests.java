package com.project;

import com.project.utils.OutputUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = MyApplication.class)
@RunWith(SpringRunner.class)
public class MyApplicationTests {

    @Autowired
    OutputUtils output;

    @Test
    public void test() throws Exception{


    }

}
