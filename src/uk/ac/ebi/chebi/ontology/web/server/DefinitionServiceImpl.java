package uk.ac.ebi.chebi.ontology.web.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;

public class DefinitionServiceImpl extends RemoteServiceServlet implements DefinitionService {
    public Definition fetchDefinition(String id) {
        return null;
    }
}