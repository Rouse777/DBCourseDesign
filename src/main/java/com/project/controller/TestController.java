package com.project.controller;

import com.project.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {
    @GetMapping("/ping")
    public Result ping(){
        log.info("Receive ping!!!");
        return Result.success("ping success!!!");
    }
}
