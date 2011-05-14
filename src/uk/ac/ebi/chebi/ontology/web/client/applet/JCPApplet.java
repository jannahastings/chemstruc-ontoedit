package uk.ac.ebi.chebi.ontology.web.client.applet;

import com.google.gwt.gwtai.applet.client.*;
import org.openscience.jchempaint.applet.OntologyEditorApplet;

@ImplementingClass(OntologyEditorApplet.class)
@Height("350")
@Width("350")
@Archive("GwtAI-Client.jar,cdk-1.3.7.jar,chemontology.jar,xstream-1.3.1.jar")
public interface JCPApplet extends Applet {
//    public void setRootStructure(RootStructure rootStructure);
//    public RootStructure getRootStructure();
    public String getTestString();
    public String setTestString(String test);
    public String getDefinitionString();
    public String setDefinitionString(String definitionString);
}
