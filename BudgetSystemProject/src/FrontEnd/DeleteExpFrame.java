package FrontEnd;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import BackEnd.*;

public class DeleteExpFrame extends DisplayFrame implements ActionListener {
	
	ExpAcct expA;
	int id;
	int acctN;

	DeleteExpFrame(BudgetSystem budgetSystem, Scenario scenario, int acct, int index) {
		
		super(budgetSystem, scenario);
		
		id = index;
		acctN = acct;
		
		// set up label at the beginning
		label = new JLabel("Only shows 1 result Below. So please try to provide more detail of note.");
		label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(label);
		
		// create some empty space between different elements
		panel.add(Box.createRigidArea(new Dimension(0, 10))); 
		
		// get data to be displayed in the table
		expA = s.expAccts.get(acct);
		
		String[] columnNames = { "Note", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Total" };
		String[][] data = new String[1][14];
		
		// covert note and amounts to data list
		String[] d = new String[14];
		
		for (int i = 0; i<14; i++) {
			if (i == 0) d[i] = expA.notes.get(id);
			else d[i] = expA.amounts[i-1].toString();
		}
		
		data[0] = d;
		
		table = new JTable(data, columnNames);
		
		table.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        js.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        table.setPreferredScrollableViewportSize(new Dimension(450,30));
        table.setFillsViewportHeight(true);
        panel.add(js);
        
        // create some empty space between different elements
        panel.add(Box.createRigidArea(new Dimension(0, 10))); 
		
        // add button for submission
		button = new JButton("Confirm Deletion");
		button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(button);
        button.addActionListener(this);
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button) {
			
			 if (expA.deleteItem(id)) {
				 frame.dispose();
				 new SuccessFrame(bs);
			 }else{
				 frame.dispose();
				 new FailFrame(bs);
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
