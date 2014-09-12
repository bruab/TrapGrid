package com.reallymany.trapgrid;

import java.awt.geom.Point2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import au.com.bytecode.opencsv.CSVReader;
/**
 * Amazing Outbreak class abstracts a collection of OutbreakLocations. The constructor expects
 * a tab-separated file containing x, y, n and D values where (x, y) is the location of
 * an outbreak, n is the number of flies and D is their diffusion coefficient.
 * @author bhall
 *
 */
public class Outbreak {
	ArrayList<OutbreakLocation> allOutbreakLocations;

	// Not using this constructor these days -- will we ever want a single outbreak
	// with multiple locations? Because if not, we could completely scrap this class.
	public Outbreak(String filename, long seed) throws NumberFormatException, IOException {
		ArrayList<OutbreakLocation> myPoints = new ArrayList<OutbreakLocation>();
		try {
			CSVReader reader = new CSVReader(new FileReader(filename), '\t');
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				if (nextLine.length == 4) {
					double x = Double.parseDouble(nextLine[0]);
					double y = Double.parseDouble(nextLine[1]);
					int n = Integer.parseInt(nextLine[2]);
					double diffc = Double.parseDouble(nextLine[3]);
					Point2D.Double newPoint = new Point2D.Double(x, y);
					OutbreakLocation newOutbreakLocation = new OutbreakLocation(newPoint, n, diffc, 0, 0, 0, false, seed);
					myPoints.add(newOutbreakLocation);
				} else {
					System.err.println("Invalid input! " +
							"OutbreakLocation file must have 4 values per line (x, y, number of flies, diffusion coefficient).");
					reader.close();
					throw new IOException();
				}
			}
			reader.close();
			this.allOutbreakLocations = myPoints;			
		} catch (FileNotFoundException e) {
			System.err.println("Input file " + filename + " not found");
			e.printStackTrace();
			throw new FileNotFoundException();
		} 
		// TODO Auto-generated constructor stub
	}
	/**
	 * Constructor for a randomized Outbreak
	 * returns an Outbreak object with a single,
	 * randomly-chosen OutbreakLocation
	 * @param n Number of flies
	 * @param diffCoeff Diffusion Coefficient for outbreak
	 * @param rng Random object
	 */
	public Outbreak(double x, double y, int n, double diffCoeff, double stepSize, int stepsPerDay, double turnAngleStdev,
			boolean useMDD, long seed, boolean locationIsRandom) {
		this.allOutbreakLocations = new ArrayList<OutbreakLocation>();
		Point2D.Double myPoint;
		if (locationIsRandom) {
			Random r = new Random(seed);
			myPoint = EsotericMath.pickPointInGrid(x, y, r);
		} else {
			myPoint = new Point2D.Double(x, y);
		} 		
		OutbreakLocation rp = new OutbreakLocation(myPoint, n, diffCoeff, stepSize, stepsPerDay, 
				turnAngleStdev, useMDD, seed);
		this.allOutbreakLocations.add(rp);
	}
	
	
	public String toString() {
		int numberOfRPoints = this.allOutbreakLocations.size();
		if (numberOfRPoints == 1) {
			return this.allOutbreakLocations.get(0).shortString();
		} else {
			String result = "Outbreak with " + numberOfRPoints + " OutbreakLocation(s): ";
			for (int i=0; i<numberOfRPoints-1; i++) {
				result += this.allOutbreakLocations.get(i).toString() + "; ";
			}
			// Don't want to add ';' to last OutbreakLocation entry...
			result += this.allOutbreakLocations.get(numberOfRPoints-1).toString();
			return result;
		}		
	}

	
}
