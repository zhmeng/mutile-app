package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class ApiCreatePreauthcodeReq {
    @JsonProperty("component_appid")
    public String componentAppid;

    public ApiCreatePreauthcodeReq(String appid) {
        this.componentAppid = appid;
    }
}
