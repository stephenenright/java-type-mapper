package com.stephenenright.typemapper.internal.configuration;

import com.stephenenright.typemapper.TypeIntrospectionStore;
import com.stephenenright.typemapper.configuration.TypeAccessLevel;
import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.type.info.TypeIntrospectionStoreImpl;

public class TypeMapperConfigurationImpl implements TypeMapperConfiguration {

    private TypeAccessLevel accessLevel;
    private TypeIntrospectionStore introspectionStore = new TypeIntrospectionStoreImpl();

    public TypeMapperConfigurationImpl() {
        accessLevel = TypeAccessLevel.PUBLIC;
    }

    public TypeAccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(TypeAccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    @Override
    public TypeIntrospectionStore getIntrospectionStore() {
        return introspectionStore;
    }

    @Override
    public void setIntrospectionStore(TypeIntrospectionStore store) {
        this.introspectionStore = store;

    }

}
