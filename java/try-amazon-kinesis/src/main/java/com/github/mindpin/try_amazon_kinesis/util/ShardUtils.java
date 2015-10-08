package com.github.mindpin.try_amazon_kinesis.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.DescribeStreamRequest;
import com.amazonaws.services.kinesis.model.DescribeStreamResult;
import com.amazonaws.services.kinesis.model.Shard;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by li fei on 2015/10/8.
 */
public class ShardUtils {
    public static List<Shard> get_shards(AmazonKinesis client, String steam_name){
        DescribeStreamRequest describeStreamRequest = new DescribeStreamRequest();
        describeStreamRequest.setStreamName(steam_name);
        List<Shard> shards = new ArrayList<Shard>();
        String exclusiveStartShardId = null;
        do {
            describeStreamRequest.setExclusiveStartShardId( exclusiveStartShardId );
            DescribeStreamResult describeStreamResult = client.describeStream( describeStreamRequest );
            shards.addAll( describeStreamResult.getStreamDescription().getShards() );
            if (describeStreamResult.getStreamDescription().getHasMoreShards() && shards.size() > 0) {
                exclusiveStartShardId = shards.get(shards.size() - 1).getShardId();
            } else {
                exclusiveStartShardId = null;
            }
        } while ( exclusiveStartShardId != null );

        System.out.println("shares size: " + shards.size());
        return shards;
    }

    public static void shard_info(Shard shard){
        System.out.println("shard id : " + shard.getShardId());
        System.out.println("shard startingHashKey : " + shard.getHashKeyRange().getStartingHashKey());
        System.out.println("shard endingHashKey : " + shard.getHashKeyRange().getEndingHashKey());
    }
}
