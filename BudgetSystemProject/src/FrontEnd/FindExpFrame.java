package FrontEnd;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import BackEnd.BudgetSystem;
import BackEnd.ExpAcct;
import BackEnd.Scenario;

public class FindExpFrame extends Frame{
	
	Scenario s;
	JTextField acct;

	FindExpFrame(BudgetSystem budgetSystem , Scenario scenario) {
		
		super(budgetSystem);
		
		s= scenario;
		
	    // set up the label and create a object of JTextField with 16 columns for acct and keyword
	    label = new JLabel("Expense Account Number:");
	    panel.add(label);
	    
        acct = new JTextField(8);
        panel.add(acct);
		
        // add button and ActionListener to button
		button = new JButton("Find");
		panel.add(button);
        button.addActionListener(this);	
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		 if (e.getSource() == button) {
			 
			 try {
				 int acctNo = Integer.parseInt(acct.getText());
				 
				 if (acctNo < 7000 || acctNo >=8000) {
					 frame.dispose();
					 new FailFrame(bs, "The account number you entered was not an expense account.");
				 }else if (!s.expAccts.containsKey(acctNo)) {
					 frame.dispose();
					 new FailFrame(bs, "The expense account does not exist! Please add it first");
				 }
				 else {
					 frame.dispose();
					 new ViewExpByNotesFrame(bs, s, acctNo);
				 }
				 
			 }catch(Exception exp) {
				System.out.println(exp);
				frame.dispose();
    			new FailFrame(bs, "The account number you entered was not an integer.");
			 } 
		 }
		 
        else if (e.getSource() == m1) {
			frame.dispose();
			new AddNewYearFrame(bs);
		}
		
		else if (e.getSource() == m2) {
			frame.dispose();
			new AddNewScenarioFrame(bs);
		}
		
		else if (e.getSource() == s1) {
			frame.dispose();
			new GetScenarioFrame(bs, 1);
		}
		
		else if (e.getSource() == s2) {
			frame.dispose();
			new GetScenarioFrame(bs, 2);
		}
		
		else if (e.getSource() == s3) {
			frame.dispose();
			new GetScenarioFrame(bs, 3);
		}
		
		else if (e.getSource() == s4) {
			frame.dispose();
			new GetScenarioFrame(bs, 4);
		}
		
		else if (e.getSource() == s5) {
			frame.dispose();
			new GetScenarioFrame(bs, 5);
		}
		
		else if (e.getSource() == s6) {
			frame.dispose();
			new GetScenario_twoScenarioFrame(bs);
		}
		
		else if (e.getSource() == s7) {
			frame.dispose();
			new GetScenarioFrame(bs, 7);
		}
		
		else if (e.getSource() == s8) {
			frame.dispose();
			new GetScenarioFrame(bs, 8);
		}
	}

}
