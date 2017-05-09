package com.teamclub;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-5-8.
 */
@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping("/")
    public String index() {
        return "HELLO TEAMCLUB";
    }
}
