package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import BackEnd.BudgetSystem;
import BackEnd.Scenario;

public class GetScenario_DeleteExpFrame extends GetScenarioFrame implements ActionListener{

	GetScenario_DeleteExpFrame(BudgetSystem budgetSystem) {
		
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
                        
                    	// if valid scenario, go to the Upload Frame
        				frame.dispose();
        				new FindExpFrame(bs, result);
                    }
                }catch(Exception exc) {
                	frame.dispose();
    				new FailFrame(bs, "The year you entered is not an integer.");
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
			new GetScenario_plFrame(bs);
		}
		
		else if (e.getSource() == s2) {
			frame.dispose();
			new GetScenario_RevByClientFrame(bs);
		}
		
		else if (e.getSource() == s3) {
			frame.dispose();
			new GetScenario_RevByMediaFrame(bs);
		}
		
		else if (e.getSource() == s4) {
			frame.dispose();
			new GetScenario_RevByAcctFrame(bs);
		}
		
		else if (e.getSource() == s5) {
			frame.dispose();
			new GetScenario_ExpFrame(bs);
		}
		
		else if (e.getSource() == s6) {
			frame.dispose();
			new GetScenario_twoScenarioFrame(bs);
		}
		
		else if (e.getSource() == s7) {
			frame.dispose();
			new GetScenario_UploadDataFrame(bs);
		}
		
		else if (e.getSource() == s8) {
			frame.dispose();
			new GetScenario_DeleteExpFrame(bs);
		}
    }

}
