package uk.ac.ebi.chebi.ontology.core.engine.cache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import com.esotericsoftware.kryo.serialize.MapSerializer;
import com.esotericsoftware.kryo.serialize.ReferenceFieldSerializer;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.aromaticity.CDKHueckelAromaticityDetector;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IMolecule;
import org.openscience.cdk.io.MDLReader;
import org.openscience.cdk.io.MDLV2000Reader;
import org.openscience.cdk.io.iterator.IteratingMDLReader;
import org.openscience.cdk.nonotify.*;
import org.openscience.cdk.tools.manipulator.AtomContainerManipulator;
import uk.ac.ebi.chebi.ontology.core.util.DatabaseUtil;

import java.io.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.RandomAccess;

public class IndexGenerator {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, CDKException, IOException {
        File binaryFile=new File("data/ChEBI_complete.bin");
        File indexFile = new File("data/ChEBI_complete.bin.index");
        if(binaryFile.exists())binaryFile.delete();
        if(indexFile.exists())indexFile.delete();

        RandomAccessFile binaryRandomAccessFile = new RandomAccessFile(binaryFile, "rw");
        Kryo kryo = new ExtendedKryo();
        kryo.setRegistrationOptional(true);
        ObjectBuffer buffer = new ObjectBuffer(kryo, 10000000);       //10M buffer to deal with BufferOverflowException
        HashMap indexMap = new HashMap<Integer, IndexItem>();

        Connection conn = DatabaseUtil.getConnection();

        try {
            Statement stmt = conn.createStatement();
            try {
                ResultSet rset = stmt.executeQuery("SELECT vpc.PARENT_ID,\n" +
                        "        CHEBI.STRUCTURES.STRUCTURE \n" +
                        "  FROM CHEBI.VW_PARENTS_AND_CHILDREN vpc,\n" +
                        "          CHEBI.STRUCTURES,\n" +
                        "          CHEBI.DEFAULT_STRUCTURES\n" +
                        "  WHERE vpc.CHILD_ID=CHEBI.STRUCTURES.COMPOUND_ID \n" +
                        "          AND CHEBI.DEFAULT_STRUCTURES.STRUCTURE_ID = CHEBI.STRUCTURES.ID\n" +
                        "          AND CHEBI.STRUCTURES.TYPE='mol'");
                try {
                    while (rset.next()) {
                        int id = rset.getInt(1);
//                        System.out.println("ID:" + id);
                        NNMolecule molecule = null;
                        try {
                            MDLV2000Reader reader = new MDLV2000Reader(new StringReader(rset.getString(2)));
                            molecule = new NNMolecule();
                            molecule = reader.read(molecule);
                            AtomContainerManipulator.percieveAtomTypesAndConfigureAtoms(molecule);
                            CDKHueckelAromaticityDetector.detectAromaticity(molecule);
                            byte[] data = buffer.writeObject(molecule);
                            long pos = binaryRandomAccessFile.getFilePointer();
                            binaryRandomAccessFile.write(data);
                            indexMap.put(id, new IndexItem(pos, data.length));
//                            System.out.println("ID:" + id + ",POS:" + pos+",LEN:"+(data.length));
                        } catch (Exception e) {
                            System.out.println(id);
//                            e.printStackTrace();
                        }
                    }
                    binaryRandomAccessFile.close();
                    FileOutputStream fileOutputStream = new FileOutputStream(indexFile);
                    buffer.writeObject(fileOutputStream,indexMap);
                    fileOutputStream.close();
                } finally {
                    try {
                        rset.close();
                    } catch (Exception ignore) {
                    }
                }
            } finally {
                try {
                    stmt.close();
                } catch (Exception ignore) {
                }
            }
        } finally {
            try {
                conn.close();
            } catch (Exception ignore) {
            }
        }

    }
}
