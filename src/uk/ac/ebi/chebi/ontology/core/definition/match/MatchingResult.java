package uk.ac.ebi.chebi.ontology.core.definition.match;

import java.io.Serializable;
import java.util.List;

public class MatchingResult implements Serializable{
    public List<Integer> hitsInClass;
    public List<Integer> hitsOutOfClass;
    public List<Integer> nonHitsInClass;

    public MatchingResult(){

    }

}
