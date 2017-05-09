package com.teamclub.pay.services;

import com.riversoft.weixin.common.WxSslClient;
import com.riversoft.weixin.common.util.JsonMapper;
import com.riversoft.weixin.common.util.XmlObjectMapper;
import com.riversoft.weixin.pay.PayWxClientFactory;
import com.riversoft.weixin.pay.base.PaySetting;
import com.riversoft.weixin.pay.util.SignatureUtil;
import com.teamclub.pay.dtos.DtoQqMerchantSync;

import java.util.HashMap;
import java.util.SortedMap;

/**
 * Created by ilkkzm on 17-5-8.
 */
public class QqMerchantSyncService {
    public static String url = "https://api.qpay.qq.com/cgi-bin/merchant/qpay_submch_manage.cgi";
    public static String signKey = "95c2ab5f40c8179f3d5c1a2980193953";

    public static void main(String[] args) throws Exception{
        DtoQqMerchantSync sync = new DtoQqMerchantSync();
        sync.setAction("add");
        sync.setMchId("1460374001");
        sync.setMerchantName("中华人民共和国");
        sync.setMerchantShortName("共产党");
        sync.setServicePhone("8888888");
        sync.setBusiness("线下零售-超市");
        sync.setMerchantRemark("12312312113");
        SortedMap<String, Object> reqMap = (SortedMap) JsonMapper.nonEmptyMapper().getMapper().convertValue(sync, SortedMap.class);
        String sign = SignatureUtil.sign(reqMap, signKey);
        sync.setSign(sign);
        String s = XmlObjectMapper.nonEmptyMapper().toXml(sync);
        System.out.println("req: " + s);
        PaySetting paySetting = new PaySetting();
        paySetting.setMchId("1460374001");
        paySetting.setCertPath("apiclient_cert.p12");
        paySetting.setCertPassword("1460374001");
        WxSslClient client = PayWxClientFactory.getInstance().with(paySetting);
        String resp = client.post(url, s);
        HashMap<String,String> respMap = XmlObjectMapper.nonEmptyMapper().fromXml(resp, HashMap.class);
        if("0".equals(respMap.get("retcode"))) {
            String qqNum = respMap.get("sub_mch_id");
        }else {
            System.out.println(respMap.get("retmsg"));
        }
    }
}
