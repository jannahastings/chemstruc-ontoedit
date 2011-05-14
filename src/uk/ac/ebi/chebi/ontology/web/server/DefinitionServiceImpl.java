package uk.ac.ebi.chebi.ontology.web.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thoughtworks.xstream.XStream;
import oracle.sql.CLOB;
import uk.ac.ebi.chebi.ontology.core.chebi.ChEBIInterface;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.IDefinitionPart;
import uk.ac.ebi.chebi.ontology.core.definition.chebi.ChEBICompound;
import uk.ac.ebi.chebi.ontology.core.definition.match.MatchingResult;
import uk.ac.ebi.chebi.ontology.core.engine.MatchEngine;
import uk.ac.ebi.chebi.ontology.core.util.DatabaseUtil;
import uk.ac.ebi.chebi.ontology.core.util.XStreamUtil;
import uk.ac.ebi.chebi.ontology.web.client.DefinitionService;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefinitionServiceImpl extends RemoteServiceServlet implements DefinitionService {
    private final String rootPath = "c:/tmp/";

    public DefinitionServiceImpl() {
        super();
        File rootDir = new File(rootPath);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
    }

    public Definition fetchDefinition(String id) {
        XStream xStream = XStreamUtil.getAliasedXStream();
        File file = new File(rootPath + id + ".xml");
        if (file.exists()) {
            try {
                Definition definition = (Definition) xStream.fromXML(new FileReader(file));
                definition.rootDefinition = (IDefinitionPart) xStream.fromXML(definition.rootDefinitionString);
                return definition;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("Class with ID:" + id + " does not exists.");
        }
    }

    public Definition getDefinition(int id) {
        XStream xStream = XStreamUtil.getAliasedXStream();
//        Definition definition=
        Definition definition=null;
        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "SELECT COMPOUND_ID,DEFINITION,STATUS FROM CHEBI.ONTOLOGY_DEFINITION WHERE COMPOUND_ID=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            boolean hasResult = resultSet.next();
            if (!hasResult) {
                definition = new Definition();
                definition.id = id;
                ChEBICompound chEBICompound=getChEBICompound(id);
                if(chEBICompound!=null){
                    definition.name=chEBICompound.name;
                    definition.comment=chEBICompound.textDefinition;
                }
            } else {
                Clob clob = resultSet.getClob(2);
                definition=(Definition) xStream.fromXML(clob.getCharacterStream());
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            definition = new Definition();
            definition.id = id;
        }
        return definition;
    }

    public List<Definition> listAllDefinitions() {
//        Connection conn=null;
//        try {
//            conn = DatabaseUtil.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
        File rootDir = new File(rootPath);
        File[] files = rootDir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".xml");
            }
        });
        XStream xStream = XStreamUtil.getAliasedXStream();
        List<Definition> result = new ArrayList<Definition>();
        for (File file : files) {
            try {
                result.add((Definition) xStream.fromXML(new FileReader(file)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void saveDefinition(Definition definition) {
        XStream xStream = XStreamUtil.getAliasedXStream();

        try {
            Connection connection = DatabaseUtil.getConnection();
            String sql = "INSERT INTO CHEBI.ONTOLOGY_DEFINITION (COMPOUND_ID,DEFINITION,STATUS) VALUES(?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, definition.id);
            CLOB clob = CLOB.createTemporary(connection, true, CLOB.DURATION_SESSION);
            Writer writer = clob.getCharacterOutputStream();
            xStream.toXML(definition, writer);
            statement.setClob(2, clob);
//            statement.setCharacterStream(2,new StringReader(xStream.toXML(definition)));
            statement.setString(3, "E");
            statement.execute();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        XStream xStream= XStreamUtil.getAliasedXStream();
//        try {
//            FileWriter writer = new FileWriter(rootPath + definition.id + ".xml");
//            definition.rootDefinition= (IDefinitionPart) xStream.fromXML(definition.rootDefinitionString);
//            xStream.toXML(definition, writer);
//            writer.flush();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public List<ChEBICompound> searchChEBI(String query) {
        ChEBIInterface chEBIInterface = new ChEBIInterface();
        try {
            chEBIInterface.connectToDatabase();
            return chEBIInterface.searchByText(query);
        } catch (Exception e) {
            return new ArrayList<ChEBICompound>();
        } finally {
            chEBIInterface.disconnectFromDatabase();
        }
    }


//    @Override
    public ChEBICompound getChEBICompound(int id) {
        ChEBIInterface chEBIInterface = new ChEBIInterface();
        try {
            chEBIInterface.connectToDatabase();
            return chEBIInterface.getChEBICompound(id);
        } catch (Exception e) {
            return null;
        } finally {
            chEBIInterface.disconnectFromDatabase();
        }
    }

    @Override
    public List<ChEBICompound> getChildren(int id, int start, int end) {
        ChEBIInterface chEBIInterface = new ChEBIInterface();
        try {
            chEBIInterface.connectToDatabase();
            return chEBIInterface.getCompoundsWithStructureInClass(id, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<ChEBICompound>();
        } finally {
            chEBIInterface.disconnectFromDatabase();
        }
    }

    Map<Long,MatchEngine.ValidationThread> threadMap=new HashMap<Long, MatchEngine.ValidationThread>();
    public long validateDefinition(int id){
        Definition definition = getDefinition(id);
        if(definition.rootDefinitionString!=null){
            definition.rootDefinition= (IDefinitionPart) XStreamUtil.getAliasedXStream().fromXML(definition.rootDefinitionString);
        }
        MatchEngine.ValidationThread validationThread = MatchEngine.matchDefinitionAsync(definition);
        threadMap.put(validationThread.getId(),validationThread);
        return validationThread.getId();
    }

    public double checkValidationProgress(long threadId){
        return threadMap.get(threadId).getProgress();
    }

    public MatchingResult getValidationResult(long threadId){
       return threadMap.get(threadId).getResult();
    }

}