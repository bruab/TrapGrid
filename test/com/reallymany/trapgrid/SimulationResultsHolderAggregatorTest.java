package com.reallymany.trapgrid;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SimulationResultsHolderAggregatorTest {
	SimulationResultsHolderAggregator simResHolAgg;

	@Before
	public void setUp() throws Exception {
		// First make results holders
		SimulationResultsHolder testHolder1, testHolder2;
		testHolder1 = new SimulationResultsHolder();
		testHolder2 = new SimulationResultsHolder();
		
		// testHolder1 will have final cumulative escape probability 0.05
		testHolder1.addAvgEscapeProbability(1, 0.1);
		testHolder1.addAvgEscapeProbability(2, 0.5);
		
		// testHolder2 will have final cumulative escape probability of 0.01
		testHolder2.addAvgEscapeProbability(1, 0.1);
		testHolder2.addAvgEscapeProbability(2, 0.1);
		
		ArrayList<SimulationResultsHolder> holders = new ArrayList<SimulationResultsHolder>();
		holders.add(testHolder1);
		holders.add(testHolder2);
		// Now make results holder aggregator!
		simResHolAgg = new SimulationResultsHolderAggregator(holders);
	}
	
	@Test
	public void testCalculateAvgCumProb() {
		double expected1 = 0.1;
		double expected2 = 0.03;
		double actual1 = simResHolAgg.calculateAvgCumProb(1);
		double actual2 = simResHolAgg.calculateAvgCumProb(2);
		assertEquals(expected1, actual1, 0.0001);
		assertEquals(expected2, actual2, 0.0001);
	}

	@Test
	public void testAggregateSimulationResultsHolders() {
		// Avg final cumulative esc prob = avg(0.05, 0.01) = 0.03
		String expected = "Day\tAv Cumulative Escape Probability\n";
		expected += "1\t0.1\n";
		expected += "2\t0.030000000000000002\n"; // could format prettily but not gonna
		String actual = simResHolAgg.aggregrateSimulationResultsHolders();
		assertEquals(expected, actual);
	}

}
