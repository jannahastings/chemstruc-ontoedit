package org.openscience.cdk.ontology;

import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.isomorphism.UniversalIsomorphismTester;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainer;
import org.openscience.cdk.isomorphism.matchers.QueryAtomContainerCreator;
import org.openscience.cdk.isomorphism.mcss.RMap;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FragmentGroupTester {
    public boolean match(IAtomContainer ac, IFragmentGroup fragmentGroup) {
        IAtomContainer rootStructure = fragmentGroup.getRootStructure();
        try {
            List<List<RMap>> maps = UniversalIsomorphismTester.getSubgraphAtomsMaps(ac, rootStructure);
            System.out.println("Matched root structures:"+maps.size());
            maps = removeSameUnit(maps,ac);
            System.out.println("After removing duplicated mapping:"+maps.size());
//            IAtomContainer acWithoutRootStructure=getACWithoutRootStructure(ac,maps);
            for(AttachedGroup attachedGroup:fragmentGroup.getAttachedGroupList()){
                QueryAtomContainer qac=QueryAtomContainerCreator.createAnyAtomForPseudoAtomQueryContainer(attachedGroup.getGroup());
                List<List<RMap>> mapsGroup = UniversalIsomorphismTester.getSubgraphAtomsMaps(ac, qac);
                System.out.println("Matched attached group"+attachedGroup.toString()+":"+mapsGroup.size());
                mapsGroup = removeSameUnit(mapsGroup,ac);
                System.out.println("After removing duplicated mapping:"+mapsGroup.size());

//                for(List<RMap>)
            }

        } catch (CDKException e) {
            e.printStackTrace();
        }
        return false;
    }

    private IAtomContainer getACWithoutRootStructure(IAtomContainer ac,List<List<RMap>> maps){
        IAtomContainer acClone = null;
        try {
            acClone = (IAtomContainer) ac.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        for (List<RMap> map : maps) {
            for(RMap atomMap:map){
                acClone.removeAtom(atomMap.getId1());
            }
        }
        return ac;
    }

    private List<List<RMap>> removeSameUnit(List<List<RMap>> maps, IAtomContainer ac) {
        Set<Set<IAtom>> atomSet = new HashSet<Set<IAtom>>();
        List<List<RMap>> result = new ArrayList<List<RMap>>();
        for (List<RMap> map : maps) {
            Set<IAtom> matchedAtomSet=new HashSet<IAtom>();
            for(RMap atomMap:map){
                matchedAtomSet.add(ac.getAtom(atomMap.getId1()));
            }
            if(!atomSet.contains(matchedAtomSet)){
                atomSet.add(matchedAtomSet);
                result.add(map);
            }
        }

        return result;
    }
}
