package uk.ac.ebi.chebi.ontology.test;

import com.thoughtworks.xstream.XStream;
import junit.framework.TestCase;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;
import uk.ac.ebi.chebi.ontology.core.convertor.CDKConverter;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.SimpleAtomContainer;
import uk.ac.ebi.chebi.ontology.core.util.XStreamUtil;

public class CDKConverterTest extends TestCase {

    public void testFromCDKAtomContainer() throws InvalidSmilesException {
            SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
            IAtomContainer ac=parser.parseSmiles("CCCc1ccccc1");
            CDKConverter converter=new CDKConverter();
            SimpleAtomContainer simpleAtomContainer=converter.fromCDKAtomContainer(ac);
            XStream xStream= XStreamUtil.getAliasedXStream();
            System.out.println(xStream.toXML(simpleAtomContainer));
    }

    public void testToCDKAtomContainer() throws InvalidSmilesException {
        SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        IAtomContainer ac=parser.parseSmiles("CCCc1ccccc1");
        CDKConverter converter=new CDKConverter();
        SimpleAtomContainer simpleAtomContainer=converter.fromCDKAtomContainer(ac);
        XStream xStream= XStreamUtil.getAliasedXStream();
        String xml = xStream.toXML(simpleAtomContainer);
        SimpleAtomContainer simpleAtomContaine2r= (SimpleAtomContainer) xStream.fromXML(xml);
        System.out.println(xStream.toXML(simpleAtomContaine2r));
    }

}
