package com.project.controller;


import com.csvreader.CsvReader;
import com.project.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.python.core.*;
import org.python.util.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping("/analyse")
@Slf4j
@Api(tags = "数据分析业务")
public class AnalysisController {


    @ApiOperation("主邻小区C2I干扰分析")
    @RequestMapping("/C2IDisturb")
    @SneakyThrows
    public Result Analyse0(){

        Runtime.getRuntime().exec("cmd \\c D:\\Desktop\\new_demo\\DBDesign\\src\\main\\java\\com\\project\\python\\3-6.py");
        String inpath="D:\\Desktop\\new_demo\\DBDesign\\src\\main\\java\\com\\project\\python\\data\\tbC2Inew.csv";
        ArrayList<String []> List=new ArrayList<String []>();
        CsvReader reader = new CsvReader(inpath,',', Charset.forName("utf-8"));
        reader.readHeaders();
        List.add(reader.getHeaders());
        while(reader.readRecord()) {
            List.add(reader.getValues());
        }
        reader.close();

        return new Result(200,"C2Inew",List);

    }




    @ApiOperation("重叠覆盖干扰分析")
    @RequestMapping("/C2IOverlap")
    @SneakyThrows
    public Result Analyse1(@RequestBody String x){

        Runtime.getRuntime().exec("cmd \\c D:\\Desktop\\new_demo\\DBDesign\\src\\main\\java\\com\\project\\python\\3-7.py");
        String inpath="D:\\Desktop\\new_demo\\DBDesign\\src\\main\\java\\com\\project\\python\\data\\tbC2I3.csv";
        ArrayList<String []> List=new ArrayList<String []>();
        CsvReader reader = new CsvReader(inpath,',', Charset.forName("utf-8"));
        reader.readHeaders();
        List.add(reader.getHeaders());
        while(reader.readRecord()) {
            List.add(reader.getValues());
        }
        reader.close();

        return new Result(200,"C2I3",List);


    }



    @ApiOperation("网络干扰结构分析")
    @GetMapping(value = "/Network",produces = {MediaType.IMAGE_JPEG_VALUE,MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] Analyse2() throws Exception {




        Runtime.getRuntime().exec("cmd \\c D:\\Desktop\\new_demo\\DBDesign\\src\\main\\java\\com\\project\\python\\3-9.py");
        String file="D:\\Desktop\\new_demo\\DBDesign\\src\\main\\java\\com\\project\\python\\data\\internet.png";
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;

    }





}
