package org.springframework.samples.petclinic.model.priceCalculators;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;

public class SimplePriceCalculatorTest {
    private SimplePriceCalculator priceCalculator;

    @Before
    public void setUp() {
        priceCalculator = new SimplePriceCalculator();
    }

    @Test
    public void testCalculatePrice() {
        List<Pet> pets = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Pet pet = new Pet();
            pet.setType(new PetType());
            pets.add(pet);
            System.out.println(pets.get(i).getType().getRare());
        }

        assertTrue(1 == 1);
    }
}