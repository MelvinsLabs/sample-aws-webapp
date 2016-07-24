/*
 *
 */

package me.melvins.labs.capability;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import me.melvins.labs.exception.KnownException;
import me.melvins.labs.exception.handling.ErrorCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFormatMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * @author Mels
 */
@Component
public class CapabilityImpl implements Capability {

    private static final Logger LOGGER = LogManager.getLogger(CapabilityImpl.class,
            new MessageFormatMessageFactory());

    @Autowired
    private Environment env;

    @Autowired
    private DynamoDB dynamoDB;

    @Override
    public String read(String hashKey, String rangeKey) {

        String dynamoTableName = env.getProperty("dynamo.table.name");

        String dynamoTableHashKeyName = env.getProperty("dynamo.table.hashKey.name");
        String dynamoDBTableRangeKeyName = env.getProperty("dynamo.table.rangeKey.name");

        Table table = dynamoDB.getTable(dynamoTableName);

        Item item = table.getItem(dynamoTableHashKeyName, hashKey, dynamoDBTableRangeKeyName, rangeKey);

        if (item != null) {
            return item.toJSON();

        } else {
            return "Not Available";
        }
    }

}
