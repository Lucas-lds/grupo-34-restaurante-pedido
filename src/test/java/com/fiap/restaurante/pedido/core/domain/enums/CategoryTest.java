package com.fiap.restaurante.pedido.core.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void testEnumValues() {
        Category[] expectedValues = {
            Category.LANCHE,
            Category.ACOMPANHAMENTO, 
            Category.BEBIDA,
            Category.SOBREMESA
        };
        assertArrayEquals(expectedValues, Category.values());
    }

    @Test
    void testEnumCount() {
        assertEquals(4, Category.values().length);
    }
}
