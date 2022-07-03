package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import BackEnd.*;

public class FailFrame extends Frame implements ActionListener{

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
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == m1) {
			frame.dispose();
			new AddNewYearFrame(bs);
		}
		
		else if (e.getSource() == m2) {
			frame.dispose();
			new AddNewScenarioFrame(bs);
		}
		
		else if (e.getSource() == s1) {
			frame.dispose();
			new GetScenario_plFrame(bs);
		}
		
		else if (e.getSource() == s2) {
			frame.dispose();
			new GetScenario_RevByClientFrame(bs);
		}
		
		else if (e.getSource() == s3) {
			frame.dispose();
			new GetScenario_RevByMediaFrame(bs);
		}
		
		else if (e.getSource() == s4) {
			frame.dispose();
			new GetScenario_RevByAcctFrame(bs);
		}
		
		else if (e.getSource() == s5) {
			frame.dispose();
			new GetScenario_ExpFrame(bs);
		}
		
		else if (e.getSource() == s6) {
			frame.dispose();
			new GetScenario_twoScenarioFrame(bs);
		}
		
		else if (e.getSource() == s7) {
			frame.dispose();
			new GetScenario_UploadDataFrame(bs);
		}
		
		else if (e.getSource() == s8) {
			frame.dispose();
			new GetScenario_DeleteExpFrame(bs);
		}
	}

}
