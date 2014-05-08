package com.reallymany.trapgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TrapGridTest {
	TrapGrid testTrapGrid1;
	Trap testTrap1, testTrap2;
	
	@Before
	public void setUp() {
		testTrapGrid1 = new TrapGrid();
		testTrap1 = new Trap();
		testTrap2 = new Trap(2, 2, 0.2);
	}

	@Test
	public void testTrapGrid() throws IOException {
		setUp();
		assertTrue(testTrapGrid1 instanceof TrapGrid);
		assertEquals(0, testTrapGrid1.trapList.size());
		
		TrapGrid testTrapGrid2 = new TrapGrid("test/com/reallymany/trapgrid/test_files/test_grid_1.tsv");
		assertTrue(testTrapGrid2 instanceof TrapGrid);
		assertEquals(9, testTrapGrid2.trapList.size());		
	}
	
	@SuppressWarnings("unused")
	@Test(expected = IOException.class)
	public void testTrapGridBadInput1() throws IOException {
		TrapGrid badTrapGrid1 = new TrapGrid("test/com/reallymany/trapgrid/test_files/bad_grid_1.tsv");
	}		
	
	@SuppressWarnings("unused")
	@Test(expected = NumberFormatException.class)
	public void testTrapGridBadInput2() throws IOException {
		TrapGrid badTrapGrid2 = new TrapGrid("test/com/reallymany/trapgrid/test_files/bad_grid_2.tsv");
	}
	
	@SuppressWarnings("unused")
	@Test(expected = FileNotFoundException.class)
	public void testTrapGridBadINput3() throws IOException {
		TrapGrid badTrapGrid2 = new TrapGrid("nonexistent_file");
	}
	
	// TODO meditate on exception handling, this all seems like too much work.

	@Test
	public void testPlaceTrap() {
		setUp();		
		testTrapGrid1.placeTrap(testTrap1);
		assertEquals(1, testTrapGrid1.trapList.size());
		
		testTrapGrid1.placeTrap(5, -10, 0.3);
		assertEquals(2, testTrapGrid1.trapList.size());		
	}
	
	@Test
	public void testGetTotalEscapeProbability() {
		setUp();
		Point2D.Double p1 = new Point2D.Double(5, 5);
		testTrapGrid1.placeTrap(testTrap1);
		assertEquals(0.9417631, testTrapGrid1.getTotalEscapeProbability(p1), 0.0001);
		
		testTrapGrid1.placeTrap(testTrap2);
		assertEquals(0.2764765, testTrap2.getEscapeProbability(p1), 0.0001);
		assertEquals(0.2603754, testTrapGrid1.getTotalEscapeProbability(p1), 0.0001);		
	}
	
	@Test
	// This one takes a minute to run if you increase tolerance to 0.0001 :)
	public void testCalculateAverageEscapeProbability() {
		Random rng = new Random();
		double tolerance = 0.001;
		TrapGrid testTrapGrid3 = new TrapGrid();
		Trap t = new Trap(5, 5, 0.3);
		testTrapGrid3.placeTrap(t);
		testTrapGrid3.xMax = 10;
		testTrapGrid3.yMax = 10;
		long testSeed = rng.nextLong();
		double result = testTrapGrid3.calculateAverageEscapeProbability(testSeed, tolerance);
		System.out.println("avg esc prob for grid to (10,10) is " + result);
		assertTrue(result >= 0);
		assertTrue(result <= 1);
		assertEquals(0.4135, result, 0.05);	// this test can still fail, technically...
	}

}
