package BackEnd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;

public class Client {
	
	/*
	 *  variables
	 */
	
	// client name
	public String name;

	// hash table - key is the media type, value is the int[12] for monthly revenue amount
	public Hashtable<String, BigDecimal[]> amounts;
	
	
	/*
	 * Constructor
	 */
	public Client(String name) {
		
		this.name = name.toLowerCase();

		this.amounts = new Hashtable<String, BigDecimal[]>();
		
		// initiate amounts variable with medias and 0 monthly amount
		for (String media: BudgetSystem.mediaTypes) {
			
			BigDecimal[] amt = new BigDecimal[13];

			for (int i = 0; i < 13; i++) {
				amt[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			}
			
			this.amounts.put(media, amt);
		}
	}

}
