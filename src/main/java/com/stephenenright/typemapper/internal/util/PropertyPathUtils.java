package com.stephenenright.typemapper.internal.util;

import java.util.List;

import com.stephenenright.typemapper.internal.common.CommonConstants;

public abstract class PropertyPathUtils {

    private PropertyPathUtils() {

    }

    public static String joinPaths(List<String> properties) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, properties);
    }
    
    
    
    public static void addPropertyPath(StringBuilder builder, String path) {
        if(builder.length() > 0) {
            builder.append(CommonConstants.PROPERTY_PATH_SEPERATOR);
        }
        
        builder.append(path);
    }
    
}
