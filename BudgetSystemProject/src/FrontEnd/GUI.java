package FrontEnd;

import BackEnd.*;

public class GUI  {
	
	static BudgetSystem bs = new BudgetSystem();


	public static void main(String[] args) {
		
		
		new MainFrame(bs);


	}

}

