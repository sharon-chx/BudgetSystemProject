package BackEnd;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;

import javax.naming.spi.DirStateFactory.Result;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScenarioTest {
	
	Scenario s1;
	Scenario s2;

	@BeforeEach
	void setUp() throws Exception {
		s1 = new Scenario(2022, "FP");
		s2 = new Scenario(2022, "BUD");
	}

	@Test
	void testUpdateRevAcct() {
		
		double[] amt = {1.01, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 78.01};
		BigDecimal[] amounts = new BigDecimal[13];
		
		for (int i = 0; i < 13; i++) {
			amounts[i] = new BigDecimal(amt[i]).setScale(2, RoundingMode.HALF_UP);
		}
		
		// test for invalid inputs
		assertFalse(s1.updateRevAcct(0, "ALDI", "DISPLAY", amounts));
		assertFalse(s1.updateRevAcct(7000, "ALDI", "DISPLAY", amounts));
		assertFalse(s1.updateRevAcct(6000, null, "DISPLAY", amounts));
		assertFalse(s1.updateRevAcct(6000, "ALDI", null, amounts));
		assertFalse(s1.updateRevAcct(6000, "ALDI", "DISPLAY", null));
		
		// test
		assertTrue(s1.updateRevAcct(6000, "ALDI", "DISPLAY", amounts));
		//System.out.println(s1.revAccts.get(6000).clients.get("aldi").printAmt());
		
		assertTrue(s2.updateRevAcct(6000, "BMW", "video", amounts));
		//System.out.println(s2.revAccts.get(6000).clients.get("bmw").printAmt());
		
	}
	
	
	@Test
	void testReadRow() throws IOException {
		File f = new File("test - copy.csv");
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		
		String[] result = s1.readRow(br);
		for (int i= 0; i < result.length - 1; i++) {
			String text = result[i];
			if (text == null ) text = "null";
			else text = text.toString();
			System.out.print(text + ", ");
		}
		System.out.print(result[result.length-1].toString());
		System.out.println();
		System.out.println();
	}
	
	
	@Test
	void testUpload() {
		
		// test upload on rev acct
		File f = new File("test.csv");
		
		System.out.println(s1.upload(f));
		
		for (RevAcct revAcct: s1.revAccts.values()) {
			System.out.println(revAcct.number);
			for (Client c: revAcct.clients.values()) {
				System.out.println(c.name);
				System.out.println(c.printAmt());
			}
			
			for (int i = 0; i < 13; i++) {
				System.out.print(revAcct.amounts[i] + ", ");
			}
			System.out.println();
			System.out.println();
		}
		
		
		//System.out.println(s1.revAccts.get(6000).clients.get("aldi").printAmt());
		//System.out.println(s1.revAccts.get(6000).clients.get("bmw").printAmt());
		//System.out.println(s1.revAccts.get(6195).clients.get("bmw").printAmt());
		
		
		// test upload on exp acct
		File f1 = new File("test_copy.csv");
		
		System.out.println(s1.upload(f1));
		
		for (ExpAcct expAcct: s1.expAccts.values()) {
			System.out.println(expAcct.number);
			System.out.println(expAcct.printAmt());
		}
		
	}
	
	@Test
	void testGetClients() {
		// test upload on rev acct
		File f = new File("test.csv");
		
		s1.upload(f);
		
		HashSet<String> expect = new HashSet<String>();
		expect.add("aldi");
		expect.add("bmw");
		expect.add("amazon");
		expect.add("bloomin brand");
		assertEquals(expect, s1.getClients());
	}
	
	@Test
	void testGetClientRev() {
		File f = new File("test.csv");
		
		s1.upload(f);
		
		String[] clients = {"aldi", "bmw"};
		
		String[][] result = s1.getClientsRev(clients);
		System.out.println("test get client rev:");
		
		for(int i = 0; i < result.length; i++) {
			for (int j = 0; j < 14; j++) {
				System.out.print(result[i][j] + ", ");
			}
			System.out.println();
		}
		
		System.out.println("done!");
	}
	
	
	@Test
	void testExportCSV() {
		File f = new File("test.csv");
		
		s1.upload(f);
		
		String[] clients = {"aldi", "bmw"};
		
		String[][] result = s1.getClientsRev(clients);
		
		s1.exportCSV(result, "client");
	}

}
