package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;
import uk.ac.ebi.chebi.ontology.web.client.Ontology;

public class DefinitionEditor extends Composite {
    public Definition definition;

    public Definition getDefinition() {
        return definition;
    }

    interface Binder extends UiBinder<DockLayoutPanel, DefinitionEditor> {
    }

    private Ontology ontology;

    public Ontology getOntology() {
        return ontology;
    }

    public void setOntology(Ontology ontology) {
        this.ontology = ontology;
    }

    private static Binder ourUiBinder = GWT.create(Binder.class);
    @UiField
    TextBox tbName;
    @UiField
    TextArea taComment;
    @UiField
    TextBox tbId;
    @UiField
    Button btSave;
    @UiField
    JCPWidget jcpWidget;


    public DefinitionEditor() {
        DockLayoutPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
        refreshUI();
    }

    private void refreshUI() {
        if(definition.id==null){ //Enable edit if is a new class
            tbId.setEnabled(true);
        }else{
            tbId.setText(definition.id);
            tbId.setEnabled(false);
        }
        tbName.setText(definition.name);
        taComment.setText(definition.comment);
    }

    private void updateDataObject() {
        definition.id=tbId.getText();
        definition.name = tbName.getText();
        definition.comment = taComment.getText();
//        definition.rootDefinition=jcpWidget.getApplet().getSkeleton();
//        Window.alert(definition.rootDefinition.toString());
        jcpWidget.getApplet().getTestString(new AsyncCallback<String>() {
            public void onFailure(Throwable throwable) {
                Window.alert(throwable.toString());
            }

            public void onSuccess(String s) {
                Window.alert(s);
            }
        });
    }

    @UiHandler("btSave")
    void handleBtSaveClick(ClickEvent event){
        updateDataObject();
        DefinitionService.App.getInstance().saveDefinition(definition,new AsyncCallback<Void>() {
            public void onFailure(Throwable throwable) {

            }

            public void onSuccess(Void aVoid) {
                refreshUI();
                Ontology.App.getInstance().getSideBar().getClassList().refreshClassList();
//                Window.alert("Saved");
            }
        });
    }

}