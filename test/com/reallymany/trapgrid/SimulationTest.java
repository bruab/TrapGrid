package com.reallymany.trapgrid;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SimulationTest {
	Simulation testSim1;
	TrapGrid mockTG;
	Outbreak mockFR;
	

	@Before
	public void setUp() throws Exception {
		mockTG = mock(TrapGrid.class);
		mockFR = mock(Outbreak.class);
		when(mockFR.toString()).thenReturn("(Outbreak.toString goes here)");
		testSim1 = new Simulation(mockTG, mockFR, 10);
	}

	@Test
	public void testSimulation() {
		assertTrue(testSim1 instanceof Simulation);
	}

	
}
