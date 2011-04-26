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

    }
}
