package uk.ac.ebi.chebi.ontology.web.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtai.applet.client.AppletJSUtil;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import uk.ac.ebi.chebi.ontology.web.client.applet.JCPApplet;

public class JCPWidget extends Composite {
    private JCPApplet applet;
    public JCPWidget(){
        applet= GWT.create(JCPApplet.class);
        Widget widgetApplet = AppletJSUtil.createAppletWidget(applet);
//        HTML widgetApplet=new HTML();
////        <applet mayscript="true" code="com.google.gwt.gwtai.demo.impl.CounterAppletImpl.class" width="350" height="60" name="CounterAppletImpl" id="CounterAppletImpl" alt="Java Runtime Environment is not working on your system" archive="GwtAI-Core.jar,GwtAI-Demo.jar"><param name="image" value="loadingc.gif"></applet>
//        widgetApplet.setHTML("<applet mayscript=\"true\" code=\"com.google.gwt.gwtai.applet.proxy.AppletProxy\" width=\"350\" height=\"350\" name=\"JCPApplet_Proxy1\" id=\"JCPApplet_Proxy1\" alt=\"Java Runtime Environment is not working on your system\" archive=\"gwtai-client.jar,gwtai-core.jar,cdk-1.3.7.jar,chemontology.jar\"><param name=\"classname\" value=\"org.openscience.jchempaint.applet.OntologyEditorApplet\"><param name=\"applet_name\" value=\"JCPApplet_Proxy1\"><param name=\"codebase_lookup\" value=\"true\"></applet>");
        initWidget(widgetApplet);
    }

    public JCPApplet getApplet() {
        return applet;
    }
}
