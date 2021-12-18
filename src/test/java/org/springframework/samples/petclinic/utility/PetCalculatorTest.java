package org.springframework.samples.petclinic.utility;

import jdk.vm.ci.meta.Local;
import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PetCalculatorTest {

	private PriceCalculator priceCalculator;
	private Visit visit1, visit2;
	@Before
	public void setup()
	{
		priceCalculator = new PriceCalculator();
		visit1 = new Visit();
		visit1.setDate(LocalDate.now().minusDays(99));
		visit2 = new Visit();
		visit2.setDate(LocalDate.now().minusDays(101));
	}

	@Test
	public void testPriceCalc1() {
		List<Pet> pets = new ArrayList<>();
		for (int i = 0 ; i < 10 ; i++)
		{
			Pet pet = new Pet();
			if (i % 3 == 0)
			{
				LocalDate birthDate = LocalDate.now();
				pet.setBirthDate(birthDate);

				for (int j = 0 ; j < 3 ; j++)
					pet.addVisit(visit1);
			}
			if (i % 3 == 1)
			{
				LocalDate birthDate = LocalDate.now().minusYears(3);
				pet.setBirthDate(birthDate);

				for (int j = 0 ; j < 3 ; j++)
					pet.addVisit(visit2);
			}
			if (i % 3 == 2)
			{
				LocalDate birthDate = LocalDate.now();
				pet.setBirthDate(birthDate);

				for (int j = 0 ; j < 3 ; j++)
					pet.addVisit(visit1);
			}
			pets.add(pet);
		}

		double expected = 3633.88;
		assertEquals(expected, priceCalculator.calcPrice(pets, 5, 12));
	}
}
