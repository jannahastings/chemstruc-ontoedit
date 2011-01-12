package uk.ac.ebi.chebi.ontology.core.matcher;

import org.openscience.cdk.interfaces.IAtomContainer;

import java.io.Serializable;

public interface IMatcher extends Serializable {
    public abstract boolean matches(IAtomContainer ac);
}
