package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionServiceAsync;
import uk.ac.ebi.chebi.ontology.web.client.Ontology;

import java.util.List;

public class ClassList extends Composite {
    interface ClassListUiBinder extends UiBinder<ScrollPanel, ClassList> {
    }

    private static ClassListUiBinder ourUiBinder = GWT.create(ClassListUiBinder.class);
    @UiField
    VerticalPanel verticalPanel;
    @UiField
    Button btAdd;

    public ClassList() {
        final ScrollPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
        refreshClassList();
    }

    public void refreshClassList() {
        DefinitionService.App.getInstance().listAllDefinitions(new AsyncCallback<List<Definition>>() {
            public void onFailure(Throwable throwable) {
                verticalPanel.clear();
                verticalPanel.add(new Label("Error Loading Ontology List, " + throwable.getMessage()));
            }

            public void onSuccess(List<Definition> definitions) {
                verticalPanel.clear();
                if(definitions!=null){
                    for(final Definition definition:definitions){
                        Label label = new Label(definition.name);
                        label.addClickHandler(new ClickHandler() {
                            public void onClick(ClickEvent event) {
                                Ontology.App.getInstance().getMainFrame().addOntologyEditor(definition);
                            }
                        });
                        verticalPanel.add(label);
                    }
                }
            }
        });
    }


    @UiHandler("btAdd")
    void handleBtAddClick(ClickEvent event){
        Ontology.App.getInstance().getMainFrame().addOntologyEditor(new Definition());
    }
}