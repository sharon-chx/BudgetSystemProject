package FrontEnd;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import BackEnd.*;

public class AddNewScenarioFrame extends Frame{
	
	JTextField t;

	AddNewScenarioFrame(BudgetSystem budgetSystem) {
		super(budgetSystem);
		
	    // set up the label
	    label = new JLabel("Please enter the scenario you want to add:");
	    panel.add(label);

 
        // create a object of JTextField with 8 columns
        t = new JTextField(3);
        panel.add(t);
        
        // add button and ActionListener to button
		button = new JButton("Submit");
		panel.add(button);
        button.addActionListener(this);
        
	}
	
	
	public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == button) {
        	
        	// get the text and add scenario
            String scenario = t.getText();
                
            boolean result = bs.addScenario(scenario);
                
            if (result == false) {
            	new FailFrame(bs, "You didn't enter anything, or entered more than 3 characters, or the scenario you entered already exists.");
            }
                
            else {
                // successful page
            	System.out.println(bs.scenarios);
				frame.dispose();
				new SuccessFrame(bs);
            }
  
        }
    }

}
