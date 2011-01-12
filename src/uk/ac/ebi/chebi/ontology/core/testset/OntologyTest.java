package uk.ac.ebi.chebi.ontology.core.testset;

import uk.ac.ebi.chebi.ontology.core.Definition;
import uk.ac.ebi.chebi.ontology.core.parser.XMLParser;
import uk.ac.ebi.chebi.ontology.core.matcher.IMatcher;
import junit.framework.TestCase;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/*
* Abstract class for testing ontology classification.
*/
public abstract class OntologyTest extends TestCase {
    protected String fileName=null;
    protected Definition definition=null;
    protected void testMatch(boolean isMatch){
        initDefinition();
        IteratingMDLReader reader= null;
        try {
            reader = new IteratingMDLReader(new FileReader(System.getProperty("user.dir")+("/src/uk/ac/ebi/chebi/ontology/core/testset/"+fileName+(isMatch?"_positive":"_negative")+".sdf")), DefaultChemObjectBuilder.getInstance());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        IMatcher matcher=this.definition.getMatcher();
        while(reader.hasNext()){
            IAtomContainer ac= (IAtomContainer) reader.next();
            try {
                AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
                CDKHueckelAromaticityDetector.detectAromaticity(ac);
                if(matcher!=null){
                    assertEquals(isMatch,matcher.matches(ac));                    
                }
            } catch (CDKException e) {
                e.printStackTrace();
            }
        }                    
    }

    protected void initDefinition(){
        if(definition==null){
            XMLParser parser=new XMLParser();
            List<Definition> definitions = null;
            try {
                definitions=parser.parse(System.getProperty("user.dir")+("/src/uk/ac/ebi/chebi/ontology/core/testset/"+fileName+".xml"));
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            if(definitions.size()==0){
                throw new RuntimeException("No definition found in "+fileName+".xml");    
            }else if(definitions.size()>1){
                throw new RuntimeException("Multiple definition found in "+fileName+".xml\nOnly one allowed in test set");    
            }
            this.definition=definitions.get(0);
        }
    }

    public void testPositive(){
        testMatch(true);
    }
    public void testNegative(){
        testMatch(false);
    }
    
}
