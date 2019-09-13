package com.stephenenright.typemapper.internal.conversion;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.stephenenright.typemapper.internal.util.ClassUtils;

abstract class TypeConverterUtils {

    private TypeConverterUtils() {

    }

    public static List<Class<?>> findAncestorsForType(final Class<?> type) {
        final List<Class<?>> hierarchyList = new ArrayList<>(20);
        final Set<Class<?>> visitedList = new HashSet<>(20);
        addToHierarchy(0, ClassUtils.resolvePrimitiveAsWrapperIfNessecary(type), false, hierarchyList, visitedList);
        final boolean array = ClassUtils.isArray(type);

        int i = 0;
        while (i < hierarchyList.size()) {
            Class<?> candidate = hierarchyList.get(i);
            candidate = (array ? candidate.getComponentType()
                    : ClassUtils.resolvePrimitiveAsWrapperIfNessecary(candidate));
            Class<?> superclass = candidate.getSuperclass();
            if (superclass != null && superclass != Object.class && superclass != Enum.class) {
                addToHierarchy(i + 1, candidate.getSuperclass(), array, hierarchyList, visitedList);
            }
            addInterfaces(candidate, array, hierarchyList, visitedList);
            i++;
        }

        if (Enum.class.isAssignableFrom(type)) {
            addToHierarchy(hierarchyList.size(), Enum.class, array, hierarchyList, visitedList);
            addToHierarchy(hierarchyList.size(), Enum.class, false, hierarchyList, visitedList);
            addInterfaces(Enum.class, array, hierarchyList, visitedList);
        }

        addToHierarchy(hierarchyList.size(), Object.class, array, hierarchyList, visitedList);
        addToHierarchy(hierarchyList.size(), Object.class, false, hierarchyList, visitedList);
        return hierarchyList;
    }

    private static void addInterfaces(Class<?> type, boolean asArray, List<Class<?>> hierarchy, Set<Class<?>> visited) {

        for (Class<?> implementedInterface : type.getInterfaces()) {
            addToHierarchy(hierarchy.size(), implementedInterface, asArray, hierarchy, visited);
        }
    }

    private static void addToHierarchy(int index, Class<?> type, boolean asArray, List<Class<?>> hierarchy,
            Set<Class<?>> visited) {

        if (asArray) {
            type = Array.newInstance(type, 0).getClass();
        }

        if (visited.add(type)) {
            hierarchy.add(index, type);
        }
    }
}
