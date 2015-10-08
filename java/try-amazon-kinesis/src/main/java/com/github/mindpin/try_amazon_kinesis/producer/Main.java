package com.github.mindpin.try_amazon_kinesis.producer;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.model.CreateStreamRequest;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.github.mindpin.try_amazon_kinesis.util.ConfigurationUtils;
import com.github.mindpin.try_amazon_kinesis.util.Constant;
import com.github.mindpin.try_amazon_kinesis.util.CredentialUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by li fei on 2015/9/30.
 */
public class Main {
    private static final Log LOG = LogFactory.getLog(Main.class);
    private static int CURRENT_INDEX = 0;

    public static void main( String[] args ) throws IOException {
//        InputStream is = Main.class.getResourceAsStream("/input.txt");
//        LINES = IOUtils.toString(is).split("\r\n");

        AWSCredentials credentials = CredentialUtils.get_credentials();

        AmazonKinesis client = new AmazonKinesisClient(credentials, ConfigurationUtils.get_config());
        client.setRegion(Constant.REGIN);

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            put_record(client, get_current_index());
        }
    }

    private static void put_record(AmazonKinesis client, int current_index) {
        PutRecordRequest putRecord = new PutRecordRequest();
        putRecord.setStreamName(Constant.STREAM_NAME);
        putRecord.setPartitionKey(get_partition_key(current_index));
        putRecord.setExplicitHashKey(get_explicit_key(current_index));
        putRecord.setData(ByteBuffer.wrap((current_index + "").getBytes()));

        try {
            PutRecordResult pr = client.putRecord(putRecord);
            System.out.println("put record : " + current_index + "  shard id: " + pr.getShardId());
        } catch (AmazonClientException ex) {
            LOG.warn("Error sending record to Amazon Kinesis.", ex);
        }
    }

    private static int get_current_index(){
        int index = CURRENT_INDEX;
        CURRENT_INDEX+=1;
        return index;
    }

    private static String get_partition_key(int current_index){
        if(current_index % 2 == 0){
            return "even";
        }else{
            return "odd";
        }
    }

    private static String get_explicit_key(int current_index){
        if(current_index % 2 == 0){
            return "180141183460469231731687303715884105727";
        }else{
            return "1";
        }

    }

}
