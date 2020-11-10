package uk.ac.ebi.biostudies.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:mongodb.properties")
public class MongoDBConfig {

    public String getMongoConnectionString() {
        return mongoConnectionString;
    }

    public boolean isPartialUpdatesEnabled() {
        return partialUpdatesEnabled;
    }

    public String getSchema() {
        return schema;
    }

    public String getCollection() {
        return collection;
    }

    @Value("${mongodb.connectionString}")
    private String mongoConnectionString;

    @Value("${mongodb.schema}")
    private String schema;

    @Value("${mongodb.collection}")
    private String collection;

    @Value("${mongodb.partial.updates.enabled}")
    private boolean partialUpdatesEnabled;

}
