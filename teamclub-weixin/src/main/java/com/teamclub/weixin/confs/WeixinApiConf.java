package com.teamclub.weixin.confs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by zhangmeng on 17-1-19.
 */
@Component
@ConfigurationProperties(prefix="weixin.open")
public class WeixinApiConf {

    private String apiComponentToken;

    private String apiCreatePreAuthCode;

    private String authUrl ;

    private String apiQueryAuth;

    private String apiAuthorizerToken;

    private String apiGetAuthorizerInfo;

    public String getApiGetAuthorizerInfo(String token) {
        return String.format(apiGetAuthorizerInfo, token);
    }

    public void setApiGetAuthorizerInfo(String apiGetAuthorizerInfo) {
        this.apiGetAuthorizerInfo = apiGetAuthorizerInfo;
    }

    public String getApiAuthorizerToken(String token) {
        return String.format(apiAuthorizerToken, token);
    }

    public void setApiAuthorizerToken(String tk) {
        this.apiAuthorizerToken = tk;
    }

    public String getApiQueryAuth(String token) {
        return String.format(apiQueryAuth, token);
    }

    public void setApiQueryAuth(String auth) {
        this.apiQueryAuth = auth;
    }

    public String getApiComponentToken() {
        return apiComponentToken;
    }

    public void setApiComponentToken(String apiComponentToken) {
        this.apiComponentToken = apiComponentToken;
    }

    public String getApiCreatePreAuthCode(String s) {
        return String.format(this.apiCreatePreAuthCode, s);
    }

    public void setApiCreatePreAuthCode(String apiCreatePreAuthCode) {
        this.apiCreatePreAuthCode = apiCreatePreAuthCode;
    }

    public String getAuthUrl(String appId, String authCode, String redirectUrl) {
        return String.format(this.authUrl, appId, authCode, redirectUrl);
    }

    public void setAuthUrl(String url) {
        this.authUrl = url;
    }


}
