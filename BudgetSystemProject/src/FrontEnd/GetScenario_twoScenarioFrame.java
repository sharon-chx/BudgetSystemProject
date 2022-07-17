package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import BackEnd.*;

public class GetScenario_twoScenarioFrame extends Frame implements ActionListener{

	Object[] scenario;
	JList list2;
	JLabel label2;
	
	GetScenario_twoScenarioFrame(BudgetSystem budgetSystem) {
		super(budgetSystem);
		
		label = new JLabel("Compare from:");
		panel.add(label);
		
		scenario = bs.getScenarios().toArray();
		Arrays.sort(scenario);

		list = new JList(scenario);
		panel.add(new JScrollPane(list));
		list.setSelectedIndex(0);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list.setLayoutOrientation(JList.VERTICAL);
	    
	    label2 = new JLabel("to:");
		panel.add(label2);
		
		list2 = new JList(scenario);
		panel.add(new JScrollPane(list2));
		list2.setSelectedIndex(0);
		list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    list2.setLayoutOrientation(JList.VERTICAL);
		
	    button = new JButton("Submit");
		panel.add(button);
        button.addActionListener(this);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == button) {
			
			 int[] selectedIndex = new int[2];
			 
			 selectedIndex[0] = list.getSelectedIndex();
			 selectedIndex[1] = list2.getSelectedIndex();
			 
			 Scenario[] scenarioList = new Scenario[2];
			 
			 for (int i=0; i<2; i++) {
				 int num = selectedIndex[i];
				 
				 if (num == -1) {
					 frame.dispose();
					 new FailFrame(bs, "You didn't choose 2 scenarios only");
				 }
				 else {
					 String sce = (String) scenario[num];
					 String[] sc = sce.split(" ");
					 int year = Integer.parseInt(sc[0]);
					 String scr = sc[1].trim().toLowerCase();
					 
					 scenarioList[i] = bs.getScenario(year, scr);
					 
				 }
			 }
			 
			 if (scenarioList[0] == null || scenarioList[1] == null) {
				 frame.dispose();
				 new FailFrame(bs, "The scenarios you chose don't have data yet!");
			 }else {
				 frame.dispose();
				 new ComparePLFrame(bs, scenarioList);
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
