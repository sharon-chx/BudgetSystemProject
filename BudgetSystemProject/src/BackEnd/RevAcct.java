package BackEnd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
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

	/*
	 * Update the given HashMap based on the rev of given client
	 */
	public HashMap<String, BigDecimal[]> getClientRev(String client, HashMap<String, BigDecimal[]> clientItem) {
		
		if (client == null) return null;
		
		client = client.toLowerCase();
		
		if (clients.containsKey(client)) {
			
			BigDecimal[] values = clients.get(client).totals;
			
			// if client in hash map, and sum the values
			if (clientItem.containsKey(client)) {

				for (int i=0; i<13; i++) {
					values[i] = values[i].add(clientItem.get(client)[i]);
				}
				
			}
			
			clientItem.put(client, values);
		}
		
		return clientItem;
	}

}
