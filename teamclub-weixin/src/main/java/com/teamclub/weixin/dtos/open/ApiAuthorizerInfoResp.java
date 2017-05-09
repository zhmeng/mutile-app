package com.teamclub.weixin.dtos.open;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by zhangmeng on 17-2-14.
 */
public class ApiAuthorizerInfoResp {

    public static class VerifyTypeInfo {
        @JsonProperty("id")
        public Integer id;
    }

    public static class ServiceTypeInfo {
        @JsonProperty("id")
        public Integer id;
    }
    public static class AuthorizerInfo {
        @JsonProperty("nick_name")
        public String nickName;

        @JsonProperty("head_img")
        public String headImg;

        @JsonProperty("user_name")
        public String userName;

        @JsonProperty("principal_name")
        public String principalName;

        @JsonProperty("service_type_info")
        public ServiceTypeInfo serviceTypeInfo;

        @JsonProperty("verify_type_info")
        public VerifyTypeInfo verifyTypeInfo;
    }
    @JsonProperty("authorizer_info")
    public AuthorizerInfo authorizerInfo;

}
