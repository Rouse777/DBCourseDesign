package com.project.controller;


import com.project.po.Cell;
import com.project.po.Kpi;
import com.project.po.Prb;
import com.project.result.Result;
import com.project.result.ResultCode;
import com.project.service.PrbService;
import com.project.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
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
    private PrbService prbService;

    @Autowired
    public void setPrbService(PrbService prbService) {
        this.prbService = prbService;
    }

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
    @ApiOperation("查询PRB表中的所有ENODEB_NAME（网元名称）")
    @GetMapping("/enodeb-name")
    public Result getEnodebNames() {
        List<String> enodebNames = prbService.listEnodebName();
        return Result.success().put("enodebNames", enodebNames);
    }

    @ApiOperation("根据NODEB_NAME查询PRB记录，按StartTime升序")
    @GetMapping("/by-enodeb-name/{enodebName}")
    public Result getByEnodebName(@PathVariable String enodebName,
                                  @RequestParam(required = false) String from,
                                  @RequestParam(required = false) String to) {
        List<Prb> prbs = prbService.listByEnodebName(enodebName,from,to);
        return Result.success().put("prbs", prbs);
    }
}

