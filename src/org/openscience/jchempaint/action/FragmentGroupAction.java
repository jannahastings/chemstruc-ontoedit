package org.openscience.jchempaint.action;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IChemObject;
import org.openscience.cdk.isomorphism.matchers.IRGroupQuery;
import org.openscience.cdk.isomorphism.matchers.RGroup;
import org.openscience.cdk.isomorphism.matchers.RGroupList;
import org.openscience.cdk.ontology.AttachedGroup;
import org.openscience.cdk.ontology.FragmentGroup;
import org.openscience.cdk.ontology.IFragmentGroup;
import org.openscience.cdk.tools.manipulator.ChemModelManipulator;
import org.openscience.jchempaint.GT;
import org.openscience.jchempaint.controller.IChemModelRelay;
import org.openscience.jchempaint.ontology.FragmentGroupHandler;
import org.openscience.jchempaint.renderer.selection.IChemObjectSelection;
import org.openscience.jchempaint.rgroups.RGroupHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentGroupAction extends JCPAction {
    /**
     * Handles the user action, such as defining a root structure, substitutes,
     * attachment atoms and bonds.
     *
     * @see JCPAction#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent event) {
        System.out.println("action iz " + type);
        IChemObject eventSource = getSource(event);

        IChemObjectSelection selection = jcpPanel.getRenderPanel().getRenderer().getRenderer2DModel().getSelection();
        if (selection == null || !selection.isFilled()
                && (type.equals("setAttachedGroup") || type.equals("setFragmentRoot"))) {
            JOptionPane.showMessageDialog(jcpPanel, GT._("You have not selected any atoms or bonds."));
            return;
        }

        IChemModelRelay hub = jcpPanel.get2DHub();
        Molecule molecule=null;
        FragmentGroupHandler fragmentGroupHandler=hub.getFragmentGroupHandler();

        if (type.equals("setFragmentRoot")) {
            IAtomContainer atc = selection.getConnectedAtomContainer();
            if (!isProperSelection(atc)) {
                JOptionPane.showMessageDialog(jcpPanel, GT._("Please do not make a partial selection."));
                return;
            }

            if (fragmentGroupHandler == null) {
                fragmentGroupHandler = new FragmentGroupHandler(new FragmentGroup());
                hub.setFragmentGroupHandler(fragmentGroupHandler);
            }else{
                if(fragmentGroupHandler.getFragmentGroup().getRootStructure()!=null){
                    JOptionPane.showMessageDialog(jcpPanel, GT._("You can only define one root structure"));
                    return;
                }
            }

            molecule = createMolecule(atc);
            hub.getChemModel().getMoleculeSet().addAtomContainer(molecule);
            fragmentGroupHandler.getFragmentGroup().setRootStructure(molecule);
//            molecule.setProperty(CDKConstants.TITLE,"Root");

            hub.updateView();

        }else if(type.equals("setAttachedGroup")){
            IAtomContainer atc = selection.getConnectedAtomContainer();
            if (!isProperSelection(atc)) {
                JOptionPane.showMessageDialog(jcpPanel, GT._("Please do not make a partial selection."));
                return;
            }
            if(fragmentGroupHandler==null){
                JOptionPane.showMessageDialog(jcpPanel, GT._("Please define a root structure first"));
                return;
            }
            molecule = createMolecule(atc);
            hub.getChemModel().getMoleculeSet().addAtomContainer(molecule);
            AttachedGroup attachedGroup=new AttachedGroup();
            attachedGroup.setGroup(molecule);
            fragmentGroupHandler.getFragmentGroup().getAttachedGroupList().add(attachedGroup);

//            fragmentGroupHandler.getFragmentGroup().(molecule);
//            molecule.setProperty(CDKConstants.TITLE, "Attached Group");

            hub.updateView();

        }
    }

    /**
     * Check if the attached group is properly annotated(pseudo atom with star marker)
     * @param attachedGroup The attached group to check.
     * @return
     */
    private boolean isProperAnnotated(AttachedGroup attachedGroup){
        for(IAtom atom:attachedGroup.getGroup().atoms()){

        }
        return false;
    }

    /**
     * Creates a new molecule based on a user selection, and removes the
     * selected atoms/bonds from the atom container where they are currently in.
     */
    private Molecule createMolecule(IAtomContainer atc) {
        for (IAtom atom : atc.atoms()) {
            IAtomContainer original = ChemModelManipulator
                    .getRelevantAtomContainer(jcpPanel.getChemModel(), atom);
            original.removeAtom(atom);
        }
        for (IBond bond : atc.bonds()) {
            IAtomContainer original = ChemModelManipulator
                    .getRelevantAtomContainer(jcpPanel.getChemModel(), bond);
            original.removeBond(bond);
        }
        Molecule molecule = new Molecule();
        molecule.add(atc);
        return molecule;
    }

    /**
     * Determines if a user has made a proper selection for R-Group
     * manipulation. Proper means: make a selection that includes all
     * atoms/bonds that are bound together in a structure, not leaving any
     * orphans dangling.
     *
     * @param atc
     * @return
     */
    private boolean isProperSelection(IAtomContainer atc) {
        boolean properSelection = true;
        completeSelection:
        for (IAtom atom : atc.atoms()) {

            IAtomContainer modelAtc = ChemModelManipulator
                    .getRelevantAtomContainer(jcpPanel.getChemModel(), atom);
            List<IAtom> connectedAtoms = new ArrayList<IAtom>();
            findConnectedAtoms(atom, modelAtc, connectedAtoms);
            for (IAtom modelAt : connectedAtoms) {
                if (!atc.contains(modelAt)) {
                    properSelection = false;
                    break completeSelection;
                }
            }
        }
        return properSelection;
    }

    /**
     * Starting from start point atom, finds all other atoms connected to it by
     * traversing a graph. Used to determine a proper selection.
     *
     * @param atom
     * @param atc
     * @param result
     */
    private void findConnectedAtoms(IAtom atom, IAtomContainer atc,
                                    List<IAtom> result) {
        result.add(atom);
        for (IBond bond : atc.bonds()) {
            if (bond.contains(atom)) {
                if (!result.contains(bond.getConnectedAtom(atom))) {
                    findConnectedAtoms(bond.getConnectedAtom(atom), atc, result);
                }
            }
        }
    }


}
