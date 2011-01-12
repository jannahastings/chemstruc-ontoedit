package uk.ac.ebi.chebi.ontology.core;

import uk.ac.ebi.chebi.ontology.core.matcher.IMatcher;

@Deprecated
public class Definition {
    private String id;
    private IMatcher matcher;
    private String comment;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IMatcher getMatcher() {
        return matcher;
    }

    public void setMatcher(IMatcher matcher) {
        this.matcher = matcher;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
