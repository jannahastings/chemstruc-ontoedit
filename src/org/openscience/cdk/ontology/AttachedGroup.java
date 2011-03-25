package org.openscience.cdk.ontology;

import org.openscience.cdk.ChemObject;
import org.openscience.cdk.PseudoAtom;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AttachedGroup {

    IAtomContainer group;
    List<Attachment> attachmentList;


    public IAtomContainer getGroup() {
        return group;
    }

    public void setGroup(IAtomContainer group) {
        this.group = group;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public class Attachment{
        PseudoAtom attachmentPoint;
        String positions;
    }
}
