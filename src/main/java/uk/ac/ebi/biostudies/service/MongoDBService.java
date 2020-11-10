package uk.ac.ebi.biostudies.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public interface MongoDBService extends InitializingBean, DisposableBean {

    void replaceOne(JsonNode submission);

}
