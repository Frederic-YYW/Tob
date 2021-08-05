package com.hw.tobcore.api;

import com.hw.tobcore.vo.ResultVO;
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
    public ResultVO test() {
        return new ResultVO();
    }
}
