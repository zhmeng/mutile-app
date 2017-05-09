package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class AuthorizerAccessTokenReq {
    @JsonProperty("component_appid")
    public String componentAppid;

    @JsonProperty("authorization_code")
    public String authorizationCode;

    public AuthorizerAccessTokenReq(String appId, String code) {
        this.componentAppid = appId;
        this.authorizationCode = code;
    }
}
