package uk.ac.ebi.chebi.ontology.core.definition.fragment;

import uk.ac.ebi.chebi.ontology.core.definition.IDefinitionPart;

import java.util.List;
import java.util.Map;

public class Fragment implements IDefinitionPart {
    public Skeleton skeleton;
    public List<AttachedGroup> attachedGroups;
//    public Map<AttachedGroup,> attachedGroupMap;
    public ArbitraryPart arbitraryPart;

}
