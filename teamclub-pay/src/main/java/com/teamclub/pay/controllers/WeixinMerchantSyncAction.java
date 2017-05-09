package com.teamclub.pay.controllers;

import com.teamclub.pay.services.WeixinMerchantSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-5-5.
 */
@RestController("com.teamclub.pay.controllers.WeixinMerchantSyncAction")
public class WeixinMerchantSyncAction {

    @Autowired
    private WeixinMerchantSyncService weixinMerchantSyncService;

    public String sync() {
        weixinMerchantSyncService.sync();
        return "HELLO WORLD";
    }
}
