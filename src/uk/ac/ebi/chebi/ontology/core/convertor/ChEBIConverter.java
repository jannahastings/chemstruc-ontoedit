package uk.ac.ebi.chebi.ontology.core.convertor;

import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.utilities.integratedSearch.model.SearchResultTO;

public class ChEBIConverter {
    public static ChEBICompound toChEBICompound(SearchResultTO searchResultTO){
        ChEBICompound chEBICompound=new ChEBICompound();
        chEBICompound.id=Integer.valueOf(searchResultTO.getChebiId().replace("CHEBI:", ""));
        chEBICompound.name=searchResultTO.getChebiName();
        return chEBICompound;
    }

}
