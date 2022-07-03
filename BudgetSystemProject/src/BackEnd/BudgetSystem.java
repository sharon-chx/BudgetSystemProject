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
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.TreeMap;



public class BudgetSystem {
	
	/*
	 * variables
	 */
	
	// linked hash map (preserve the insertion order) to store version and corresponding column index of it
	public LinkedHashMap <String, Integer> scenarioMapping;
	
	// static variable to store the start year, row index of later year can be calculated by subtraction
	public static int startYear= 2022;
	
	// two dimension Array List to store versions: row is year and column is scenario
	public ArrayList<ArrayList<Scenario>> scenarios;
	
	// hash set of media types
	public static HashSet<String> mediaTypes = new HashSet<String>();
	
	// hash table of default Rev Accounts
	public static HashSet <Integer> defaultRevAccts = new HashSet<Integer>();
	
	// hash table of default Exp Accounts
	public static HashSet <Integer> defaultExpAccts = new HashSet <Integer>();
	
	// set up static variables default accounts and categories
    static {
        
        // set up default accounts
        defaultRevAccts.add(6000);   // rev account for disclosed clients
        defaultRevAccts.add(6195);	// rev account for non-disclosed clients
        defaultExpAccts.add(7000);  // base salary account
        defaultExpAccts.add(7150);	// benefit account
        defaultExpAccts.add(7240);	// Lodge expense account
        defaultExpAccts.add(7350);	// Airfare expense account
        
        // set up media types
        mediaTypes.add("display");
        mediaTypes.add("video");
        mediaTypes.add("social");
        
      }
	

	/*
	 *  constructor
	 */
	public BudgetSystem() {
		this.scenarioMapping = new LinkedHashMap<String, Integer>();
		this.scenarios = new ArrayList<ArrayList<Scenario>>();
		this.addYear(BudgetSystem.startYear);
		this.addScenario("FP");
		this.addScenario("BUD");
		this.addScenario("1RE");
		this.addScenario("ACT");
	}

	
	/*
	 * Methods
	 */
	
	/*
	 * Add given new year to scenarios variable
	 */
	public boolean addYear(int year) {
		
		// check if valid year
		if (year < BudgetSystem.startYear) return false;
		
		// check how many years before the given year is missing
		int end = year - BudgetSystem.startYear;  
		int start = this.scenarios.size(); 
		
		// add in new row for all missing years and add in corresponding scenarios
		for (int i = start; i <= end; i++) {
			scenarios.add(new ArrayList<Scenario>());
			for (String scenario: scenarioMapping.keySet()) {
				scenarios.get(i).add(new Scenario(BudgetSystem.startYear + i, scenario));
			}
		}
		
		return true;
		
	}

	/*
	 * Add given new scenario to scenarios and scenarioMapping variable
	 */
	public boolean addScenario(String scenario) {

		// if scenario is null value or already exists, return false
		if (scenario == null || scenario.length() > 3) return false;
		
		scenario = scenario.toLowerCase();
		
		if (scenarioMapping.containsKey(scenario)) return false;
				
		// add to scenarioMapping, using the scenarioMapping's size as the value (column index of scenario in scenarios
		scenarioMapping.put(scenario, scenarioMapping.size());
		
		// loop through each row of scenarios and add the new Scenario
		for (int i = 0; i < scenarios.size(); i++) {
			scenarios.get(i).add(new Scenario(BudgetSystem.startYear + i, scenario));
		}
		
		return true;
	}
	
	
	/*
	 * Get the index of given year and scenario
	 */
	public int[] getIndex(int year, String scenario) {
		
		// check if both given year and scenario is valid
		if (scenario == null || scenario.length() > 3 || scenarios.size() <= year - BudgetSystem.startYear - 1 
				|| !scenarioMapping.containsKey(scenario.toLowerCase())) 
			return null;
		
		scenario = scenario.toLowerCase();
		
		// get the row and column index by given year and scenario
		int[] index = new int[2];
		index[0] = year - BudgetSystem.startYear;
		index[1] = scenarioMapping.get(scenario);
		
		return index;	
	}


