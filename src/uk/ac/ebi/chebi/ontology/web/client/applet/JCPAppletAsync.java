package uk.ac.ebi.chebi.ontology.web.client.applet;

import com.google.gwt.gwtai.applet.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.openscience.jchempaint.applet.OntologyEditorApplet;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;

public interface JCPAppletAsync {
    void setSkeleton(Skeleton skeleton, AsyncCallback<Void> async);

    void getSkeleton(AsyncCallback<Skeleton> async);

    void getTestString(AsyncCallback<String> async);
}
