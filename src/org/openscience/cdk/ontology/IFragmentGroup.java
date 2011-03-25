package org.openscience.cdk.ontology;

import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IChemObject;

import java.util.List;

/**
 * A fragment group is a basic unit of editing. One fragment group is attached to one applet.
 */
public interface IFragmentGroup extends IChemObject {

    /**
     * Setter for the root structure of this fragment group.
     * @see #getRootStructure
     * @param rootStructure the root structure (or scaffold) container
     *
     */
    public void setRootStructure(IAtomContainer rootStructure);

    /**
     * Getter for the root structure of this fragment group.
     * @see #setRootStructure
     * @return the root structure (or scaffold) container
     */
    public IAtomContainer getRootStructure();


    public List<AttachedGroup> getAttachedGroupList();

    public void setAttachedGroupList(List<AttachedGroup> attachedGroupList);
}
