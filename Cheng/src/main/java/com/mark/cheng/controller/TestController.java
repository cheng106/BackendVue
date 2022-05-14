package com.mark.cheng.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cheng
 * @since 2022/5/14 23:36
 **/
@Slf4j
@RestController
public class TestController {

    @GetMapping("test")
    public ResponseEntity<String> test() {
        log.info("TEST");
        return ResponseEntity.ok("OK");
    }
}
