package com.teamclub.weixin.libs;

import com.avaje.ebean.Ebean;
import com.riversoft.weixin.common.AccessToken;
import com.riversoft.weixin.common.AccessTokenHolder;
import com.teamclub.domain.wechat.OtoWechatAuthorizer;
import com.teamclub.util.Springs;
import com.teamclub.weixin.services.open.WeixinOpenApi;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Created by zhangmeng on 17-2-9.
 */

@Service
@Scope("prototype")
public class OpenAccessTokenHolder extends AccessTokenHolder {

    private CloseableHttpClient httpClient;

    private String appid;

    @Autowired
    private Springs springs;

    public OpenAccessTokenHolder(String appid){
        super("", "", "");
        this.appid = appid;
        httpClient = HttpClients.createDefault();
    }

    @Override
    public AccessToken getAccessToken() {
        OtoWechatAuthorizer authorizer = Ebean.find(OtoWechatAuthorizer.class).where().eq("appid", this.appid).findUnique();
        String accessToken = springs.getBean(WeixinOpenApi.class).getComponentAccessToken(authorizer);
        AccessToken token = new AccessToken();
        token.setAccessToken(accessToken);
        return token;
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

    @Override
    public void refreshToken() {

    }

    @Override
    public void expireToken() {

    }
}
