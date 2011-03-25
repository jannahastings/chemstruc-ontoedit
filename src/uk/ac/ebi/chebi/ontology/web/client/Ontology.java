package uk.ac.ebi.chebi.ontology.web.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import uk.ac.ebi.chebi.ontology.web.client.ui.MainFrame;
import uk.ac.ebi.chebi.ontology.web.client.ui.SideBar;

public class Ontology implements EntryPoint {
    @UiTemplate("Ontology.ui.xml")
    interface Binder extends UiBinder<DockLayoutPanel, Ontology> {}

    private static final Binder binder = GWT.create(Binder.class);
    @UiField
    SideBar sideBar;
    @UiField
    MainFrame mainFrame;

    public SideBar getSideBar() {
        return sideBar;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public static class App {
        private static Ontology ourInstance;

        public static Ontology getInstance() {
            return ourInstance;
        }
    }
    public Ontology(){
        super();
        App.ourInstance=this;
    }


    public void onModuleLoad() {
        // Create the UI defined in Ontology.ui.xml.
        DockLayoutPanel outer = binder.createAndBindUi(this);

       // Get rid of scrollbars, and clear out the window's built-in margin, because we want to take advantage of the entire client area.
        Window.enableScrolling(false);
        Window.setMargin("0px");
        // Add the outer panel to the RootLayoutPanel, so that it will be displayed.
        RootLayoutPanel root = RootLayoutPanel.get();
        root.add(outer);

    }
}
