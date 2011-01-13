package uk.ac.ebi.chebi.ontology.core.util;

//import uk.ac.ebi.chebi.ontology.core.buffer.BufferedObjects;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.smiles.SmilesParser;
//import uk.ac.ebi.chebi.ontology.core.buffer.BufferedObjects;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.SimpleAtomContainer;

import java.util.ArrayList;
import java.util.List;

public class Convertor {
//      public static SimpleAtomContainer fromCDKAtomContainer(IAtomContainer ac){
//        for(int i=0;i<ac.getAtomCount();i++){
//            IAtom cdkAtom=ac.getAtom(i);
//
//        }
//      }
//    public static BufferedObjects.AtomContainerBuffer fromCDKAtomContainer(IAtomContainer ac){
//        BufferedObjects.AtomContainerBuffer.Builder acBufferBuilder=BufferedObjects.AtomContainerBuffer.newBuilder();
//        List<BufferedObjects.AtomBuffer> atomBufferList=new ArrayList<BufferedObjects.AtomBuffer>();
//        for(int i=0;i<ac.getAtomCount();i++){
//            IAtom cdkAtom=ac.getAtom(i);
//            BufferedObjects.AtomBuffer.Builder atomBufferBuilder=BufferedObjects.AtomBuffer.newBuilder();
//            atomBufferBuilder.setSymbol(cdkAtom.getSymbol());
//            BufferedObjects.AtomBuffer atomBuffer=atomBufferBuilder.build();
//            atomBufferList.add(atomBuffer);
//            acBufferBuilder.addAtom(atomBuffer);
//        }
//        for(int i=0;i<ac.getBondCount();i++){
//            IBond cdkBond=ac.getBond(i);
//            BufferedObjects.BondBuffer.Builder bondBufferBuilder=BufferedObjects.BondBuffer.newBuilder();
//            bondBufferBuilder.addAtom(atomBufferList.get(ac.getAtomNumber(cdkBond.getAtom(0))));
//            bondBufferBuilder.addAtom(atomBufferList.get(ac.getAtomNumber(cdkBond.getAtom(1))));
//            acBufferBuilder.addBond(bondBufferBuilder.build());
//        }
//        acBufferBuilder.setAtomContainerType(BufferedObjects.AtomContainerBuffer.AtomContainerType.skeleton);
//
//        return acBufferBuilder.build();
//
//    }

    public static void main(String[] args) {
//        try {
//            SmilesParser parser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
//            IAtomContainer ac=parser.parseSmiles("CCCc1ccccc1");
//            SimpleAtomContainer acBuffer=Convertor.fromCDKAtomContainer(ac);
////           SimpleAtomContainer acBuffer2= BufferedObjects.AtomContainerBuffer.parseFrom(acBuffer.toByteArray());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
}
