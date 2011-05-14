package uk.ac.ebi.chebi.ontology.core.matcher.fragment;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import uk.ac.ebi.chebi.ontology.core.convertor.CDKConverter;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;
import uk.ac.ebi.chebi.ontology.core.matcher.IMatcher;

public class SkeletonMatcher implements IMatcher{
    Skeleton skeleton;

    public SkeletonMatcher(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public boolean matches(IAtomContainer ac) {
        try {
            IAtomContainer skeletonAc= new CDKConverter().toCDKAtomContainer(skeleton);
            return UniversalIsomorphismTester.isSubgraph(ac, skeletonAc);
        } catch (CDKException e) {
            e.printStackTrace();
        }
        return false;
    }




}
