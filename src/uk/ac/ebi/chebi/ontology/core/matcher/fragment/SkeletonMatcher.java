package uk.ac.ebi.chebi.ontology.core.matcher.fragment;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import uk.ac.ebi.chebi.ontology.core.convertor.CDKConverter;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;
import uk.ac.ebi.chebi.ontology.core.matcher.IMatcher;

public class SkeletonMatcher implements IMatcher{
    Skeleton skeleton;

    public Skeleton getRootStructure() {
        return skeleton;
    }

    public void setRootStructure(Skeleton skeleton) {
        this.skeleton = skeleton;
    }

    public boolean matches(IAtomContainer ac) {
        try {
            IAtomContainer skeletonAc= new CDKConverter().toCDKAtomContainer(skeleton);

        } catch (CDKException e) {
            e.printStackTrace();
        }
        return false;
    }




}
