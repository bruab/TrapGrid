package com.reallymany.trapgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

public class OutbreakTest {
	Random rng;
	Outbreak testOutbreak1, testOutbreak2, testOutbreak3;

	@Before
	public void setUp() throws Exception {
		rng = new Random();
		long seed = rng.nextLong();
		double stepSize = 0.0;
		int stepsPerDay = 0;
		double turnAngleStdev = 0.0;
		boolean useMDD = false;
		testOutbreak1 = new Outbreak("test/com/reallymany/trapgrid/test_files/test_releases_1.tsv", seed);
		testOutbreak2 = new Outbreak(10, 10, 500, 30, stepSize, stepsPerDay, turnAngleStdev, useMDD, 100, true);
		testOutbreak3 = new Outbreak(25, 38.3, 200, 30, stepSize, stepsPerDay, turnAngleStdev, useMDD, 100, false);
	}

	@Test
	public void testOutbreak() throws Exception {
		setUp();
		assertTrue(testOutbreak1 instanceof Outbreak);
		assertEquals(2, testOutbreak1.allOutbreakLocations.size());
		
		assertTrue(testOutbreak2 instanceof Outbreak);
		assertEquals(1, testOutbreak2.allOutbreakLocations.size());
		assertEquals(500, testOutbreak2.allOutbreakLocations.get(0).numberOfFlies);
		assertTrue(testOutbreak2.allOutbreakLocations.get(0).location.getX() <= 10);
		assertTrue(testOutbreak2.allOutbreakLocations.get(0).location.getY() <= 10);
	}
	
	@Test
	public void testOutbreakFromXYValues() throws Exception {
		setUp();
		assertTrue(testOutbreak3 instanceof Outbreak);
		assertEquals(38.3, testOutbreak3.allOutbreakLocations.get(0).location.getY(), 0.01);
	}
	
	@Test
	public void testToString() {
		String expected = "Outbreak with 2 OutbreakLocation(s): ";
		expected += "100 flies released at (-3.0, 2.0) with Diffusion Coefficient 30.0; ";
		expected += "100 flies released at (8.0, -4.0) with Diffusion Coefficient 30.0";
		assertEquals(expected, testOutbreak1.toString());
	}
	
	@Test
	public void testToStringWithOneOutbreakLocation() {
		String expected = "(7.220096548596434, 1.9497605734770518)";
		assertEquals(expected, testOutbreak2.toString());
	}
	

}
