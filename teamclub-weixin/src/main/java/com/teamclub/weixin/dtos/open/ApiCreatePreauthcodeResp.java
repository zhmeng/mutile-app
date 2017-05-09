package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class ApiCreatePreauthcodeResp {
    @JsonProperty("pre_auth_code")
    public String preAuthCode;

    @JsonProperty("expires_in")
    public String expiresIn;
}
