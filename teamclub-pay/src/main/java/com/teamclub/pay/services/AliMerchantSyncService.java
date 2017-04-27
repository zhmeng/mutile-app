package com.teamclub.pay.services;

import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayObject;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AddressInfo;
import com.alipay.api.domain.AntMerchantExpandIndirectCreateModel;
import com.alipay.api.domain.BankCardInfo;
import com.alipay.api.domain.ContactInfo;
import com.alipay.api.request.AntMerchantExpandIndirectCreateRequest;
import com.alipay.api.response.AntMerchantExpandIndirectCreateResponse;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

/**
 * Created by ilkkzm on 17-4-26.
 */
@Service
public class AliMerchantSyncService {
    String appid = "2016100902064811";
    String pid = "2088421964674144";
    String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALEj1SUCaJ8HUpBaSZx9T1f+AIF5BUrOAlIzZ7PLCB20b6rF5pn9CR8rZ0BxQkSUkqOwHKIPmENzfOhbRRrJenpUBAGCmy2oPeuHyMG0I5+EjGgCJQ2yIIhxYlEhkNsWE6KwScOSYVApFtxTFiro0h582MOZNvJREW/sdANsEIjrAgMBAAECgYAs0ntRdQ2SB+FNY3sy3MZh5oSsPjonln8h254G8X3pPknvNUpHdq6DM5FZzg7NmaaNQrfMDhQiJSvbA5U11PtnYuvhYqsIPC35M2VpPlw5ucDBzBh+6lv7qnFrvEa5CTuhsOfHhlJ3HcPVJUYCva8cdBG7EAhQjuPLv2FPhRC00QJBANX/8+MQ7U14HPXbzH1wd3mwfGUNLEGFenVsYwoE+i1Wds6i6+Hy9lwvrcY+uEdcq6q5yXbFeU7hYuNbRsIH6mMCQQDT5+veTW3cbdKf1z4swDcYJK8ppayBxhQbOGK+DFRCQCzYgWZkTxVFEb6QnxHfiQVpl0OW/ZMEf3OZe43OOynZAkBAD/5PtEqFzXZF94Ww65EooberfJUKDE5LhqXjYvgNyHuKgmVA3732fON1nGMIIerWEgYIgzc8cOZsj2+QOFRnAkAR4kHiyhAaiV48RTMnHbXIoNfzoNrVPP1XasPSnEHEMZxXTVxx3MbDxIKbvAELJ4pAZha7OH1e5M7Bxk6qeA3JAkEAj8mf7Fs9+C2MIBEDNmJ3OhtyJEiA1LpkjVUsyKiLyHvG1Fs10PJR5Nl6n8eGvaHkiIvJ/ZbP7lzMPgfGquE4Fw==";
    String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";


//    String publicKey2 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp/ZRZwlupcs0Qkqm+DFSmhZSqRUDhOfrVe4AW+M1UmSUBnenF3h8lvcYmv8mhBbBeJB4QvNye4p6yAFvvI2+TIbm0DLceo9naQA3TvbRgc0wvD8vVwdr1ApqXBHlgiUgO4likWJvDp7+YoqnLOnl/2tprof40p4DL8CYdK2XEctvq57u+fvPAhIuP3O12ZpvMHOHXsTOlBoSDg8/KvtSr+AyJKYeV8UZfcjupJdjjMJdhIfGX2Psm8F7bHEiW1+GWNgOLuezMkdbGwmYUtLFosXTtf3RQMb8++AKhAyO9yHDuOBsCF+62PVEhaFrCumNwfrnkfw+rPs5kM9XfkxeeQIDAQAB";

    public void sync() {
        AlipayClient client = new DefaultAlipayClient(
                "https://openapi.alipay.com/gateway.do",
                appid,
                privateKey,
                "json",
                "UTF-8",
                publicKey,
                "RSA"
                );

        AntMerchantExpandIndirectCreateRequest request = new AntMerchantExpandIndirectCreateRequest();
        request.setBizModel(getModel("15121000"));
        try {
            AntMerchantExpandIndirectCreateResponse response = client.execute(request);
            System.out.println(response.isSuccess());
            String merchantId = response.getSubMerchantId();
            System.out.println(merchantId);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AlipayObject getModel(String merchantId) {
        AntMerchantExpandIndirectCreateModel model = new AntMerchantExpandIndirectCreateModel();
        model.setExternalId("15121000"); //商户编号，由机构定义，保证唯一
        model.setName("工商管理局"); //商户名称
        model.setAliasName("工商局");  //商户简称
        model.setServicePhone("95188"); //商户客服电话
        model.setCategoryId("2016062900190371"); //商户经营类目
        model.setSource(pid);  //商户来源机构表示

        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setName("张三"); //名字
        contactInfo.setType("LEGAL_PERSON"); //联系人类型
        contactInfo.setIdCardNo("13112719901011003X"); //身份证号

        AddressInfo addressInfo = new AddressInfo();
        addressInfo.setProvinceCode(""); //所在省编码
        addressInfo.setCityCode(""); //所在市编码
        addressInfo.setDistrictCode(""); //所在区编码
        addressInfo.setAddress(""); //详细经营地址

        BankCardInfo bankCardInfo = new BankCardInfo();
        bankCardInfo.setCardNo(""); //银行卡号
        bankCardInfo.setCardName(""); //银行持卡人姓名

        model.setContactInfo(Lists.newArrayList(contactInfo)); //商户联系人信息
//        model.setAddressInfo(Lists.newArrayList(addressInfo));
//        model.setBankcardInfo(Lists.newArrayList(bankCardInfo));
        return model;
    }


    public static void main(String[] args) {
        AliMerchantSyncService aliMerchantSyncService = new AliMerchantSyncService();
        aliMerchantSyncService.sync();
    }

}
