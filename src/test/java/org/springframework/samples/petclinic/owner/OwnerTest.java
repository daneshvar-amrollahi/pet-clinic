package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

class OwnerTest {
    private Owner owner = new Owner();
    @Test
    void testGetAddress() {
        assertEquals(null, owner.getAddress());
    }

    @Test
    void testSetAddress() {
        owner.setAddress("karaj");
        assertEquals("karaj", owner.getAddress());
    }
}
