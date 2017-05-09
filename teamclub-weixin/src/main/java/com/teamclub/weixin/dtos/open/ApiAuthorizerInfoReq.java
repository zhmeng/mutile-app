package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-2-14.
 */
public class ApiAuthorizerInfoReq {
    @JsonProperty("component_appid")
    public String componentAppid;

    @JsonProperty("authorizer_appid")
    public String authorizerAppid;

    public ApiAuthorizerInfoReq(String componentAppid, String authorizerAppid) {
        this.componentAppid = componentAppid;
        this.authorizerAppid = authorizerAppid;
    }
}
