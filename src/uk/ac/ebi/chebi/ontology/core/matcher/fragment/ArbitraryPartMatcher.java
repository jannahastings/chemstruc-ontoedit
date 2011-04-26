package uk.ac.ebi.chebi.ontology.core.matcher.fragment;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import uk.ac.ebi.chebi.ontology.core.convertor.CDKConverter;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.ArbitraryPart;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;
import uk.ac.ebi.chebi.ontology.core.matcher.IMatcher;

public class ArbitraryPartMatcher implements IMatcher{
    ArbitraryPart arbitraryPart;

    public ArbitraryPartMatcher(ArbitraryPart arbitraryPart) {
        this.arbitraryPart = arbitraryPart;
    }

    public ArbitraryPart getArbitraryPart() {
        return arbitraryPart;
    }

    public void setArbitraryPart(ArbitraryPart arbitraryPart) {
        this.arbitraryPart = arbitraryPart;
    }

    public boolean matches(IAtomContainer ac) {
        try {
            IAtomContainer arbitraryPartAc= new CDKConverter().toCDKAtomContainer(arbitraryPart);
            return UniversalIsomorphismTester.isSubgraph(ac,arbitraryPartAc);
        } catch (CDKException e) {
            e.printStackTrace();
        }
        return false;
    }




}
