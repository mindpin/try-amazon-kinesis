package com.github.mindpin.try_amazon_kinesis.manager;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.Shard;
import com.github.mindpin.try_amazon_kinesis.util.ConfigurationUtils;
import com.github.mindpin.try_amazon_kinesis.util.Constant;
import com.github.mindpin.try_amazon_kinesis.util.CredentialUtils;
import com.github.mindpin.try_amazon_kinesis.util.ShardUtils;

import java.util.List;

/**
 * Created by li fei on 2015/10/8.
 */
public class ShardInfoMain {
    public static void main(String[] args){
        AWSCredentials credentials = CredentialUtils.get_credentials();
        AmazonKinesis client = new AmazonKinesisClient(credentials, ConfigurationUtils.get_config());
        client.setRegion(Constant.REGIN);

        List<Shard> shards = ShardUtils.get_shards(client, Constant.STREAM_NAME);

        for(Shard shard: shards){
            ShardUtils.shard_info(shard);
        }

    }
}
