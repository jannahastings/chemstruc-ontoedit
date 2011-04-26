package uk.ac.ebi.chebi.ontology.core.engine.cache;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.serialize.ReferenceFieldSerializer;

public class ExtendedKryo extends Kryo {
    /**
     * Called by {@link #newSerializer(Class)} when a serializer could not otherwise be determined. The default implementation
     * returns a new {@link com.esotericsoftware.kryo.serialize.ReferenceFieldSerializer}.
     */
    @Override
    protected Serializer newDefaultSerializer (Class type) {
        return new ReferenceFieldSerializer(this, type);
    }

}
