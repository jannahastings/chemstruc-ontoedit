package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;

public class DefinitionPreview extends Composite {
    private ChEBICompoundCell chEBICompoundCell;

    interface DefinitionPreviewUiBinder extends UiBinder<HTMLPanel, DefinitionPreview> {
    }

    private static DefinitionPreviewUiBinder ourUiBinder = GWT.create(DefinitionPreviewUiBinder.class);
    @UiField
    Label lbName;
    @UiField
    Label lbId;
    @UiField
    Label lbTextDefinition;
    @UiField
    CellList<ChEBICompound> listClassifiedMatch;
    @UiField
    CellList<ChEBICompound> listUnclassifiedMatch;
    @UiField
    CellList<ChEBICompound> listClassifiedMismatch;

//    private Definition

    public DefinitionPreview() {
        chEBICompoundCell = new ChEBICompoundCell();
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        initWidget(rootElement);
    }

    @UiFactory
    CellList<ChEBICompound> makeCricketScores() {
      return new CellList<ChEBICompound>(chEBICompoundCell);
    }


    static class ChEBICompoundCell extends AbstractCell<ChEBICompound>{
        @Override
        public void render(Context context, ChEBICompound value, SafeHtmlBuilder sb) {
            if (value == null) {
              return;
            }
            sb.appendEscaped(value.name);
            sb.appendEscaped(" CHEBI:");
            sb.append(value.id);
        }
    }

}