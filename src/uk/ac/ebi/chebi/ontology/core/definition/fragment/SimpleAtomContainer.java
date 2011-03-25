package uk.ac.ebi.chebi.ontology.core.definition.fragment;

import uk.ac.ebi.chebi.ontology.core.definition.fragment.Atom;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Bond;

import java.util.ArrayList;
import java.util.List;

public class SimpleAtomContainer {
    public List<Atom> atoms=new ArrayList<Atom>();
    public List<Bond> bonds=new ArrayList<Bond>();
    public Boolean isAromatic;
    public Boolean isBranch;
    public Boolean isLinear;
    public Boolean isCyclic;
    public Double totalCharge;
}
