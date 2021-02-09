package uk.ac.ebi.biostudies.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import uk.ac.ebi.biostudies.config.MongoDBConfig;
import uk.ac.ebi.biostudies.service.MongoDBService;

@Service
@Scope("singleton")

public class MongoDBServiceImpl implements MongoDBService {

    private Logger logger = LogManager.getLogger(MongoDBServiceImpl.class.getName());

    private MongoCollection<Document> collection;

    @Autowired
    MongoDBConfig mongoDBConfig;

    @Override
    @Async
    public void replaceOne(JsonNode submission) {
        if (!mongoDBConfig.isPartialUpdatesEnabled()) return;
        final boolean isInOldFormat = submission.has("accno");
        String accession = isInOldFormat ? submission.get("accno").textValue() : submission.get("accNo").textValue();
        try {
            Document doc = Document.parse(submission.toString());
            if (isInOldFormat) doc.put("accNo", accession);
            collection.replaceOne(Filters.eq("accNo", accession), doc, new ReplaceOptions().upsert(true));
        } catch (Exception e) {
            System.err.println("error in " + accession);
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void afterPropertiesSet() {
        if (!mongoDBConfig.isPartialUpdatesEnabled()) return;
        MongoClient mongoClient = MongoClients.create(mongoDBConfig.getMongoConnectionString());
        MongoDatabase db = mongoClient.getDatabase(mongoDBConfig.getSchema());
        collection = db.getCollection(mongoDBConfig.getCollection());
    }
}
