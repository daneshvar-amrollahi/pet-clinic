package bdd.PetService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetService;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindPetStepdefs {
	@Autowired
	PetService petService;

	@Autowired
	PetTimedCache pets;

	private int TEST_PET_ID = 1;
	private Pet foundPet;

	@Given("pet with owner id {int} and type id {int} exists")
	public void petWithIdExists(int ownerId, int typeId) {
		Pet pet = new Pet();
		pet.setName("Pat");
		pet.setBirthDate(LocalDate.of(2017, 1, 1));

		PetType petType = new PetType();
		petType.setId(typeId);
		pet.setType(petType);

		Owner owner = new Owner();
		owner.setId(ownerId);
		owner.addPet(pet);

		pets.save(pet);
		TEST_PET_ID = pet.getId();
	}

	@When("I try to find pet with id {int}")
	public void iTryToFindPetWithId(int id) {
		foundPet = petService.findPet(TEST_PET_ID);
	}

	@Then("pet with id {int} is returned")
	public void petWithIdIsReturned(int arg0) {
		assertEquals(TEST_PET_ID, foundPet.getId());
	}
}
