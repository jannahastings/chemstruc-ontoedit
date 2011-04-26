package uk.ac.ebi.chebi.ontology.core.engine.cache;

public class IndexItem {
    public long position;
    public int length;

    public IndexItem() {
    }

    public IndexItem(long position, int length) {
        this.position = position;
        this.length = length;
    }
}
