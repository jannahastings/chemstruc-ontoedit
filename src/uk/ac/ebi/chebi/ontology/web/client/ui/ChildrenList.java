package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;

import java.util.ArrayList;
import java.util.List;


public class ChildrenList extends Composite {
    private int classId;

    public ChildrenList(int classId) {
        this.classId = classId;
        ChEBICompoundCell chEBICompoundCell=new ChEBICompoundCell();
        cellList=new CellList<ChEBICompound>(chEBICompoundCell);
        cellList.setPageSize(30);
        Widget rootElement = ourUiBinder.createAndBindUi(this);
        MyDataProvider myDataProvider=new MyDataProvider(classId);
        myDataProvider.addDataDisplay(cellList);
        pagerPanel.setDisplay(cellList);
        initWidget(rootElement);
    }

    interface ChildrenListUiBinder extends UiBinder<Widget, ChildrenList> {
    }

    private static ChildrenListUiBinder ourUiBinder = GWT.create(ChildrenListUiBinder.class);
    @UiField
    ShowMorePagerPanel pagerPanel;

    private CellList<ChEBICompound> cellList;

    /**
     * A custom {@link AsyncDataProvider}.
     */
    private static class MyDataProvider extends AsyncDataProvider<ChEBICompound> {
        private MyDataProvider(int classId) {
            this.classId = classId;
        }

        int classId;
      @Override
      protected void onRangeChanged(HasData<ChEBICompound> display) {
        // Get the new range.
        final Range range = display.getVisibleRange();
          DefinitionService.App.getInstance().getChildren(classId,range.getStart(),range.getLength()+range.getStart(),new AsyncCallback<List<ChEBICompound>>() {
              @Override
              public void onFailure(Throwable caught) {
                  Window.alert("Load Failure:"+caught.getMessage());
              }

              @Override
              public void onSuccess(List<ChEBICompound> result) {
//                List<ChEBICompound> newData = new ArrayList<ChEBICompound>();
                updateRowData(range.getStart(),result);
              }
          });

//        /*
//         * Query the data asynchronously. If you are using a database, you can
//         * make an RPC call here. We'll use a Timer to simulate a delay.
//         */
//        new Timer() {
//          @Override
//          public void run() {
//            // We are creating fake data. Normally, the data will come from a
//            // server.
//            int start = range.getStart();
//            int length = range.getLength();
//            List<String> newData = new ArrayList<String>();
//            for (int i = start; i < start + length; i++) {
//              newData.add("Item " + i);
//            }
//
//            // Push the data to the displays. AsyncDataProvider will only update
//            // displays that are within range of the data.
//            updateRowData(start, newData);
//          }
//        }.schedule(3000);
      }
    }

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