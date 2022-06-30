package FrontEnd;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import BackEnd.*;

public abstract class Frame implements ActionListener{

	JFrame frame;
	JPanel panel;
	JButton button;
	JLabel label;
	JList list;
	BudgetSystem bs;
	JMenuBar menuBar;
	JMenu menu, subMenu1, subMenu2, subMenu3;
	JMenuItem m1, m2, s1, s2, s3, s4, s5, s6, s7, s8;
	
	Frame(BudgetSystem budgetSystem){
		
		
		frame = new JFrame();
		panel = new JPanel();
		menuBar = new JMenuBar();
		
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		
		//set name of the frame
		frame.setTitle("Budget System");

		
		// set up the menu
		menu = new JMenu("Menu");
		subMenu1 = new JMenu("Get scenario info");
		subMenu2 = new JMenu("Edit scenario");
		subMenu3 = new JMenu("Compare scenario");
		
		m1 = new JMenuItem("Add new year");
		m2 = new JMenuItem("Add new scenario");
		s1 = new JMenuItem("View P&L");
        s2 = new JMenuItem("View revenue by clients");
        s3 = new JMenuItem("View client's revenue by media type");
        s4 = new JMenuItem("View client's revenue by account");
        s5 = new JMenuItem("View expense detail");
        s6 = new JMenuItem("Compare P&L");
        s7 = new JMenuItem("Upload data");
        s8 = new JMenuItem("Edit expense item");

		// add menu items to menu
		menu.add(m1);
		menu.add(m2);
		
		subMenu1.add(s1);
		subMenu1.add(s2);
		subMenu1.add(s3);
		subMenu1.add(s4);
		subMenu1.add(s5);
		menu.add(subMenu1);
		
		subMenu2.add(s7);
		subMenu2.add(s8);
		menu.add(subMenu2);
		

		subMenu3.add(s6);
		menu.add(subMenu3);


		
		// add ActionListener to menuItems
		m1.addActionListener(this);
		m2.addActionListener(this);
		s1.addActionListener(this);
		s2.addActionListener(this);
		s3.addActionListener(this);
		s4.addActionListener(this);
		s5.addActionListener(this);
		s6.addActionListener(this);
		s7.addActionListener(this);
		s8.addActionListener(this);
		
		// add menu to menu bar, add menubar to frame
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
	    bs = budgetSystem;
	    
		// pack and show the this
		frame.add(panel);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(750, 750);
	    frame.setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == m1) {
			frame.dispose();
			new AddNewYearFrame(bs);
		}
		
		else if (e.getSource() == m2) {
			frame.dispose();
			new AddNewScenarioFrame(bs);
		}
		
		else if (e.getSource() == s1) {
			frame.dispose();
			new ViewPLFrame(bs);
		}
		
		else if (e.getSource() == s2) {
			frame.dispose();
			new ViewRevByClientsFrame(bs);
		}
		
		else if (e.getSource() == s3) {
			frame.dispose();
			new ViewClientRevByMediasFrame(bs);
		}
		
		else if (e.getSource() == s4) {
			frame.dispose();
			new ViewRevByAcctsFrame(bs);
		}
		
		else if (e.getSource() == s5) {
			frame.dispose();
			new ViewExpByNotesFrame(bs);
		}
		
		else if (e.getSource() == s6) {
			frame.dispose();
			new ComparePLFrame(bs);
		}
		
		else if (e.getSource() == s7) {
			frame.dispose();
			new GetScenario_UploadDataFrame(bs);
		}
		
		else if (e.getSource() == s8) {
			frame.dispose();
			new EditExpenseFrame(bs);
		}
	}
	
}

