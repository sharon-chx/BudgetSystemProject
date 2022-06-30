package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BudgetSystemTest {

	BudgetSystem bs;

	@BeforeEach
	void setUp() throws Exception {
		bs = new BudgetSystem();
	}

	@Test
	void testAddScenario() {
		bs.addYear(2023);
		assertFalse(bs.addScenario(null));
		assertFalse(bs.addScenario("bud"));
		assertFalse(bs.addScenario("what"));
		assertTrue(bs.addScenario("4RE"));
		System.out.println("Test Add Scenario");
		System.out.println(bs.scenarios);
		System.out.println(bs.scenarioMapping);
		System.out.println();
	}
	
	@Test
	void testAddYear() {
		assertFalse(bs.addYear(2020));
		assertTrue(bs.addYear(2024));
		System.out.println("Test Add Year");
		System.out.println(bs.scenarios);
		System.out.println();
	}

	@Test
	void testGetScenario() {
		assertNull(bs.getScenario(2024, "FP"));
		assertNull(bs.getScenario(2022, "what"));
		assertNull(bs.getScenario(2022, null));
		assertNull(bs.getScenario(2020, "FP"));
		assertNull(bs.getScenario(2022, "4RE"));
		Scenario s = bs.getScenario(2022, "BUD");
		System.out.println("Test Get Scenario");
		System.out.println(s);
		System.out.println();
	}
}
