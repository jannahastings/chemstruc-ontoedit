package uk.ac.ebi.chebi.ontology.core.definition.chebi;

import java.io.Serializable;

public class ChEBICompound implements Serializable{
    public int id;
    public String name;
    public String textDefinition;
    public boolean hasProgrammaticDefinition;
    public String status;
    public int childrenCount=0;
    public String structure;

    public ChEBICompound(){

    }

    public ChEBICompound(int id, String name, String textDefinition, boolean hasProgrammaticDefinition, String status, int childrenCount) {
        this.id = id;
        this.name = name;
        this.textDefinition = textDefinition;
        this.hasProgrammaticDefinition = hasProgrammaticDefinition;
        this.status = status;
        this.childrenCount = childrenCount;
    }

    @Override
    public String toString() {
        return "ChEBICompound{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", textDefinition='" + textDefinition + '\'' +
                ", hasProgrammaticDefinition=" + hasProgrammaticDefinition +
                ", status='" + status + '\'' +
                ", childrenCount=" + childrenCount +
                '}';
    }
}
