package com.reallymany.trapgrid;

import static org.junit.Assert.*;

import java.awt.geom.Point2D;

import org.junit.Before;
import org.junit.Test;

public class TrapTest {
	Trap testTrap1, testTrap2, testTrap3, testTrap4;
	
	@Before
	public void setUp() {
		testTrap1 = new Trap();
		testTrap2 = new Trap(2, 3.1, 0.18);
		testTrap3 = new Trap(0, 0, 0.2);
		testTrap4 = new Trap(0, 0, 0.5);
	}

	@Test
	public void testTrap() {
		setUp();
		Point2D.Double origin = new Point2D.Double();
		assertEquals(origin, testTrap1.location);
		assertEquals("Position: (0.0,0.0); Lambda: 0.5", testTrap1.toString());
		assertEquals(0.5, testTrap1.getLambda(), 0.001);
		
		assertEquals("Position: (2.0,3.1); Lambda: 0.18", testTrap2.toString());
		assertEquals(0.18, testTrap2.getLambda(), 0.001);				
	}
	
	@Test
	public void testGetEscapeProbability() {		
		setUp();
		Point2D.Double p1 = new Point2D.Double(5, 5);
		assertEquals(0.5409, testTrap3.getEscapeProbability(p1), 0.0001);
		assertEquals(0.9417631, testTrap4.getEscapeProbability(p1), 0.0001);
	}
	
	@Test
	public void testGetEscapeProbabilityWhenFarAway() {
		setUp();
		Point2D.Double p = new Point2D.Double(5000, 5000);
		double escapeProb = testTrap3.getEscapeProbability(p);
		assertFalse(Double.isNaN(escapeProb));
	}
	
}

