package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.cell.client.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.view.client.ListDataProvider;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.ontology.web.client.Ontology;

import java.util.List;


public class SearchResultTable extends Composite {
    interface SearchResultTableUiBinder extends UiBinder<HTMLPanel, SearchResultTable> {
    }

    private static SearchResultTableUiBinder ourUiBinder = GWT.create(SearchResultTableUiBinder.class);
    @UiField
    CellTable<ChEBICompound> cellTable;

    private ListDataProvider<ChEBICompound> dataProvider = new ListDataProvider<ChEBICompound>();

    List<ChEBICompound> result;
    public SearchResultTable() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
        TextColumn<ChEBICompound> nameColumn = new TextColumn<ChEBICompound>() {
            @Override
            public String getValue(ChEBICompound object) {
                return object.name;
            }
        };
        cellTable.addColumn(nameColumn,"Name");
        TextColumn<ChEBICompound> statusColumn = new TextColumn<ChEBICompound>() {
            @Override
            public String getValue(ChEBICompound object) {
                return object.status;
            }
        };
        cellTable.addColumn(statusColumn,"Status");

        TextColumn<ChEBICompound> programmaticColumn = new TextColumn<ChEBICompound>() {
            @Override
            public String getValue(ChEBICompound object) {
                return String.valueOf(object.hasProgrammaticDefinition);
            }
        };
        cellTable.addColumn(programmaticColumn,"Programmatic Definition");

        TextColumn<ChEBICompound> textDefinitionColumn = new TextColumn<ChEBICompound>() {
            @Override
            public String getValue(ChEBICompound object) {
                return object.textDefinition;
            }
        };
        cellTable.addColumn(textDefinitionColumn,"Text Definition");

        Column<ChEBICompound, Number> childrenCountColumn = new Column<ChEBICompound,Number>(new NumberCell()){
            @Override
            public Integer getValue(ChEBICompound object) {
                return object.childrenCount;
            }
        };
        cellTable.addColumn(childrenCountColumn,"Number of Children");


        Column<ChEBICompound, String> editButtonColumn = new Column<ChEBICompound, String>(
                new ButtonCell()) {
            @Override
            public String getValue(ChEBICompound object) {
                return "Edit";
            }
        };
        editButtonColumn.setFieldUpdater(new FieldUpdater<ChEBICompound, String>() {
            @Override
            public void update(int index, ChEBICompound object, String value) {
                Ontology.App.getInstance().getMainFrame().openDefinitionEditor(object.id);
            }
        });
        cellTable.addColumn(editButtonColumn,"Action");

        Column<ChEBICompound, String> validateButtonColumn = new Column<ChEBICompound, String>(
                new ButtonCell()) {
            @Override
            public String getValue(ChEBICompound object) {
                return "Validate";
            }
        };
        validateButtonColumn.setFieldUpdater(new FieldUpdater<ChEBICompound, String>() {
            @Override
            public void update(int index, ChEBICompound object, String value) {
                Ontology.App.getInstance().getMainFrame().openDefinitionValidator(object.id);
            }
        });
        cellTable.addColumn(validateButtonColumn,"Action");


    }
    public void setResult(List<ChEBICompound> list){
        this.result=list;
        ListDataProvider<ChEBICompound> dataProvider = new ListDataProvider<ChEBICompound>();
        dataProvider.addDataDisplay(cellTable);
        dataProvider.getList().addAll(list);
        refresh();
    }
    public void refresh(){

    }

}