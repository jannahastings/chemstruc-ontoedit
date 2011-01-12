package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;

public class Ontology implements EntryPoint {
    public void onModuleLoad() {
       DockLayoutPanel dock = new DockLayoutPanel(Style.Unit.PX);
       Label label=new Label("Ontology Editor");
       dock.addNorth(label,20);

       RootPanel.get().add(dock);
    }
}
