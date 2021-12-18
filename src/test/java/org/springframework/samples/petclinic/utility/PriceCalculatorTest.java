package org.springframework.samples.petclinic.utility;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceCalculatorTest {

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
	public void testPriceCalc() {
		List<Visit> visits = new ArrayList<>();
		List<Pet> pets = new ArrayList<>();

		for (int i = 98; i < 103; i++) {
			Visit visit = new Visit();
			visit.setDate(LocalDate.now().minusDays(i));
			visits.add(visit);
		}

		for (int i = 0; i < 10; i++) {
			Pet pet = new Pet();
			pet.setBirthDate(LocalDate.now().minusYears(i % 5));
			pets.add(pet);
		}

		for (int i = 0 ; i < 10 ; i++)
		{
			Pet pet = pets.get(i);
			int n = 5 - i % 4;
			for (int j = 0; j < n; j++) {
				pet.addVisit(visits.get(j));
			}
		}

		double expected = 52318.6;
		assertEquals(expected, PriceCalculator.calcPrice(pets, 5, 12));
	}
}