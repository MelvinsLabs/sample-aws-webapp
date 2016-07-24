/*
 * 
 */

package me.melvins.labs.config;

import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mels
 */
@Configuration
public class AmazonConfig {

    private static final Logger LOGGER = LogManager.getLogger(AmazonConfig.class, new MessageFormatMessageFactory());

    private AWSCredentialsProviderChain awsCredentialsProviderChain;

    public AmazonConfig() {
        LOGGER.debug("------- Initializing AmazonConfig -------");
        createAWSCredentialsProviderChain();
    }

    private void createAWSCredentialsProviderChain() {
        LOGGER.info("------- Initializing AWSCredentialsProviderChain -------");
        awsCredentialsProviderChain =
                new AWSCredentialsProviderChain(
                        new InstanceProfileCredentialsProvider(),
                        new ProfileCredentialsProvider());
    }

    @Bean
    public AmazonDynamoDBClient amazonDynamoDBClient() {
        LOGGER.info("------- Defining AmazonDynamoDBClient -------");
        return new AmazonDynamoDBClient(this.awsCredentialsProviderChain)
                .withRegion(Regions.US_WEST_2);
    }

    @Bean
    public DynamoDB dynamoDB() {
        LOGGER.info("------- Defining DynamoDB -------");
        return new DynamoDB(amazonDynamoDBClient());
    }
}
