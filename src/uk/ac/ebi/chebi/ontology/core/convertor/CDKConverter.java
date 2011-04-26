package uk.ac.ebi.chebi.ontology.core.convertor;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Atom;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Bond;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.SimpleAtomContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to convert CDK objects and plain data objects.
 * @see uk.ac.ebi.chebi.ontology.core.definition
 */
public class CDKConverter {
    public SimpleAtomContainer fromCDKAtomContainer(IAtomContainer ac){
        return fromCDKAtomContainer(ac,new SimpleAtomContainer());
    }
    public SimpleAtomContainer fromCDKAtomContainer(IAtomContainer ac,SimpleAtomContainer simpleAtomContainer){
        Map<IAtom,Atom> atomMap=new HashMap<IAtom,Atom>();
        for(int i=0;i<ac.getAtomCount();i++){
            IAtom cdkAtom=ac.getAtom(i);
            Atom definitionAtom=new Atom();
            definitionAtom.symbol=cdkAtom.getSymbol();
            definitionAtom.charge=cdkAtom.getCharge();
            definitionAtom.isAromatic=cdkAtom.getFlag(CDKConstants.ISAROMATIC);
            simpleAtomContainer.atoms.add(definitionAtom);
            atomMap.put(cdkAtom,definitionAtom);
        }
        for(int i=0;i<ac.getBondCount();i++){
            IBond cdkBond=ac.getBond(i);
            Bond definitionBond=new Bond();
            definitionBond.bondOrder=fromCDKBondOrder(cdkBond.getOrder());
            definitionBond.atoms.add(atomMap.get(cdkBond.getAtom(0)));
            definitionBond.atoms.add(atomMap.get(cdkBond.getAtom(1)));
            simpleAtomContainer.bonds.add(definitionBond);
        }
        return simpleAtomContainer;
    }
    public IAtomContainer toCDKAtomContainer(SimpleAtomContainer simpleAtomContainer) throws CDKException {
        return toCDKAtomContainer(simpleAtomContainer,new AtomContainer());
    }
    public IAtomContainer toCDKAtomContainer(SimpleAtomContainer simpleAtomContainer,IAtomContainer ac) throws CDKException {
        Map<Atom,IAtom> atomMap=new HashMap<Atom,IAtom>();

        for(Atom atom:simpleAtomContainer.atoms){
            IAtom cdkAtom=new org.openscience.cdk.Atom();
            if(atom.symbol!=null)cdkAtom.setSymbol(atom.symbol);
            if(atom.charge!=null)cdkAtom.setCharge(atom.charge);
            if(atom.isAromatic!=null)cdkAtom.setFlag(CDKConstants.ISAROMATIC, atom.isAromatic);
            ac.addAtom(cdkAtom);
            atomMap.put(atom,cdkAtom);
        }

        for(Bond bond:simpleAtomContainer.bonds){
            IBond cdkBond=new org.openscience.cdk.Bond();
            cdkBond.setAtom(atomMap.get(bond.atoms.get(0)),0);
            cdkBond.setAtom(atomMap.get(bond.atoms.get(1)), 1);
            cdkBond.setFlag(CDKConstants.ISAROMATIC,bond.isAromatic);
        }
        AtomContainerManipulator.percieveAtomTypesAndConfigureUnsetProperties(ac);
        CDKHueckelAromaticityDetector.detectAromaticity(ac);
        return ac;
    }
    private Bond.Order fromCDKBondOrder(IBond.Order cdkBondOrder){
        if(cdkBondOrder==null)return null;
        switch (cdkBondOrder){
            case SINGLE:
                return Bond.Order.SINGLE;
            case DOUBLE:
                return Bond.Order.DOUBLE;
            case TRIPLE:
                return Bond.Order.TRIPLE;
            case QUADRUPLE:
                return Bond.Order.QUADRUPLE;
            default:
                return null;
        }
    }
    private IBond.Order toCDKBondOrder(Bond.Order bonOrder){
        if(bonOrder==null)return null;
        switch (bonOrder){
            case SINGLE:
                return IBond.Order.SINGLE;
            case DOUBLE:
                return IBond.Order.DOUBLE;
            case TRIPLE:
                return IBond.Order.TRIPLE;
            case QUADRUPLE:
                return IBond.Order.QUADRUPLE;
            default:
                return null;
        }
    }

}
