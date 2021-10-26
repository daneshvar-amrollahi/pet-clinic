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
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
class PetManagerTest {

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

    // For each test the following are indicated:
    // <test double>, <verification>, <approach>
    
    @Test // mock, behavioral, mockist
    void findOwnerTest() {
        int ownerId = 6;
        petManagerWithMocks.findOwner(ownerId);
        verify(owners).findById(ownerId);
    }

    @Test // mock + dummy, behavioral, mockist
    void newPetTest()
    {
        Owner owner = mock(Owner.class);
        Pet pet = petManagerWithMocks.newPet(owner);
        verify(owner).addPet(pet);
    }

    @Test // mock, behavioral, mockist
    void findPetTest() {
        int petId = 11;
        petManagerWithMocks.findPet(petId);
        verify(pets).get(petId);
    }

    @Test // mock + dummy, behavioral, mockist
    void savePetTest() {
        Owner owner = mock(Owner.class);
        Pet pet = mock(Pet.class);
        petManagerWithMocks.savePet(pet, owner);
        verify(owner).addPet(pet);
    }

    @Test // spy, behavioral, classic
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

        @Test // mock, behavioral, mockist
        void getOwnerPets() {
            int ownerId = 5;
            mockPetManager.findOwner(ownerId);
            verify(mockPetManager).findOwner(ownerId);
        }
    }

    @Nested
    @RunWith(SpringRunner.class)
    @SpringBootTest
    class ActualPetManagerTest {
        @Autowired
        PetManager petManager;

        @Test // (almost) fake object + stub, state, classic
        void getOwnerPetTypesTest() {
            int ownerId = 3;
            Set<PetType> actual = petManager.getOwnerPetTypes(ownerId);
            PetType petType = mock(PetType.class);
            when(petType.getName()).thenReturn("dog");
            Set<PetType> expected = new HashSet<>();
            expected.add(petType);
            
            assertTrue(actual.size() == 1);
            PetType ptActual = actual.iterator().next();
            assertTrue(ptActual.getName().equals(petType.getName()));
        }

        @Test // (almost) fake object, state, classic
        void getVisitsBetweenTest() {
            int petId = 8;
            LocalDate startDate = LocalDate.of(2000, 1, 1);
            LocalDate endDate = LocalDate.of(2020, 12, 12);
            List<Visit> actual = petManager.getVisitsBetween(petId, startDate, endDate);
            
            assumeTrue(actual.size() > 0);
            String visit1descr = actual.get(0).getDescription();
            String visit2descr = actual.get(1).getDescription();
            assertTrue(visit1descr.equals("rabies shot"));
            assertTrue(visit2descr.equals("neutered"));
        }
    }

    
}