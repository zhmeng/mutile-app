package com.teamclub.pay.services;

import com.avaje.ebean.EbeanServer;
import com.riversoft.weixin.common.util.JsonMapper;
import com.riversoft.weixin.common.util.XmlObjectMapper;
import com.riversoft.weixin.pay.util.SignatureUtil;
import com.teamclub.domain.payment.GatewayCert;
import com.teamclub.pay.dtos.DtoMerchantSync;
import com.teamclub.pay.lib.PaySetting;
import com.teamclub.pay.lib.PayWxClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.SortedMap;

/**
 * Created by ilkkzm on 17-5-4.
 */
@Service
public class WeixinMerchantSyncService {
    private Logger logger = LoggerFactory.getLogger(WeixinMerchantSyncService.class);
    private String syncUrl = "https://api.mch.weixin.qq.com/secapi/mch/submchmanage?action=add";
    private String appid = "wxdace645e0bc2c424";
    private String mchId = "1900008971";
    private String signKey = "3ACA91426F056322E053645AA8C0CC12";
    private String subMchId = "11386316";

    @Autowired
    private EbeanServer server;

    public String buildData() {
        DtoMerchantSync dtoMerchantSync = new DtoMerchantSync();
        dtoMerchantSync.setAppid(appid);
        dtoMerchantSync.setMchId(mchId);
        dtoMerchantSync.setMerchantName("中华人民共和国1");
        dtoMerchantSync.setMerchantShortname("共产党1");
        dtoMerchantSync.setServicePhone("8888888");
        dtoMerchantSync.setChannelId(mchId);
        dtoMerchantSync.setBusiness("204");
        dtoMerchantSync.setMerchantRemark("中华人民共和国国歌1");
        SortedMap<String, Object> reqMap = (SortedMap) JsonMapper.nonEmptyMapper().getMapper().convertValue(dtoMerchantSync, SortedMap.class);
        String sign = SignatureUtil.sign(reqMap, signKey);
        dtoMerchantSync.setSign(sign);
        try {
            return XmlObjectMapper.nonEmptyMapper().toXml(dtoMerchantSync);
        }catch (Exception e) {
            return "";
        }
    }

    public void sync() {
        PaySetting paySetting = new PaySetting();
        paySetting.setAppId(appid);
        paySetting.setMchId(mchId);
        paySetting.setKey(signKey);
        GatewayCert gatewayCert = server.find(GatewayCert.class).where().eq("id", "1900008971").setMaxRows(1).findUnique();
        paySetting.setCert(gatewayCert.getCertContent());
        paySetting.setCertPassword(gatewayCert.getCertPwd());
        String s = buildData();
        logger.info("req data: {}", s);
        String resp = PayWxClientFactory.getInstance().with(paySetting).post(syncUrl, s);
        logger.info("resp: {}", resp);

    }
}
