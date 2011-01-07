package uk.ac.ebi.chebi.ontology.web.sample.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("SampleService")
public interface SampleService extends RemoteService {
    // Sample interface method of remote interface
    String sendDefinition(DefinitionClass msg);

    /**
     * Utility/Convenience class.
     * Use SampleService.App.getInstance() to access static instance of SampleServiceAsync
     */
    public static class App {
        private static SampleServiceAsync ourInstance = GWT.create(SampleService.class);

        public static synchronized SampleServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
