package FrontEnd;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import BackEnd.*;

public class UploadFrame extends Frame{
	
	Scenario s;
	

	UploadFrame(BudgetSystem budgetSystem, Scenario scenario) {
		
		super(budgetSystem);
		
		s= scenario;
		
        // add button and ActionListener to button
		button = new JButton("Upload a CSV File");
		panel.add(button);
        button.addActionListener(this);	
		
	}
	
	
	
	public void actionPerformed(ActionEvent e){
		
        if (e.getSource() == button) {
        	
        	// set up JFileChooser to choose file to upload
        	JFileChooser file = new JFileChooser();
        	int res = file.showOpenDialog(null);
        	
        	// if choose a file successfully
        	if (res == JFileChooser.APPROVE_OPTION) {
        		
        		String pathName = file.getSelectedFile().getAbsolutePath();
        		File filePath = new File(pathName);
        		System.out.println(filePath);
        		
        		// if the file is a CSV file, which if valid
        		if ( (pathName != null ) && (pathName.length() > 4) 
        				&& (pathName.substring(pathName.length() - 4).toLowerCase().equals(".csv")) ) {
        			
        			// get the String of row numbers that have errors
        			String result = s.upload(filePath);
        			
        			if (result == "") {
        				frame.dispose();
        				new SuccessFrame(bs);
        			}
        			else {
        				frame.dispose();
        				new FailFrame(bs, "Rows " + result + "can't be uploaded due to input errors. Other rows were uploaded successfully");
        			}
        		}
        		else {
        			frame.dispose();
        			new FailFrame(bs, "The file you uploaded was not a CSV file.");
        		}
        	}
        	else {
        		frame.dispose();
        		new FailFrame(bs);
        	}
        }
    }

}

