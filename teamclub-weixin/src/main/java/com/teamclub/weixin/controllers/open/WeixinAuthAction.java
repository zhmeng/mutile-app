package com.teamclub.weixin.controllers.open;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.riversoft.weixin.common.WxClient;
import com.riversoft.weixin.common.decrypt.MessageDecryption;
import com.riversoft.weixin.common.decrypt.SHA1;
import com.riversoft.weixin.common.event.EventRequest;
import com.riversoft.weixin.common.message.MsgType;
import com.riversoft.weixin.common.message.XmlMessageHeader;
import com.riversoft.weixin.common.request.TextRequest;
import com.riversoft.weixin.common.util.XmlObjectMapper;
import com.riversoft.weixin.mp.care.CareMessages;
import com.riversoft.weixin.mp.message.MpXmlMessages;
import com.teamclub.domain.wechat.OtoWechatAuthorizer;
import com.teamclub.domain.wechat.OtoWechatThirdPlat;
import com.teamclub.util.Springs;
import com.teamclub.weixin.confs.WeixinApiConf;
import com.teamclub.weixin.dtos.open.ApiAuthorizerInfoResp;
import com.teamclub.weixin.dtos.open.AuthorizerAccessTokenResp;
import com.teamclub.weixin.libs.OpenAccessTokenHolder;
import com.teamclub.weixin.services.open.WeixinOpenApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by zhangmeng on 17-1-18.
 */
@RestController("com.teamclub.weixin.controllers.open.WeixinAuthAction")
public class WeixinAuthAction {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Springs springs;

    @Autowired
    private WeixinApiConf weixinApiConf;

    @Autowired
    private WeixinOpenApi weixinOpenApi;

    static class A {
        @JsonProperty("AppId")
        public String appId ;
        @JsonProperty("Encrypt")
        public String encrypt ;
        public String toString(){
            return "appId: " + appId + ", encrypt: " + encrypt;
        }
    }

    static class Msg {
        @JsonProperty("ToUserName")
        public String toUserName;

        @JsonProperty("Encrypt")
        public String encrypt;
    }


    static class TicketD {
        public String AppId;
        public String CreateTime;
        public String InfoType;
        public String ComponentVerifyTicket;
        public String AuthorizerAppid;
        public String AuthorizationCode;
        public String AuthorizationCodeExpiredTime;
    }

    public String testConf() {
        logger.info("apiComponentToken: " + weixinApiConf.getApiComponentToken());
        logger.info("apiCreatePreAuthCode: " + weixinApiConf.getApiCreatePreAuthCode("12345"));
        logger.info("authUrl: " + weixinApiConf.getAuthUrl("123", "123456", "zhangmwjr.dev.szjyyg.cn/weixinauth/callback"));
        return "";
    }

    /**
     * 公众帐号授权页面
     * @param thirdOrganNo
     * @return
     */
    public void getAuthUrl(@RequestParam String thirdOrganNo, @RequestParam String pubOrganNo, HttpServletResponse response) throws IOException{
        OtoWechatThirdPlat thirdPlat = Ebean.find(OtoWechatThirdPlat.class).where().eq("organNo", thirdOrganNo).findUnique();
        String authUrl = weixinOpenApi.getAuthUrl(thirdPlat, pubOrganNo);
        logger.info("authUrl: " + authUrl);
        response.sendRedirect(authUrl);
    }

