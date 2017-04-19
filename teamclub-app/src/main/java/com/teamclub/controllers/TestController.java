package com.teamclub.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ilkkzm on 17-4-19.
 */
@Controller
public class TestController {
    @ResponseBody
    public String simpleAction() {
        return "HELLO WORLD";
    }
}
