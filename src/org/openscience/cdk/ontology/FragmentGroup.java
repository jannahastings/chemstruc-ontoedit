package org.openscience.cdk.ontology;


import org.openscience.cdk.ChemObject;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FragmentGroup extends ChemObject implements IChemObject, Serializable,IFragmentGroup {
    IAtomContainer rootStructure;
    List<AttachedGroup> attachedGroupList=new ArrayList<AttachedGroup>();

    public void setRootStructure(IAtomContainer rootStructure) {
        this.rootStructure=rootStructure;
    }

    public IAtomContainer getRootStructure() {
        return this.rootStructure;
    }

    public List<AttachedGroup> getAttachedGroupList() {
        return attachedGroupList;
    }

    public void setAttachedGroupList(List<AttachedGroup> attachedGroupList) {
        this.attachedGroupList = attachedGroupList;
    }

//    public void addAttachedGroup(AttachedGroup attachedGroup){
//        this.attachedGroupList.add(attachedGroup);
//    }
//
//    public int getAttachedGroupCount(){
//        return this.attachedGroupList.size();
//    }
//
//    public AttachedGroup getAttachedGroup(int i){
//        return this.attachedGroupList.get(i);
//    }


}