    /***
     * 公众帐号授权回调回写参数
     * @param thirdOrganNo
     * @param pubOrganNo
     * @param authCode
     * @param expiresIn
     * @return
     */
    public String authCallback(@PathVariable String thirdOrganNo, @PathVariable String pubOrganNo, @RequestParam(name="auth_code") String authCode, @RequestParam(name="expires_in") Long expiresIn) {
        logger.info("authCode: " + authCode);
        OtoWechatThirdPlat thirdPlat = Ebean.find(OtoWechatThirdPlat.class).where().eq("organNo", thirdOrganNo).findUnique();
        if(thirdPlat == null) {
            logger.warn("第三方平台缺失 organNo is ：{}", thirdOrganNo);
            return "fail";
        }
        AuthorizerAccessTokenResp authorizerResp = weixinOpenApi.getAuthorizerAccessToken(thirdPlat.getComponentAccessToken(), thirdPlat.getAppid(), authCode);
        OtoWechatAuthorizer otoWechatAuthorizer = Ebean.find(OtoWechatAuthorizer.class).where().eq("appid", authorizerResp.authorizationInfo.authorizerAppid).findUnique();
        if(otoWechatAuthorizer == null) {
            otoWechatAuthorizer = new OtoWechatAuthorizer();
        }
        otoWechatAuthorizer.setAppid(authorizerResp.authorizationInfo.authorizerAppid);
        otoWechatAuthorizer.setAccessToken(authorizerResp.authorizationInfo.authorizerAccessToken);
        otoWechatAuthorizer.setRefreshToken(authorizerResp.authorizationInfo.authorizerRefreshToken);
        otoWechatAuthorizer.setAccessTokenCreatedAt(new Date());
        otoWechatAuthorizer.setAccessTokenExpires(authorizerResp.authorizationInfo.expiresIn);
        otoWechatAuthorizer.setPlatId(thirdPlat.getId());
        if(otoWechatAuthorizer.getId() == null) {
            otoWechatAuthorizer.save();
        }else {
            otoWechatAuthorizer.update();
        }

        ApiAuthorizerInfoResp apiAuthorizerInfo = weixinOpenApi.getApiAuthorizerInfo(thirdPlat.getComponentAccessToken(), thirdPlat.getAppid(), authorizerResp.authorizationInfo.authorizerAppid);

        otoWechatAuthorizer.setNickName(apiAuthorizerInfo.authorizerInfo.nickName);
        otoWechatAuthorizer.setHeadImg(apiAuthorizerInfo.authorizerInfo.headImg);
        otoWechatAuthorizer.setName(apiAuthorizerInfo.authorizerInfo.principalName);
        otoWechatAuthorizer.setServiceTypeInfo(apiAuthorizerInfo.authorizerInfo.serviceTypeInfo.id);
        otoWechatAuthorizer.setVerifyTypeInfo(apiAuthorizerInfo.authorizerInfo.verifyTypeInfo.id);
        otoWechatAuthorizer.setAuthTime(new Date());
        otoWechatAuthorizer.setStatus(1);
        otoWechatAuthorizer.update();

        return "success";
    }

    /***
     * 根据appid获取对应的accessToken
     * @param appid
     * @return
     */
    public String accessToken(@PathVariable String appid) {
        if(appid.equals("wx570bc396a51b8ff8")) { //全网发布使用
            logger.info("全网发布去获取accessToken");
            OtoWechatAuthorizer authorizer = Ebean.find(OtoWechatAuthorizer.class).where().eq("appid", appid).findUnique();
            OtoWechatThirdPlat plat = Ebean.find(OtoWechatThirdPlat.class).where().eq("id", authorizer.getPlatId()).findUnique();
            AuthorizerAccessTokenResp authorizerAccessToken = weixinOpenApi.getAuthorizerAccessToken(weixinOpenApi.getComponentAccessToken(plat), plat.getAppid(), weixinOpenApi.tmpAuthCode);
            logger.info("全网发布获取accessToken结果: " + authorizerAccessToken.authorizationInfo.authorizerAccessToken);
            String token = authorizerAccessToken.authorizationInfo.authorizerAccessToken;
            return token;
        }
        OtoWechatAuthorizer authorizer = Ebean.find(OtoWechatAuthorizer.class).where().eq("appid", appid).findUnique();
        String componentAccessToken = weixinOpenApi.getComponentAccessToken(authorizer);
        logger.info("accessToken: " + componentAccessToken);
        return componentAccessToken;
    }

