package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.ontology.core.definition.match.MatchingResult;
import uk.ac.ebi.chebi.ontology.core.engine.MatchEngine;

import java.util.List;

public interface DefinitionServiceAsync {
    void fetchDefinition(String id, AsyncCallback<Definition> async);

    void listAllDefinitions(AsyncCallback<List<Definition>> async);

    void saveDefinition(Definition definition, AsyncCallback<Void> async);

    void searchChEBI(String query, AsyncCallback<List<ChEBICompound>> async);

    void getChildren(int id, int start, int end, AsyncCallback<List<ChEBICompound>> async);

    void getDefinition(int id, AsyncCallback<Definition> async);

    void getValidationResult(long threadId, AsyncCallback<MatchingResult> async);

    void checkValidationProgress(long threadId, AsyncCallback<Double> async);

    void validateDefinition(int id, AsyncCallback<Long> async);
}
