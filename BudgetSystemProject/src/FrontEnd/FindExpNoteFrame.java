package FrontEnd;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import BackEnd.*;


public class FindExpNoteFrame extends Frame{
	
	Scenario s;
	JLabel label2;
	JTextField acct;
	JTextField keyword;

	FindExpNoteFrame(BudgetSystem budgetSystem , Scenario scenario) {
		
		super(budgetSystem);
		
		s= scenario;
		
	    // set up the label and create a object of JTextField with 16 columns for acct and keyword
	    label = new JLabel("Expense Account Number:");
	    panel.add(label);
	    
        acct = new JTextField(8);
        panel.add(acct);
        
	    label2 = new JLabel("Keyword:");
	    panel.add(label2);
	    
        keyword = new JTextField(8);
        panel.add(keyword);
		
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
				 String note = keyword.getText();
				 
				 if (acctNo < 7000 || acctNo >=8000) {
					 frame.dispose();
					 new FailFrame(bs, "The account number you entered was not an expense account.");
				 }else if(note.isEmpty()) {
					 frame.dispose();
					 new FailFrame(bs, "You didn't enter the keyword!");
				 }else if (!s.expAccts.containsKey(acctNo)) {
					 frame.dispose();
					 new FailFrame(bs, "The expense account does not exist! Please add it first");
				 }
				 else {
					 //s.showExpLine(note);
					 ExpAcct expA = s.expAccts.get(acctNo);
					 
					 int index = expA.findItem(note);
					 
					 if (index == -1) {
						 frame.dispose();
						 new FailFrame(bs, "Can't find the expense line item based on the keyword.");
					 }else {
						 frame.dispose();
						 new DeleteExpFrame(bs, s, acctNo, index);
					 }
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
