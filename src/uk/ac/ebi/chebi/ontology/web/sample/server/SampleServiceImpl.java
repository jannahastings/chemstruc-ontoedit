package uk.ac.ebi.chebi.ontology.web.sample.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import uk.ac.ebi.chebi.ontology.web.sample.client.DefinitionClass;
import uk.ac.ebi.chebi.ontology.web.sample.client.SampleService;

public class SampleServiceImpl extends RemoteServiceServlet implements SampleService {
    // Implementation of sample interface method
    public String sendDefinition(DefinitionClass msg) {
        return "Class Name: \"" + msg.getClassName();
    }
}