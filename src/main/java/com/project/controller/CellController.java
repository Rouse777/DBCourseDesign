package com.project.controller;


import com.project.po.Cell;
import com.project.result.Result;
import com.project.result.ResultCode;
import com.project.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.bcel.generic.FSUB;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 小区/基站工参表 前端控制器
 * </p>
 *
 * @author 
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/cell")
@Slf4j
@Api(tags = "cell业务")
public class CellController {
    @ApiOperation("tbCell数据导入，Excel文件")
    @PostMapping("")
    public Result importData(@RequestParam("file")MultipartFile file){
        log.info("收到文件：{}，大小为{}KB",file.getOriginalFilename(),file.getSize()/1024.0);
        if(!ExcelUtils.isExcelName(file.getOriginalFilename()))
            return Result.any(ResultCode.ILLEGAL_ARGS,"文件扩展名不是.xlsx");
        List<Cell> cells = ExcelUtils.getListByExcel(file, Cell.class);
        System.out.println(cells);
        return Result.success();
    }
}

