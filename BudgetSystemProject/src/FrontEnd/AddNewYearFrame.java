package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import BackEnd.*;

public class AddNewYearFrame extends Frame implements ActionListener{
		

    Integer[] yearOptions = {2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030};
    int selectedIndex = -1;
	

	AddNewYearFrame(BudgetSystem budgetSystem){
		
		super(budgetSystem);
		
		// set the frame and panel
		button = new JButton("Submit");
	    
	    // set up the list field for input
	    label = new JLabel("Please select the year you want to add:");
	    panel.add(label);

	    DefaultListModel years = new DefaultListModel<Integer>();
	    years.addElement(2023);
	    years.addElement(2024);
	    years.addElement(2025);
	    years.addElement(2026);
	    years.addElement(2027);
	    years.addElement(2028);
	    years.addElement(2029);
	    years.addElement(2030);
	    JList list = new JList<>(years);
	    list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setLayoutOrientation(JList.VERTICAL);
	    
	    // add list selection listener
	    list.addListSelectionListener(
	    		new ListSelectionListener() {
	    			public void valueChanged(ListSelectionEvent event) {
	    				selectedIndex = list.getSelectedIndex();
	    			}
	    		}
	    		
	    		);
	    
	    
	    // add list and button and ActionListener
	    panel.add(list);
	    panel.add(new JScrollPane(list));
	    panel.add(button);
	    button.addActionListener(this);

		
	}
	
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == button) {
			
			if (selectedIndex != -1) {
				int year = yearOptions[selectedIndex];
				bs.addYear(year);
				System.out.println(bs.scenarios);
				frame.dispose();
				new SuccessFrame(bs);
			}else {
				frame.dispose();
				new FailFrame(bs);
			}
		}
		
		
	}

	
	
}
