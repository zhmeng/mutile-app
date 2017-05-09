package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-1-20.
 */
public class AuthorizerAccessTokenResp {
    public static class AuthorizationInfo {
        @JsonProperty("authorizer_appid")
        public String authorizerAppid;

        @JsonProperty("authorizer_access_token")
        public String authorizerAccessToken;

        @JsonProperty("expires_in")
        public Integer expiresIn;

        @JsonProperty("authorizer_refresh_token")
        public String authorizerRefreshToken;
    }
    @JsonProperty("authorization_info")
    public AuthorizationInfo authorizationInfo;
}
