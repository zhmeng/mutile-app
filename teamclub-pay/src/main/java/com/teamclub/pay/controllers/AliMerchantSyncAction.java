package com.teamclub.pay.controllers;

import com.teamclub.pay.services.AliMerchantSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilkkzm on 17-4-26.
 */
@RestController("com.teamclub.pay.controllers.AliMerchantSyncAction")
public class AliMerchantSyncAction {

    @Autowired
    private AliMerchantSyncService aliMerchantSyncService;

    public String sync() {
        aliMerchantSyncService.sync();
        return "HELLO XXX WORLD";
    }
}
