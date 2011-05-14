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
    Button btSave;
    @UiField
    JCPWidget jcpWidget;
    @UiField
    Button btPreview;
    @UiField
    SimplePanel westPanel;
    @UiField
    Label lbId;
    @UiField
    Label lbName;
    @UiField
    Label lbTextDefinition;

    int classId;

    public DefinitionEditor(int classId) {
        this.classId=classId;
        DockLayoutPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
//
        loadDefinition(classId);
    }

    public void loadDefinition(int classId){
        DefinitionService.App.getInstance().getDefinition(classId,new AsyncCallback<Definition>() {
            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(Definition result) {
                System.out.println(result);
                setDefinition(result);
            }
        });
    }

    public void setDefinition(Definition definition) {
        this.definition = definition;
        westPanel.setWidget(new ChildrenList(classId));
        refreshUI();
    }

    private void refreshUI() {
        lbId.setText(String.valueOf(definition.id));
        lbName.setText(definition.name);
        lbTextDefinition.setText(definition.comment);
    }

    private void updateDataObject() {
//        definition.id= Integer.valueOf(lbId.getText());
//        definition.name = lbName.getText();

        definition.rootDefinitionString=jcpWidget.getApplet().getDefinitionString();
        Window.alert(definition.rootDefinitionString);
//        definition.rootDefinition=jcpWidget.getApplet().getRootStructure();
//        Window.alert(jcpWidget.getApplet().getTestString());
    }

    @UiHandler("btSave")
    void handleBtSaveClick(ClickEvent event){
        updateDataObject();
        DefinitionService.App.getInstance().saveDefinition(definition,new AsyncCallback<Void>() {
            public void onFailure(Throwable throwable) {
                System.out.println(throwable);
            }

            public void onSuccess(Void aVoid) {
                refreshUI();
                Ontology.App.getInstance().getSideBar().getClassList().refreshClassList();
            }
        });
    }

    @UiHandler("btPreview")
    void handleBTPreviewClick(ClickEvent event){
        Window.alert(jcpWidget.getApplet().setDefinitionString(definition.rootDefinitionString));


//        updateDataObject();
//        Ontology.App.getInstance().getMainFrame().addClosableTab(new DefinitionPreview(),"Preview"+definition.id);
    }

}