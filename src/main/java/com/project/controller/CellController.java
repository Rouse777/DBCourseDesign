package com.project.controller;


import com.project.po.Cell;
import com.project.result.Result;
import com.project.result.ResultCode;
import com.project.service.CellService;
import com.project.utils.CsvUtils;
import com.project.utils.ExcelUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.project.utils.MultipartFileToFile.multipartFileToFile;

/**
 * <p>
 * 小区/基站工参表 前端控制器
 * </p>
 *
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/cell")
@Slf4j
@Api(tags = "CELL业务")
public class CellController {
    private CellService cellService;

    @Autowired
    public void setCellService(CellService cellService) {
        this.cellService = cellService;
    }

    @ApiOperation("tbCell数据导入，Excel文件")
    @PostMapping("/excel")
    public Result importData(@RequestParam("file") MultipartFile file) {
        log.info("收到文件：{}，大小为{}KB", file.getOriginalFilename(), file.getSize() / 1024.0);
        if (ExcelUtils.isNotExcelName(file.getOriginalFilename()))
            return Result.any(ResultCode.ILLEGAL_ARGS, "文件扩展名不是.xlsx");
        List<Cell> cells = ExcelUtils.getListByExcel(file, Cell.class);
        cellService.cleanAndSaveBatch(cells);
        return Result.success();
    }



    @ApiOperation("Cell数据导入，csv文件")
    @PostMapping("/csv")
    public Result importData2(@RequestParam("file") MultipartFile file) {
        log.info("收到文件：{}，大小为{}KB", file.getOriginalFilename(), file.getSize() / 1024.0);

        File file1 = null;
        try {
            file1 = multipartFileToFile(file);
            System.out.println("file.path: " + file1.getAbsolutePath());
            CsvUtils.start(file1.getAbsolutePath(), "Cell");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail();
        }
        return Result.success();
    }





    @ApiOperation("获取所有小区名称(SECTOR_NAME)")
    @GetMapping("/sector-name")
    public Result getAllSectorName() {
        List<String> sectorNames = cellService.listSectorNames();
        return Result.success().put("sectorNames", sectorNames);
    }

    @ApiOperation("小区配置信息查询，SECTOR_NAME")
    @GetMapping("/by-sector-name")
    public Result getInfoBySectorName(@RequestParam String sectorName) {
        Cell cell = cellService.getBySectorName(sectorName);
        if (cell == null) {
            return Result.fail();
        }
        return Result.success().put("cell", cell);
    }

    @ApiOperation("小区配置信息查询，SECTOR_ID")
    @GetMapping("/by-sector-id")
    public Result getInfoBySectorId(@RequestParam String sectorId) {
        Cell cell = cellService.getById(sectorId);
        if (cell == null) {
            return Result.fail();
        }
        return Result.success().put("cell", cell);
    }

    @ApiOperation("查询所有ENODEBID")
    @GetMapping("/enodebid")
    public Result getAllEnodebid() {
        List<Integer> list = cellService.listEnodebid();
        return Result.success().put("enodebids", list);
    }

    @ApiOperation("查询所有ENODEB_NAME")
    @GetMapping("/enodeb-name")
    public Result getAllEnodebName() {
        List<String> list = cellService.listEnodebName();
        return Result.success().put("enodebName", list);
    }
    @ApiOperation("基站 eNodeB 信息查询，根据ENODEB_NAME")
    @GetMapping("/by-enodeb-name")
    public Result getInfoByEnodebName(@RequestParam String enodebName) {
        List<Cell> cells = cellService.listByEnodebName(enodebName);
        return Result.success().put("cells", cells);
    }
    @ApiOperation("基站 eNodeB 信息查询，根据ENODEBID")
    @GetMapping("/by-enodebid")
    public Result getInfoByEnodebid(@RequestParam Integer enodebid) {
        List<Cell> cells = cellService.listByEnodebid(enodebid);
        return Result.success().put("cells", cells);
    }
}

