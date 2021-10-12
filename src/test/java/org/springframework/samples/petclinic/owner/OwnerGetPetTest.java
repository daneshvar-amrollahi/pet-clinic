package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.FromDataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(Theories.class)
public class OwnerGetPetTest {
	private final Owner owner = new Owner();

	@DataPoints("petNames")
	public static List<String> petNames() {
		return asList("zac", null, "cat", "pat", "");
	}

	@Theory
	public void testGetPet(@FromDataPoints("petNames") String name) {
		assumeTrue("Name can not be null!", name != null);
		assumeTrue("Name can not be empty!", name.length() > 0);

		System.out.println("Testing with Pet(" + name + ")");
		Pet pet = new Pet();
		pet.setName(name);
		owner.addPet(pet);

		assumeNotNull(owner.getPet(name));
		assertEquals(pet, owner.getPet(name));
	}
}
