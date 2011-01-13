package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.uibinder.client.UiBinder;

public class OntologyList {
    interface OntologyListUiBinder extends UiBinder<DivElement, OntologyList> {
    }

    private static OntologyListUiBinder ourUiBinder = GWT.create(OntologyListUiBinder.class);

    public OntologyList() {
        DivElement rootElement = ourUiBinder.createAndBindUi(this);

    }
}