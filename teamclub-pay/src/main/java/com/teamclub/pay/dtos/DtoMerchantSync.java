package com.teamclub.pay.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by ilkkzm on 17-5-4.
 */
@JacksonXmlRootElement(
        localName = "xml"
)
public class DtoMerchantSync {
    @JacksonXmlCData
    @JsonProperty("appid")
    private String appid; //银行服务商的公众账号ID

    @JacksonXmlCData
    @JsonProperty("mch_id")
    private String mchId; //银行服务商的商户号

    @JacksonXmlCData
    @JsonProperty("sign")
    private String sign; //通过签名算法计算得出的签名值

    @JacksonXmlCData
    @JsonProperty("merchant_name")
    private String merchantName; //该名称是公司主体全称，绑定公众号时会对主体一致性校验

    @JacksonXmlCData
    @JsonProperty("merchant_shortname")
    private String merchantShortname; //该名称是显示给消费者看的商户名称

    @JacksonXmlCData
    @JsonProperty("service_phone")
    private String servicePhone; //方便微信在必要时能联系上商家，会在支付详情展示给消费者

    @JacksonXmlCData
    @JsonProperty("channel_id")
    private String channelId; //银行为其渠道商申请（在服务商平台申请，请见《渠道录入指引》）的渠道标识，如是银行自行拓展的商户，即自有渠道，则渠道号填写银行商户号

    @JacksonXmlCData
    @JsonProperty("business")
    private String business; //行业类目，请填写对应的ID

    @JacksonXmlCData
    @JsonProperty("merchant_remark")
    private String merchantRemark; //同一个受理机构，子商户“商户备注”唯一。 商户备注重复时，生成商户识别码失败，并返回提示信息“商户备注已存在，请修改后重新提交”

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantShortname() {
        return merchantShortname;
    }

    public void setMerchantShortname(String merchantShortname) {
        this.merchantShortname = merchantShortname;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getMerchantRemark() {
        return merchantRemark;
    }

    public void setMerchantRemark(String merchantRemark) {
        this.merchantRemark = merchantRemark;
    }
}
