package com.github.stephenenright.typemapper.internal.util;

import java.util.List;
import java.util.Set;

import com.github.stephenenright.typemapper.internal.common.CommonConstants;
import com.github.stephenenright.typemapper.internal.common.Pair;

public abstract class PropertyPathUtils {

    private PropertyPathUtils() {

    }

    public static String joinPaths(List<String> properties, int toIndex) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, properties, toIndex);
    }

    public static String joinPaths(List<String> properties) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, properties);
    }

    public static String joinPaths(List<String> properties, Set<Integer> indexesToExclude) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, properties, indexesToExclude);
    }

    public static String joinPaths(String... paths) {
        return StringUtils.joinStrings(CommonConstants.PROPERTY_PATH_SEPERATOR, paths);
    }

    public static Pair<String, String> joinPathsWithParent(List<String> paths, Set<Integer> indexesToExclude) {

        if (paths.size() == 0) {
            return new Pair<>(CommonConstants.EMPTY_STRING, CommonConstants.EMPTY_STRING);
        }

        StringBuilder builder = new StringBuilder();
        int i = 0;
        int lastIndex = paths.size() - 1;
        String parentPath = CommonConstants.EMPTY_STRING;
        for (String str : paths) {
            if (indexesToExclude != null && indexesToExclude.contains(i)) {
                
                if (lastIndex > 0 && lastIndex == i) {
                    parentPath = builder.toString();
                }
                
                i++;
                continue;
            }

            if (StringUtils.isNullOrEmpty(str)) {
                continue;
            }

            if (builder.length() > 0) {
                builder.append(CommonConstants.PROPERTY_PATH_SEPERATOR);
            }

            builder.append(str);

            if (lastIndex > 0 && lastIndex == i) {
                parentPath = builder.toString();
            }

            i++;
        }

        return new Pair<>(parentPath, builder.toString());

    }

    public static void addPropertyPath(StringBuilder builder, String path) {
        if (builder.length() > 0) {
            builder.append(CommonConstants.PROPERTY_PATH_SEPERATOR);
        }

        builder.append(path);
    }

    public static String createIndexedPropertyName(int index) {
        return CommonConstants.PROPERTY_INDEXED_START + String.valueOf(index) + CommonConstants.PROPERTY_INDEXED_END;
    }

    private static final char PROPERTY_INDEX_END = ']';

    public static boolean isPathIndexedPath(String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            return false;
        }

        char lastChar = path.charAt(path.length() - 1);

        return lastChar == PROPERTY_INDEX_END;
    }

    public static String getParentPath(String path) {

        if (StringUtils.isNullOrEmpty(path)) {
            return CommonConstants.EMPTY_STRING;
        }

        String[] parts = path.split(CommonConstants.PROPERTY_PATH_SEPERATOR_FOR_REGEX);

        if (parts.length > 1) {
            return parts[parts.length - 2];
        }

        return CommonConstants.EMPTY_STRING;
    }
}
