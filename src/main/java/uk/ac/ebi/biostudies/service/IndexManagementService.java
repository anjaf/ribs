package uk.ac.ebi.biostudies.service;

public interface IndexManagementService {
    void stopAcceptingSubmissionMessagesAndCloseIndices();
    void openIndicesWritersAndSearchersStartStomp();
    void openEfoIndexAndLoadOntology();
    boolean isClosed();
    void closeWebsocket();
    void openWebsocket();
}
