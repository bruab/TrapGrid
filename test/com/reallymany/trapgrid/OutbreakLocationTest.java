package com.reallymany.trapgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class OutbreakLocationTest {
	OutbreakLocation fr1;

	@Before
	public void setUp() throws Exception {
		Point2D.Double p1 = new Point2D.Double(5, 7);
		Random testRNG = new Random();
		long testSeed = testRNG.nextLong();
		fr1 = new OutbreakLocation(p1, 100, 30, testSeed);
	}

	@Test
	public void testReleasePoint() {
		
		assertTrue(fr1 instanceof OutbreakLocation);
		assertEquals(100, fr1.numberOfFlies);
		assertEquals(30, fr1.diffusionCoefficient, 0.00000000000000000001);
		assertEquals(7, fr1.location.getY(), 0.0000000000000000001);
	}
	
	@Test
	public void testLocateFlies() {
		ArrayList<Point2D.Double> locations1 = fr1.locateFlies(1);
		assertEquals(100, locations1.size());
	}
	
	@Test
	public void testToString() {
		assertEquals("100 flies released at (5.0, 7.0) with Diffusion Coefficient 30.0", fr1.toString());
	}
	
	@Test
	public void testShortString() {
		assertEquals("(5.0, 7.0)", fr1.shortString());
	}

}
