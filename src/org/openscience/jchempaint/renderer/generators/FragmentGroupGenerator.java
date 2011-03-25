package org.openscience.jchempaint.renderer.generators;

import org.openscience.cdk.CDKConstants;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.ontology.AttachedGroup;
import org.openscience.cdk.ontology.IFragmentGroup;
import org.openscience.jchempaint.renderer.RendererModel;
import org.openscience.jchempaint.renderer.elements.ElementGroup;
import org.openscience.jchempaint.renderer.elements.IRenderingElement;

import java.util.List;

public class FragmentGroupGenerator implements IGenerator{

	private IFragmentGroup fragmentGroup;

	public IFragmentGroup getFragmentGroup() {
		return fragmentGroup;
	}

	public void setFragmentGroup(IFragmentGroup fragmentGroup) {
		this.fragmentGroup = fragmentGroup;
	}

	public IRenderingElement generate(IAtomContainer ac, RendererModel model) {

		if (fragmentGroup ==null || ac.getAtomCount()==0) {
			return null;
		}

		model.setShowMoleculeTitle(true);

		ElementGroup diagram = new ElementGroup();

		boolean acDetachedFromFragmentGroup=true;

		if (ac== fragmentGroup.getRootStructure()) {
			acDetachedFromFragmentGroup=false;
            ac.setProperty(CDKConstants.TITLE, "Root Structure");
		}
		else {
            for(AttachedGroup attachedGroup:fragmentGroup.getAttachedGroupList()){
                if(ac==attachedGroup.getGroup()){
                    acDetachedFromFragmentGroup=false;
                    ac.setProperty(CDKConstants.TITLE,"Attached Group "+fragmentGroup.getAttachedGroupList().indexOf(attachedGroup));
                }
            }
		}
		
		if (acDetachedFromFragmentGroup) {
			ac.setProperty(CDKConstants.TITLE, "Not in Fragment Group");
		}
		return diagram;
	}

	public List<IGeneratorParameter> getParameters() {
		// Auto-generated method stub
		return null;
	}



}
