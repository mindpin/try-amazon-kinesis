package com.github.mindpin.try_amazon_kinesis.client;

import com.amazonaws.services.kinesis.clientlibrary.exceptions.InvalidStateException;
import com.amazonaws.services.kinesis.clientlibrary.exceptions.ShutdownException;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorCheckpointer;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownReason;
import com.amazonaws.services.kinesis.model.Record;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

/**
 * Created by li fei on 2015/9/30.
 */
public class NumberRecordProcessor implements IRecordProcessor {
    private static final Log LOG = LogFactory.getLog(NumberRecordProcessor.class);
    private String shard_id;
    private final CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();

    public void initialize(String shard_id) {
        System.out.println("initialize shard_id: " + shard_id );
        this.shard_id = shard_id;
    }

    public void processRecords(List<Record> records, IRecordProcessorCheckpointer checkpointer) {
        for (Record record : records) {
            processRecord(record);
        }
        try {
            checkpointer.checkpoint();
        } catch (InvalidStateException e) {
            e.printStackTrace();
        } catch (ShutdownException e) {
            e.printStackTrace();
        }
    }

    private void processRecord(Record record) {
        String data = null;
        try {
            data = decoder.decode(record.getData()).toString();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        System.out.println("!process record : " + data + " shard_id: " + this.shard_id);
        LOG.info("process record: " + data + " , PartitionKey: " + record.getPartitionKey() + " , shard_id:" + shard_id);
    }

    public void shutdown(IRecordProcessorCheckpointer checkpointer, ShutdownReason shutdownReason) {
        System.out.println("shutdown " + this.shard_id + "  " + shutdownReason);
        if(ShutdownReason.TERMINATE == shutdownReason){
            try {
                checkpointer.checkpoint();
                System.out.println("shutdown checkpoint");
            } catch (InvalidStateException e) {
                e.printStackTrace();
            } catch (ShutdownException e) {
                e.printStackTrace();
            }
        }
    }
}
