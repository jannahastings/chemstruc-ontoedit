package uk.ac.ebi.chebi.ontology.core.util;

import com.thoughtworks.xstream.XStream;
import uk.ac.ebi.chebi.ontology.core.definition.Definition;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Atom;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Bond;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.SimpleAtomContainer;
import uk.ac.ebi.chebi.ontology.core.definition.fragment.Skeleton;
import uk.ac.ebi.chebi.ontology.core.definition.logical.And;
import uk.ac.ebi.chebi.ontology.core.definition.logical.Or;

public class XStreamUtil {
    public static XStream getAliasedXStream(){
        XStream xStream=new XStream();
        xStream.alias("Atom", Atom.class);
        xStream.alias("Bond", Bond.class);
        xStream.alias("SimpleAtomContainer", SimpleAtomContainer.class);
        xStream.alias("Skeleton",Skeleton.class);
        xStream.alias("And",And.class);
        xStream.alias("Or",Or.class);
        xStream.alias("Definition",Definition.class);
        return xStream;
    }
}
