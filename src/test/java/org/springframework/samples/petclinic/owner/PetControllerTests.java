package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTests {
	private final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository pets;

	@MockBean
	private OwnerRepository owners;

	@Test
	void testInitCreationForm() throws Exception {
		when(owners.findById(1)).thenReturn(new Owner());
		ResultActions resultActions = mockMvc.perform(get("/owners/1/pets/new"));
		resultActions.andExpect(status().isOk());
	}

	@Test
	void testProcessCreationForm() throws Exception {
		PetType dog = new PetType();
		dog.setId(10);
		dog.setName("funfe");
		Owner owner = new Owner();
		owner.setId(1);
		when(owners.findById(1)).thenReturn(owner);
		when(pets.findPetTypes()).thenReturn(Lists.newArrayList(dog));

		Pet pet = new Pet();
		pet.setId(100);
		when(pets.findById(100)).thenReturn(pet);

		ResultActions resultActions =
			mockMvc.perform(post("/owners/1/pets/new")
				.param("name", "Alfred")
				.param("type", "funfe")
				.param("birthDate", "2019-11-18")
			);
		resultActions.andExpect(status().is3xxRedirection());

		assertEquals(owner, pet.getOwner());
	}
}