	/*
	 * Get the Scenario class by given year and scenario
	 */
	public Scenario getScenario(int year, String scenario) {
		
		try {
			int rowIndex = getIndex(year, scenario)[0];
			int colIndex = getIndex(year, scenario)[1];
			return scenarios.get(rowIndex).get(colIndex);
		} catch (Exception e) {
			return null;
		}
	}


	/*
	 * Get the list of valid scenarios (scenario that not null)
	 * Return the array list of String (the year and name of scenarios)
	 */
	public ArrayList<String> getScenarios() {
		
		ArrayList<String> result = new ArrayList<String>();
		
		// loop through scenarios to find non-null scenarios
		for (int i = 0; i<scenarios.size(); i++) {
			for (int j = 0; j<scenarioMapping.size(); j++) {
				if (scenarios.get(i).get(j) != null) {
					result.add(scenarios.get(i).get(j).year + " " + scenarios.get(i).get(j).name);
				}
			}
		}
		
		return result;
		
	}


	/*
	 * Get the list of P&L of two scenarios 
	 * Return the array list of String (the year and name of scenarios)
	 */
	public String[][] compareScenarios(Scenario[] items) {
		
		int size1 = items[0].revAccts.size()+items[0].expAccts.size();
		
		int size2 = items[1].revAccts.size()+items[1].expAccts.size();
		
		if (size1 < size2) return helper(items[1], items[0], 2);
		else return helper(items[0], items[1], 1);
	}
	
	/*
	 * Helper function of compareScenarios function
	 */
	public String[][] helper(Scenario s1, Scenario s2, int compareFrom){
		
		int rowCount = s1.revAccts.size() + s1.expAccts.size() + 3;
		String[][] result = new String[rowCount][4];
		String[] line = new String[4];
		
		TreeMap<Integer, BigDecimal> p1 = s1.getPLTotal();
		TreeMap<Integer, BigDecimal> p2 = s2.getPLTotal();
		
		int count = 0;
		
		// loop through every entry of tree map
		for (Entry<Integer, BigDecimal> entry: p1.entrySet()) {
			
			int acctN = entry.getKey();
			
			if (acctN == 6999) line[0] = "total rev:";
			else if (acctN == 7999) line[0] = "total exp:";
			else if (acctN == 8999) line[0] = "net income:";
			else line[0] = Integer.toString(acctN);
			
			BigDecimal total1 = entry.getValue();
			BigDecimal total2 = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
			BigDecimal variance = total1;
			
			// if compare from is 1, s1 will be in column 2, and s2 will be in column3
			if (compareFrom == 1) {
				
				line[1] = total1.toString();

				// if p2 also has this acct, reassign total2
				if (p2.containsKey(acctN)) {
					total2 = p2.get(acctN);
				}
				
				line[2] = total2.toString();
				
				if ((acctN < 7000 || acctN >= 8000)) line[3] = total1.add(total2.negate()).toString();
				else line[3] = total2.add(total1.negate()).toString();
				
			}
			// if compare from is 2, s2 will be in column 2, and s1 will be in column 3
			else if (compareFrom == 2){
				line[2] = total1.toString();

				// if p2 also has this acct, reassign total2
				if (p2.containsKey(acctN)) {
					total2 = p2.get(acctN);
				}
				
				line[1] = total2.toString();
				
				if ((acctN < 7000 || acctN >= 8000)) line[3] = total2.add(total1.negate()).toString();
				else line[3] = total1.add(total2.negate()).toString();
			}
			
			result[count] = line.clone();
			count ++;
		}
		
		return result;
		
	}


	public boolean exportCSV(String[][] data, String scenario1, String scenario2) {
		
		try {
			File f = new File("export.csv");
			FileWriter fw = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(fw);
			
			int columnLen = data[0].length;
			
			// write the Scenario on the first row
			bw.write("p&l comparison between " + scenario1 + " and " + scenario2);
			bw.write("\n");
			
			String[] colName = { "Account", scenario1, scenario2, "Variance B/(W)" };
			
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
}
