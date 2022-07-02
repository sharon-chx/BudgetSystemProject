package FrontEnd;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import BackEnd.*;

public class ChooseClientsFrame extends Frame{
	
	Scenario s;
	Object[] clients;

	public ChooseClientsFrame(BudgetSystem budgetSystem, Scenario scenario) {
		super(budgetSystem);
		s = scenario;
		
		label = new JLabel("Choose client(s):");
		panel.add(label);
		
		clients = s.getClients().toArray();
		Arrays.sort(clients);

		list = new JList(clients);
		panel.add(new JScrollPane(list));
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    list.setLayoutOrientation(JList.VERTICAL);
		
		button = new JButton("Confirm");
		panel.add(button);
        button.addActionListener(this);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button) {
			
			 int[] selectedIndex = list.getSelectedIndices();
			 
			 if (selectedIndex.length == 0) {
				 frame.dispose();
				 new FailFrame(bs, "You didn't choose a client");
			 }else {
				 String[] clientList = new String[selectedIndex.length];
				 
				 for (int i = 0; i < selectedIndex.length; i++) {
					 int num = selectedIndex[i];
					 clientList[i] = (String) clients[num];
				 }
				 
				 frame.dispose();
				 new ViewRevByClientsFrame(bs, s, clientList);
			 }
		}
	}

}
