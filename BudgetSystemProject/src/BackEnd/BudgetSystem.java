package BackEnd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;



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
	public static Hashtable <Integer, RevAcct> defaultRevAccts = new Hashtable <Integer, RevAcct>();
	
	// hash table of default Exp Accounts
	public static Hashtable <Integer, ExpAcct> defaultExpAccts = new Hashtable <Integer, ExpAcct>();
	
	// set up static variables default accounts and categories
    static {
        
        // set up default accounts
        defaultRevAccts.put(6000, new RevAcct(6000));   // rev account for disclosed clients
        defaultRevAccts.put(6195, new RevAcct(6195));	// rev account for non-disclosed clients
        defaultExpAccts.put(7000, new ExpAcct(7000));  // base salary account
        defaultExpAccts.put(7150, new ExpAcct(7150));	// benefit account
        defaultExpAccts.put(7240, new ExpAcct(7240));	// Lodge expense account
        defaultExpAccts.put(7350, new ExpAcct(7350));	// Airfare expense account
        
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
	protected int[] getIndex(int year, String scenario) {
		
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


}
