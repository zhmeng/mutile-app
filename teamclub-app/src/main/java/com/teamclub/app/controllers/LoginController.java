package com.teamclub.app.controllers;

import com.teamclub.app.sforms.AppSubjectUserForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-4-27.
 */
@RestController("com.teamclub.app.controllers.LoginController")
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);
    public String login(AppSubjectUserForm form){
        return "LOGIN";
    }
}
