package com.dryox.water_pls_mobile.client;

import com.dryox.water_pls_mobile.domain.StompMessage;

/**
 * Created by chen0 on 10/12/2017.
 */

public interface StompMessageListener {
    void onMessage(StompMessage message);
}
