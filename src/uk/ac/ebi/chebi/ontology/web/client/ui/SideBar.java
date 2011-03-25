package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.DecoratedStackPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import uk.ac.ebi.chebi.ontology.web.client.Ontology;

public class SideBar extends ResizeComposite{
    @UiTemplate("SideBar.ui.xml")
    interface Binder extends com.google.gwt.uibinder.client.UiBinder<StackLayoutPanel,SideBar> {
    }

    private static Binder binder = GWT.create(Binder.class);
    @UiField
    ClassList classList;

    public ClassList getClassList() {
        return classList;
    }

    public SideBar() {
        StackLayoutPanel rootElement = binder.createAndBindUi(this);
        initWidget(rootElement);
    }


}