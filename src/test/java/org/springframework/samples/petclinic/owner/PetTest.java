package org.springframework.samples.petclinic.owner;

import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import java.util.*;

@RunWith(Theories.class)
public class PetTest {
	
	@DataPoint
	public static List<Visit> visits1() {
		List<Visit> vlist = new ArrayList<>();
		Visit v1 = new Visit();
		Visit v2 = new Visit();
		Visit v3 = new Visit();

		v1.setDate(LocalDate.of(2000, 1, 21));
		vlist.add(v1);
		v2.setDate(LocalDate.of(2001, 4, 17));
		vlist.add(v2);
		v3.setDate(LocalDate.of(1999, 4, 19));
		vlist.add(v3);

		return vlist;
	}

	@DataPoint
	public static List<Visit> visits2() {
		List<Visit> vlist = new ArrayList<>();
		Visit v1 = new Visit();
		Visit v2 = new Visit();
		Visit v3 = new Visit();

		v1.setDate(LocalDate.of(2000, 10, 7));
		vlist.add(v1);
		v2.setDate(LocalDate.of(2000, 11, 19));
		vlist.add(v2);
		v3.setDate(LocalDate.of(1999, 4, 19));
		vlist.add(v3);

		return vlist;
	}

	@Theory
	public void testGetVisits(List<Visit> visitList) {
		Pet pet = new Pet();
		for (Visit visit: visitList)
			pet.addVisit(visit);

		List<Visit> expected = visitList;
		Collections.sort(expected, new Comparator<Visit>() {
			@Override
			public int compare(Visit v1, Visit v2) {
			  return v2.getDate().compareTo(v1.getDate());
			}
		  });
	
		assertEquals(expected, pet.getVisits());

	}
}
