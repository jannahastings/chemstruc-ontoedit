package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;

public interface DefinitionServiceAsync {
    void fetchDefinition(String id, AsyncCallback<Definition> async);
}
