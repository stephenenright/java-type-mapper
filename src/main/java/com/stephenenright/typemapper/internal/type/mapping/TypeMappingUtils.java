package com.stephenenright.typemapper.internal.type.mapping;

import java.util.List;

import com.stephenenright.typemapper.internal.common.CommonConstants;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfo;

public abstract class TypeMappingUtils {

    private TypeMappingUtils() {

    }
    
    
    public static String mappingToString(TypeMapping mapping) {
        return String.format("Mapping[%s -> %s]", mappingPropertiesToString(mapping.getSourceGetters()),
                mappingPropertiesToString(mapping.getDestinationSetters()));
        
    }

    public static String mappingPropertiesToString(List<? extends TypePropertyInfo> properties) {
        StringBuilder builder = new StringBuilder();

        int i = 0;

        for (TypePropertyInfo pi : properties) {
            if (i == 0) {
                builder.append(pi.toString());
            } else {
                if (builder.length() > 0) {
                    builder.append(CommonConstants.PROPERTY_PATH_SEPERATOR);
                }

                builder.append(pi.getName());
            }
            
            i++;
        }

        return builder.toString();
    }

}
