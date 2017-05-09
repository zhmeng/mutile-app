package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class ApiAuthorizerTokenResp {
    @JsonProperty("authorizer_access_token")
    public String authorizerAccessToken;

    @JsonProperty("expires_in")
    public Integer expiresIn;

    @JsonProperty("authorizer_refresh_token")
    public String authorizerRefreshToken;

    public String toString() {
        return MoreObjects.toStringHelper(this.getClass())
                .add("authorizerAccessToken", authorizerAccessToken)
                .add("expiresIn", expiresIn)
                .add("authorizerRefreshToken", authorizerRefreshToken)
                .toString();
    }
}
