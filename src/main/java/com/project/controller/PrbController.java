package com.project.controller;


import com.project.po.Cell;
import com.project.po.Prb;
import com.project.result.Result;
import com.project.result.ResultCode;
import com.project.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 优化区17日-19日每PRB干扰 查询-15分钟 前端控制器
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/prb")
@Slf4j
@Api(tags = "PRB业务")
public class PrbController {
    @ApiOperation("PRB数据导入，Excel文件")
    @PostMapping("")
    public Result importData(@RequestParam("file") MultipartFile file){
        log.info("收到文件：{}，大小为{}KB",file.getOriginalFilename(),file.getSize()/1024.0);
        if(!ExcelUtils.isExcelName(file.getOriginalFilename()))
            return Result.any(ResultCode.ILLEGAL_ARGS,"文件扩展名不是.xlsx");
        List<Prb> prbs = ExcelUtils.getListByExcel(file, Prb.class);
        System.out.println(prbs);
        return Result.success();
    }
}

