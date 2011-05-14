package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.Range;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;

import java.util.List;


public class ValidationResultList extends Composite {
    private List<ChEBICompound> chEBICompoundList;

    public ValidationResultList(List<ChEBICompound> chEBICompoundList) {
//        this.classId = classId;
        this.chEBICompoundList=chEBICompoundList;
        ChEBICompoundCell chEBICompoundCell=new ChEBICompoundCell();
        cellList=new CellList<ChEBICompound>(chEBICompoundCell);
        cellList.setPageSize(30);
        Widget rootElement = ourUiBinder.createAndBindUi(this);
        ListDataProvider<ChEBICompound> myDataProvider=new ListDataProvider<ChEBICompound>(chEBICompoundList);
        myDataProvider.addDataDisplay(cellList);
        pagerPanel.setDisplay(cellList);
        initWidget(rootElement);
    }

    interface ChildrenListUiBinder extends UiBinder<Widget, ValidationResultList> {
    }

    private static ChildrenListUiBinder ourUiBinder = GWT.create(ChildrenListUiBinder.class);
    @UiField
    ShowMorePagerPanel pagerPanel;

    private CellList<ChEBICompound> cellList;

    static class ChEBICompoundCell extends AbstractCell<ChEBICompound> {

        @Override
        public void render(Context context, ChEBICompound value, SafeHtmlBuilder sb) {
      // Value can be null, so do a null check..
        if (value == null) {
          return;
        }

        sb.appendHtmlConstant("<table>");

        sb.appendHtmlConstant("<tr><td rowspan='3'>");
        sb.appendHtmlConstant("<img src=\"http://www.ebi.ac.uk/chebi/displayImage.do?defaultImage=true&imageIndex=0&chebiId="+value.id+"\" width=150px height=150px />");

        sb.appendHtmlConstant("</td>");

        sb.appendHtmlConstant("<td style='font-size:95%;'>");
        sb.appendEscaped("ChEBI:" + value.id);
        sb.appendHtmlConstant("</td></tr><tr><td>");
        sb.appendEscaped(value.name);
        sb.appendHtmlConstant("</td></tr></table>");

        }
    }

}