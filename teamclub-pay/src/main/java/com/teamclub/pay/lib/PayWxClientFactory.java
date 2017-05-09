package com.teamclub.pay.lib;


import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ilkkzm on 17-5-5.
 */
public class PayWxClientFactory {
    private static PayWxClientFactory instance = new PayWxClientFactory();
    private static ConcurrentHashMap<String, WxSslClient> wxClients = new ConcurrentHashMap();

    public PayWxClientFactory() {
    }

    public static PayWxClientFactory getInstance() {
        return instance;
    }

    public WxSslClient defaultWxSslClient() {
        return this.with(PaySetting.defaultSetting());
    }

    public WxSslClient with(PaySetting paySetting) {
        if(!wxClients.containsKey(this.key(paySetting))) {
            WxSslClient wxClient = new WxSslClient(paySetting.getCert(), paySetting.getCertPassword());
            wxClients.putIfAbsent(this.key(paySetting), wxClient);
        }

        return (WxSslClient)wxClients.get(this.key(paySetting));
    }

    private String key(PaySetting paySetting) {
        return paySetting.getAppId() + ":" + paySetting.getMchId();
    }
}
