package com.reallymany.trapgrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

public class SimulationResultsHolder {
	ArrayList<String[]> rawData;
	Map<Integer, Double> avgEscapeProbabilityByDay;
	Map<Integer, Double> cumEscapeProbabilityByDay;
	String flyReleaseInfo;	

	public SimulationResultsHolder() {
		rawData = new ArrayList<String[]>();
		avgEscapeProbabilityByDay = new TreeMap<Integer, Double>();
		cumEscapeProbabilityByDay = new TreeMap<Integer, Double>();
		flyReleaseInfo = "";
	}

	public void addRawData(String[] oneLineOfData) {
		rawData.add(oneLineOfData);
	}

	public void addAvgEscapeProbability(int day, double prob) {
		avgEscapeProbabilityByDay.put(day, prob);
	}

	public void calculateCumulativeProbabilities() {
		double currentCumulativeProbability = 1.0;
		for (Map.Entry<Integer, Double> dailyAvg : avgEscapeProbabilityByDay.entrySet()) {
			currentCumulativeProbability *= dailyAvg.getValue();
			cumEscapeProbabilityByDay.put(dailyAvg.getKey(), currentCumulativeProbability);
		}
	}

	public String summarize() {
		calculateCumulativeProbabilities();
		String result = "Outbreak Location\tDay\tCumulative Escape Probability\n";
		for (Map.Entry<Integer, Double> dailyAvg : avgEscapeProbabilityByDay.entrySet()) {
			int day = dailyAvg.getKey();
			result += this.flyReleaseInfo + "\t";
			result += Integer.toString(day) + "\t";
			result += Double.toString(cumEscapeProbabilityByDay.get(day)) + "\n";
		}
		result += "##################################################################\n";
		return result;
	}

	public String rawDataToString() {
		String result = "[Day\tReleasePoint\tFlyLocation\tP(escape)]\n";
		for (String[] line : rawData) {
			result += Arrays.toString(line) + "\n";
		}
		return result;
	}

	public void addFlyReleaseInfo(String info) {
		flyReleaseInfo = info;
	}
	
	

}
