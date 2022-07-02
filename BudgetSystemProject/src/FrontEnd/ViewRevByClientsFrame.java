package FrontEnd;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import BackEnd.*;

public class ViewRevByClientsFrame extends DisplayFrame {
	
	Scenario s;
	String[] clients;

	ViewRevByClientsFrame(BudgetSystem budgetSystem, Scenario scenario, String[] clients) {
		super(budgetSystem, scenario);
		s = scenario;
		clients = clients;
		
		// set up label at the beginning
		label = new JLabel("Result of Revenue by Clients:");
		label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(label);
		
		// create some empty space between different elements
		panel.add(Box.createRigidArea(new Dimension(0, 10))); 
		
		String[] columnNames = { "Client Name", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Total" };
		
		String[][] data = s.getClientsRev(clients);
		
		table = new JTable(data, columnNames);
		
		table.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        JScrollPane js=new JScrollPane(table);
        js.setVisible(true);
        js.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        table.setPreferredScrollableViewportSize(new Dimension(450,30));
        table.setFillsViewportHeight(true);
        panel.add(js);
		
	}

}
