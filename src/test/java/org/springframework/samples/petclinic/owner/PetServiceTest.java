package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PetServiceTest {
    private Owner owner = new Owner();
    @Autowired
    PetService petService;

	@ParameterizedTest
	@ValueSource(ints = {14, 1000, 1001, 1010, -1})
    void findPetTest(int petId) {
        assumeNotNull(owner);
        System.out.println("Testing with petId " + petId);
    	Pet pet = petService.findPet(petId);
        assumeFalse("Found pet is null",pet != null);
    }
}
