package uk.ac.ebi.chebi.ontology.core.definition.match;

import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MatchingResult implements Serializable{
    public List<ChEBICompound> matchedClassified=new ArrayList<ChEBICompound>();
    public List<ChEBICompound> matchedUnclassified=new ArrayList<ChEBICompound>();
    public List<ChEBICompound> unmatchedUnclassified=new ArrayList<ChEBICompound>();

    public MatchingResult(){

    }

}
