package com.project;

import com.project.po.Cell;
import com.project.utils.LogUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.project.mapper")
public class MyApplication {

    public static void main(String[] args) {
        //不同jar包的多xml解析器冲突
        System.setProperty("javax.xml.parsers.DocumentBuilderFactory", "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
        SpringApplication.run(MyApplication.class, args);
    }

}
