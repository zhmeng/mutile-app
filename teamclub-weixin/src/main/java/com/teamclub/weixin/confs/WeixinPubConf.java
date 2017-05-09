package com.teamclub.weixin.confs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zhangmeng on 17-2-8.
 */
@Component
@ConfigurationProperties(prefix = "weixin.public")
public class WeixinPubConf {

    private String templateSend;

    private String oauthUrl;

    private String oauthUrl2;

    public String getTemplateSend(String token) {
        return String.format(templateSend, token);
    }

    public void setTemplateSend(String templateSend) {
        this.templateSend = templateSend;
    }

    public String getOauthUrl(String appid, String redirectUri, String scope, String state, String componentAppid) {
        return String.format(oauthUrl, appid, redirectUri, scope, state, componentAppid);
    }

    public void setOauthUrl(String oauthUrl) {
        this.oauthUrl = oauthUrl;
    }

    public String getOauthUrl2(String appid, String code, String componentAppid, String componentAccessToken) {
        return String.format(oauthUrl2, appid, code, componentAppid, componentAccessToken);
    }

    public void setOauthUrl2(String oauthUrl2) {
        this.oauthUrl2 = oauthUrl2;
    }
}
