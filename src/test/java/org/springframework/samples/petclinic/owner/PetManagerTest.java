package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
class PetManagerTest {

    // @Autowired
    // PetManager petManager;
    
    // @Test
    // void findOwnerTest()
    // {
    //     int ownerId = 4;
    //     Owner returnedOwner = petManager.findOwner(ownerId);
    //     assertEquals(petManager.findOwner(ownerId).getId(), returnedOwner.getId());
    // }

    @InjectMocks
    PetManager petManagerWithMocks;

    @Mock
    OwnerRepository owners;
  
    @Mock
    PetTimedCache pets;

    @Mock
    Logger criticalLogger;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findOwnerTest() {
        int ownerId = 6;
        petManagerWithMocks.findOwner(ownerId);
        verify(owners).findById(ownerId);
    }

    @Test
    void newPetTest()
    {
        Owner owner = mock(Owner.class);
        Pet pet = petManagerWithMocks.newPet(owner);
        verify(owner).addPet(pet);
    }

    @Test
    void findPetTest() {
        int petId = 11;
        petManagerWithMocks.findPet(petId);
        verify(pets).get(petId);
    }

    @Test
    void savePetTest() {
        Owner owner = mock(Owner.class);
        Pet pet = mock(Pet.class);
        petManagerWithMocks.savePet(pet, owner);
        verify(owner).addPet(pet);
    }

    @Test
    void savePetTestWithActualPet() {
        Owner owner = spy(Owner.class);
        Pet pet = new Pet();
        pet.setName("zac");
        petManagerWithMocks.savePet(pet, owner);
        
        verify(owner).addPet(pet);
        assertNotNull(owner.getPet("zac"));
    }

    @Nested
    @RunWith(SpringRunner.class)
    @SpringBootTest
    class SpyPetManagerTest {
        @MockBean
        PetManager mockPetManager;

        @Test
        void getOwnerPets() {
            int ownerId = 5;
            mockPetManager.findOwner(ownerId);
            verify(mockPetManager).findOwner(ownerId);
        }
    }
}