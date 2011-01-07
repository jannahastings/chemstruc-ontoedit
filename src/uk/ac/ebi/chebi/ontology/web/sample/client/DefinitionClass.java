package uk.ac.ebi.chebi.ontology.web.sample.client;

import java.io.Serializable;

public class DefinitionClass implements Serializable {
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
