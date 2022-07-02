package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RevAcctTest {
	
	Scenario s;
	RevAcct a;

	@BeforeEach
	void setUp() throws Exception {
		s = new Scenario(2022, "fp");
		File f = new File("test.csv");
		s.upload(f);
		a = s.revAccts.get(6195);
	}

	@Test
	void testGetClientRev() {
		
		// test set up
		HashMap<String, BigDecimal[]> clientItem = new HashMap<String, BigDecimal[]>();
		
		double[] amt = {100.00, 100.00, 100.00, 100.00, 100.00, 100.00, 100.00, 100.00, 100.00, 100.00, 100.00, 100.00, 1200.00};
		BigDecimal[] amounts = new BigDecimal[13];
		
		for (int i = 0; i < 13; i++) {
			amounts[i] = new BigDecimal(amt[i]).setScale(2, RoundingMode.HALF_UP);
		}
		
		clientItem.put("bmw", amounts);
		
		// expect value
		HashMap<String, BigDecimal[]> expect = new HashMap<String, BigDecimal[]>();
		
		double[] amt2 = {90.00, 90.00, 90.00, 90.00, 90.00, 90.00, 90.00, 90.00, 90.00, 90.00, 90.00, 90.00, 1080.00};
		BigDecimal[] amounts2 = new BigDecimal[13];
		
		for (int i = 0; i < 13; i++) {
			amounts2[i] = new BigDecimal(amt2[i]).setScale(2, RoundingMode.HALF_UP);
		}
		
		expect.put("bmw", amounts2);
		
		// test
		assertArrayEquals(expect.values().toArray(), a.getClientRev("bmw", clientItem).values().toArray());
		
	}

}
