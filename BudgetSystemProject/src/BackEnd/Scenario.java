package BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Hashtable;


public class Scenario {
	
	/*
	 * variables
	 */
	// name of the Scenario
	public String name;
	
	// year of the Scenario
	public int year;
	
	// Hash table of account number and accounts (rev accounts and expense accounts)
	public Hashtable<Integer, RevAcct> revAccts;
	
	public Hashtable<Integer, ExpAcct> expAccts;
	
	/*
	 * constructor
	 */
	public Scenario(int year, String name) {
		this.year = year;
		this.name = name.toLowerCase();
		this.revAccts = BudgetSystem.defaultRevAccts;
		this.expAccts = BudgetSystem.defaultExpAccts;
	}
	
	
	/*
	 * methods
	 */
	
	
	@Override
	public boolean equals(Object object) {
		Scenario other = (Scenario) object;
		return (other.year == year) && (other.name.equals(name));
	}
	
	@Override
	public String toString() {
		return this.year + " " +this.name;
	}
	
	
	/*
	 * update the Rev Acct by given account number, client name, media type, amounts
	 */
	public boolean updateRevAcct(int account, String clientName, String media, BigDecimal[] amounts) {
		
		// check invalid inputs
		if (clientName == null || media == null || amounts == null || account <6000 || account >=7000)
			return false;
		
		// if acct not exist, add acct
		if (!this.revAccts.containsKey(account)) this.revAccts.put(account, new RevAcct(account)); 
		
		// Get the rev acct
		RevAcct acct = this.revAccts.get(account);
		
		// if client not exists, add client with amts
		if (!acct.clients.containsKey(clientName)) acct.clients.put(clientName, new Client(clientName));
		
		// Get the Client
		Client client = acct.clients.get(clientName);
		
		// otherwise, update info
		client.updateMedia(media, amounts);
		
		return true;
		
		
	}
	


	public boolean upload(File filePath) {
		boolean result = false;
		
		FileReader fr;
		BufferedReader br;
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			
			// skip the first line - the column name, and start to read the second line
			br.readLine();
			String buffer = br.readLine();
			
			while (buffer != null) {
				
				buffer = buffer.toLowerCase();
				
				String[] line = buffer.split(",");
				int account = Integer.parseInt(line[0]);
				
				BigDecimal[] amts = new BigDecimal[13];
				BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
				for (int i=0; i<12; i++) {
					amts[i] = new BigDecimal(line[i+3]).setScale(2, RoundingMode.HALF_UP);
					total = total.add(amts[i]);
				}
				amts[12] = total;
				
				// if it's a rev acct
				if (account < 7000 ) {
					updateRevAcct(account, line[1], line[2], amts);	
				}
				
				// if it's a expense acct
				else {
					
				}
				
				// read the next line
				buffer = br.readLine();
				
			}
			
			
			
		} catch (Exception e) {
			return result;
		}
		
		return result;
	}
	
	

}
