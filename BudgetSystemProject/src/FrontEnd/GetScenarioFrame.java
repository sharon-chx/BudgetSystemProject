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
	//source{1: View P&L; 2: View revenue by clients; 3: View client's revenue by media type; 4: View client's revenue by account
	// 		5: View expense detail; 7: Upload data; 8: Delete expense item}
	int sourceCode;

	GetScenarioFrame(BudgetSystem budgetSystem, int s) {
		
		super(budgetSystem);
		
		this.sourceCode = s;

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
        				
        				// get to following frame based on source code
        				if (sourceCode == 1) new ViewPLFrame(bs, result);
        				else if (sourceCode == 2) new ChooseClientsFrame(bs, result);
        				else if (sourceCode == 3) new ChooseClientFrame(bs, result);
        				else if (sourceCode == 4) new ChooseClient_AcctFrame(bs, result);
        				else if (sourceCode == 5) new FindExpFrame(bs, result);
        				else if (sourceCode == 7) new UploadFrame(bs, result);
        				else if (sourceCode == 8) new FindExpNoteFrame(bs, result);
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
