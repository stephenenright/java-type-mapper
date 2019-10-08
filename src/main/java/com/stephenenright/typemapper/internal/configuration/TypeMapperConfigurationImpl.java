package com.stephenenright.typemapper.internal.configuration;

import com.stephenenright.typemapper.TypeAccessLevel;
import com.stephenenright.typemapper.TypeIntrospector;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.type.info.TypeIntrospectorImpl;
import com.stephenenright.typemapper.internal.util.AssertUtils;

public class TypeMapperConfigurationImpl implements TypeMapperConfiguration {

    private TypeAccessLevel accessLevel;
    private TypeIntrospector introspectionStore = new TypeIntrospectorImpl();

    public TypeMapperConfigurationImpl() {
        accessLevel = TypeAccessLevel.PUBLIC;
    }

    public TypeAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(TypeAccessLevel accessLevel) {
        AssertUtils.notNull(accessLevel, "Access Level cannot be null");
        this.accessLevel = accessLevel;
    }

    @Override
    public TypeIntrospector getIntrospector() {
        return introspectionStore;
    }

    @Override
    public void setIntrospector(TypeIntrospector store) {
        AssertUtils.notNull(store);
        this.introspectionStore = store;

    }

}
