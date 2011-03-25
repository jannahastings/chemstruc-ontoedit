package org.openscience.jchempaint.ontology;

import org.openscience.cdk.ontology.IFragmentGroup;

public class FragmentGroupHandler {
    IFragmentGroup fragmentGroup;

    public FragmentGroupHandler(IFragmentGroup fragmentGroup) {
        this.fragmentGroup = fragmentGroup;
    }

    public IFragmentGroup getFragmentGroup() {
        return fragmentGroup;
    }


}
