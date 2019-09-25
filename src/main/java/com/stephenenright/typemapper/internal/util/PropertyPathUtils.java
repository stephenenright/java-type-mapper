package com.stephenenright.typemapper.internal.util;

import java.util.List;

import com.stephenenright.typemapper.internal.common.CommonConstants;

public abstract class PropertyPathUtils {

    private PropertyPathUtils() {

    }

    public static String joinPaths(List<String> properties) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, properties);
    }

}
