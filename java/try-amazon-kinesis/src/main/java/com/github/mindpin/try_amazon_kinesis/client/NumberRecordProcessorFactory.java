package com.github.mindpin.try_amazon_kinesis.client;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;

/**
 * Created by li fei on 2015/9/30.
 */
public class NumberRecordProcessorFactory implements IRecordProcessorFactory {
    public NumberRecordProcessorFactory() {
        super();
    }

    public IRecordProcessor createProcessor() {
        return new NumberRecordProcessor();
    }
}
