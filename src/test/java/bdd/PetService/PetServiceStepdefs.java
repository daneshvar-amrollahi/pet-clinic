package bdd.PetService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetService;
import org.springframework.samples.petclinic.owner.PetType;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetServiceStepdefs {
	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	private int TEST_OWNER_ID;
	private Owner foundOwner;
	private PetType petType;

	@Given("owner with id {int} exists")
	public void ownerWithIdExists(int id) {
		Owner owner = new Owner();
		owner.setFirstName("Amu");
		owner.setLastName("Gholam");
		owner.setAddress("Najibie - Kooche shahid abbas alavi");
		owner.setCity("Tehran");
		owner.setTelephone("09191919223");
		ownerRepository.save(owner);
		TEST_OWNER_ID = owner.getId();
	}

	@When("findOwner is called with argument {int}")
	public void findownerIsCalledWithArgument(int id) {
		foundOwner = petService.findOwner(TEST_OWNER_ID);
	}

	@Then("the owner with id {int} is returned")
	public void theOwnerWithIdIsReturned(int arg0) {
		assertEquals(TEST_OWNER_ID, foundOwner.getId());
	}
}
