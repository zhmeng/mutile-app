package com.teamclub.pay.services;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.teamclub.util.libs.F;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

/**
 * Created by ilkkzm on 17-5-10.
 */
@Service
public class AliOauthService {
    String appid = "2016100902064811";
    String pid = "2088421964674144";
    String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALEj1SUCaJ8HUpBaSZx9T1f+AIF5BUrOAlIzZ7PLCB20b6rF5pn9CR8rZ0BxQkSUkqOwHKIPmENzfOhbRRrJenpUBAGCmy2oPeuHyMG0I5+EjGgCJQ2yIIhxYlEhkNsWE6KwScOSYVApFtxTFiro0h582MOZNvJREW/sdANsEIjrAgMBAAECgYAs0ntRdQ2SB+FNY3sy3MZh5oSsPjonln8h254G8X3pPknvNUpHdq6DM5FZzg7NmaaNQrfMDhQiJSvbA5U11PtnYuvhYqsIPC35M2VpPlw5ucDBzBh+6lv7qnFrvEa5CTuhsOfHhlJ3HcPVJUYCva8cdBG7EAhQjuPLv2FPhRC00QJBANX/8+MQ7U14HPXbzH1wd3mwfGUNLEGFenVsYwoE+i1Wds6i6+Hy9lwvrcY+uEdcq6q5yXbFeU7hYuNbRsIH6mMCQQDT5+veTW3cbdKf1z4swDcYJK8ppayBxhQbOGK+DFRCQCzYgWZkTxVFEb6QnxHfiQVpl0OW/ZMEf3OZe43OOynZAkBAD/5PtEqFzXZF94Ww65EooberfJUKDE5LhqXjYvgNyHuKgmVA3732fON1nGMIIerWEgYIgzc8cOZsj2+QOFRnAkAR4kHiyhAaiV48RTMnHbXIoNfzoNrVPP1XasPSnEHEMZxXTVxx3MbDxIKbvAELJ4pAZha7OH1e5M7Bxk6qeA3JAkEAj8mf7Fs9+C2MIBEDNmJ3OhtyJEiA1LpkjVUsyKiLyHvG1Fs10PJR5Nl6n8eGvaHkiIvJ/ZbP7lzMPgfGquE4Fw==";
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
    String URL = "https://openapi.alipay.com/gateway.do";

    String oauthUrl = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=%s&scope=auth_user&redirect_uri=%s";

    private Logger logger = LoggerFactory.getLogger(AliOauthService.class);

    public F.Option<String> oauth(String authCode) {
        AlipayClient client = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                appid,
                privateKey,
                "json",
                "UTF-8",
                publicKey,
                "RSA"
        );
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(authCode);
        request.setGrantType("authorization_code");
        try {
            AlipaySystemOauthTokenResponse response = client.execute(request);
            String accessToken = response.getAccessToken();
            return F.Option.Some(response.getUserId());
        } catch (AlipayApiException e) {
            logger.error("", e);
            return F.None();
        }
    }

    public String geneUrl(String redirectUrl){
        try {
            String s = String.format(oauthUrl, appid, URLEncoder.encode("http://zhangmeng.dev.szjyyg.cn/pay/ali/oath?redirectUrl=" + redirectUrl, "UTF-8"));
            logger.info("gene url is : {}", s);
            return s;
        } catch(Exception e) {
            return "";
        }
    }
}
