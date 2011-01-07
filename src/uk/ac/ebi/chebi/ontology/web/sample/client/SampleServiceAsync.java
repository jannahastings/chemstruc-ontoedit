package uk.ac.ebi.chebi.ontology.web.sample.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SampleServiceAsync {

    // Sample interface method of remote interface
    void sendDefinition(DefinitionClass msg, AsyncCallback<String> async);
}
