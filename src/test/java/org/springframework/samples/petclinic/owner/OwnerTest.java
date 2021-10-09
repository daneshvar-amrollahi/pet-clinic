package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

class OwnerTest {
    private Owner owner;

    @BeforeEach
    void createOwner() {
        owner = new Owner();
    }

    @Test
    void testSetGetAddress() {
        owner.setAddress("taleghani st");
        assertEquals("taleghani st", owner.getAddress());
    }

    @Test
    void testSetGetCity() {
        owner.setCity("karaj");
        assertEquals("karaj", owner.getCity());
    }

    @Test
    void testSetGetTelephone() {
        owner.setTelephone("+989334615405");
        assertEquals("+989334615405", owner.getTelephone());
    }

    @Test
    void testAddGetPets() {
        Pet pet1 = new Pet();
        pet1.setName("zac");
        Pet pet2 = new Pet();
        pet2.setName("jack");
        Pet pet3 = new Pet();
        pet3.setName("pat");

        owner.addPet(pet1);
        owner.addPet(pet2);
        owner.addPet(pet3);

        List<Pet> expected = new ArrayList<Pet>();
        expected.add(pet2);
        expected.add(pet3);
        expected.add(pet1);
        assertEquals(expected, owner.getPets());
    }

    @Test
    void testRemovePet() {
        Pet pet1 = new Pet();
        pet1.setName("zac");
        Pet pet2 = new Pet();
        pet2.setName("jack");

        owner.addPet(pet1);
        owner.addPet(pet2);
        owner.removePet(pet1);

        List<Pet> expected = new ArrayList<Pet>();
        expected.add(pet2);
        assertEquals(expected, owner.getPets());
    }

    @Test
    void testGetPet() {
        Pet pet = new Pet();
        pet.setName("zac");

        owner.addPet(pet);

        assertEquals(pet, owner.getPet("zac"));
    }
}
