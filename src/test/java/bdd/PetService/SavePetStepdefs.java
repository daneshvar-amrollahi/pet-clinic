package bdd.PetService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SavePetStepdefs {

	private Pet pet;
	private Owner owner;

	@Autowired
	private PetService petService;

	@Autowired
	private PetTimedCache pets;

	@Autowired
	private OwnerRepository ownerRepository;

	@Given("owner with id {int} and pet with id {int} exist")
	public void ownerAndPetWithIdExist(int ownerId, int petId) {
		pet = new Pet();
		pet.setName("Pat");
		pet.setBirthDate(LocalDate.of(2017, 1, 1));

		PetType petType = new PetType();
		petType.setId(1);
		pet.setType(petType);

		owner = new Owner();
		owner.setFirstName("Gholam");
		owner.setLastName("Rezaii");
		owner.setAddress("Hell's basement");
		owner.setCity("You-know-where");
		owner.setTelephone("09191919223");
		ownerRepository.save(owner);

		owner.addPet(pet);

		pets.save(pet);
	}


	@When("owner {int} saves pet {int} information")
	public void ownerSavesPetInformation(int ownerId, int petId) {
		petService.savePet(pet, owner);
	}

	@Then("pet is added to owner pets")
	public void petIsAddedToOwnerPets() {
		assertTrue(owner.getPets().contains(pet));
	}
}
