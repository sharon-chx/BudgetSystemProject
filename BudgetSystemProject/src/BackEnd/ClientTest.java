package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest {
	
	BudgetSystem bs;
	Client client;

	@BeforeEach
	void setUp() throws Exception {
		
		bs = new BudgetSystem();
		client = new Client("Aldi");

	}

	@Test
	void testUpdateMedia() {
		
		// default amount before update
		System.out.println(client.printAmt());
		
		// test 1: update existing media type
		double[] amt = {1.01, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 78.01};
		BigDecimal[] amounts = new BigDecimal[13];
		
		for (int i = 0; i < 13; i++) {
			amounts[i] = new BigDecimal(amt[i]).setScale(2, RoundingMode.HALF_UP);
		}
		
		client.updateMedia("DISPLAY", amounts);
		
		System.out.println(client.printAmt());
		
		// add and update new media type
		double[] amt2 = {1.01, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 78.01};
		BigDecimal[] amounts2 = new BigDecimal[13];
		
		for (int i = 0; i < 13; i++) {
			amounts2[i] = new BigDecimal(amt2[i]).setScale(2, RoundingMode.HALF_UP);
		}
		
		client.updateMedia("SERACH", amounts);
		
		System.out.println(client.printAmt());
		
	}

}
