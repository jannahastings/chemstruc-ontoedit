package org.openscience.jchempaint.applet;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemModel;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.interfaces.IMoleculeSet;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.jchempaint.JChemPaintPanel;
import uk.ac.ebi.chebi.ontology.core.convertor.CDKConverter;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.ArbitraryPart;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;
import uk.ac.ebi.chebi.ontology.core.util.XStreamUtil;
import uk.ac.ebi.chebi.ontology.web.client.applet.JCPApplet;

public class OntologyEditorApplet extends JChemPaintAbstractApplet implements JCPApplet {
    public static final String GUI_APPLET = "applet";
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

//    public void setRootStructure(String skeletonStr) {
////        chemModel.getMoleculeSet().removeAllAtomContainers();
////        try {
////            chemModel.getMoleculeSet().addAtomContainer(new CDKConverter().toCDKAtomContainer(rootStructure));
////        } catch (CDKException e) {
////            e.printStackTrace();
////        }
//    }
//
    public Skeleton getSkeleton() {
        IAtomContainer atomContainer = chemModel.getMoleculeSet().getAtomContainer(0);
        System.out.println(atomContainer.getAtomCount());
        Skeleton skeleton = (Skeleton) new CDKConverter().fromCDKAtomContainer(atomContainer, new Skeleton());
//        System.out.println(skeleton.atoms.size());
        return skeleton;
    }


    public String getTestString() {
        return "test";
    }

    @Override
    public String setTestString(String test) {
        return test;
    }

    @Override
    public String getDefinitionString() {
        try{
            IAtomContainer atomContainer = chemModel.getMoleculeSet().getAtomContainer(0);
            Skeleton skeleton = (Skeleton) new CDKConverter().fromCDKAtomContainer(atomContainer, new Skeleton());
            return XStreamUtil.getAliasedXStream().toXML(skeleton);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String setDefinitionString(String definitionString) {
        try {
        Skeleton skeleton = (Skeleton) XStreamUtil.getAliasedXStream().fromXML(definitionString);
        System.out.println(skeleton);
        chemModel.getMoleculeSet().removeAllAtomContainers();
            IAtomContainer atomContainer = new CDKConverter().toCDKAtomContainer(skeleton);
            System.out.println(atomContainer);
            StructureDiagramGenerator sdg = new StructureDiagramGenerator();
            sdg.setMolecule((IMolecule) atomContainer);
            sdg.generateCoordinates();
            atomContainer=sdg.getMolecule();

            chemModel.getMoleculeSet().addAtomContainer(atomContainer);
            return "OK";
        } catch (Exception e) {
            System.out.println("ERROR"+e.getMessage());
           e.printStackTrace();
            return "ERROR"+e.getMessage();
        }
    }
}
