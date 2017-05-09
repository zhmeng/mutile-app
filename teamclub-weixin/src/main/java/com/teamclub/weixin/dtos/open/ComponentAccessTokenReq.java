package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class ComponentAccessTokenReq {
    @JsonProperty("component_appid")
    public String componentAppid;

    @JsonProperty("component_appsecret")
    public String componentAppsecret;

    @JsonProperty("component_verify_ticket")
    public String componentVerifyTicket;

    public ComponentAccessTokenReq(String appid, String secret, String ticket) {
        this.componentAppid = appid ;
        this.componentAppsecret = secret;
        this.componentVerifyTicket = ticket;
    }
}
