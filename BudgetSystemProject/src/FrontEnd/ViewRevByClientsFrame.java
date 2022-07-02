package FrontEnd;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import BackEnd.*;

public class ViewRevByClientsFrame extends DisplayFrame {
	
	String[] clients;
	String[][] data;

	ViewRevByClientsFrame(BudgetSystem budgetSystem, Scenario scenario, String[] cls) {
		super(budgetSystem, scenario);
		clients = cls;
		
		// set up label at the beginning
		label = new JLabel("Result of Revenue by Clients:");
		label.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		panel.add(label);
		
		// create some empty space between different elements
		panel.add(Box.createRigidArea(new Dimension(0, 10))); 
		
		String[] columnNames = { "Client Name", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "Total" };
		
		data = s.getClientsRev(clients);
		
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
			
			boolean result = s.exportCSV(data, "client");
			
			if (result == true) {
				frame.dispose();
				new SuccessFrame(bs);
			}else {
				frame.dispose();
				new FailFrame(bs);
			}
			
		}
	}

}
