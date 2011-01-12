package uk.ac.ebi.chebi.ontology.core.definition.fragment;

import uk.ac.ebi.chebi.ontology.core.definition.IDefinitionPart;

import java.util.List;

public class Fragment implements IDefinitionPart {
    public Skeleton skeleton;
    public List<AttachedGroup> attachedGroups;
    public ArbitraryPart arbitraryPart;

}
