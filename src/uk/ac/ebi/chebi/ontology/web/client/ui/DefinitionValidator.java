package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.HTMLPanel;

/**
 * Created by IntelliJ IDEA.
 * User: chemhack
 * Date: 11-5-14
 * Time: 下午3:26
 * To change this template use File | Settings | File Templates.
 */
public class DefinitionValidator {
    interface DefinitionValidatorUiBinder extends UiBinder<HTMLPanel, DefinitionValidator> {
    }

    private static DefinitionValidatorUiBinder ourUiBinder = GWT.create(DefinitionValidatorUiBinder.class);

    public DefinitionValidator() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);

    }
}