package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class ApiAuthorizerTokenReq {
    @JsonProperty("component_appid")
    public String componentAppid;

    @JsonProperty("authorizer_appid")
    public String authorizerAppid;

    @JsonProperty("authorizer_refresh_token")
    public String authorizerRefreshToken;

    public ApiAuthorizerTokenReq(String componentAppid, String authorizerAppid, String authorizerRefreshToken) {
        this.componentAppid = componentAppid;
        this.authorizerAppid = authorizerAppid;
        this.authorizerRefreshToken = authorizerRefreshToken;
    }
}
