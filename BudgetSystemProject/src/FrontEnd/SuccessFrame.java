package FrontEnd;

import javax.swing.JLabel;

import BackEnd.*;


public class SuccessFrame extends Frame{
	
	SuccessFrame(BudgetSystem budgetSystem) {
		super(budgetSystem);
		
		label = new JLabel("Action performed successfully!");
	    panel.add(label);
		
		
	}
	
}
