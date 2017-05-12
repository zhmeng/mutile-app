package com.teamclub.pay.controllers;

import com.teamclub.pay.services.AliOauthService;
import com.teamclub.util.libs.F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by ilkkzm on 17-5-10.
 */
@RestController("com.teamclub.pay.controllers.AliOauthAction")
public class AliOauthAction {
    private Logger logger = LoggerFactory.getLogger(AliOauthAction.class);

    @Autowired
    private AliOauthService aliOauthService;

    public void oauthUrl(@RequestParam(name="auth_code") String authCode, @RequestParam String redirectUrl, HttpServletResponse response) throws Exception{
        logger.info("auth code: {}", authCode);
        F.Option<String> oauth = aliOauthService.oauth(authCode);
        if(oauth.isDefined()) {
            logger.info("response userid: {}",  oauth.get());
            String fullRedirectUrl = redirectUrl + "?openid=" + oauth.get();
            response.sendRedirect(fullRedirectUrl);
        }
    }

    public void oauthHtml(@RequestParam String redirectUrl, HttpServletResponse response) throws Exception{
        response.sendRedirect(aliOauthService.geneUrl(URLEncoder.encode(redirectUrl, "UTF-8")));
    }

}
