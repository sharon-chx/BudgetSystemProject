package FrontEnd;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import BackEnd.*;


public class FindExpFrame extends Frame{
	
	Scenario s;
	JLabel label2;
	JTextField acct;
	JTextField keyword;

	FindExpFrame(BudgetSystem budgetSystem , Scenario scenario) {
		
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
	}

}
