package com.github.mindpin.try_amazon_kinesis.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;

/**
 * Created by li fei on 2015/9/30.
 */
public class CredentialUtils {
    public static AWSCredentials get_credentials(){
        DefaultAWSCredentialsProviderChain credential_chain = new DefaultAWSCredentialsProviderChain();
        return credential_chain.getCredentials();
    }
}
