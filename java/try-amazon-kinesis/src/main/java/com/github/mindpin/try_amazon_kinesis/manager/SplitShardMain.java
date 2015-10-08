package com.github.mindpin.try_amazon_kinesis.manager;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.*;
import com.github.mindpin.try_amazon_kinesis.util.ConfigurationUtils;
import com.github.mindpin.try_amazon_kinesis.util.Constant;
import com.github.mindpin.try_amazon_kinesis.util.CredentialUtils;
import com.github.mindpin.try_amazon_kinesis.util.ShardUtils;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by li fei on 2015/10/8.
 */
public class SplitShardMain {
    public static void main(String[] args){
        AWSCredentials credentials = CredentialUtils.get_credentials();
        AmazonKinesis client = new AmazonKinesisClient(credentials, ConfigurationUtils.get_config());
        client.setRegion(Constant.REGIN);

        List<Shard> shards = ShardUtils.get_shards(client, Constant.STREAM_NAME);
        ShardUtils.shard_info(shards.get(0));
        Shard shard = shards.get(0);

        SplitShardRequest splitShardRequest = new SplitShardRequest();
        splitShardRequest.setStreamName(Constant.STREAM_NAME);
        splitShardRequest.setShardToSplit(shard.getShardId());

        BigInteger startingHashKey = new BigInteger(shard.getHashKeyRange().getStartingHashKey());
        BigInteger endingHashKey   = new BigInteger(shard.getHashKeyRange().getEndingHashKey());

        splitShardRequest.setNewStartingHashKey("221927652340938865172815364521841099064");
        client.splitShard(splitShardRequest);

        shards = ShardUtils.get_shards(client, Constant.STREAM_NAME);

        for(Shard shard1 : shards){
            ShardUtils.shard_info(shard1);
        }

    }
}
