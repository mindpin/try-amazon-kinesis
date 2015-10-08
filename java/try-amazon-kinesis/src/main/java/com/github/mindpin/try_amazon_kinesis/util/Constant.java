package com.github.mindpin.try_amazon_kinesis.util;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;

/**
 * Created by li fei on 2015/9/30.
 */
public class Constant {
    public static Region REGIN = RegionUtils.getRegion("cn-north-1");

    public static String STREAM_NAME = "try_amazon_kinesis";

    public static final String APPLICATION_NAME = "try-amazon-kinesis";
    public static final String VERSION = "1.0.0";
}
