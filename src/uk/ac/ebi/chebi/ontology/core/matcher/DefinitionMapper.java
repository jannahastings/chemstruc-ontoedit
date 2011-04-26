package uk.ac.ebi.chebi.ontology.core.matcher;

import uk.ac.ebi.chebi.ontology.core.definition.IDefinitionPart;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.ArbitraryPart;
import uk.ac.ebi.chebi.ontology.core.definition.logical.And;
import uk.ac.ebi.chebi.ontology.core.definition.logical.Or;
import uk.ac.ebi.chebi.ontology.core.matcher.fragment.ArbitraryPartMatcher;

/**
 * This is the class to map IDefinitionPart to a IMatcher
 */
public class DefinitionMapper {
    /**
     * Static method to IDefinitionPart to a IMatcher
     * @param rootPart the root IDefinitionPart
     * @return The IMatcher to match given definition
     */
    public static IMatcher map(IDefinitionPart rootPart){
        IMatcher rootMatcher=mapIndividualPart(rootPart);
        return rootMatcher;
    }

    private static IMatcher mapIndividualPart(IDefinitionPart part){
        if(part instanceof ArbitraryPart){
            return new ArbitraryPartMatcher((ArbitraryPart) part);
        }else if(part instanceof And){

        }else if(part instanceof Or){

        }
        return null;
    }

}
