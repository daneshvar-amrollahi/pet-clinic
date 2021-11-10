package org.springframework.samples.petclinic.model.priceCalculators;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

public class SimplePriceCalculatorTest {
    private SimplePriceCalculator priceCalculator;

    @Before
    public void setUp() {
        priceCalculator = new SimplePriceCalculator();
    }

    @Test
    public void testCalcPrice1() {
        List<Pet> pets = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Pet pet = new Pet();

            PetType petType = new PetType();
			if (i % 2 == 1)
				petType.setRare(true);
			else
				petType.setRare(false);

            pet.setType(petType);

            pets.add(pet);
            System.out.println(pets.get(i).getType().getRare());
        }

        UserType userType = UserType.NEW;
        double expected = priceCalculator.calcPrice(pets, 0, 1, userType);
    	assertTrue(expected == 3.04);
    }

    @Test
	public void testCalcPrice2() {
		List<Pet> pets = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Pet pet = new Pet();

			PetType petType = new PetType();
			if (i % 2 == 1)
				petType.setRare(true);
			else
				petType.setRare(false);

			pet.setType(petType);

			pets.add(pet);
			System.out.println(pets.get(i).getType().getRare());
		}

		UserType userType = UserType.SILVER;
		double actual = priceCalculator.calcPrice(pets, 0, 1, userType);
		assertTrue(actual == 3.2);
	}
}
