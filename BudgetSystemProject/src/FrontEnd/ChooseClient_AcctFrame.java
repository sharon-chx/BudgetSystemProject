package FrontEnd;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import BackEnd.*;

public class ChooseClient_AcctFrame extends ChooseClientsFrame{

	public ChooseClient_AcctFrame(BudgetSystem budgetSystem, Scenario scenario) {
		super(budgetSystem, scenario);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		label = new JLabel("Choose one client:");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button) {
			
			 int selectedIndex = list.getSelectedIndex();
			 
			 if (selectedIndex == -1) {
				 frame.dispose();
				 new FailFrame(bs, "You didn't choose a client");
				 
			 }else {
				 String selectedClient = (String) clients[selectedIndex];
				 
				 frame.dispose();
				 new ViewRevByAcctsFrame(bs, s, selectedClient);
			 }
			
		}
	}

}
