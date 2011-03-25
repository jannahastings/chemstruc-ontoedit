package uk.ac.ebi.chebi.ontology.core.definition.query;

import java.io.Serializable;

public class NumericQuery<T> implements Serializable{
    public T maximum;
    public T minimum;
}
