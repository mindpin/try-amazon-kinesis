package com.github.mindpin.try_amazon_kinesis.util;

import com.amazonaws.ClientConfiguration;

/**
 * Created by li fei on 2015/9/30.
 */
public class ConfigurationUtils {
    public static ClientConfiguration get_config(){
        ClientConfiguration config = new ClientConfiguration();
        StringBuilder userAgent = new StringBuilder(ClientConfiguration.DEFAULT_USER_AGENT);

        userAgent.append(" ");
        userAgent.append(Constant.APPLICATION_NAME);
        userAgent.append("/");
        userAgent.append(Constant.VERSION);

        config.setUserAgent(userAgent.toString());

        return config;
    }
}
