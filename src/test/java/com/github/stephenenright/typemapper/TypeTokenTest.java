package com.github.stephenenright.typemapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.github.stephenenright.typemapper.test.models.vending.VendingMachine;

public class TypeTokenTest {

    @Test
    public void fromClass() {
        TypeToken<VendingMachine> typeToken = TypeToken.of(VendingMachine.class);
        assertEquals(typeToken.getRawType(), VendingMachine.class);
        assertEquals(typeToken.getType(), VendingMachine.class);
    }

    @Test
    public void fromParameterizedType() {
        TypeToken<List<VendingMachine>> typeToken = new TypeToken<List<VendingMachine>>() {
        };
        assertEquals(typeToken.getRawType(), List.class);
        assertEquals(typeToken.getType().toString(),
                "java.util.List<com.github.stephenenright.typemapper.test.models.vending.VendingMachine>");

    }

}
