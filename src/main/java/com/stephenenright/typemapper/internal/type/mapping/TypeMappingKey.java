package com.stephenenright.typemapper.internal.type.mapping;

public class TypeMappingKey<S, D> {

    private final Class<S> sourceType;
    private final Class<D> destinationType;
    private final int hashcode;

    public TypeMappingKey(Class<S> sourceType, Class<D> destinationType) {
        this.sourceType = sourceType;
        this.destinationType = destinationType;
        this.hashcode = calculateHashCode();
    }

    public Class<S> getSourceType() {
        return sourceType;
    }

    public Class<D> getDestinationType() {
        return destinationType;
    }

    @Override
    public int hashCode() {
        return hashcode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof TypeMappingKey)) {
            return false;
        }

        TypeMappingKey<?, ?> other = (TypeMappingKey<?, ?>) obj;

        return sourceType.equals(other.sourceType) && destinationType.equals(other.destinationType);
    }

    @Override
    public String toString() {
        return String.format("Source Type: %s, Destination Type: %s", sourceType.getName(), destinationType.getName());
    }

    private int calculateHashCode() {
        int result = 31 * sourceType.hashCode();
        result = 31 * result + destinationType.hashCode();
        return result;
    }
}
