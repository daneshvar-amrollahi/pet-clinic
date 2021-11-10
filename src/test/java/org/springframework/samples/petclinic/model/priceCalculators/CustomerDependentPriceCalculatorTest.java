package org.springframework.samples.petclinic.model.priceCalculators;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;

public class CustomerDependentPriceCalculatorTest {

	private CustomerDependentPriceCalculator customerDependentPriceCalculator;
	private PetType rareType;
	private PetType frequentType;
	private UserType newUserType;
	private UserType goldUserType;
	private UserType silverUserType;

	@Before
	public void setUp() {
		customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
		rareType = new PetType();
		rareType.setRare(true);
		frequentType = new PetType();
		frequentType.setRare(false);
		newUserType = UserType.NEW;
		goldUserType = UserType.GOLD;
		silverUserType = UserType.SILVER;
	}

	@Test
	public void testCalcPrice1() {
		Pet pet1 = new Pet();
		pet1.setType(frequentType);
		pet1.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet2 = new Pet();
		pet2.setType(rareType);
		pet2.setBirthDate(new LocalDate(2018, 1, 1).toDate());

		Pet pet3 = new Pet();
		pet3.setType(frequentType);
		pet3.setBirthDate(new LocalDate(2018, 1, 1).toDate());

		Pet pet4 = new Pet();
		pet4.setType(rareType);
		pet4.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		List<Pet> pets = new ArrayList<>();
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);

		assertTrue(5.08 == customerDependentPriceCalculator.calcPrice(pets, 0, 1, newUserType));
	}

	@Test
	public void testCalcPrice2() {
		Pet pet1 = new Pet();
		pet1.setType(rareType);
		pet1.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet2 = new Pet();
		pet2.setType(rareType);
		pet2.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet3 = new Pet();
		pet3.setType(rareType);
		pet3.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet4 = new Pet();
		pet4.setType(rareType);
		pet4.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet5 = new Pet();
		pet5.setType(rareType);
		pet5.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet6 = new Pet();
		pet6.setType(rareType);
		pet6.setBirthDate(new LocalDate(2018, 1, 1).toDate());

		List<Pet> pets = new ArrayList<>();
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet5);
		pets.add(pet6);

		assertTrue(9.12 == customerDependentPriceCalculator.calcPrice(pets, 0, 1, newUserType));
	}

	@Test
	public void testCalcPrice3() {
		Pet pet1 = new Pet();
		pet1.setType(frequentType);
		pet1.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet2 = new Pet();
		pet2.setType(rareType);
		pet2.setBirthDate(new LocalDate(2018, 1, 1).toDate());

		Pet pet3 = new Pet();
		pet3.setType(frequentType);
		pet3.setBirthDate(new LocalDate(2018, 1, 1).toDate());

		Pet pet4 = new Pet();
		pet4.setType(rareType);
		pet4.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		List<Pet> pets = new ArrayList<>();
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);

		assertTrue(4.064 == customerDependentPriceCalculator.calcPrice(pets, 0, 1, goldUserType));
	}

	@Test
	public void testCalcPrice4() {
		Pet pet1 = new Pet();
		pet1.setType(rareType);
		pet1.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet2 = new Pet();
		pet2.setType(rareType);
		pet2.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet3 = new Pet();
		pet3.setType(rareType);
		pet3.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet4 = new Pet();
		pet4.setType(rareType);
		pet4.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet5 = new Pet();
		pet5.setType(rareType);
		pet5.setBirthDate(new LocalDate(2020, 1, 1).toDate());

		Pet pet6 = new Pet();
		pet6.setType(rareType);
		pet6.setBirthDate(new LocalDate(2018, 1, 1).toDate());

		List<Pet> pets = new ArrayList<>();
		pets.add(pet1);
		pets.add(pet2);
		pets.add(pet3);
		pets.add(pet4);
		pets.add(pet5);
		pets.add(pet6);

		assertTrue(8.64 == customerDependentPriceCalculator.calcPrice(pets, 0, 1, silverUserType));
	}

}
