package uk.ac.ebi.chebi.ontology.core.chebi;

import uk.ac.ebi.chebi.model.compounds.Compound;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.persistence.Searcher;
import uk.ac.ebi.chebi.persistence.or.DAOSourceFactory;
import uk.ac.ebi.chebi.persistence.or.IDAOChebi;
import uk.ac.ebi.chebi.persistence.or.IDAOSourceChebi;
import uk.ac.ebi.chebi.utilities.integratedSearch.controller.SearchController;
import uk.ac.ebi.chebi.utilities.integratedSearch.model.SearchResultList;
import uk.ac.ebi.chebi.utilities.integratedSearch.model.SearchResultTO;
import uk.ac.ebi.chebi.utilities.textSearch.lucene.ChebiIndexField;
import uk.ac.ebi.chebi.utilities.textSearch.lucene.queryParser.QueryBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class ChEBIInterface {
    IDAOChebi dao = null;
    Searcher searcher = null;

    public void connectToDatabase() throws Exception {
        if(dao!=null)return;
        IDAOSourceChebi daoSource = DAOSourceFactory.getDAOSource();
        System.out.println(daoSource.getConfig());
		dao = daoSource.getDAO();
        dao.open();
        searcher = new Searcher(dao);
//		CvRelationType.assignCvRelationTypes((List) searcher.getAllCvRelationTypes());
//		CvStatus.assignCvStatus((List) searcher.getAllCvStatus());

   }

    public Compound getCompoundForId(Integer chebiId) throws Exception {

        Collection results = searcher.searchChebiId(chebiId);
//        searcher.dao
        if (!results.isEmpty())  {
              for (Object o : results)  {
                  Compound compound = (Compound) o;
                  if (compound.getCompoundId().equals(chebiId)  ) {
                      return compound;
                  }
              }
        }

        return null;
    }

    public void disconnectFromDatabase() {
        if (dao !=null) {
            try { dao.close(); } catch (Exception e) {}finally{dao=null;}
        }
    }

    /**
     * Retrieves all the ChEBI compounds which have structures and are is-a children of the specified class.
     * @param classId
     * @return
     */

    private static final int MAX_RESULTS = 200;

     public List<SearchResultTO> getCompoundsWithStructureInClass(Integer classId) throws Exception {
        SearchController controller = new SearchController();

        //Construct query bean for Lucene search on ontology children with structures
        QueryBean bean = new QueryBean();
		bean.addIncomingOntologyQuery("CHEBI:"+classId,
                ChebiIndexField.IS_A, QueryBean.QueryOperator.AND);
        bean.setChemicalStructure(true);

        SearchResultList results = controller.search(bean, MAX_RESULTS);

        return results;
    }

    public List<ChEBICompound> getCompoundsWithStructure(Integer classId) throws Exception {
           return null;
    }

    public List<ChEBICompound> searchByText(String query) throws Exception {
        List<ChEBICompound> list=new ArrayList<ChEBICompound>();
        SearchController controller = new SearchController();
        QueryBean bean = new QueryBean();
        bean.addTextQuery(query,ChebiIndexField.ALL_FIELDS, QueryBean.QueryOperator.AND);
        SearchResultList results = controller.search(bean, MAX_RESULTS);
        for (SearchResultTO result : results) {
            System.out.println("Found "+result.getChebiId()+" ("+result.getChebiName()+").");
            Compound compound=getCompoundForId(Integer.valueOf(result.getChebiId().replace("CHEBI:","")));
//            compound.getDefaultStructure().getStructure();
            ChEBICompound chEBICompound=new ChEBICompound(compound.getCompoundId(),compound.getName(),compound.getDefinition(),false,compound.getStatus(),0);
            list.add(chEBICompound);
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        ChEBIInterface interf = new ChEBIInterface();

        interf.connectToDatabase();
//        List<ChEBICompound> results = interf.searchByText("poly*");
////        dao.findBySQL()
//        for (ChEBICompound result : results) {
//            System.out.println(result);
//        }
        interf.disconnectFromDatabase();

    }
}
