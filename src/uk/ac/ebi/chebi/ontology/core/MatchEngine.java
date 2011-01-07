package uk.ac.ebi.chebi.ontology.core;

import org.openscience.cdk.Atom;
import org.openscience.cdk.interfaces.IAtom;
import uk.ac.ebi.chebi.ontology.core.matcher.FormulaMatcher;
import uk.ac.ebi.chebi.ontology.core.matcher.PropertyMatcher;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;

import java.io.*;

public class MatchEngine {
    public static void main(String[] args) throws FileNotFoundException {
        String[] classNames=new String[]{"hydrocarbon"};
        for(String className : classNames){
            IteratingMDLReader reader=new IteratingMDLReader(new FileReader(System.getProperty("user.dir")+("/src/uk/ac/ebi/chebi/ontology/core/testset/penicillin_negative.sdf")), DefaultChemObjectBuilder.getInstance());
            PropertyMatcher matcher=new FormulaMatcher();
            while(reader.hasNext()){
                long l1=System.nanoTime();
                IAtomContainer ac= (IAtomContainer) reader.next();
                long l2=System.nanoTime();
                System.out.println((l2-l1)+" nanoseconds in parse");
                try {
                    long l3=System.nanoTime();
                    AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(ac);
                    long l4=System.nanoTime();
                    System.out.println((l4-l3)+" nanoseconds in config atoms");
                    long l5=System.nanoTime();
                    CDKHueckelAromaticityDetector.detectAromaticity(ac);
                    long l6=System.nanoTime();
                    System.out.println((l6-l5)+" nanoseconds in detectAromaticity");
                    matcher.matches(ac);
                    try {
                        ByteArrayOutputStream bos = new ByteArrayOutputStream(3000);
                        ObjectOutputStream oos=new ObjectOutputStream(bos);
                        long l7=System.nanoTime();
                        oos.writeObject(ac);
                        long l8=System.nanoTime();
                        System.out.println((l8-l7)+" nanoseconds in IO");
                        ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
                        long l9=System.nanoTime();
                        ois.readObject();
                        long l10=System.nanoTime();
                        System.out.println((l10-l9)+" nanoseconds in IO");


                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }


                } catch (CDKException e) {
                    e.printStackTrace(); 
                }

            }
        }
    }
}
