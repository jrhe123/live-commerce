package org.example.live.common.interfaces.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ThreadLocalRandom;

import ch.qos.logback.core.PropertyDefinerBase;

public class IpLogConversionRule extends PropertyDefinerBase {

    @Override
    public String getPropertyValue() {
        return this.getLogIndex();
    }

    /**
     * Get container IP addr as log file name
     * 
     * @return
     */
    private String getLogIndex() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000));
    }
}