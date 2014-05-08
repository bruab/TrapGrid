package com.reallymany.trapgrid;

import java.awt.geom.Point2D;

/**
 * Amazing Trap class abstracts the concept of a Trap having a given location and attractiveness,
 * contains a method for calculating the probability of escape from a Trap given a location.
 * @author bhall
 *
 */
public class Trap {
	Point2D.Double location;	
	double lambda;
	
	public Trap() {
		this.location = new Point2D.Double();
		this.lambda = 0.5;
	}
	
	public Trap(double x, double y, double lam) {
		this.location = new Point2D.Double(x, y);
		this.lambda = lam;
	}
	
	public Point2D.Double getLocation() {
		return location;
	}

	public double getLambda() {
		return lambda;
	}

	public String toString() {
		return "Position: ("+this.location.getX()+","+this.location.getY()+"); Lambda: "+this.lambda;
	}

	public double getEscapeProbability(Point2D.Double p) {
		double distance = location.distance(p);
		double numerator = 2*Math.exp(lambda*distance);
		double denominator = 1+(Math.exp(2*lambda*distance));
		double escapeProb = 1 - (numerator / denominator);
		if (Double.isNaN(escapeProb)) {
			return 1.0;
		} else {
			return escapeProb;
		}
	}		
}
