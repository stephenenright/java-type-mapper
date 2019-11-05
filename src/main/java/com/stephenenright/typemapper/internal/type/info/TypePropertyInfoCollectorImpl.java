package com.stephenenright.typemapper.internal.type.info;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeIntrospector;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.common.Pair;
import com.stephenenright.typemapper.internal.util.JavaBeanUtils;
import com.stephenenright.typemapper.internal.util.MemberUtils;
import com.stephenenright.typemapper.internal.util.MethodUtils;

public class TypePropertyInfoCollectorImpl implements TypePropertyInfoCollector {

    private final TypeIntrospector introspector;

    public TypePropertyInfoCollectorImpl(TypeIntrospector introspector) {
        this.introspector = introspector;
    }

    @Override
    public Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>> collectProperties(Object source,
            Class<?> type, TypeMapperConfiguration configuration, TypeInfoRegistry registry) {

        Map<String, TypePropertyGetter> getterMap = new HashMap<>();
        Map<String, TypePropertySetter> setterMap = new HashMap<>();

        collectMethods(type, configuration, getterMap, setterMap, registry);

        return new Pair<Map<String, TypePropertyGetter>, Map<String, TypePropertySetter>>(getterMap, setterMap);

    }

    private void collectMethods(Class<?> type, TypeMapperConfiguration configuration,
            Map<String, TypePropertyGetter> getterMap, Map<String, TypePropertySetter> setterMap,
            TypeInfoRegistry registry) {

        Method[] methods = introspector.getDeclaredMethods(type);

        for (Method method : methods) {
            boolean isAccessAllowed = MemberUtils.isMemberAccessAllowed(method, configuration.getAccessLevel());

            if (!isAccessAllowed) {
                continue;
            }

            if (isGetterMethod(method)) {
                final String propertyName = JavaBeanUtils.extractPropertyNameFromGetter(method);
                getterMap.put(propertyName, new TypePropertyMethodGetterImpl(propertyName, type, method, registry));
                MethodUtils.makeAccessible(method);

            } else if (isSetterMethod(method)) {
                final String propertyName = JavaBeanUtils.extractPropertyNameFromSetter(method);
                setterMap.put(propertyName, new TypePropertyMethodSetterImpl(propertyName, type, method, registry));
                MethodUtils.makeAccessible(method);
            }
        }
    }

    private boolean isGetterMethod(Method method) {
        return method.getParameterTypes().length == 0 && !method.getReturnType().equals(void.class)
                && JavaBeanUtils.hasMethodGetterNamingConvention(method);
    }

    private boolean isSetterMethod(Method method) {
        return method.getParameterTypes().length == 1 && (method.getReturnType().equals(void.class)
                // method chaining
                || method.getReturnType().equals(method.getDeclaringClass()))
                && JavaBeanUtils.hasMethodSetterNamingConvention(method);
    }
}
