package BackEnd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

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


	public void calculateTotal() {
		// TODO Auto-generated method stub
		
	}

}
