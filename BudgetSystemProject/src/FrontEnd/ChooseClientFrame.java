package FrontEnd;

import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import BackEnd.*;

public class ChooseClientFrame extends ChooseClientsFrame{
	
	// {1: view client rev by medias; 2: view rev by accounts}
	int sourceCode;
	

	public ChooseClientFrame(BudgetSystem budgetSystem, Scenario scenario, int code) {
		super(budgetSystem, scenario);
		this.sourceCode = code;
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
				 if (sourceCode == 1) new ViewClientRevByMediasFrame(bs, s, selectedClient);
				 else if (sourceCode == 2) new ViewRevByAcctsFrame(bs, s, selectedClient);
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
