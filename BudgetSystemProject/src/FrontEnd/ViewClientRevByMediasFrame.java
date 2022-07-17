package FrontEnd;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import BackEnd.BudgetSystem;
import BackEnd.Scenario;

public class ViewClientRevByMediasFrame extends DisplayFrame{
	
	String client;
	String[][] data;

	ViewClientRevByMediasFrame(BudgetSystem budgetSystem, Scenario scenario, String selectedClient) {
		super(budgetSystem, scenario);
		client = selectedClient;
		
		// set up label at the beginning
		label = new JLabel("Result of Revenue by Media for " + client + ":");
		label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(label);
		
		// create some empty space between different elements
		panel.add(Box.createRigidArea(new Dimension(0, 10))); 
		
		String[] columnNames = { "Media Type", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Total" };
		
		data = s.getMediaRev(client);
		
		table = new JTable(data, columnNames);
		
		table.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        js.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        table.setPreferredScrollableViewportSize(new Dimension(450,30));
        table.setFillsViewportHeight(true);
        panel.add(js);
        
        // add button for download data
		button = new JButton("Export");
		button.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(button);
        button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button) {
			
			boolean result = s.exportCSV(data, "media", "Client " + client);
			
			if (result == true) {
				frame.dispose();
				new SuccessFrame(bs);
			}else {
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
