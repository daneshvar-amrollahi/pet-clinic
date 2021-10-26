package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.utility.SimpleDI;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
class PetManagerTest {

    @Autowired
    PetManager petManager;
    
    @Test
    void findOwnerTest()
    {
        int ownerId = 4;
        Owner returnedOwner = petManager.findOwner(ownerId);
        assertEquals(petManager.findOwner(ownerId).getId(), returnedOwner.getId());
    }

    @Test
    void newPetTest()
    {
        Owner owner = mock(Owner.class);
        Pet pet = petManager.newPet(owner);
        verify(owner).addPet(pet);
    }

    

}