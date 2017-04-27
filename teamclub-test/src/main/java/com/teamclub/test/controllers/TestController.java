package com.teamclub.test.controllers;

import com.teamclub.util.Springs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-4-19.
 */
@RestController("com.teamclub.controllers.TestController")
public class TestController {
    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    Springs springs;

    public String simpleAction(@RequestParam String a) throws Exception {
        logger.info("param a: {}", a);
        Class<?> aClass = Class.forName("com.teamclub.test.controllers.HelloWorldController");
        HelloWorldController controller = (HelloWorldController)springs.getBean(aClass);
        controller.index();
        return "HELLO WORLD";
    }
}
