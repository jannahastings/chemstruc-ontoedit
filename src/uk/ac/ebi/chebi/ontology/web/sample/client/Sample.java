package uk.ac.ebi.chebi.ontology.web.sample.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class Sample implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        VerticalPanel vp=new VerticalPanel();

        HorizontalPanel hpFieldClassName=new HorizontalPanel();
        final Label lblClassName = new Label("Class Name");
        final TextBox tbCalssName=new TextBox();
        hpFieldClassName.add(lblClassName);
        hpFieldClassName.add(tbCalssName);
        vp.add(hpFieldClassName);

        final Label lblResult = new Label("");
        vp.add(lblResult);
        final Button buttonSubmit=new Button("Submit");
        vp.add(buttonSubmit);

        buttonSubmit.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                DefinitionClass defClass=new DefinitionClass();
                defClass.setClassName(tbCalssName.getText());
                SampleService.App.getInstance().sendDefinition(defClass,new AsyncCallback<String>(){

                    public void onFailure(Throwable throwable) {
                        lblResult.setText("Request failed"+throwable);
                    }

                    public void onSuccess(String s) {
                       lblResult.setText("Server Response"+s);
                    }
                });
            }
        });

        // Assume that the host HTML has elements defined whose
        // IDs are "slot1", "slot2".  In a real app, you probably would not want
        // to hard-code IDs.  Instead, you could, for example, search for all
        // elements with a particular CSS class and replace them with widgets.
        //
        RootPanel.get().add(vp);
    }

//    private class MyAsyncCallback implements AsyncCallback<String> {
//        private Label label;
//
//        public MyAsyncCallback(Label label) {
//            this.label = label;
//        }
//
//        public void onSuccess(String result) {
//            label.getElement().setInnerHTML(result);
//        }
//
//        public void onFailure(Throwable throwable) {
//            label.setText("Failed to receive answer from server!");
//        }
//    }
}
