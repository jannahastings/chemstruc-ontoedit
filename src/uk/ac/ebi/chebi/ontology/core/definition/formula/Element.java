package uk.ac.ebi.chebi.ontology.core.definition.formula;

import uk.ac.ebi.chebi.ontology.core.definition.query.NumericQuery;

import java.io.Serializable;

public class Element implements Serializable {
    public String symbol;
    public NumericQuery<Integer> count;
}
