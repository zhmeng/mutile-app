package com.teamclub.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-4-19.
 */
@RestController
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);
    public String simpleAction(@RequestParam String a) {
        logger.info("param a: {}", a);
        return "HELLO WORLD";
    }
}
