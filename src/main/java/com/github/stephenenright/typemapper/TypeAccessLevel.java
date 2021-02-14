package com.github.stephenenright.typemapper;

public enum TypeAccessLevel {
    
    PUBLIC(1), PROTECTED(2), PACKAGE_PRIVATE(3), PRIVATE(4);

    private final int accessLevel;

    TypeAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getAccessLeve() {
        return accessLevel;
    }
}
