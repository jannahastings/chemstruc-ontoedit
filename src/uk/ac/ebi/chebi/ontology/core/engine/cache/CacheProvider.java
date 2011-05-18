package uk.ac.ebi.chebi.ontology.core.engine.cache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.nonotify.NNMolecule;
import org.openscience.cdk.smiles.SmilesParser;

import java.io.*;
import java.util.*;

public class CacheProvider {
    RandomAccessFile binaryFile;
    File indexFile;
    private Kryo kryo;
    private ObjectBuffer objectBuffer;
    private Map<Integer,IndexItem> indexMap;

    public CacheProvider(String filePath) throws FileNotFoundException {
        binaryFile = new RandomAccessFile(filePath, "r");
        indexFile = new File(filePath+".index");
        kryo = new ExtendedKryo();
        kryo.setRegistrationOptional(true);
//        ReferenceFieldSerializer referenceFieldSerializer=new ReferenceFieldSerializer(kryo,NNMolecule.class);
//        kryo.register(NNMolecule.class,referenceFieldSerializer);
        objectBuffer = new ObjectBuffer(kryo,10000000);
        if(indexFile!=null){
            try {
                FileInputStream fileInputStream = new FileInputStream(indexFile);
                this.indexMap = objectBuffer.readObject(fileInputStream,HashMap.class);
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public IAtomContainer get(int chebi_id){
        IndexItem indexItem=indexMap.get(chebi_id);
        try {
            binaryFile.seek(indexItem.position);
            byte[] b = new byte[indexItem.length];
            binaryFile.read(b);
            return objectBuffer.readObject(b, NNMolecule.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Collection<Integer> getChEBIIds(){
        return indexMap.keySet();
    }


    public static CacheProvider getDefaultCacheReader() throws IOException {
        Properties prop = new Properties();
        InputStream in = Object.class.getResourceAsStream("/ontology.properties");
        prop.load(in);
        in.close();
        return new CacheProvider(prop.getProperty("chebi.ontology.matching.cache"));
//        return new CacheProvider("C:\\Users\\chemhack\\IdeaProjects\\ChemOntology\\data\\ChEBI_complete.bin");
    }

    public void close(){
        try {
            binaryFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, CDKException {
        CacheProvider cacheProvider =getDefaultCacheReader();
//        System.out.println(cacheProvider.get(9254));
//        IAtomContainer query=cacheProvider.get(50658);
        IAtomContainer query=new SmilesParser(DefaultChemObjectBuilder.getInstance()).parseSmiles("CCN");
        System.out.println(query);
        long t = System.currentTimeMillis();
//
        int i=0;
        for(Integer id: cacheProvider.getChEBIIds()){
            IAtomContainer ac= cacheProvider.get(id);

            boolean isSubgraph=UniversalIsomorphismTester.isSubgraph(ac,query);
//            if(isSubgraph) System.out.println(id);
            i++;
//            if(i>5000)break;
        }
        double totalTime = System.currentTimeMillis() - t + 0.0;
        System.out.println(i+" Compounds screened. Total Time:" + totalTime+" Average Time:" + totalTime / i);

    }
}
