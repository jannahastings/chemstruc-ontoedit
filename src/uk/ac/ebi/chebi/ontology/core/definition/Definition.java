package uk.ac.ebi.chebi.ontology.core.definition;

import java.io.Serializable;

public class Definition implements Serializable {
   public String name;
   public String comment;
   public IDefinitionPart rootDefinition;
}
