package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.ontology.core.definition.match.MatchingResult;
import uk.ac.ebi.chebi.ontology.core.engine.MatchEngine;

import java.util.List;

@RemoteServiceRelativePath("DefinitionService")
public interface DefinitionService extends RemoteService {
    /**
     * Utility/Convenience class.
     * Use DefinitionService.App.getInstance() to access static instance of DefinitionServiceAsync
     */
    public static class App {
        private static final DefinitionServiceAsync ourInstance = (DefinitionServiceAsync) GWT.create(DefinitionService.class);

        public static DefinitionServiceAsync getInstance() {
            ((ServiceDefTarget) ourInstance).setServiceEntryPoint( "uk.ac.ebi.chebi.ontology.web.Ontology/DefinitionService");

            return ourInstance;
        }
    }
    public Definition fetchDefinition(String id);

    /**
     * List all definitions
     * @return list of definitions, only meta data(name and comment)
     */
    public List<Definition> listAllDefinitions();

    public void saveDefinition(Definition definition);

    public List<ChEBICompound> searchChEBI(String query);

    public List<ChEBICompound> getChildren(int id,int start,int end);

    public Definition getDefinition(int id);

    public long validateDefinition(int id);

    public double checkValidationProgress(long threadId);

    public MatchingResult getValidationResult(long threadId);


}