package com.project.controller;


import com.project.result.Result;
import com.project.utils.CsvUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.project.utils.MultipartFileToFile.deleteTempFile;
import static com.project.utils.MultipartFileToFile.multipartFileToFile;

/**
 * <p>
 *  MRO 测量报告数据表 前端控制器
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/mrodata")
public class MrodataController {

    @ApiOperation("Mro数据导入，csv文件")
    @PostMapping("/csv")
    public Result importData2(@RequestParam("file") MultipartFile file) {

        File file1 = null;
        try{
            file1 = multipartFileToFile(file);
            System.out.println("file.path: "+file1.getAbsolutePath());
            CsvUtils.start(file1.getAbsolutePath(),"Mrodata");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        } finally {
            deleteTempFile(file1);
            return Result.success();
        }

    }

}

