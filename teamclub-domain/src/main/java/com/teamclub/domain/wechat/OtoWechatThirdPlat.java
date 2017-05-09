package com.teamclub.domain.wechat;

import com.avaje.ebean.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.Date;

/**
 * Created by zhangmeng on 17-1-19.
 */
@Entity
public class OtoWechatThirdPlat extends Model {
    @Id
    private Integer id ;

    private String organNo;

    private String token;

    private String appid;

    private String host;

    private String appsecret;

    private String aesKey;

    private String verifyTicket;

    private String componentAccessToken;

    private Integer accessTokenExpires;

    private Date accessTokenCreatedAt;

    private String preAuthCode;

    private Integer authCodeExpires;

    private Date authCodeCreatedAt;

    private String authHost;

    @Version
    @JsonIgnore
    private  Integer updateVersion;

    public Integer getUpdateVersion() {
        return updateVersion;
    }

    public void setUpdateVersion(Integer updateVersion) {
        this.updateVersion = updateVersion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganNo() {
        return organNo;
    }

    public void setOrganNo(String organNo) {
        this.organNo = organNo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getVerifyTicket() {
        return verifyTicket;
    }

    public void setVerifyTicket(String verifyTicket) {
        this.verifyTicket = verifyTicket;
    }

    public String getComponentAccessToken() {
        return componentAccessToken;
    }

    public void setComponentAccessToken(String componentAccessToken) {
        this.componentAccessToken = componentAccessToken;
    }


    public String getPreAuthCode() {
        return preAuthCode;
    }

    public void setPreAuthCode(String preAuthCode) {
        this.preAuthCode = preAuthCode;
    }

    public Integer getAccessTokenExpires() {
        return accessTokenExpires;
    }

    public void setAccessTokenExpires(Integer accessTokenExpires) {
        this.accessTokenExpires = accessTokenExpires;
    }

    public Integer getAuthCodeExpires() {
        return authCodeExpires;
    }

    public void setAuthCodeExpires(Integer authCodeExpires) {
        this.authCodeExpires = authCodeExpires;
    }

    public Date getAccessTokenCreatedAt() {
        return accessTokenCreatedAt;
    }

    public void setAccessTokenCreatedAt(Date accessTokenCreatedAt) {
        this.accessTokenCreatedAt = accessTokenCreatedAt;
    }

    public Date getAuthCodeCreatedAt() {
        return authCodeCreatedAt;
    }

    public void setAuthCodeCreatedAt(Date authCodeCreatedAt) {
        this.authCodeCreatedAt = authCodeCreatedAt;
    }

    public String getAuthHost() {
        return authHost;
    }

    public void setAuthHost(String authHost) {
        this.authHost = authHost;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
