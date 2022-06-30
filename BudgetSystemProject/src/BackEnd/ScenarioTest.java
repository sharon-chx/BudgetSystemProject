package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScenarioTest {
	
	Scenario s1;
	Scenario s2;

	@BeforeEach
	void setUp() throws Exception {
		s1 = new Scenario(2022, "FP");
		s2 = new Scenario(2022, "BUD");
	}

	@Test
	void testUpdateRevAcct() {
		
		double[] amt = {1.01, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 78.01};
		BigDecimal[] amounts = new BigDecimal[13];
		
		for (int i = 0; i < 13; i++) {
			amounts[i] = new BigDecimal(amt[i]).setScale(2, RoundingMode.HALF_UP);
		}
		
		// test for invalid inputs
		assertFalse(s1.updateRevAcct(0, "ALDI", "DISPLAY", amounts));
		assertFalse(s1.updateRevAcct(7000, "ALDI", "DISPLAY", amounts));
		assertFalse(s1.updateRevAcct(6000, null, "DISPLAY", amounts));
		assertFalse(s1.updateRevAcct(6000, "ALDI", null, amounts));
		assertFalse(s1.updateRevAcct(6000, "ALDI", "DISPLAY", null));
		
		// test
		assertTrue(s1.updateRevAcct(6000, "ALDI", "DISPLAY", amounts));
		System.out.println(s1.revAccts.get(6000).clients.get("ALDI").printAmt());
		System.out.println(s2.revAccts.get(6000).clients.get("ALDI").printAmt());
	}

}
