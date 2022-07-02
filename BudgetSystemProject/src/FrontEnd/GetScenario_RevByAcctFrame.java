package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BackEnd.BudgetSystem;
import BackEnd.Scenario;

public class GetScenario_RevByAcctFrame extends GetScenarioFrame implements ActionListener{

	GetScenario_RevByAcctFrame(BudgetSystem budgetSystem) {
		super(budgetSystem);
		
	}

	@Override
	public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == button) {
        	
        	String scenario = tScenario.getText().toLowerCase();
                
                try {
                	
                	int year = Integer.parseInt(tYear.getText());
                    Scenario result = bs.getScenario(year, scenario);
                    
                    // can't find the scenario by given year or scenario, return error message
                    if (result == null) {
                    	frame.dispose();
                    	new FailFrame(bs, "Year or scenario you entered doesn't exist! Please add them first!");

                    }else {
                        
                    	// if valid scenario, go to the ChosseClient Frame
        				frame.dispose();
        				new ChooseClient_AcctFrame(bs, result);
                    }
                }catch(Exception exc) {
                	frame.dispose();
    				new FailFrame(bs, "The year you entered is not an integer.");
                }	
        }
    }
	
}
