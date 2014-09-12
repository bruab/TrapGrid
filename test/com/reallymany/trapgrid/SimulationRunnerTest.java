package com.reallymany.trapgrid;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class SimulationRunnerTest {
	TrapGrid mockTG;
	Random rand;
	SimulationRunner testSimRunner1, testSimRunner2, testSimRunner3;
	Simulation mockSim1;
	int numFlies = 100;
	int numDays = 10;
	double diffCoeff = 30;
	double stepSize = 1.0;
	int stepsPerDay = 10;
	double turnAngleStdev = 40.0;
	boolean useMDD = false;
	
	// Outbreak requires: double x, double y, int n, double diffCoeff, long seed
	// simrunner requires TrapGrid tg, int numDays, int numFlies, double diffC, long seed, int numSims
	@Before
	public void setUp() throws Exception {
		rand = new Random();
		long seed = rand.nextLong();
		mockTG = mock(TrapGrid.class);
		testSimRunner1 = new SimulationRunner(mockTG, numDays, numFlies, diffCoeff, 
				stepSize, stepsPerDay, turnAngleStdev, useMDD, seed, 5);
		mockSim1 = mock(Simulation.class);
		testSimRunner2 = spy(new SimulationRunner(mockTG, numDays, numFlies, diffCoeff, 
				stepSize, stepsPerDay, turnAngleStdev, useMDD, seed, 2));
		doReturn(mockSim1)
			.when(testSimRunner2)
			.createSimulation( any( TrapGrid.class), any ( Outbreak.class));
		SimulationResultsHolder mockSimResults = mock(SimulationResultsHolder.class);
		doReturn(mockSimResults)
			.when(mockSim1)
			.runSimulation();
		String outbreakFile = "foo.outbreak";
		testSimRunner3 = new SimulationRunner(mockTG, outbreakFile, numDays, numFlies, diffCoeff, 
				stepSize, stepsPerDay, turnAngleStdev, useMDD, seed);
	}

	@Test
	public void testSimulationRunner() {
		assertTrue(testSimRunner1 instanceof SimulationRunner);
		assertTrue(testSimRunner1.tg instanceof TrapGrid);
		assertEquals(10, testSimRunner1.numberOfDays);
		assertTrue(testSimRunner1.rng instanceof Random);
		assertTrue(testSimRunner1.allResults instanceof ArrayList);
		assertFalse(testSimRunner1.outbreakLocationsProvided);
	}
	
	@Test
	public void testSimulationRunnerWithOutbreakLocations() {
		assertTrue(testSimRunner3 instanceof SimulationRunner);
		assertTrue(testSimRunner3.outbreakLocationsProvided);
	}
	
	@Test
	public void testCreateOutbreaksFromFile() throws IOException {
		ArrayList<Outbreak> outbreaks;
		outbreaks = testSimRunner3.createOutbreaksFromFile("test/com/reallymany/trapgrid/test_files/test_outbreaks.tsv");
		assertEquals(2, outbreaks.size());
		assertEquals(33.3, outbreaks.get(0).allOutbreakLocations.get(0).location.getY(), 0.01);
	}
	
	@Test
	public void testRunSimulations() {
		assertEquals(0, testSimRunner2.allResults.size());
		testSimRunner2.runSimulations();
		assertEquals(2, testSimRunner2.allResults.size());
	}
	
	

}
