package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;

/**
 * Created by IntelliJ IDEA.
 * User: chemhack
 * Date: 11-1-12
 * Time: 下午4:39
 * To change this template use File | Settings | File Templates.
 */
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

}
