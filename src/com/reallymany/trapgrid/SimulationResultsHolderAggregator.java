package com.reallymany.trapgrid;

import java.util.ArrayList;

public class SimulationResultsHolderAggregator {
	ArrayList<SimulationResultsHolder> resultsHolders;

	public SimulationResultsHolderAggregator(
			ArrayList<SimulationResultsHolder> holders) {
		resultsHolders = holders;
		for (SimulationResultsHolder simResHol : resultsHolders) {
			simResHol.calculateCumulativeProbabilities();
		}
	}
	
	public String aggregrateSimulationResultsHolders() {
		String result = "Day\tAv Cumulative Escape Probability\n";
		double avgProb;
		// Find out how many days we simulated
		int numDays = resultsHolders.get(0).avgEscapeProbabilityByDay.size();
		
		for (int day=1; day<=numDays; day++) {
			avgProb = calculateAvgCumProb(day);
			result += String.valueOf(day) + "\t";
			result += String.valueOf(avgProb) + "\n";
		}
		
		return result;
	}

	public double calculateAvgCumProb(int day) {
		double avgProb, dailyProb;
		double sum = 0.0;
		int numProbs = 0;
		for (SimulationResultsHolder simResHol : resultsHolders) {
			System.out.println(simResHol.cumEscapeProbabilityByDay);
			dailyProb = simResHol.cumEscapeProbabilityByDay.get(day); 
			sum += dailyProb;
			numProbs += 1;
		}
		avgProb = sum / numProbs;
		return avgProb;
	}

}
