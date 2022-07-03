package BackEnd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class ExpAcct {
	
	/*
	 * variables
	 */
	// account number
	public int number;
	
	// monthly total amounts
	public BigDecimal[] amounts;
	
	// ArrayList of line items
	public ArrayList<BigDecimal[]> items;
	
	// ArrayList of notes corresponding to items list by index
	public ArrayList<String> notes;
	
	
	/*
	 * Constructor
	 */
	public ExpAcct(int number) {
		this.number = number;
		this.amounts = new BigDecimal[13];
		this.items = new ArrayList<BigDecimal[]>();
		this.notes = new ArrayList<String>();
		
		// initiate amounts with 0; 12 months plus total at last column
		for (int i = 0; i < 13; i++) {
			amounts[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}
	}


	/*
	 * Recalculate total of the Account
	 */
	public void calculateTotal() {
		
		// reset the totals
		for (int i = 0; i < 13; i++) {
			amounts[i] = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
		}
				
		for (BigDecimal[] item: items) {
			
			for (int i = 0; i < 13; i++) {	
	
				amounts[i] = amounts[i].add(item[i]);
			}
		}
		
	}

	
	/*
	 * Update/add the note and corresponding amts
	 */
	public boolean update(String note, BigDecimal[] amounts) {
		
		if (note == null || amounts == null) return false;
		
		note = note.toLowerCase();
		
		int noteIndex = notes.indexOf(note);
		
		if ( noteIndex != -1) items.set(noteIndex, amounts);
		
		else {
			notes.add(note);
			items.add(amounts);
		}
		
		return true;
		
	}
	
	
	/*
	 * Print amounts of line items
	 */
	
	public String printAmt() {
		
		String result = "";
		
		for (int i = 0; i < items.size(); i++) {
			
			String amt = "";
			
			for (int j = 0; j < 13; j++) {
				amt = amt + items.get(i)[j] + ", ";
			}

			result = result + notes.get(i) + ", " + amt;
			
			// a new line
			result = result + "\n";
		}
		
		return result;
	}


	/*
	 * Find the line item by given keyword, return the index number
	 */
	public int findItem(String note) {
	
		if (note == null) return -1;
		
		for (int i = 0; i < notes.size(); i++) {
			if (notes.get(i).contains(note)) return i;
		}
		
		return -1;
	}



	/*
	 * Delete the line item by given index
	 */
	public boolean deleteItem(int index) {
		
		// check for invalid input
		if (index < 0 || index >= items.size()) return false;
		
		// remove the note and amounts
		items.remove(index);
		notes.remove(index);
		
		return true;
		
	}


	/*
	 * Get line items of the Expense Account
	 */
	public String[][] getItems() {
		
		int count = 0;
		String[][] result = new String[items.size()+1][14];
		
		// add the key and value of items to result
		for (int i = 0 ; i< items.size(); i++) {
			
			String[] line = new String[14];
			line[0] = notes.get(i);
			
			BigDecimal[] nums = items.get(i);
			
			for (int j = 0; j < 13; j++) {
				BigDecimal num = nums[i];
				line[j+1] = num.toString();
			}
			
			result[count] = line;
			count++;
		}
		
		// convert total to Sting[]
		String[] totalToString = new String[14];
		totalToString[0] = "expense total:";
		for (int j = 0; j < 13; j++) {
			totalToString[j+1] = amounts[j].toString();
		}
		
		result[count] = totalToString;
		
		return result;
	}
	
	

}
