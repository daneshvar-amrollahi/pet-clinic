package org.springframework.samples.petclinic.owner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(value = PetController.class,
	includeFilters = {
		@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE),
	}
	)
class PetControllerTests {
	private final String VIEWS_PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository pets;

	@MockBean
	private OwnerRepository owners;

	private Owner owner;
	private Pet pet;

	@BeforeEach
	void setup() {
		PetType dog = new PetType();
		dog.setId(10);
		dog.setName("funfe");
		pet = new Pet();
		pet.setId(100);
		owner = new Owner();
		owner.setId(1);
		given(this.pets.findPetTypes()).willReturn(Lists.newArrayList(dog));
		given(this.owners.findById(1)).willReturn(owner);
		given(this.pets.findById(100)).willReturn(pet);
	}

	@Test
	void testInitCreationForm() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/owners/1/pets/new"));
		resultActions.andExpect(status().isOk());
	}

	@Test
	void testProcessCreationForm() throws Exception {
		ResultActions resultActions =
			mockMvc.perform(post("/owners/1/pets/new")
				.param("name", "Alfred")
				.param("type", "funfe")
				.param("birthDate", "2019-11-18")
			);
		resultActions.andExpect(status().is3xxRedirection());
	}

	@Test
	void testInitUpdateForm() throws Exception {
		ResultActions resultActions = mockMvc.perform(get("/owners/1/pets/100/edit"));
		resultActions.andExpect(status().isOk());
		resultActions.andExpect(view().name("pets/createOrUpdatePetForm"));
		resultActions.andExpect(model().attributeExists("pet"));
	}

	@Test
	void testProcessUpdateForm() throws Exception {
		ResultActions resultActions =
			mockMvc.perform(post("/owners/1/pets/100/edit")
				.param("name", "Alfred")
				.param("type", "funfe")
				.param("birthDate", "2019-11-18")
			);

		resultActions.andExpect(view().name("redirect:/owners/{ownerId}"));
		resultActions.andExpect(status().is3xxRedirection());
	}
}
