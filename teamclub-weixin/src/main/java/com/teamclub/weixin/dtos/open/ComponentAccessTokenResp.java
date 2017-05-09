package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class ComponentAccessTokenResp {
    @JsonProperty("component_access_token")
    public String componentAccessToken;

    @JsonProperty("expires_in")
    public String expiresIn;

    @JsonProperty("errcode")
    public String errcode;

    @JsonProperty("errmsg")
    public String errmsg;
}
