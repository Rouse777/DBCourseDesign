package com.project.controller;


import com.project.po.Prb;
import com.project.po.Prbnew;
import com.project.result.Result;
import com.project.service.PrbnewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2021-03-28
 */
@RestController
@RequestMapping("/prbnew")
@Api(tags = "PRBNew业务")
public class PrbnewController {
    private PrbnewService prbnewService;

    @Autowired
    public void setPrbnewService(PrbnewService prbnewService) {
        this.prbnewService = prbnewService;
    }

    @ApiOperation("查询PRBnew表中的所有ENODEB_NAME（网元名称）")
    @GetMapping("/enodeb-name")
    public Result getEnodebNames() {
        List<String> enodebNames = prbnewService.listEnodebName();
        return Result.success().put("enodebNames", enodebNames);
    }

    @ApiOperation("根据NODEB_NAME查询PRBnew记录，按StartTime升序")
    @GetMapping("/by-enodeb-name")
    public Result getByEnodebName(@RequestParam String enodebName,
                                  @RequestParam(required = false) String from,
                                  @RequestParam(required = false) String to) {
        List<Prbnew> prbnews = prbnewService.listByEnodebName(enodebName, from, to);
        return Result.success().put("prbnews", prbnews);
    }

}

