package com.teamclub.weixin.services.open;

import com.avaje.ebean.EbeanServer;
import com.google.common.collect.ImmutableMap;
import com.teamclub.domain.wechat.OtoWechatAuthorizer;
import com.teamclub.domain.wechat.OtoWechatThirdPlat;
import com.teamclub.util.libs.F;
import com.teamclub.util.libs.Json;
import com.teamclub.util.network.HTTP;
import com.teamclub.weixin.confs.WeixinPubConf;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by ilkkzm on 17-5-9.
 */

@Service
@Scope("prototype")
public class OpenOAuth2s implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(OpenOAuth2s.class);

    private String appid;
    private String componentAppid;
    private String componentAccessToken;

    @Autowired
    private WeixinPubConf weixinPubConf;

    @Autowired
    private WeixinOpenApi weixinOpenApi;

    @Autowired
    private EbeanServer server;

    private String state = ""; // oauth state 保存数据

    private String host ;

    private String redirectUrl;

    private String code;

    public OpenOAuth2s(){
    }

    public OpenOAuth2s(String appid) {
        this.appid = appid;
    }

    public OpenOAuth2s(String appid, String code, String state) {
        this.appid = appid;
        this.code = code;
        this.state = state;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        OtoWechatAuthorizer otoWechatAuthorizer = server.find(OtoWechatAuthorizer.class).where().eq("appid", appid).setMaxRows(1).findUnique();
        OtoWechatThirdPlat otoWechatThirdPlat = server.find(OtoWechatThirdPlat.class, otoWechatAuthorizer.getPlatId());
        this.componentAppid = otoWechatThirdPlat.getAppid();
        this.componentAccessToken = weixinOpenApi.getComponentAccessToken(otoWechatThirdPlat);
        this.host = otoWechatThirdPlat.getHost();
        this.redirectUrl = this.host + "/weixin/open/weixinpub/oauth/receiveCode";
    }

    public String authenticationUrl() {
        return this.authenticationUrl("snsapi_base");
    }

    public String authenticationUrl(String scope) {
        String url = weixinPubConf.getOauthUrl(this.appid , this.redirectUrl, scope, this.state, this.componentAppid);
        return url;
    }

    public F.Option<String> authenticationUrl2() {
        String url = weixinPubConf.getOauthUrl2(this.appid, this.code, this.componentAppid, this.componentAccessToken);
        F.Option<String> oResp = HTTP.post(url, "").toStr();
        if(oResp.isDefined()) {
            String s = oResp.get();
            Map<String, String> map = Json.fromJson(Json.parse(s), Map.class);
            String openid = map.get("openid");
            if (StringUtils.isBlank(openid)) {
                logger.warn("errorcode: {} ,errormsg: {}", map.get("errcode"), map.get("errmsg"));
                return F.Option.None();
            } else {
                String redirect = getState("redirect");
                if(redirect.indexOf("?") != -1 ){
                    redirect = redirect + "&openid=" + openid;
                }else {
                    redirect = redirect + "?openid=" + openid;
                }
                logger.info("redirect is : {}", redirect);
                return F.Option.Some(redirect);
            }
        } else {
            return F.Option.None();
        }
    }


    public void setState(Map<String, String> map) {
        try {
            state = URLEncoder.encode(Json.stringify(Json.toJson(map)), "UTF-8");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getState(String key) {
        try {
            String decode = URLDecoder.decode(this.state, "UTF-8");
            Map<String, String> map = Json.fromJson(Json.parse(decode), Map.class);
            return map.get(key);
        }catch(Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        OpenOAuth2s openOAuth2s = new OpenOAuth2s("");
        openOAuth2s.setState(ImmutableMap.of("hah", "123"));
        String v = openOAuth2s.getState("hah");
        System.out.println(v);

    }
}
