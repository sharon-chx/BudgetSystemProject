package BackEnd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;


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
		
		// recalculate totals of the Rev Acct
		acct.calculateTotal();
		
		return true;	
	}
	
	
	/*
	 * read one row of given buffered reader
	 */
	public String[] readRow(BufferedReader reader) throws IOException {
		
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
	public String upload(File filePath) {
		
		FileReader fr;
		BufferedReader br;
		int rowCount = 0;
		ArrayList<Integer> errorRows = new ArrayList<Integer>();
		
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			
			// skip the first line - the column name, and start to read the second line
			br.readLine();
			rowCount++;
			
			// loop till reach end of file
			while (br.ready()) {
				
				String[] line = this.readRow(br);
				rowCount++;
				int account = Integer.parseInt(line[0]);
				
				// if it's a rev acct
				if (account < 7000 && account >= 600) {
					
					BigDecimal[] amts = new BigDecimal[13];
					BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
					for (int i=0; i<12; i++) {
						amts[i] = new BigDecimal(line[i+3]).setScale(2, RoundingMode.HALF_UP);
						total = total.add(amts[i]);
					}
					amts[12] = total;
					
					if (line[1] == null || line[2] == null) {
						errorRows.add(rowCount);
					}else {
						updateRevAcct(account, line[1], line[2], amts);	
					}
				}
				
				// if it's a expense acct
				else if (account >= 7000 && account < 8000){
					
					BigDecimal[] amts = new BigDecimal[13];
					BigDecimal total = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
					for (int i=0; i<12; i++) {
						
						if (line[i+2] == null) line[i+2] = "0";
						
						amts[i] = new BigDecimal(line[i+2]).setScale(2, RoundingMode.HALF_UP);
						total = total.add(amts[i]);
					}
					amts[12] = total;
					
					if (line[1] == null) {
						errorRows.add(rowCount);
					}else {	
						updateExpAcct(account, line[1], amts);	
					}
				}
				
				else errorRows.add(rowCount);
			}	
		} catch (Exception e) {
			System.out.println(e);
			return "Something went wrong when try opening the file";
		}
		
		String result = "";
		for (int i : errorRows) {
			result = result + i + " ";
		}
		
		return result;
	}


	/*
	 * Update the Exp Acct by given acct number, note, and monthly amts
	 */
	public boolean updateExpAcct(int account, String note, BigDecimal[] amounts) {
		
		// check invalid inputs
		if ( note == null || amounts == null || account >= 8000 || account <7000)
			return false;
		
		note = note.toLowerCase();
		
		// if acct not exist, add acct
		if (!this.expAccts.containsKey(account)) this.expAccts.put(account, new ExpAcct(account)); 
		
		// Get the rev acct
		ExpAcct acct = this.expAccts.get(account);
		
		// Update the exp acct
		acct.update(note, amounts);
		
		// recalculate totals of the Rev Acct
		acct.calculateTotal();
		
		return true;
		
	}
	
	
	/*
	 * Get the full client list
	 */
	public HashSet<String> getClients(){
		
		HashSet<String> clients = new HashSet<String>();
		
		for (RevAcct revAcct : this.revAccts.values()) {
			clients.addAll(revAcct.clients.keySet());
		}
		
		return clients;
	}


	/*
	 * Get the monthly rev of given list of clients
	 * Return nested list of String
	 */
	public String[][] getClientsRev(String[] clients) {
		
		String[][] result = new String[clients.length+1][14];
		
		HashMap<String, BigDecimal[]> clientItem = new HashMap<String, BigDecimal[]>();
		
		// update hashmap for given clients and its value in different accounts
		for (RevAcct revAcct: this.revAccts.values()) {
			for (String clientName: clients) {
				clientItem = revAcct.getClientRev(clientName, clientItem);
			}
			
		}
		
		// intitate the variable to store clients rev total
		BigDecimal[] total = new BigDecimal[13];
		for (int j = 0; j < 13; j++) {
			total[j] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}
		
		int count = 0;
		
		// convert hashmap to String[][] and sum the total
		for (Entry <String, BigDecimal[]> entry: clientItem.entrySet()) {
			
			String[] line = new String[14];
			line[0] = entry.getKey();
			
			for (int j = 0; j < 13; j++) {
				BigDecimal num = entry.getValue()[j];
				total[j] = total[j].add(num);
				line[j+1] = num.toString();
			}
			
			result[count] = line;
			count++;
		}
		
		// convert total to Sting[]
		String[] totalToString = new String[14];
		totalToString[0] = "total rev:";
		for (int j = 0; j < 13; j++) {
			totalToString[j+1] = total[j].toString();
		}
		
		result[clients.length] = totalToString;
		
		return result;
	}
	
	/*
	 * Write the givein nested String list to a file in ordert to export
	 */
	public boolean exportCSV(String[][] data, String type) {
		
		try {
			File f = new File("export.csv");
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			
			int columnLen = data[0].length;
			
			// write the Scenario on the first row
			bw.write(year + " " + name);
			bw.write("\n");
			
			String[] colName = { "Client Name", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Total" };
			
			// change column name for rev by media
			if (type == "media") {
				colName[0] = "Media Type";
			}	
			// change column name for rev by acct or for p&l
			else if (type == "acct" || type == "p&l") {
				colName[0] = "Account Number";
			}
			// change column name for expense by notes
			else if (type == "note") {
				colName[0] = "Notes";
			}
			
			for (int i=0; i<colName.length; i++) {
				bw.write(colName[i]);
				bw.write(",");
			}
			bw.write("\n");
			
			// write data in following row
			for (String[] line: data) {
				for (int j=0; j<columnLen; j++) {
					bw.write(line[j]);
					bw.write(",");
				}
				bw.write("\n");
			}
			
			bw.flush();
			fw.close();
			bw.close();
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/*
	 * Get rev by medias for given client
	 */
	public String[][] getMediaRev(String client) {
		
		String[][] result = new String[BudgetSystem.mediaTypes.size()+1][14];
		
		HashMap<String, BigDecimal[]> mediaItem = new HashMap<String, BigDecimal[]>();
		
		// update hashmap for medias and their value in different accounts
		for (RevAcct revAcct: this.revAccts.values()) {
			
			// if the rev acct contains the Client
			if (revAcct.clients.containsKey(client)) {
				
				Client c = revAcct.clients.get(client);
				
				for (String media: BudgetSystem.mediaTypes) {
					mediaItem = c.getMediaMonthTotal(media, mediaItem);
				}
			}
			
		}
		
		// initiate the variable to store clients rev total
		BigDecimal[] total = new BigDecimal[13];
		for (int j = 0; j < 13; j++) {
			total[j] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}
		
		int count = 0;
		
		// convert hashmap to String[][] and sum the total
		for (Entry <String, BigDecimal[]> entry: mediaItem.entrySet()) {
			
			String[] line = new String[14];
			line[0] = entry.getKey();
			
			for (int j = 0; j < 13; j++) {
				BigDecimal num = entry.getValue()[j];
				total[j] = total[j].add(num);
				line[j+1] = num.toString();
			}
			
			result[count] = line;
			count++;
		}
		
		// convert total to Sting[]
		String[] totalToString = new String[14];
		totalToString[0] = "total rev:";
		for (int j = 0; j < 13; j++) {
			totalToString[j+1] = total[j].toString();
		}
		
		result[BudgetSystem.mediaTypes.size()] = totalToString;
		
		return result;
	
	}


	/*
	 * Get rev by accounts for given client
	 */
	public String[][] getAcctRev(String client) {
		
		HashMap<Integer, BigDecimal[]> acctItem = new HashMap<Integer, BigDecimal[]>();
		
		// update hashmap for account and their value in different accounts
		for (RevAcct revAcct: this.revAccts.values()) {
			
			// if the rev acct contains the Client
			if (revAcct.clients.containsKey(client)) {
				
				Client c = revAcct.clients.get(client);
				
				acctItem.put(revAcct.number, c.totals);
			}
			
		}
		
		// initiate the variable to store clients rev total
		BigDecimal[] total = new BigDecimal[13];
		for (int j = 0; j < 13; j++) {
			total[j] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}
		
		int count = 0;
		String[][] result = new String[acctItem.size()+1][14];
		
		// convert hashmap to String[][] and sum the total
		for (Entry <Integer, BigDecimal[]> entry: acctItem.entrySet()) {
			
			String[] line = new String[14];
			line[0] = entry.getKey().toString();
			
			for (int j = 0; j < 13; j++) {
				BigDecimal num = entry.getValue()[j];
				total[j] = total[j].add(num);
				line[j+1] = num.toString();
			}
			
			result[count] = line;
			count++;
		}
		
		// convert total to Sting[]
		String[] totalToString = new String[14];
		totalToString[0] = "total rev:";
		for (int j = 0; j < 13; j++) {
			totalToString[j+1] = total[j].toString();
		}
		
		result[count] = totalToString;
		
		return result;
		
	}
}
