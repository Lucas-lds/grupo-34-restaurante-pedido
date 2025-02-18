package com.fiap.restaurante.pedido.core.domain.enums;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentStatusTest {

    @Test
    void testEnumValues() {
        PaymentStatus[] expectedValues = {
            PaymentStatus.PENDING,
            PaymentStatus.APPROVED, 
            PaymentStatus.EXPIRED
        };
        assertArrayEquals(expectedValues, PaymentStatus.values());
    }

    @Test
    void testEnumCount() {
        assertEquals(3, PaymentStatus.values().length);
    }
}
