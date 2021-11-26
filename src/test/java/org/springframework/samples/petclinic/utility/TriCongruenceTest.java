package org.springframework.samples.petclinic.utility;

import com.github.mryf323.tractatus.*;
import com.github.mryf323.tractatus.experimental.extensions.ReportingExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ReportingExtension.class)
@ClauseDefinition(clause = 'a', def = "t1A == t2A")
@ClauseDefinition(clause = 'b', def = "t1B == t2B")
@ClauseDefinition(clause = 'c', def = "t1C == t2C")
@ClauseDefinition(clause = 'd', def = "t1A < 0")
@ClauseDefinition(clause = 'e', def = "t1A + t1B < t1C")
class TriCongruenceTest {

	private static final Logger log = LoggerFactory.getLogger(TriCongruenceTest.class);

	// For predicate in line 14, we have:
	// f(a, b, c) = ~a + ~b + ~c
	// The following UTPs then can be considered w.r.t a, b, and c:
	// UTPs: { FTT, TFT, TTF }
	// As this expression is symmetric w.r.t our implicants, a single
	// near false point of TTT can be obtained.

	public boolean predicate1(double t1A, double t1B, double t1C,
							 double t2A, double t2B, double t2C) {
		double[] t1arr = {t1A, t1B, t1C};
		double[] t2arr = {t2A, t2B, t2C};

		Arrays.sort(t1arr);
		Arrays.sort(t2arr);

		return t1arr[0] != t2arr[0] || t1arr[1] != t2arr[1] || t1arr[2] != t2arr[2];
	}

	@UniqueTruePoint(
		predicate = "~a + ~b + ~c",
		dnf = "~a + ~b + ~c",
		implicant = "~a",
		valuations = {
			@Valuation(clause = 'a', valuation = false),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void testCUTPNFP1() {
		assertTrue(predicate1(1, 3, 7, 2, 3, 7));
	}

	@UniqueTruePoint(
		predicate = "~a + ~b + ~c",
		dnf = "~a + ~b + ~c",
		implicant = "~b",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = false),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void testCUTPNFP2() {
		assertTrue(predicate1(2, 4, 7, 2, 3, 7));
	}

	@UniqueTruePoint(
		predicate = "~a + ~b + ~c",
		dnf = "~a + ~b + ~c",
		implicant = "~c",
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = false)
		}
	)
	@Test
	public void testCUTPNFP3() {
		assertTrue(predicate1(2, 3, 6, 2, 3, 7));
	}

	@NearFalsePoint(
		predicate = "~a + ~b + ~c",
		dnf = "~a + ~b + ~c",
		implicant = "~a",
		clause = 'a',
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void testCUTPNFP4() {
		assertFalse(predicate1(1, 3, 7, 1, 3, 7));
	}

	@NearFalsePoint(
		predicate = "~a + ~b + ~c",
		dnf = "~a + ~b + ~c",
		implicant = "~a",
		clause = 'b',
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void testCUTPNFP5() {
		assertFalse(predicate1(1, 3, 7, 1, 3, 7));
	}

	@NearFalsePoint(
		predicate = "~a + ~b + ~c",
		dnf = "~a + ~b + ~c",
		implicant = "~a",
		clause = 'c',
		valuations = {
			@Valuation(clause = 'a', valuation = true),
			@Valuation(clause = 'b', valuation = true),
			@Valuation(clause = 'c', valuation = true)
		}
	)
	@Test
	public void testCUTPNFP6() {
		assertFalse(predicate1(1, 3, 7, 1, 3, 7));
	}

	private boolean predicate2(double t1A, double t1B, double t1C) {
		double[] t1arr = {t1A, t1B, t1C};
		Arrays.sort(t1arr);
		return (t1arr[0] < 0) || (t1arr[0] + t1arr[1] < t1arr[2]);
	}

	// For predicate in line 15, we have:
	// f(d, e) = d + e
	// For Clause Coverage to be satisfied, we need the following TRs:
	// {TF, FT}

	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false)
		}
	)
	@Test
	public void testCC1() {
		assertTrue(predicate2(-1, 4, 3));
	}

	@ClauseCoverage(
		predicate = "d + e",
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = true)
		}
	)
	@Test
	public void testCC2() {
		assertTrue(predicate2(1, 2, 4));
	}




	// A counter-example to show that CUTPNFP does not subsume UTPC.
	// f = ab+cd. 
	// Implicant: ab
	// CUPTPNP set: {T T F T, T F F T, F T F T } combined with {F T T T, F T T F, F T F T }
	// for implicant cd. 
	// Note that there is a near false point in common; Hence the
	// resulting CUTPNFP set has five elements: {T T F T, T F F T, F T F T, F T T T, F T T F }.

	// To consider UTPC we need to compute a minimal form for ~f.
	//~f = ~a~c + ~a~d + ~b~c + ~b~d

	// Note that we have 4 prime implicants in ~f , along with 2 prime implicants in f ; hence
	// we need exactly 6 tests for UTPC. The CUTPNFP set has only 5; hence CUTPNFP
	// does not subsume UTPC.


	private static boolean questionTwo(boolean a, boolean b, boolean c, boolean d, boolean e) {
		boolean predicate = false;
//		predicate = a predicate with any number of clauses
		return predicate;
	}

	// For CACC to be satisfied on predicate in line 15 w.r.t d,
	// we need the following TRs:
	// {TF, FF}
	// As this predicate is symmetric, replacing TF with FF in the above set
	// can make CACC be satisfied w.r.t e as well.



	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = true),
			@Valuation(clause = 'e', valuation = false)
		},
		predicateValue = true
	)
	@Test
	public void testCACC1() {
		assertTrue(predicate2(-1, 4, 3));
	}

	@CACC(
		predicate = "d + e",
		majorClause = 'd',
		valuations = {
			@Valuation(clause = 'd', valuation = false),
			@Valuation(clause = 'e', valuation = false)
		},
		predicateValue = false
	)
	@Test
	public void testCACC2() {
		assertFalse(predicate2(1, 4, 3));
	}
}
