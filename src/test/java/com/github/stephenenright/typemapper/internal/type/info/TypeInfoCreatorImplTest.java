package com.github.stephenenright.typemapper.internal.type.info;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.github.stephenenright.typemapper.DefaultMapperConfiguration;
import com.github.stephenenright.typemapper.TypeInfo;
import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeIntrospector;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.github.stephenenright.typemapper.test.models.vending.PaymentProcessor;
import com.github.stephenenright.typemapper.test.models.vending.VendingMachine;
import com.github.stephenenright.typemapper.test.models.vending.VendingMachineStatus;

public class TypeInfoCreatorImplTest {

    private TypeInfoCreatorDefaultImpl creator;
    private TypeInfoRegistry typeInfoRegistry;

    @Before
    public void setup() {
        TypeIntrospector introspector = new TypeIntrospectorImpl();
        creator = new TypeInfoCreatorDefaultImpl(new TypePropertyInfoCollectorImpl(introspector));
        typeInfoRegistry = FixtureUtils.createTypeInfoRegistry(creator);

    }

    @Test
    public void create() {
        Map<String, Class<?>> expectedGetterMap = new HashMap<>();
        Map<String, Class<?>> expectedSetterMap = new HashMap<>();

        expectedGetterMap.put("name", String.class);
        expectedGetterMap.put("slots", List.class);
        expectedGetterMap.put("processor", PaymentProcessor.class);
        expectedGetterMap.put("status", VendingMachineStatus.class);

        expectedSetterMap.put("name", String.class);
        expectedSetterMap.put("slots", List.class);
        expectedSetterMap.put("processor", PaymentProcessor.class);
        expectedSetterMap.put("status", VendingMachineStatus.class);

        TypeMapperConfiguration configuration = DefaultMapperConfiguration.create();
        TypeInfo<VendingMachine> typeInfo = creator.create(null, VendingMachine.class, configuration, typeInfoRegistry);

        assertNotNull(typeInfo);
        assertEquals(VendingMachine.class, typeInfo.getType());

        for (String getter : expectedGetterMap.keySet()) {
            assertTrue(typeInfo.getPropertyGetters().containsKey(getter));
            assertEquals(expectedGetterMap.get(getter), typeInfo.getPropertyGetters().get(getter).getType());
        }

        for (String setter : expectedSetterMap.keySet()) {
            assertTrue(typeInfo.getPropertySetters().containsKey(setter));
            assertEquals(expectedSetterMap.get(setter), typeInfo.getPropertySetters().get(setter).getType());
        }

    }

}
