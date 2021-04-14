package com.project.controller;


import com.project.result.Result;
import com.project.utils.OutputUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@Slf4j
@Api(tags = "数据导出")
public class OutputController {


    @Autowired
    OutputUtils output;

    private static final String[] filenames={"cell","kpi","prb_new"};

    @ApiOperation(value="数据导出,filename只有三种选择cell,kpi,prb_new")

    @PostMapping("/output")
    public Result out(@RequestParam String filename, HttpServletResponse response) throws Exception{

        boolean flag=false;
        System.out.println(filename);
        for(int i=0;i<3;i++){

            if(filename.equals(filenames[i]))flag=true;
        }
        if(flag==false)return Result.fail("非法数据表选项");

//        if(!dir.matches("output[0~5]"))return Result.fail("非法存储路径选项");


        try {
            File file = new File("/root/server/output0/"+filename+".txt");
//            File file = new File("D:\\Desktop"+"\\"+filename+".txt");
            if(!file.exists()) output.output(filename);

            log.info(file.getPath());
            String ext = "txt";
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return Result.success();

    }






}
