package BackEnd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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



}
