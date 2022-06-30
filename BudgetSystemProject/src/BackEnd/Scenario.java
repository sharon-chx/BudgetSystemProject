package BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
		this.revAccts = new Hashtable<Integer, RevAcct>();
		this.expAccts = new Hashtable<Integer, ExpAcct>();
		
		// add in default rev and exp accts
		for (Integer acct: BudgetSystem.defaultRevAccts) {
			this.revAccts.put(acct, new RevAcct(acct));
		}
		
		for (Integer acct: BudgetSystem.defaultExpAccts) {
			this.expAccts.put(acct, new ExpAcct(acct));
		}
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
		
		clientName = clientName.toLowerCase();
		media = media.toLowerCase();
		
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
	
	
	/*
	 * read one row of given buffered reader
	 */
	public String[] readRevRow(BufferedReader reader) throws IOException {
		
		ArrayList<String> line = new ArrayList<>();
		String word = "";
		int charInt;
		int commaCount = 0;
		
		// read the first char, return null if empty
    	charInt = reader.read();
    	if (charInt == -1) return null;
    	
		while (charInt != 10) {
			switch(charInt) {
			
			// if char is "\r", indicating end of line
			case 13:
				if (word != "") line.add(word.toLowerCase());
				else if (commaCount > 1 && commaCount < 4) line.add(null);
				else line.add("0");	
				charInt = reader.read();
				break;
			
			// if char is ",", indicating end of the word, so add the word to array list, and reset word variable
			case 44:
				commaCount ++;
				if (word != "") {
					line.add(word.toLowerCase());
					word = "";
				}else {
					// if missing value is in first 3 column, add in null value
					if (commaCount > 1 && commaCount < 4) line.add(null);
					// otherwise, add in 0 indicating zero amount
					else line.add("0");
				}
					
				charInt = reader.read();
				break;
				
			// other cases: valid character, add to the word, then read the next char
			default :
				word = word + (char) charInt;
				charInt = reader.read();
				break;
			}
		}
		
		
		// check if missing acct number, since above won't catch the case when
		// first column is empty
		try{
			Integer.parseInt(line.get(0));
		}catch (Exception e) {
			line.add(0, "0");
		}
		
		// convert ArrayList to Array and return
        return line.toArray(new String[0]);
	}
	


	/*
	 * Read the given File and update the acct info. 
	 * Return the row numbers that error incurred
	 * If no error, return empty list
	 */
	public int[] upload(File filePath) {
		
		FileReader fr;
		BufferedReader br;
		
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			
			// skip the first line - the column name, and start to read the second line
			br.readLine();
			
			while (br != null) {
				
				String[] line = this.readRevRow(br);
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
					continue;
				}
				
			}
			
			
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	

}
