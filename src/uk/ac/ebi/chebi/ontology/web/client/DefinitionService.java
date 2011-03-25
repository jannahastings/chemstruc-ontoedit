package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;

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
}
