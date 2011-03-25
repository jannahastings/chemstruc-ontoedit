package org.openscience.jchempaint.applet;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.jchempaint.JChemPaintPanel;
import uk.ac.ebi.chebi.ontology.core.convertor.CDKConverter;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;
import uk.ac.ebi.chebi.ontology.web.client.applet.JCPApplet;

public class OntologyEditorApplet extends JChemPaintAbstractApplet implements JCPApplet {
    public static final String GUI_APPLET="applet";
    private JChemPaintPanel jChemPaintPanel;
    private IChemModel chemModel;

    public void init() {
        super.init();
        chemModel = DefaultChemObjectBuilder.getInstance().newInstance(IChemModel.class);
        chemModel.setMoleculeSet(chemModel.getBuilder().newInstance(IMoleculeSet.class));
        chemModel.getMoleculeSet().addAtomContainer(chemModel.getBuilder().newInstance(IMolecule.class));
        jChemPaintPanel = new JChemPaintPanel(chemModel, GUI_APPLET, debug, this, this.blacklist);
        jChemPaintPanel.setName("appletframe");
        jChemPaintPanel.setShowInsertTextField(false);
        jChemPaintPanel.setShowStatusBar(false);
        setTheJcpp(jChemPaintPanel);
        this.add(jChemPaintPanel);
    }

    public void setSkeleton(Skeleton skeleton) {
        chemModel.getMoleculeSet().removeAllAtomContainers();
        try {
            chemModel.getMoleculeSet().addAtomContainer(new CDKConverter().toCDKAtomContainer(skeleton));
        } catch (CDKException e) {
            e.printStackTrace();
        }
    }

    public Skeleton getSkeleton() {
        IAtomContainer atomContainer = chemModel.getMoleculeSet().getAtomContainer(0);
        System.out.println(atomContainer.getAtomCount());
        Skeleton skeleton= (Skeleton) new CDKConverter().fromCDKAtomContainer(atomContainer, new Skeleton());
        System.out.println(skeleton.atoms.size());
        return skeleton;
    }

    public String getTestString() {
        return "test";
    }
}
