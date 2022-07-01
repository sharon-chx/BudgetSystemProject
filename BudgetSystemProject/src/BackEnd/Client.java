package BackEnd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;
import java.util.Map.Entry;

public class Client {
	
	/*
	 *  variables
	 */
	
	// client name
	public String name;

	// hash table - key is the media type, value is the int[13] for monthly revenue amount
	public Hashtable<String, BigDecimal[]> amounts;
	
	// list of totals of the Client
	public BigDecimal[] totals;
	
	
	/*
	 * Constructor
	 */
	public Client(String name) {
		
		this.name = name.toLowerCase();

		this.amounts = new Hashtable<String, BigDecimal[]>();
		
		this.totals = new BigDecimal[13];
		
		// initiate amounts variable with medias and 0 monthly amount
		for (String media: BudgetSystem.mediaTypes) {
			
			BigDecimal[] amt = new BigDecimal[13];

			for (int i = 0; i < 13; i++) {
				amt[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			}
			
			this.amounts.put(media, amt);
		}
		
	}


	/*
	 * Methods
	 */
	
	/*
	 * Update rev of given media type by given amounts (BigDecimal[13])
	 */
	public boolean updateMedia(String media, BigDecimal[] amounts) {
		
		if (media == null || amounts == null) return false;
		
		media = media.toLowerCase();
		
		this.amounts.put(media, amounts);
		
		this.calculateTotal();
		
		return true;
		
	}
	
	
	/*
	 * Print amounts of the client by media
	 */
	
	public String printAmt() {
		
		String result = "";
		
		for (Entry<String, BigDecimal[]> entry: this.amounts.entrySet()) {

			result = result + entry.getKey() + " ";
			
			for (int i = 0; i < 13; i++) {
				result = result + entry.getValue()[i].toString() + " ";
			}
			
			// a new line
			result = result + "\n";
		}
		
		return result;
	}


	/*
	 * Calculate the total of the client
	 */
	public void calculateTotal() {
		
		for (int i = 0; i < 13; i++) {
			
			// initiate the totals
			totals[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			
			for (BigDecimal[] amt: amounts.values()) {
				if (amt[i] == null) amt[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
					totals[i] = totals[i].add(amt[i]);
			}
		}
		
	}
	


}
