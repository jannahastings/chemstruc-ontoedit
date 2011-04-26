package uk.ac.ebi.chebi.ontology.web.client.applet;

import com.google.gwt.gwtai.applet.client.*;
import org.openscience.jchempaint.applet.OntologyEditorApplet;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;

@ImplementingClass(OntologyEditorApplet.class)
@Height("350")
@Width("350")
@Archive("GwtAI-Client-0.3.jar,cdk-1.3.7.jar,chemontology.jar,xstream-1.3.1.jar")
public interface JCPApplet extends Applet {
//    public void setSkeleton(Skeleton skeleton);
//    public Skeleton getSkeleton();
    public String getTestString();
}
