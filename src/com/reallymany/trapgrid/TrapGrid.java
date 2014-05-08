package com.reallymany.trapgrid;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Amazing TrapGrid class, contains methods for calculating capture probability
 * at a given point or as an average over the entire grid
 * @author bhall
 *
 */
public class TrapGrid {
	// for now our integrator (for calculating average probability
	// of escape) works on square areas, so only two boundary values:
	double xMax, yMax;
	ArrayList<Trap> trapList;	
	
	public TrapGrid() {		
		this.trapList = new ArrayList<Trap>();
	}
	
	public TrapGrid(String filename) throws NumberFormatException, IOException {
		ArrayList<Trap> myTraps = new ArrayList<Trap>();
		try {
			CSVReader reader = new CSVReader(new FileReader(filename), '\t');
			String[] nextLine;
			// Read first line to set xMax, yMax
			String[] firstLine = reader.readNext();
			if (firstLine.length != 2) {
				System.out.println("Error -- first line of TrapGrid file should contain two entries " +
									"representing the coordinates of the upper-right corner of the grid.");
				reader.close();
				throw new IOException();
			}
			this.xMax = Double.parseDouble(firstLine[0]);
			this.yMax = Double.parseDouble(firstLine[1]);
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine.length == 3) {
					double x = Double.parseDouble(nextLine[0]);
					double y = Double.parseDouble(nextLine[1]);
					double lambda = Double.parseDouble(nextLine[2]);
					Trap newTrap = new Trap(x, y, lambda);
					myTraps.add(newTrap);									
				} else {
					System.err.println("Invalid input! TrapGrid file must have 3 values per line (x, y, lambda).");
					reader.close();
					throw new IOException();
				}
			}
			reader.close();			
			this.trapList = myTraps;			
		} catch (FileNotFoundException e) {
			System.err.println("Input file " + filename + " not found");
			e.printStackTrace();
			throw new FileNotFoundException();
		} 
	}

	public String toString() {
		return "TrapGrid from x=0 to x="+this.xMax+", y=0 to y="+this.yMax+" containing "+
				this.trapList.size()+" traps";
	}

	public void placeTrap(Trap trap) {
		trapList.add(trap);		
	}

	public void placeTrap(int x, int y, double lambda) {
		Trap trapToAdd = new Trap(x, y, lambda);
		trapList.add(trapToAdd);
	}

	/**
	 * Returns probability of escaping from all traps on grid given a location
	 * @param currentLocation
	 * @return
	 */
	public double getTotalEscapeProbability(Point2D.Double currentLocation) {
		double escapeProbability = 1.0;
		for (Trap t : trapList) {
			escapeProbability = escapeProbability * t.getEscapeProbability(currentLocation);
		}
		return escapeProbability;
	}

	/**
	 * Uses a randomized Monte Carlo method to estimate the average probability of escape
	 * for the entire TrapGrid. Initially chooses N random points, where N = 10 * (area of grid).
	 * Iteratively averages the escape probability for the points and compares it with successive
	 * random samples until the difference between results is within tolerance.
	 * If ten consecutive attempts fail, N is increased by a factor of 10.
	 * @param rng	A Random() object
	 * @param tolerance
	 * @return
	 */
	public double calculateAverageEscapeProbability(long seed, double tolerance) {
		Random rng = new Random(seed);
		int count = 0;
		int numberOfPoints = (int) (this.xMax * this.yMax * 10);
		double currentAvg;
		double newAvg = -1;
		currentAvg = avgEscProbForNRandomPoints(numberOfPoints, rng);
		
		while (Math.abs(currentAvg - newAvg) > tolerance) {
			currentAvg = newAvg;
			count += 1;
			if (count % 10 == 0) {
				numberOfPoints *= 10;
			}
			newAvg = avgEscProbForNRandomPoints(numberOfPoints, rng);
		}		
		return newAvg;
	}

	private double avgEscProbForNRandomPoints(int numberOfPoints, Random rand) {
		double sum = 0.0;
		Point2D.Double p;
		for (int i=0; i<numberOfPoints; i++) {
			p = EsotericMath.pickPointInGrid(this.xMax, this.yMax, rand);
			sum += this.getTotalEscapeProbability(p);
		}
		return sum / numberOfPoints;
	}
}
