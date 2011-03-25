package uk.ac.ebi.chebi.ontology.core.definition.fragment;

import uk.ac.ebi.chebi.ontology.core.definition.IDefinitionPart;
import uk.ac.ebi.chebi.ontology.core.definition.query.NumericQuery;

import java.io.Serializable;
import java.util.List;

public class AttachedGroup extends SimpleAtomContainer implements IDefinitionPart {
    public NumericQuery<Integer> repeatCount;
    public List<AttachPoint> attachPoints;

    public static class AttachPoint implements Serializable {
        public Atom attachAtom;
        public Bond attachBond;
    }
}
