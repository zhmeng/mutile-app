package com.teamclub.test.controllers;

import com.avaje.ebean.EbeanServer;
import com.teamclub.domain.AppSubjectUser;
import com.teamclub.sutil.ScalaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-4-18.
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    private Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @Autowired
    EbeanServer server;

    @RequestMapping("/index")
    public String index() {
        StringBuilder sb = new StringBuilder();
        ScalaUtil.show();
        sb.append("A");
        sb.append("BCD");
        AppSubjectUser user = server.find(AppSubjectUser.class).findUnique();
        logger.info("username is {}", user.getUserName());
        return sb.toString();
    }

}
