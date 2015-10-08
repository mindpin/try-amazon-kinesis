package com.github.mindpin.try_amazon_kinesis.client;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.amazonaws.services.kinesis.model.GetShardIteratorRequest;
import com.github.mindpin.try_amazon_kinesis.util.ConfigurationUtils;
import com.github.mindpin.try_amazon_kinesis.util.Constant;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;

/**
 * Created by li fei on 2015/9/30.
 */
public class Main {
    private static final Log LOG = LogFactory.getLog(Main.class);


    public static void main(String[] args){
        String worker_id = String.valueOf(UUID.randomUUID());
        KinesisClientLibConfiguration kclConfig =
                new KinesisClientLibConfiguration(Constant.APPLICATION_NAME, Constant.STREAM_NAME, new ProfileCredentialsProvider(), worker_id)
                        .withRegionName(Constant.REGIN.getName())
                        .withCommonClientConfig(ConfigurationUtils.get_config());

        kclConfig.withInitialPositionInStream(InitialPositionInStream.TRIM_HORIZON);

        IRecordProcessorFactory recordProcessorFactory = new NumberRecordProcessorFactory();

        Worker worker = new Worker(recordProcessorFactory, kclConfig);

        int exitCode = 0;
        try {
            worker.run();
        } catch (Throwable t) {
            LOG.error("Caught throwable while processing data.", t);
            exitCode = 1;
        }
        System.exit(exitCode);
    }

}
