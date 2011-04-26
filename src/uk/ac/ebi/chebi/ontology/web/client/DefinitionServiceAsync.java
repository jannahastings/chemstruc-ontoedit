package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;

import java.util.List;

public interface DefinitionServiceAsync {
    void fetchDefinition(String id, AsyncCallback<Definition> async);

    void listAllDefinitions(AsyncCallback<List<Definition>> async);

    void saveDefinition(Definition definition, AsyncCallback<Void> async);

    void searchChEBI(String query, AsyncCallback<List<ChEBICompound>> async);
}
