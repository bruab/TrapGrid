package com.reallymany.trapgrid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SimulationResultsHolderTest {
	SimulationResultsHolder testHolder1, testHolder2;

	@Before
	public void setUp() throws Exception {
		testHolder1 = new SimulationResultsHolder();
		testHolder2 = new SimulationResultsHolder();
		String[] line1 = {"1", "(0.0, 0.0)", "(1.0, -1.0)", "0.01"};
		String[] line2 = {"2", "(0.0, 0.0)", "(2.0, -3.0)", "0.005"};
		testHolder2.addRawData(line1);
		testHolder2.addRawData(line2);
		testHolder2.addAvgEscapeProbability(1, 0.01);
		testHolder2.addAvgEscapeProbability(2, 0.005);
		testHolder2.calculateCumulativeProbabilities();
	}

	@Test
	public void testSimulationResultsHolder() {
		assertTrue(testHolder1 instanceof SimulationResultsHolder);
		assertTrue(testHolder1.flyReleaseInfo instanceof String);
		assertTrue(testHolder1.rawData instanceof ArrayList);
		assertTrue(testHolder1.cumEscapeProbabilityByDay instanceof Map);
		assertTrue(testHolder1.avgEscapeProbabilityByDay instanceof Map);
	}
	
	@Test
	public void testAddRawData() {
		assertEquals(0, testHolder1.rawData.size());
		String[] testData = {"1", "(5.0, 7.0)", "(6.0, 9.0)", "0.001"};
		testHolder1.addRawData(testData);
		assertEquals(1, testHolder1.rawData.size());
		assertEquals("0.001", testHolder1.rawData.get(0)[3]);
	}
	
	@Test
	public void testAddAvgEscapeProbability() {
		assertEquals(0, testHolder1.avgEscapeProbabilityByDay.size());
		testHolder1.addAvgEscapeProbability(1, 0.002);
		assertEquals(1, testHolder1.avgEscapeProbabilityByDay.size());
	}
	
	@Test
	public void testAddFlyReleaseInfo() {
		assertEquals("", testHolder1.flyReleaseInfo);
		testHolder1.addFlyReleaseInfo("Outbreak foo");
		assertEquals("Outbreak foo", testHolder1.flyReleaseInfo);
	}
	
	@Test
	public void testCalculateCumulativeProbabilities() {
		assertEquals(0,  testHolder1.cumEscapeProbabilityByDay.size());
		testHolder1.avgEscapeProbabilityByDay.put(1, 0.1);
		testHolder1.calculateCumulativeProbabilities();
		assertEquals(1,  testHolder1.cumEscapeProbabilityByDay.size());
		assertEquals(0.1, testHolder1.cumEscapeProbabilityByDay.get(1), 0.0001);
		testHolder1.avgEscapeProbabilityByDay.put(2, 0.2);
		testHolder1.calculateCumulativeProbabilities();
		assertEquals(0.02, testHolder1.cumEscapeProbabilityByDay.get(2), 0.0001);
	}
	
	@Test
	public void testSummarize() {
		String expected = "Outbreak Location\tDay\tCumulative Escape Probability\n";
		expected += "\t1\t0.01\n";
		expected += "\t2\t5.0E-5\n";
		expected += "##################################################################\n";
		assertEquals(expected, testHolder2.summarize());
	}
	
	@Test
	public void testSummarizeWithFlyRelaseInfo() {
		testHolder2.addFlyReleaseInfo("Outbreak foo");
		String expected = "Outbreak Location\tDay\tCumulative Escape Probability\n";
		expected += "Outbreak foo\t1\t0.01\n";
		expected += "Outbreak foo\t2\t5.0E-5\n";
		expected += "##################################################################\n";
		assertEquals(expected, testHolder2.summarize());
	}
	
	@Test
	public void testRawDataToString() {
		String expected = "[Day\tReleasePoint\tFlyLocation\tP(escape)]\n";
		expected += "[1, (0.0, 0.0), (1.0, -1.0), 0.01]\n";
		expected += "[2, (0.0, 0.0), (2.0, -3.0), 0.005]\n";
		assertEquals(expected, testHolder2.rawDataToString());
	}

}
