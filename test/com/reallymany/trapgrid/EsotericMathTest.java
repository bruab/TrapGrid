package com.reallymany.trapgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

public class EsotericMathTest {

	@Test
	public void testGamma() {
		assertEquals(1, EsotericMath.gamma(2.0), 0.01);
		assertEquals(6, EsotericMath.gamma(4), 0.01);
		assertEquals(2.36327, EsotericMath.gamma(-1.5), 0.01);
	}
	
	@Test
	public void testPhi() {
		assertEquals(0.0585498, EsotericMath.phi(1.0, 1.0, 1.0), 0.00001);
		assertEquals(0.0292749, EsotericMath.phi(2.0, 2.0, 2.0), 0.00001);
		assertEquals(0.0000003807875, EsotericMath.phi(2.0, 2.0, 7.0), 0.0000001);
		assertEquals(0.0004195444, EsotericMath.phi(3.16, 0.423, 0.0), 0.0000001);
	}
	
	@Test
	public void testCalculateStandardDeviation() {
		assertEquals(12.64911, EsotericMath.calculateStandardDeviation(40.0, 2), 0.0001);
	}
	
	@Test
	public void testPickNextPoint() {
		Point2D.Double releasePoint = new Point2D.Double(0.0, 0.0);
		Random rng = new Random();
		Point2D.Double testPoint1 = EsotericMath.pickNextPoint(releasePoint, 3.0, rng);
		assertTrue(testPoint1 instanceof Point2D.Double);
		// How else to test? run 1000 times and test that ~70% of results are within 1 stdev?
	}
	
	@Test
	public void testPickSomePoints() {
		Point2D.Double releasePoint = new Point2D.Double(0.0, 0.0);
		Random rng = new Random();
		ArrayList<Point2D.Double> myPoints = EsotericMath.pickSomePoints(releasePoint, 40.0, 1, 100, rng);
		assertEquals(100, myPoints.size());
	}
	
	@Test
	public void testPickSomePointsMDD() {
		Point2D.Double releasePoint = new Point2D.Double(0.0, 0.0);
		double stepSize = 1.0;
		int stepsPerDay = 10;
		double turnAngleStdev = 40.0;
		int day = 3;
		int numberOfPoints = 100;
		Random rng = new Random();
		ArrayList<Point2D.Double> myPoints = EsotericMath.pickSomePointsMDD(releasePoint, stepSize, stepsPerDay, 
				turnAngleStdev, day, numberOfPoints, rng);
		assertEquals(100, myPoints.size());
	}
	
	@Test
	public void testPickPointInGrid() {
		Random rng = new Random();
		// I know this doesn't *prove* anything but it's here for sanity...
		for (int i=0; i<1000; i++) {
			Point2D.Double testPoint = EsotericMath.pickPointInGrid(100, 100, rng);
			assertTrue(testPoint.getX() >= 0);
			assertTrue(testPoint.getX() <= 100);
			assertTrue(testPoint.getY() >= 0);
			assertTrue(testPoint.getY() <= 100);
		}
	}
	
	@Test
	public void testCalculateMDD() {
		double stepSize = 4;
		int numberOfSteps = 100;
		double stdev = 54;
		double expected = 35.55277767;  // thanks, Wolfram Alpha!
		double actual = EsotericMath.calculateMDD(stepSize, numberOfSteps, stdev);
		assertEquals(expected, actual, 0.00000001);
	}
	
	@Test
	public void testPickPointsWithRadius() {
		Random rng = new Random();
		Point2D.Double center = new Point2D.Double(1.1, 2.3);
		double radius = 5.0;
		int numberOfPoints = 100;
		ArrayList<Point2D.Double> results = EsotericMath.pickPointsWithRadius(center, radius, numberOfPoints, rng);
		for (int i=0; i<numberOfPoints; i++) {
			// make sure all points are the same distance from center
			assertEquals(5.0, Point2D.distance(center.x, center.y, results.get(i).x, results.get(i).y), 0.0001);
		}		
	}

}
