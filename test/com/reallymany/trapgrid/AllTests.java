package com.reallymany.trapgrid;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TrapGridTest.class, TrapTest.class, EsotericMathTest.class, OutbreakLocationTest.class, OutbreakTest.class,
				SimulationTest.class, SimulationRunnerTest.class, SimulationResultsHolderTest.class })
public class AllTests {

}
