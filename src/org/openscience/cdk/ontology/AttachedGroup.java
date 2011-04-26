package org.openscience.cdk.ontology;

import org.openscience.cdk.ChemObject;
import org.openscience.cdk.PseudoAtom;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;
import uk.ac.ebi.chebi.ontology.core.definition.query.NumericQuery;

import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AttachedGroup {

    IAtomContainer group;
    List<Attachment> attachmentList=new ArrayList<Attachment>();
    NumericQuery<Integer> repeatCount;

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

    public NumericQuery<Integer> getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(NumericQuery<Integer> repeatCount) {
        this.repeatCount = repeatCount;
    }

    public void createNewAttachment(PseudoAtom attachmentPoint, String positions){
        Attachment attachment=new Attachment(attachmentPoint, positions);
        attachmentList.add(attachment);
    }

    public class Attachment{
        PseudoAtom attachmentPoint;
        String positions;

        public Attachment(PseudoAtom attachmentPoint, String positions) {
            this.attachmentPoint = attachmentPoint;
            this.positions = positions;
        }
    }
}
