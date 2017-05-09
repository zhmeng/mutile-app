package com.teamclub.domain.payment;

import com.avaje.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class GatewayCert extends Model {
    @Id
    private  String id;   //商户编号，或者支付中心id
    private  String thridMerchantNo;   //第三方提供的商户号
    private  String certContent;   //
    @NotNull
    private  String certPwd;   //证书密码
    private  String refundUserId;   //退款用户名
    private  String refundUserPwd;   //退款账户密码
    private  Integer certType;   //证书类型,0清算中心，1商户
    @NotNull
    private Date createdTime;   //创建时间
    private  Date updatedTime;   //更新时间
    private  Integer certSet;   //0
    private String certName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThridMerchantNo() {
        return thridMerchantNo;
    }

    public void setThridMerchantNo(String thridMerchantNo) {
        this.thridMerchantNo = thridMerchantNo;
    }

    public String getCertContent() {
        return certContent;
    }

    public void setCertContent(String certContent) {
        this.certContent = certContent;
    }

    public String getCertPwd() {
        return certPwd;
    }

    public void setCertPwd(String certPwd) {
        this.certPwd = certPwd;
    }

    public String getRefundUserId() {
        return refundUserId;
    }

    public void setRefundUserId(String refundUserId) {
        this.refundUserId = refundUserId;
    }

    public String getRefundUserPwd() {
        return refundUserPwd;
    }

    public void setRefundUserPwd(String refundUserPwd) {
        this.refundUserPwd = refundUserPwd;
    }

    public Integer getCertType() {
        return certType;
    }

    public void setCertType(Integer certType) {
        this.certType = certType;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getCertSet() {
        return certSet;
    }

    public void setCertSet(Integer certSet) {
        this.certSet = certSet;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }
}
