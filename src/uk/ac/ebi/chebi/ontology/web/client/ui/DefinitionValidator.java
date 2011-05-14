package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.match.MatchingResult;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;

public class DefinitionValidator extends Composite {
    interface DefinitionValidatorUiBinder extends UiBinder<ScrollPanel, DefinitionValidator> {
    }

    private static DefinitionValidatorUiBinder ourUiBinder = GWT.create(DefinitionValidatorUiBinder.class);
    @UiField
    Label lbProgress;
    @UiField
    SimplePanel wrapperMatchedUnclassified;
    @UiField
    SimplePanel wrapperMatchedClassified;
    @UiField
    SimplePanel wrapperUnmatchedClassified;

    int classId;
    long threadId;
    Timer timerCheckProgress = new Timer() {
      public void run() {
        DefinitionService.App.getInstance().checkValidationProgress(threadId,new AsyncCallback<Double>() {
            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(Double result) {
                lbProgress.setText(String.valueOf(result));
                if(result==1){
                    timerCheckProgress.cancel();
                    DefinitionService.App.getInstance().getValidationResult(threadId,new AsyncCallback<MatchingResult>() {
                        @Override
                        public void onFailure(Throwable caught) {
                            caught.printStackTrace();
                        }

                        @Override
                        public void onSuccess(MatchingResult result) {
                            wrapperMatchedClassified.setWidget(new ValidationResultList(result.matchedClassified));
                            wrapperMatchedUnclassified.setWidget(new ValidationResultList(result.matchedUnclassified));
                            wrapperUnmatchedClassified.setWidget(new ValidationResultList(result.unmatchedUnclassified));
                            System.out.println(result.matchedClassified);
                            System.out.println(result.matchedUnclassified);
                            System.out.println(result.unmatchedUnclassified);
                        }
                    });
                }
            }
        });
      }
    };

    public DefinitionValidator(int classId) {
        this.classId=classId;

        Widget rootElement = ourUiBinder.createAndBindUi(this);

        initWidget(rootElement);
        beginValidation();
    }

    public void beginValidation(){
        DefinitionService.App.getInstance().validateDefinition(classId,new AsyncCallback<Long>() {
            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }

            @Override
            public void onSuccess(Long result) {
                threadId=result;
                System.out.println(result);
                timerCheckProgress.scheduleRepeating(5000);
            }
        });
    }

}