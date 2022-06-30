package FrontEnd;

import javax.swing.JLabel;

import BackEnd.BudgetSystem;

public class MainFrame extends Frame{

	MainFrame(BudgetSystem budgetSystem) {
		super(budgetSystem);
		
		label = new JLabel("Welcome! Please see Menu for options!");
	    panel.add(label);

	}

}
