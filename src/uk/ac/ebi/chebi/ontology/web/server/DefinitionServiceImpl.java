package uk.ac.ebi.chebi.ontology.web.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thoughtworks.xstream.XStream;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.util.XStreamUtil;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DefinitionServiceImpl extends RemoteServiceServlet implements DefinitionService {
    private final String rootPath="c:/tmp/";

    public DefinitionServiceImpl(){
        super();
        File rootDir=new File(rootPath);
        if(!rootDir.exists()){
            rootDir.mkdirs();
        }
    }

    public Definition fetchDefinition(String id) {
        XStream xStream= XStreamUtil.getAliasedXStream();
        File file=new File(rootPath+id+".xml");
        if(file.exists()){
            try {
                return (Definition) xStream.fromXML(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }else{
            throw new RuntimeException("Class with ID:"+id+" does not exists.");
        }
    }

    public List<Definition> listAllDefinitions() {
        File rootDir=new File(rootPath);
        File[] files=rootDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        XStream xStream= XStreamUtil.getAliasedXStream();
        List<Definition> result=new ArrayList<Definition>();
        for(File file:files){
            try {
                result.add((Definition) xStream.fromXML(new FileReader(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void saveDefinition(Definition definition) {
        XStream xStream= XStreamUtil.getAliasedXStream();
        try {
            FileWriter writer = new FileWriter(rootPath + definition.id + ".xml");
            xStream.toXML(definition, writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}