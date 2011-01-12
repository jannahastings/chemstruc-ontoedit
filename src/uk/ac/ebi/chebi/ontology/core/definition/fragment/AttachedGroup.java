package uk.ac.ebi.chebi.ontology.core.definition.fragment;

import uk.ac.ebi.chebi.ontology.core.definition.IDefinitionPart;
import uk.ac.ebi.chebi.ontology.core.definition.query.NumericQuery;

public class AttachedGroup extends SimpleAtomContainer implements IDefinitionPart {
    public NumericQuery<Integer> repeatCount;
}
