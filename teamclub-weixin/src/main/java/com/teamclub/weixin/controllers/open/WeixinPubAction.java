package com.teamclub.weixin.controllers.open;

import com.google.common.collect.ImmutableMap;
import com.riversoft.weixin.common.WxClient;
import com.riversoft.weixin.mp.care.CareMessages;
import com.riversoft.weixin.mp.template.Data;
import com.riversoft.weixin.mp.template.Templates;
import com.teamclub.util.Springs;
import com.teamclub.util.libs.F;
import com.teamclub.weixin.libs.OpenAccessTokenHolder;
import com.teamclub.weixin.services.open.OpenOAuth2s;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhangmeng on 17-2-8.
 */
@RestController("com.teamclub.weixin.controllers.open.WeixinPubAction")
public class WeixinPubAction {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Springs springs;

    public String sendTemplate(@PathVariable String appid) {
        Templates templates = new Templates();
        templates.setWxClient(new WxClient("", "", springs.getBean(OpenAccessTokenHolder.class, appid)));
        templates.send(
                "o17qlwIEauFwvcbAcbk7cBwnpx7A",
                "ysujDVUdxAXDew_pLyVYZgg0nCp_EEzZR53O__dG2H0",
                "http://www.baidu.com",
                ImmutableMap.of(
                        "first", new Data("haha", "#173177"),
                        "keyword1", new Data("haha", "#173177"),
                        "keyword2", new Data("haha", "#173177"),
                        "remark", new Data("haha", "#173177")
                ));
        return "success";
    }

    public String sendMsg(@PathVariable String appid) {
        CareMessages careMessages = new CareMessages();
        WxClient wxClient = new WxClient("", "", springs.getBean(OpenAccessTokenHolder.class, appid));
        careMessages.setWxClient(wxClient);
        careMessages.text("o17qlwIEauFwvcbAcbk7cBwnpx7A", "12345");
        return "123";
    }

    /***
     * @param appid  appid
     * @param url  encoded url 重定向的url
     */
    public void getOpenOauthUrl(@RequestParam String appid, @RequestParam String url, HttpServletRequest request, HttpServletResponse response) throws IOException{
        OpenOAuth2s openOAuth2s = springs.getBean(OpenOAuth2s.class, appid);
        openOAuth2s.setState(ImmutableMap.of("redirect", url));
        response.sendRedirect(openOAuth2s.authenticationUrl());
    }

    public String receiveCode(@RequestParam String code,@RequestParam String state, @RequestParam String appid, HttpServletResponse response) {
        OpenOAuth2s openOAuth2s = springs.getBean(OpenOAuth2s.class, appid, code, state);
        F.Option<String> url = openOAuth2s.authenticationUrl2();
        if(url.isDefined()) {
            return url.get();
        } else {
            return "some thing error";
        }
    }
}