    /***
     * 接收公众帐号消息服务回调 公众号消息与事件接收URL
     * @param appid
     * @param organNo
     * @param timestamp
     * @param nonce
     * @param msgSignature
     * @param msg
     * @return
     * @throws Exception
     */
    @RequestMapping("/{appid}/{organNo}/callback")
    public String appidCallback(@PathVariable String appid,
                           @PathVariable String organNo,
                           @RequestParam(required = false) String timestamp,
                           @RequestParam(required = false) String nonce,
                           @RequestParam(name = "msg_signature", required = false) String msgSignature,
                           @RequestBody Msg msg) throws Exception{
        OtoWechatThirdPlat thirdPlat = Ebean.find(OtoWechatThirdPlat.class).where().eq("organNo", organNo).findUnique();
        if(thirdPlat == null) {
            logger.warn("第三方平台缺失 organNo is ：{}", organNo);
            return "fail";
        }
        MessageDecryption messageDecryption = new MessageDecryption(thirdPlat.getToken(), thirdPlat.getAesKey(), thirdPlat.getAppid());
        String decrypt = messageDecryption.decryptEcho(msgSignature, timestamp, nonce, msg.encrypt);
        logger.info("decrypt: " + decrypt);
        XmlMessageHeader xmlMessageHeader = MpXmlMessages.fromXml(decrypt);
        String fromUser = xmlMessageHeader.getFromUser();
        String toUser = xmlMessageHeader.getToUser();
        XmlMessageHeader respXml = null;
        if(appid.equals("wx570bc396a51b8ff8")) {
            if(xmlMessageHeader instanceof EventRequest) {
                TextRequest request = new TextRequest();
                request.setContent(((EventRequest) xmlMessageHeader).getEventType().name() + "from_callback");
                request.setMsgId(String.valueOf(System.currentTimeMillis()));
                request.setCreateTime(new Date());
                request.setMsgType(MsgType.text);
                respXml = request;
            }else if(xmlMessageHeader instanceof TextRequest) {
                TextRequest request = (TextRequest)xmlMessageHeader;
                if(request.getContent().startsWith("QUERY_AUTH_CODE")) {
                    String auth=request.getContent().replace("QUERY_AUTH_CODE:", "");
                    auth += "_from_api";
                    CareMessages careMessages = new CareMessages();
                    WxClient wxClient = new WxClient("", "", springs.getBean(OpenAccessTokenHolder.class, appid));
                    careMessages.setWxClient(wxClient);
                    logger.info("发送文本：" + auth);
                    careMessages.text(request.getFromUser(), auth);
                    return ""; //直接回复
                }else {
                    request.setContent(request.getContent() + "_callback");
                    respXml = request;
                }
            }
        }
        if(respXml == null) {
            respXml = xmlMessageHeader;
        }
        respXml.fromUser(toUser).toUser(fromUser);
        String txtRespXml = MpXmlMessages.toXml(respXml);
        logger.info("resp xml: " + txtRespXml);
        String encrypt = messageDecryption.encrypt(txtRespXml, String.valueOf(System.currentTimeMillis()), String.valueOf(System.currentTimeMillis()));
        logger.info("resp: " + encrypt);
        return encrypt;
    }

    /***
     * 授权事件接收URL
     * @param organNo
     * @param signature
     * @param timestamp
     * @param nonce
     * @param encryptType
     * @param msgSignature
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping("/{organNo}/open")
    public String callback(@PathVariable String organNo,
                        @RequestParam(required = false) String signature,
                        @RequestParam(required = false) String timestamp,
                        @RequestParam(required = false) String nonce,
                        @RequestParam(name = "encrypt_type", required = false) String encryptType,
                        @RequestParam(name = "msg_signature", required = false) String msgSignature,
                        @RequestBody(required = false)A body) throws Exception{

        OtoWechatThirdPlat thirdPlat = Ebean.find(OtoWechatThirdPlat.class).where().eq("organNo", organNo).findUnique();
        if(thirdPlat == null) {
            logger.error("实体不存在");
            return "fail";
        }

        MessageDecryption messageDecryption = new MessageDecryption(thirdPlat.getToken(), thirdPlat.getAesKey(), thirdPlat.getAppid());

        if( !signature.equals(SHA1.getSHA1(thirdPlat.getToken(), timestamp, nonce))) {
            logger.info("非微信端请求");
            return "fail";
        }

        String decrypt = messageDecryption.decryptEcho(msgSignature, timestamp, nonce, body.encrypt);
        logger.info("decrypt data: " + decrypt);

        TicketD ticketD = XmlObjectMapper.defaultMapper().fromXml(decrypt, TicketD.class);

        if(ticketD.AuthorizerAppid != null && ticketD.AuthorizerAppid.equals("wx570bc396a51b8ff8")) {
            if(ticketD.AuthorizationCode != null) {
                WeixinOpenApi.tmpAuthCode = ticketD.AuthorizationCode;
                logger.info("authcode: " + WeixinOpenApi.tmpAuthCode);
                return "success"; //全网发布直接回复
            }
        }
        logger.info("ticketD ComponentVerifyTicket: " + ticketD.ComponentVerifyTicket);

        if(ticketD.ComponentVerifyTicket != null) {
            thirdPlat.setVerifyTicket(ticketD.ComponentVerifyTicket);
            thirdPlat.update();
        }

        return "success";
    }
}
