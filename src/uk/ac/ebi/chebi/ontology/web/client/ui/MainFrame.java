package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gwtai.applet.client.AppletJSUtil;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;
import uk.ac.ebi.chebi.ontology.web.client.applet.JCPApplet;


public class MainFrame extends Composite{
    interface MainFrameUiBinder extends UiBinder<TabLayoutPanel, MainFrame> {
    }

    private static MainFrameUiBinder ourUiBinder = GWT.create(MainFrameUiBinder.class);
    private TabLayoutPanel tabLayoutPanel;

    public MainFrame() {
        tabLayoutPanel = ourUiBinder.createAndBindUi(this);
        initWidget(tabLayoutPanel);
    }



    public void addOntologyEditor(Definition definition){
        DefinitionEditor definitionEditor = new DefinitionEditor();
        definitionEditor.setDefinition(definition);
        addClosableTab(definitionEditor,"Editor"+(definition.id==null?"":("-"+definition.id)));
    }

    public void addClosableTab(final Widget widget,String title){
        FlowPanel titlePanel=new FlowPanel();
        Label label = new Label(title);
        label.setStyleName("float-left");
        titlePanel.add(label);
        Label btClose=new Label("X");
        btClose.setStyleName("float-left");
        btClose.addStyleName("margin-left-5px");
        btClose.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                tabLayoutPanel.remove(widget);
            }
        });
        titlePanel.add(btClose);
        tabLayoutPanel.add(widget,titlePanel);
        tabLayoutPanel.selectTab(widget);
    }


}