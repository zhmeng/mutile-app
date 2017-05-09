package com.teamclub.pay.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * Created by ilkkzm on 17-5-8.
 */
@JacksonXmlRootElement(
        localName = "root"
)
public class DtoQqMerchantSync {
    @JacksonXmlCData
    @JsonProperty("action")
    private String action;  //add/modify/del 默认为 add, 对应 qpay_submch_add.cgi/  qpay_submch_modify.cgi/  qpay_submch_del.cgi

    @JacksonXmlCData
    @JsonProperty("mch_id")
    private String mchId;   // 受理商户号

    @JacksonXmlCData
    @JsonProperty("merchant_name")
    private String merchantName;  //  须与商户营业执照上的名称保持一致

    @JacksonXmlCData
    @JsonProperty("merchant_shortname")
    private String merchantShortName;  // 商户简称  支付成功页上显示的商户或门店名称

    @JacksonXmlCData
    @JsonProperty("service_phone")
    private String servicePhone;  //客服电话

    @JacksonXmlCData
    @JsonProperty("business")
    private String business; // 类目 http://kf.qq.com/faq/170112Vba2I7170112eEva2u.html

    @JacksonXmlCData
    @JsonProperty("merchant_remark")
    private String merchantRemark; //子商户外部 ID,同一受理商户下每个识别码对应的备注需保证唯一必须是数字和字母

    @JacksonXmlCData
    @JsonProperty("sign")
    private String sign; //参数签名

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantShortName() {
        return merchantShortName;
    }

    public void setMerchantShortName(String merchantShortName) {
        this.merchantShortName = merchantShortName;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
