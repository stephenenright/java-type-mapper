package com.stephenenright.typemapper.internal.conversion;

class TypeConverterKey {
	private final Class<?> sourceType;
	private final Class<?> targetType;

	public TypeConverterKey(Class<?> sourceType, Class<?> targetType) {
		this.sourceType = sourceType;
		this.targetType = targetType;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		
		if (!(other instanceof TypeConverterKey)) {
			return false;
		}

		TypeConverterKey otherKey = (TypeConverterKey) other;

		return (this.sourceType.equals(otherKey.sourceType)) && this.targetType.equals(otherKey.targetType);
	}
	
	public Class<?> getSourceType() {
		return sourceType;
	}

	public Class<?> getTargetType() {
		return targetType;
	}

	@Override
	public int hashCode() {
		int hashCode = this.sourceType.hashCode();
		hashCode = 31 * hashCode + this.targetType.hashCode();

		return hashCode;
	}

	@Override
	public String toString() {
		return ("TypeConverterKey sourceType: " + this.sourceType + ", targetType: " + this.targetType);
	}
}