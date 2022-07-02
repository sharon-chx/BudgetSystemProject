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
        
        // add button and ActionListener to button
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
    }

	
}
