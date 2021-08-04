package com.huawei.tobcore.api;

import com.huawei.tobcore.vo.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Slf4j
public class Test {
    @RequestMapping("/test")
    @GetMapping
    public ResultVo test() {
        return new ResultVo();
    }
}
