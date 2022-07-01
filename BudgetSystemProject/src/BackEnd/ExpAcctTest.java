package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ExpAcctTest {
	
	ExpAcct ea;

	@BeforeEach
	void setUp() throws Exception {
		ea = new ExpAcct(7000);
	}

	@Test
	void testUpdate() {
		
		double[] amt = {1.01, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 78.01};
		BigDecimal[] amounts = new BigDecimal[13];
		
		for (int i = 0; i < 13; i++) {
			amounts[i] = new BigDecimal(amt[i]).setScale(2, RoundingMode.HALF_UP);
		}
		
		ea.update("base salary", amounts);
		
		System.out.println(ea.printAmt());
	}

}
