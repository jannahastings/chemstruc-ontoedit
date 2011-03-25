package uk.ac.ebi.chebi.ontology.core.definition.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bond implements Serializable {
    public Boolean isAromatic;
    public List<Atom> atoms=new ArrayList<Atom>();
    public Order bondOrder;
    public enum Order {
        SINGLE,
        DOUBLE,
        TRIPLE,
        QUADRUPLE
    }
}
