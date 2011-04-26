package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionServiceAsync;

import java.util.List;

public class SearchPanel extends Composite {
    interface SearchPanelUiBinder extends UiBinder<HTMLPanel, SearchPanel> {
    }

    private static SearchPanelUiBinder ourUiBinder = GWT.create(SearchPanelUiBinder.class);
    @UiField
    Button btSearch;
    @UiField
    TextBox tbQuery;
    @UiField
    SimplePanel resultPanel;
    //    @UiField

    public SearchPanel() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    @UiHandler("btSearch")
    void handleBtSearchClick(ClickEvent event){
        String query = tbQuery.getText();
        if(!query.isEmpty()){
            final SearchResultTable resultTable;
            resultTable=new SearchResultTable();
            this.resultPanel.setWidget(resultTable);
            DefinitionService.App.getInstance().searchChEBI(query,new AsyncCallback<List<ChEBICompound>>(){

                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("FAILED");
                    throwable.printStackTrace();
                }

                @Override
                public void onSuccess(List<ChEBICompound> chEBICompounds) {
                    for (ChEBICompound result : chEBICompounds) {
                        System.out.println(result);
                    }
                    resultTable.setResult(chEBICompounds);

                }
            });
        }
    }
}