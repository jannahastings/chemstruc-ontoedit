package uk.ac.ebi.chebi.ontology.core.engine;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import uk.ac.ebi.chebi.ontology.core.chebi.ChEBIInterface;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.ArbitraryPart;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Atom;
import uk.ac.ebi.chebi.ontology.core.definition.match.MatchingResult;
import uk.ac.ebi.chebi.ontology.core.engine.cache.CacheProvider;
import uk.ac.ebi.chebi.ontology.core.matcher.DefinitionMapper;
import uk.ac.ebi.chebi.ontology.core.matcher.IMatcher;
import uk.ac.ebi.chebi.utilities.integratedSearch.model.SearchResultTO;

import java.util.ArrayList;
import java.util.List;

public class MatchEngine {
    public MatchingResult matchDefinition(Definition definition) throws Exception {
        MatchingResult result=new MatchingResult();


        IMatcher rootMatcher= DefinitionMapper.map(definition.rootDefinition);

        CacheProvider cacheProvider=CacheProvider.getDefaultCacheReader();
        List<Integer> matchedIds=new ArrayList<Integer>();
        System.out.println("---Matching Start-----");
        long t = System.currentTimeMillis();
        int i=0;
        for(Integer id: cacheProvider.getChEBIIds()){
            IAtomContainer ac= cacheProvider.get(id);
            if(rootMatcher.matches(ac)){
                matchedIds.add(id);
            }
            i++;
        }
        cacheProvider.close();
        System.out.println("---Matching End-----");
        double totalTime = System.currentTimeMillis() - t + 0.0;
        System.out.println(i + " compounds screened." + matchedIds.size() + " hits. Total Time(ms):" + totalTime + " Average Time(ms):" + totalTime / i);

        ChEBIInterface chEBIInterface=new ChEBIInterface();
        chEBIInterface.connectToDatabase();
        List<SearchResultTO> searchResultTOList= chEBIInterface.getCompoundsWithStructureInClass(definition.id);

        List<Integer> matchedIdsClassified=new ArrayList<Integer>();
        List<Integer> unmatchedIdsClassified=new ArrayList<Integer>();
        List<Integer> matchedIdsUnclassified=new ArrayList<Integer>();
        for (SearchResultTO searchResultTO : searchResultTOList) {
            int id = Integer.parseInt(searchResultTO.getChebiId().replace("CHEBI:", ""));
            if(matchedIds.contains(id)){
                matchedIdsClassified.add(id);
            }else{
                unmatchedIdsClassified.add(id);
            }
        }

        for(Integer id:matchedIds){
            if(!matchedIdsClassified.contains(id)){
                matchedIdsUnclassified.add(id);
            }
        }

        System.out.println("Matched Compounds Classified:"+matchedIdsClassified.size());

        System.out.println("Matched Compounds Unclassified:"+matchedIdsUnclassified.size());
        for (Integer id : matchedIdsUnclassified) {
            System.out.println(id+" "+chEBIInterface.getCompoundForId(id));
        }

        System.out.println("Unmatched Compounds Classified:"+unmatchedIdsClassified.size());
        for (Integer id : unmatchedIdsClassified) {
            System.out.println(id+" "+chEBIInterface.getCompoundForId(id));
        }

        chEBIInterface.disconnectFromDatabase();



        return result;
    }

    public static void main(String[] args) throws Exception {
        MatchEngine engine=new MatchEngine();
        Definition definition=new Definition();
        definition.id=22985;
        ArbitraryPart arbitraryPart = new ArbitraryPart();
        Atom atom = new Atom();
        atom.symbol="Ca";
        arbitraryPart.atoms.add(atom);
        definition.rootDefinition= arbitraryPart;

        engine.matchDefinition(definition);
    }

}
