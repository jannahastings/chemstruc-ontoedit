package uk.ac.ebi.chebi.ontology.test;

import com.thoughtworks.xstream.XStream;
import junit.framework.TestCase;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import uk.ac.ebi.chebi.ontology.core.convertor.CDKConverter;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;
import uk.ac.ebi.chebi.ontology.core.util.XStreamUtil;

public class DefinitionTest extends TestCase{
    public void testWriteDefinition() throws InvalidSmilesException {
        Definition definition=new Definition();
        definition.name="pyrimidines";
        definition.comment="Pyrimidine is a heterocyclic aromatic organic compound similar to benzene and pyridine, containing two nitrogen atoms at positions 1 and 3 of the six-member ring.";
        SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer ac=parser.parseSmiles("c1cncnc1");
        Skeleton skeleton=new Skeleton();
        skeleton = (Skeleton) new CDKConverter().fromCDKAtomContainer(ac,skeleton);
        definition.rootDefinition= skeleton;

        System.out.println(XStreamUtil.getAliasedXStream().toXML(definition));
    }

}
