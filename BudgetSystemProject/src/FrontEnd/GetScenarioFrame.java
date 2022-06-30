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

		// set the frame and panel
		frame.setTitle("Get scenario");;

		
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

	
}
