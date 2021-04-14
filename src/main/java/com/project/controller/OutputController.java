package com.project.controller;


import com.project.result.Result;
import com.project.utils.OutputUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "数据导出")
public class OutputController {


    @Autowired
    OutputUtils ouput;

    @ApiOperation(value="数据导出,filename只有三种选择cell,kpi,prb_new")
    @PostMapping("/output")
    public Result out(@RequestBody String dir,@RequestBody String filename) throws Exception{

        ouput.output(dir,filename);
        return Result.success();

    }






}
