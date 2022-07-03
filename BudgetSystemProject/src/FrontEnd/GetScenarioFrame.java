package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import BackEnd.*;

public class GetScenarioFrame extends Frame{
	
	JTextField tYear;
	JTextField tScenario;
	JLabel label2;

	GetScenarioFrame(BudgetSystem budgetSystem) {
		
		super(budgetSystem);

	    // set up the label and create a object of JTextField with 16 columns for year
	    label = new JLabel("Year:");
	    panel.add(label);

        tYear = new JTextField(8);
        panel.add(tYear);
        
        // set up the label and create a object of JTextField with 16 columns for scenario
	    label2 = new JLabel("Scenario:");
	    panel.add(label2);
	    
        tScenario = new JTextField(8);
        panel.add(tScenario);
        
		button = new JButton("Submit");
		panel.add(button);
        button.addActionListener(this);
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
        				new ChooseClientsFrame(bs, result);
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
