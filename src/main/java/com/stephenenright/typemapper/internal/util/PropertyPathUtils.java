package com.stephenenright.typemapper.internal.util;

import java.util.List;

import com.stephenenright.typemapper.internal.common.CommonConstants;

public abstract class PropertyPathUtils {

    private PropertyPathUtils() {

    }

    public static String joinPaths(List<String> properties, int toIndex) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, properties, toIndex);
    }

    public static String joinPaths(List<String> properties) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, properties);
    }

    public static String joinPaths(String... paths) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, paths);
    }

    public static void addPropertyPath(StringBuilder builder, String path) {
        if (builder.length() > 0) {
            builder.append(CommonConstants.PROPERTY_PATH_SEPERATOR);
        }

        builder.append(path);
    }

    public static String createIndexedPropertyName(int index) {
        return CommonConstants.PROPERTY_INDEXED_START + String.valueOf(index) + 
                CommonConstants.PROPERTY_INDEXED_END;
    }
    
    private static final char PROPERTY_INDEX_END = ']';
    
    
    public static boolean isPathIndexedPath(String path) {
        if(StringUtils.isNullOrEmpty(path)) {
            return false;
        }
        
        char lastChar = path.charAt(path.length()-1);
         
        return lastChar == PROPERTY_INDEX_END;
    }
}
