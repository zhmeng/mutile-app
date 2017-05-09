package com.teamclub.weixin.services.open;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.riversoft.weixin.common.util.JsonMapper;
import com.teamclub.domain.wechat.OtoWechatAuthorizer;
import com.teamclub.domain.wechat.OtoWechatThirdPlat;
import com.teamclub.util.libs.Json;
import com.teamclub.weixin.confs.WeixinApiConf;
import com.teamclub.weixin.dtos.open.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by zhangmeng on 17-1-19.
 */
@Service
public class WeixinOpenApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private WeixinApiConf weixinApiConf;

    private CloseableHttpClient httpClient;

    public WeixinOpenApi() {
        this.httpClient = HttpClients.createDefault();
    }

    public static String tmpAuthCode  = "";

    public String getComponentAccessToken(OtoWechatAuthorizer authorizer) {
        if(authorizer.getAppid().equals("wx570bc396a51b8ff8")) { //全网发布使用
            logger.info("全网发布去获取accessToken");
            OtoWechatThirdPlat plat = Ebean.find(OtoWechatThirdPlat.class).where().eq("id", authorizer.getPlatId()).findUnique();
            AuthorizerAccessTokenResp authorizerAccessToken = getAuthorizerAccessToken(getComponentAccessToken(plat), plat.getAppid(), tmpAuthCode);
            logger.info("全网发布获取accessToken结果: " + authorizerAccessToken.authorizationInfo.authorizerAccessToken);
            String token = authorizerAccessToken.authorizationInfo.authorizerAccessToken;
            return token;
        }
        boolean isExpires = true;
        if(authorizer.getAccessTokenCreatedAt() != null) {
            long diffTime = new Date().getTime() - authorizer.getAccessTokenCreatedAt().getTime();
            if(diffTime - authorizer.getAccessTokenExpires() * 1000 < 60 * 1000) {
                isExpires = false;
            }
        }
        if(isExpires) {
            logger.info("use updated access token");
            OtoWechatThirdPlat plat = Ebean.find(OtoWechatThirdPlat.class).where().eq("id", authorizer.getPlatId()).findUnique();
            ApiAuthorizerTokenResp apiAuthorizerTokenResp = refreshToken(getComponentAccessToken(plat), plat.getAppid(), authorizer.getAppid(), authorizer.getRefreshToken());
            authorizer.setAccessToken(apiAuthorizerTokenResp.authorizerAccessToken);
            authorizer.setAccessTokenCreatedAt(new Date());
            authorizer.setRefreshToken(apiAuthorizerTokenResp.authorizerRefreshToken);
            authorizer.setAccessTokenExpires(apiAuthorizerTokenResp.expiresIn);
            authorizer.update();
        }else {
            logger.info("use cache access token");
        }
        return authorizer.getAccessToken();
    }


    public String getComponentAccessToken(OtoWechatThirdPlat plat) {
        boolean isExpires = true;
        if(plat.getAccessTokenCreatedAt() != null) {
            long diffTime = new Date().getTime() - plat.getAccessTokenCreatedAt().getTime();
            if(diffTime - plat.getAccessTokenExpires() * 1000 < 60 * 1000) {
                isExpires = false;
            }
        }
        if(isExpires) {
            ComponentAccessTokenResp resp = getComponentAccessToken(plat.getAppid(), plat.getAppsecret(), plat.getVerifyTicket());
            plat.setComponentAccessToken(resp.componentAccessToken);
            plat.setAccessTokenCreatedAt(new Date());
            plat.setAccessTokenExpires(Integer.valueOf(resp.expiresIn));
            plat.update();
            return resp.componentAccessToken;
        }else {
            logger.info("use cache component access token");
            return plat.getComponentAccessToken();
        }
    }

    private ComponentAccessTokenResp getComponentAccessToken(String componentAppid, String componentAppSecret, String componentVerifyTicket) {
        ComponentAccessTokenReq componentAccessTokenReq = new ComponentAccessTokenReq(componentAppid, componentAppSecret, componentVerifyTicket);
        String resp = post(weixinApiConf.getApiComponentToken(), JsonMapper.defaultMapper().toJson(componentAccessTokenReq));
        ComponentAccessTokenResp componentAccessTokenResp = JsonMapper.defaultMapper().fromJson(resp, ComponentAccessTokenResp.class);
        return componentAccessTokenResp;
    }

    private String getPreAuthCode(OtoWechatThirdPlat plat) {
        boolean isExpires = true;
        if(plat.getAuthCodeCreatedAt() != null) {
            long diffTime = new Date().getTime() - plat.getAuthCodeCreatedAt().getTime();
            if(diffTime - plat.getAuthCodeExpires() * 1000 < 60 * 1000) {
                isExpires = false;
            }
        }
        if(isExpires) {
            String token = getComponentAccessToken(plat);
            ApiCreatePreauthcodeResp resp = getPreAuthCode(token, plat.getAppid());
            plat.setPreAuthCode(resp.preAuthCode);
            plat.setAuthCodeCreatedAt(new Date());
            plat.setAuthCodeExpires(Integer.valueOf(resp.expiresIn));
            plat.update();
            return resp.preAuthCode;
        }else {
            logger.info("use cache pre auth code.");
            return plat.getPreAuthCode();
        }
    }

    /***
     * 根据plat获取授权页面
     * @param plat
     *
     * @return
     */
    public String getAuthUrl(OtoWechatThirdPlat plat, String pubOrganNo) {
        String preAuthCode = getPreAuthCode(plat);
        logger.info("preAuthCode: " + preAuthCode);
        String redirectUrl = plat.getHost() + "/weixin/open/weixinauth/" + plat.getOrganNo() + "/" + pubOrganNo + "/authCallback";
        String authUrl = weixinApiConf.getAuthUrl(plat.getAppid(),  preAuthCode, redirectUrl);
        return authUrl;

    }

    private ApiCreatePreauthcodeResp getPreAuthCode(String accessToken, String componentAppid) {
        ApiCreatePreauthcodeReq apiCreatePreauthcodeReq = new ApiCreatePreauthcodeReq(componentAppid);
        String resp = post(weixinApiConf.getApiCreatePreAuthCode(accessToken), JsonMapper.defaultMapper().toJson(apiCreatePreauthcodeReq));
        ApiCreatePreauthcodeResp apiCreatePreauthcodeResp = JsonMapper.defaultMapper().fromJson(resp, ApiCreatePreauthcodeResp.class);
        return apiCreatePreauthcodeResp;
    }


    public AuthorizerAccessTokenResp getAuthorizerAccessToken(String accessToken, String appId, String authCode) {
        AuthorizerAccessTokenReq authorizerAccessTokenReq = new AuthorizerAccessTokenReq(appId, authCode);
        String resp = post(weixinApiConf.getApiQueryAuth(accessToken), JsonMapper.defaultMapper().toJson(authorizerAccessTokenReq));
        AuthorizerAccessTokenResp authorizerAccessTokenResp = JsonMapper.defaultMapper().fromJson(resp, AuthorizerAccessTokenResp.class);
        logger.info(Json.stringify(Json.toJson(authorizerAccessTokenResp)));
        return authorizerAccessTokenResp;
    }


    public ApiAuthorizerInfoResp getApiAuthorizerInfo(String accessToken, String componentAppid, String authorizerAppid) {
        ApiAuthorizerInfoReq req = new ApiAuthorizerInfoReq(componentAppid, authorizerAppid);
        String resp = post(weixinApiConf.getApiGetAuthorizerInfo(accessToken), JsonMapper.defaultMapper().toJson(req));
        ApiAuthorizerInfoResp apiAuthorizerInfoResp = JsonMapper.defaultMapper().fromJson(resp, ApiAuthorizerInfoResp.class);
        return apiAuthorizerInfoResp;
    }


    private ApiAuthorizerTokenResp refreshToken(String accessToken, String componentAppid, String authorizerAppid, String authorizerRefreshToken) {
        ApiAuthorizerTokenReq apiAuthorizerTokenReq = new ApiAuthorizerTokenReq(componentAppid, authorizerAppid, authorizerRefreshToken);
        String resp = post(weixinApiConf.getApiAuthorizerToken(accessToken), JsonMapper.defaultMapper().toJson(apiAuthorizerTokenReq));
        ApiAuthorizerTokenResp apiAuthorizerTokenResp = JsonMapper.defaultMapper().fromJson(resp, ApiAuthorizerTokenResp.class);
        logger.info("ApiAuthorizerTokenResp: " + apiAuthorizerTokenResp);
        return apiAuthorizerTokenResp;
    }


    public String post(String url, Map<String, String> map ) {
        JsonNode jn = Json.toJson(map);
        return post(url, jn);
    }

    public String post(String url, JsonNode json) {
        return post(url, Json.stringify(json));
    }
    public String post(String url, String str) {
        HttpUriRequest req = null ;
        try {
            logger.info("req: " + str);
            req = RequestBuilder.post(url).setEntity(new StringEntity(str)).build();
        }catch(Exception e) {
            logger.error("", e);
            return "";
        }
        try (CloseableHttpResponse response = httpClient.execute(req)) {
            String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
            logger.info("resp: " + resp);
            return resp;
        }catch (Exception e) {
            logger.error("", e);
            return "" ;
        }
    }

    public String get(String url) {
        HttpUriRequest req = RequestBuilder.get(url).build();
        try (CloseableHttpResponse response = httpClient.execute(req)) {
            String resp = EntityUtils.toString(response.getEntity(), "UTF-8");
            return resp;
        }catch (Exception e) {
            e.printStackTrace();
            return "" ;
        }
    }

    public static void main(String[] args) {
        String str = new WeixinOpenApi().get("http://www.baidu.com");
        System.out.println(str);
    }

}
