package uk.ac.ebi.chebi.ontology.core.testset;

import junit.framework.TestCase;
import org.openscience.cdk.Atom;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.PseudoAtom;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.ontology.AttachedGroup;
import org.openscience.cdk.ontology.FragmentGroup;
import org.openscience.cdk.ontology.FragmentGroupTester;
import org.openscience.cdk.ontology.IFragmentGroup;
import org.openscience.cdk.smiles.SmilesParser;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Fragment;
import uk.ac.ebi.chebi.ontology.core.definition.query.NumericQuery;

public class MatchTest extends TestCase{
    public void testFragmentGroupMatch() throws Exception {
        IFragmentGroup fragmentGroup=new FragmentGroup();
        IAtomContainer rootStructure= parseSmiles("c1ccccc1");
        fragmentGroup.setRootStructure(rootStructure);
        AttachedGroup group1=new AttachedGroup();
/*
        IAtomContainer ac=new Molecule();
//        ac.addAtom();
        ac.addAtom(new Atom("O"));
*/

        IMolecule groupAc = parseSmiles("[*]COC");
        group1.setGroup(groupAc);
        NumericQuery<Integer> repeatCount = new NumericQuery<Integer>();
        repeatCount.minimum=2;
        repeatCount.maximum=2;
        group1.setRepeatCount(repeatCount);
        group1.createNewAttachment((PseudoAtom) groupAc.getAtom(0),"1");
        fragmentGroup.getAttachedGroupList().add(group1);

        FragmentGroupTester tester=new FragmentGroupTester();
        IMolecule ac = parseSmiles("c1ccc(COC)c(COC)c1CC");
        ac.getAtom(3).setProperty("position",1);

        tester.match(ac,fragmentGroup);

    }

    private IMolecule parseSmiles(String smiles) throws InvalidSmilesException {
        return new SmilesParser(DefaultChemObjectBuilder.getInstance()).parseSmiles(smiles);
    }
}
