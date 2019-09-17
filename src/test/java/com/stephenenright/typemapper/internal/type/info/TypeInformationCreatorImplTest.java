package com.stephenenright.typemapper.internal.type.info;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.stephenenright.typemapper.TypeIntrospectionStore;
import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.test.models.vending.PaymentProcessor;
import com.stephenenright.typemapper.test.models.vending.VendingMachine;
import com.stephenenright.typemapper.test.models.vending.VendingMachineStatus;

public class TypeInformationCreatorImplTest {

    private TypeInformationCreatorDefaultImpl creator;

    @Before
    public void setup() {
        TypeIntrospectionStore introspector = new TypeIntrospectionStoreImpl();
        creator = new TypeInformationCreatorDefaultImpl(new TypePropertyInfoCollectorImpl(introspector));
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

        TypeMapperConfiguration configuration = TypeMapperConfiguration.create();
        TypeInformation<VendingMachine> typeInfo = creator.create(VendingMachine.class, configuration);

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
