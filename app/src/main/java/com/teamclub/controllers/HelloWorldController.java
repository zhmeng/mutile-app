package com.teamclub.controllers;

import com.teamclub.sutil.ScalaUtil;
import com.teamclub.util.JavaUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-4-18.
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    @RequestMapping("/index")
    public String index() {
        StringBuilder sb = new StringBuilder();
        JavaUtil.show();
        ScalaUtil.show();
        sb.append("ACSXXZZZSSSVVV");
        sb.append("BCD");
        return sb.toString();
    }
}
