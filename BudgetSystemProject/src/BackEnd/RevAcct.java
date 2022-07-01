package BackEnd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;

public class RevAcct {
	
	/*
	 * variables
	 */
	// account number
	public int number;
	
	// monthly amounts
	public BigDecimal[] amounts;
	
	// hash table of Clients
	public Hashtable<String, Client> clients;
	
	
	/*
	 * constructor
	 */
	public RevAcct(int number) {
		
		this.number = number;
		this.amounts = new BigDecimal[13];
		this.clients = new Hashtable<String, Client>();
		
		// initiate amounts with 0; 12 months plus total at last column
		for (int i = 0; i < 13; i++) {
			amounts[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}
	}
	
	/*
	 * Methods
	 */
	
	/*
	 * Calculate the total of the client
	 */
	public void calculateTotal() {
		
		for (int i = 0; i < 13; i++) {
			
			// initiate the totals to 0
			amounts[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			
			for (Client c: clients.values()) {
				amounts[i] = amounts[i].add(c.totals[i]);
			}
		}
	}

}
