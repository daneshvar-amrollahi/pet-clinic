package org.springframework.samples.petclinic.owner;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@SpringBootTest
@AutoConfigureMockMvc

class PetControllerTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository pets;

	@MockBean
	private OwnerRepository owners;

	private Owner owner;
	private Pet pet;


	@Test
	void testInitCreationForm() throws Exception {
		owner = new Owner();
		owner.setId(1);
		when(this.owners.findById(1)).thenReturn(owner);
		ResultActions resultActions = mockMvc.perform(get("/owners/1/pets/new"));
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(model().attributeExists("pet"));
	}

	@Test
	void testProcessCreationForm() throws Exception {
		PetType dog = new PetType();
		dog.setId(10);
		dog.setName("jack");
		pet = new Pet();
		pet.setId(100);
		owner = new Owner();
		owner.setId(1);
		when(this.pets.findPetTypes()).thenReturn(Lists.newArrayList(dog));
		when(this.owners.findById(1)).thenReturn(owner);
		when(this.pets.findById(100)).thenReturn(pet);


		ResultActions resultActions =
			mockMvc.perform(post("/owners/1/pets/new")
				.param("name", "Alfred")
				.param("type", "jack")
				.param("birthDate", "2019-11-18")
			);
		resultActions.andExpect(status().is3xxRedirection());
		assertEquals(owner.getPets().size(), 1);
		assertEquals("Alfred", owner.getPets().get(0).getName());
	}

	@Test
	void testInitUpdateForm() throws Exception {
		pet = new Pet();
	 	pet.setId(100);
		when(this.pets.findById(100)).thenReturn(pet);

		ResultActions resultActions = mockMvc.perform(get("/owners/1/pets/100/edit"));
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(view().name("pets/createOrUpdatePetForm"));
		resultActions.andExpect(model().attributeExists("pet"));
	}

	@Test
	void testProcessUpdateForm() throws Exception {
		PetType dog = new PetType();
		dog.setId(10);
		dog.setName("jack");
		pet = new Pet();
		pet.setId(100);
		owner = new Owner();
		owner.setId(1);
		when(this.pets.findPetTypes()).thenReturn(Lists.newArrayList(dog));
		when(this.owners.findById(1)).thenReturn(owner);
		when(this.pets.findById(100)).thenReturn(pet);

		ResultActions resultActions =
			mockMvc.perform(post("/owners/1/pets/100/edit")
				.param("name", "Alfred")
				.param("type", "jack")
				.param("birthDate", "2019-11-18")
			);

		resultActions.andExpect(view().name("redirect:/owners/{ownerId}"));
		resultActions.andExpect(status().is3xxRedirection());
	}
}
