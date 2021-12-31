package bdd.PetService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetService;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewPetStepdefs {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private final int TEST_OWNER_ID = 85;
	private Owner owner;
	private int initialPetsSize;

	@Given("owner exists")
	public void ownerExists() {
		owner = new Owner();
		owner.setFirstName("Amu");
		owner.setLastName("Gholam");
		owner.setAddress("Najibie - Kooche shahid abbas alavi");
		owner.setCity("Tehran");
		owner.setTelephone("09191919223");
		owner.setId(85);
		initialPetsSize = owner.getPets().size();
	}


	@When("owner buys a pet")
	public void ownerBuysAPet() {
		petService.newPet(owner);
	}


	@Then("size of owner pets is increased by one")
	public void sizeOfOwnerPetsIsIncreasedByOne() {
		assertEquals(initialPetsSize + 1, owner.getPets().size());
	}
}
