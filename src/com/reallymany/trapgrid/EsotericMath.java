package com.reallymany.trapgrid;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.math3.special.Gamma;

public class EsotericMath {

	/**
	 * Just a wrapper for the Gamma function
	 * @param d
	 * @return
	 */
	public static double gamma(double d) {
		return Gamma.gamma(d);
	}

	/**
	 * Calculates the phi function from Plant & Cunningham's
	 * "Analyses of the Dispersal of Sterile Mediterranean Fruit Flies
	 * (Diptera: Tephritidae) Released from a Point Source"
	 * @param beta
	 * @param gamma
	 * @param r
	 * @return
	 */
	public static double phi(double beta, double gamma, double r) {
		double denominator = 2*Math.PI*beta*beta*Gamma.gamma(2/gamma);
		double firstTerm = gamma/denominator;
		double secondTerm = Math.exp(-Math.pow((r/beta), gamma));		
		return firstTerm*secondTerm;
	}

	/**
	 * Picks a point from a Gaussian distribution having a given standard deviation,
	 * centered at a given release point
	 * @param location
	 * @param stdev
	 * @param rng
	 * @return
	 */
	static Point2D.Double pickNextPoint(Point2D.Double releasePoint, double stdev, Random rng) {		
		double x = rng.nextGaussian()*stdev + releasePoint.getX();
		double y = rng.nextGaussian()*stdev + releasePoint.getY();
		Point2D.Double randomPoint = new Point2D.Double(x, y);
		return randomPoint;
	}

	/**
	 * Returns an arraylist of points having a Gaussian distribution about a given relase point
	 * @param location
	 * @param diffCoeff
	 * @param day
	 * @param numberOfPoints
	 * @param rng
	 * @return
	 */
	public static ArrayList<Point2D.Double> pickSomePoints(Point2D.Double releasePoint,
			double diffCoeff, int day, int numberOfPoints, Random rng) {
		ArrayList<Point2D.Double> result = new ArrayList<Point2D.Double>();
		double stdev = calculateStandardDeviation(diffCoeff, day);
		for (int i=0; i<numberOfPoints; i++) {
			result.add(pickNextPoint(releasePoint, stdev, rng));
		}
		return result;
	}

	/**
	 * Gives the standard deviation for the Gaussian distribution of a colony
	 * given a diffusion coefficient and number of days -- from P.M. Kareiva,
	 * "Local movement in herbivorous insects: applying a passive diffusion model
	 * to mark-recapture field experiments"
	 * @param diffCoeff
	 * @param time
	 * @return
	 */
	public static double calculateStandardDeviation(double diffCoeff, int time) {
		return Math.sqrt(2*diffCoeff*time);
	}

	/**
	 * Randomly chooses a point within a rectangle defined by the x and y axes
	 * and given maximum x and y values
	 * @param xMax
	 * @param yMax
	 * @param rng
	 * @return
	 */
	public static Point2D.Double pickPointInGrid(double xMax, double yMax, Random rng) {
		double x = rng.nextDouble()*xMax;
		double y = rng.nextDouble()*yMax;
		Point2D.Double result = new Point2D.Double(x, y);
		return result;
	}	
	
	/**
	 * Calculates Mean Dispersal Distance
	 * Uses Bovet and Benhamou (1988) formula:
	 * MDD = L * sqrt((0.79 * n * (1 + r) * (1 - 4))
	 * where L is step size, n is number of steps, and
	 * r = exp(-SDA^2 / 2)
	 * (and and and, SDA is stdev in radians of turning angles)
	 */
	public static double calculateMDD(double stepSize, int numberOfSteps, double stdev) {
		double r = Math.exp((-Math.pow(stdev, numberOfSteps))/2);
		return stepSize * Math.sqrt( 0.79 * numberOfSteps * (1-r) * (1+r));
	}
	
	public static ArrayList<Point2D.Double> pickPointsWithRadius(Point2D.Double center, double radius, int numPoints, Random rng) {
		ArrayList<Point2D.Double> result = new ArrayList<Point2D.Double>();
		double angle, x, y;
		for (int i=0; i<numPoints; i++) {
			// pick an angle between 0 and 2*pi
			angle = rng.nextDouble()*2*Math.PI;  // in radians
			
			// find x and y offset (from center)
			x = Math.cos(angle) * radius;
			y = Math.sin(angle) * radius;
			
			// calculate actual x and y coordinates (considering center as starting point)
			x = x + center.x;
			y = y + center.y;
			
			// append to result
			result.add(new Point2D.Double(x, y));			
		}
		return result;
	}
	
}
