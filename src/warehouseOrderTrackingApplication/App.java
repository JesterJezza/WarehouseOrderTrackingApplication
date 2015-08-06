package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//SwingAppGUI swg = new SwingAppGUI();
		//swg.swing();
		
		
		//String testSQL = "INSERT INTO customerorder (orderTotal, deliveryAddress, isCheckedOut, eOrderStatus, idcustomer) VALUES ('234','qwerwqerqwe','0','CONFIRMED','1')";
		//warehouseJDBC testJDBC = new warehouseJDBC();
		//testJDBC.createDB(testSQL);
		
		System.out.println("1. View Customer Orders.");
		System.out.println("2. View Purchase Orders.");
		System.out.println("3. Add Stock Delivery.");
		System.out.println("4. Exit.");
		System.out.print("Please enter the number of the corresponding task to complete: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		
		try {
			input = br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		do 
		{
			switch (input) {
				case "1": 
					System.out.println("Printing Customer Orders.....");
					CustomerOrder custPrint = new CustomerOrder();
					custPrint.printOrders();
					break;
				case "2":
					System.out.println("Printing Purchase Orders.....");
					PurchaseOrder purPrint = new PurchaseOrder();
					purPrint.printOrders();
					break;
				case "3":
					System.out.println("Adding Stock Delivery to IMS.....");
					PurchaseOrder createPur = new PurchaseOrder();
					createPur.addPurchaseOrder();
					break;
				case "4":
					break;
			}
		}while (input != "4");	
	}
}