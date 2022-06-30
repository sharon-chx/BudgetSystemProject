package FrontEnd;

import javax.swing.JLabel;

import BackEnd.*;

public class FailFrame extends Frame{

	FailFrame(BudgetSystem budgetSystem, String text) {
		super(budgetSystem);
		
		label = new JLabel();
		label.setText(text);
	    panel.add(label);
	}
	
	FailFrame(BudgetSystem budgetSystem) {
		super(budgetSystem);
		
		label = new JLabel();
		label.setText("Something went wrong. Please click Menu to try again!");
	    panel.add(label);
	}	

}
