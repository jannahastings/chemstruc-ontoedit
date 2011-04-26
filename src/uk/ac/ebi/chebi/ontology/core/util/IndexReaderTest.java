package uk.ac.ebi.chebi.ontology.core.util;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.ObjectBuffer;
import org.openscience.cdk.nonotify.NNMolecule;
import sun.nio.ch.DirectBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class IndexReaderTest {
    public static void main(String[] args) throws IOException {
        RandomAccessFile binaryFile = new RandomAccessFile("data/ChEBI_complete.bin", "r");
        RandomAccessFile indexFile = new RandomAccessFile("data/ChEBI_complete.bin.index", "r");

        long t = System.currentTimeMillis();
        Kryo kryo = new Kryo();
        kryo.setRegistrationOptional(true);
        ObjectBuffer buffer = new ObjectBuffer(kryo, 10000000);       //10M buffer to deal with BufferOverflowException
//        ByteBuffer buffer;
        for (int i = 0; i < 10000; i++) {
            binaryFile.seek(220590356);
            byte[] b = new byte[4288];
            binaryFile.read(b);


            NNMolecule mol = buffer.readObject(b, NNMolecule.class);
//            System.out.println(mol);
        }
        System.out.println("Time:" + (System.currentTimeMillis() - t) / 10000.0);
    }

}
