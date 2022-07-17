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
	
	
	@Override
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
