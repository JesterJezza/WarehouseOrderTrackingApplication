package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		
		//***********************TO RUN PROGRAM IN GUI MODE***********************************
		//***************Uncomment code block to run in gui mode******************************
		//Create instance of newGUI, set dimentions, make it visible and set what to do when user presses the 'X' button
		/*newGUI gui = new newGUI();
		gui.setSize(1200,800);
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
		//String testSQL = "INSERT INTO customerorder (orderTotal, deliveryAddress, isCheckedOut, eOrderStatus, idcustomer) VALUES ('234','qwerwqerqwe','0','CONFIRMED','1')";
		//warehouseJDBC testJDBC = new warehouseJDBC();
		//testJDBC.createDB(testSQL);
		
		//***********************TO RUN PROGRAM AS A CONSOLE APP******************************
		//*******************Uncomment code block to run as console app***********************
		//Variable for do-while loop condition
		boolean flag = false;
		do 
		{
			//Print menu options
			System.out.println("1. View Customer Orders.");
			System.out.println("2. View Purchase Orders.");
			System.out.println("3. Add Stock Delivery.");
			System.out.println("4. Checkout an Order");
			System.out.println("5. Exit.");
			System.out.print("Please enter the number of the corresponding task to complete: ");
			//Initialise bufferedreader to take user input
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = "";
			
			try {
				//Set string to user input
				input = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Switch-case options for menu functionality
			switch (input) {
				case "1": 
					System.out.println("Printing Customer Orders.....");
					//Create instance of CustomerOrder class, then call printOrders()
					CustomerOrder custPrint = new CustomerOrder();
					custPrint.printOrders();
					break;
				case "2":
					System.out.println("Printing Purchase Orders.....");
					//Create instance of PurchaseOrder class, then call printOrders()
					PurchaseOrder purPrint = new PurchaseOrder();
					purPrint.printOrders();
					break;
				case "3":
					System.out.println("Adding Stock Delivery to IMS.....");
					//Create instance of PurchaseOrder class
					PurchaseOrder createPur = new PurchaseOrder();
					//Call function to allow user to add stock delivery to the DB
					createPur = createPur.addPurchaseOrder();
					System.out.print("Would you like to see which items within this purchase Order require porousware treatment? (Y/N): ");
					String choice ="";
					try
					{
						//Set choice as user input
						choice = br.readLine();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					//Switch-case statement for displaying porousware treatment items
					switch (choice) {
						case "Y":
							//Return items within this purchase order that require porousware treatment
							System.out.println("Printing items requiring Porousware treatment...");
							int ID = createPur.getOrderItemList().get(0).getItemID();
							//Create instance of WarehouseJDBC class and call getPorousItems()
							WarehouseJDBC p = new WarehouseJDBC();
							ArrayList<Item> nonPorousItems = p.getPorousItems(ID);
							int size = nonPorousItems.size();
							//Loop through each item in nonPorousItems and print info
							for (int i=0;i<size;i++)
							{
								Item item = nonPorousItems.get(i);
								System.out.println("Item ID      : " +String.valueOf(item.getItemID()));
								System.out.println("Item Name    : " +item.getItemName());
								System.out.println("Item Desc.   : " +item.getItemDesc());
								System.out.println("Item Quantity: " +String.valueOf(createPur.getOrderItemList().get(i).getOrderItemQuantity()));
							}
							break;
						case "N":
							break;
					}
					break;
				case "4":
					System.out.println("#############################");
					System.out.println("Printing backlog of orders:");
					//Create instance of WarehouseJDBC to call getBacklog()
					WarehouseJDBC r = new WarehouseJDBC();
					ArrayList<CustomerOrder> result = r.getBacklog();
					int size = result.size();
					//Loop through each item in result and print info
					for (int i = 0;i<size;i++)
					{
						CustomerOrder custOrder = result.get(i);
						System.out.println("#############################");
						System.out.println("Customer Order ID  : "+ String.valueOf(custOrder.getCustOrderID()));
						System.out.println("Customer ID        : "+ String.valueOf(custOrder.getCustID()));
						System.out.println("Delivery Address   : "+ custOrder.getDeliveryAddress());
						System.out.println("Order Status       : "+ custOrder.geteOrderStatus().toString());
						System.out.println("Employee ID        : "+ String.valueOf(custOrder.getEmployeeID()));
						System.out.println("Order Cost Total   : £"+ String.valueOf(custOrder.getOrderTotal()));
						System.out.println("#############################");
					}
					System.out.print("Would you like to check out an order from the backlog? (Y/N): ");
					try 
					{
						//User choice to check out an order or not, set input to user input
						input = br.readLine();
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//Switch-case statement for checking out an order
					switch (input){
						case "Y":
							//If user selects to check out an order, the first order in this list (FIFO) is checked out for them to pick
							//Create instance of CustomerOrder = first in list
							CustomerOrder checkOut = result.get(0);
							//Call function to checkout the order, changing the order status
							checkOut.checkOutOrder(checkOut.getOrderItemList(),checkOut.getCustOrderID());
							//Call functions to change the order status and add stock allocation (called inside updateOrderStatus)
							checkOut.updateOrderStatus(checkOut.getOrderItemList(), checkOut.getCustOrderID());
							//System.out.println("Order Details:");
							//Create instance of Travelling Salesman Algorithm class to perform algorithm on order that has been checked out
							TSAlgorithm al = new TSAlgorithm();
							//Call function to carry out algorithm, passing in the list of items for the checked out order
							al.algorithm(checkOut.getOrderItemList());
							//User option enter when order has been picked
							System.out.print("Enter Y when order has been fully picked: ");
							try 
							{
								//Set input = user input
								input = br.readLine();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							
							switch (input){
							case "Y":
								//When order has been fully picked, remove the stock allocation and update orderstatus
								checkOut.removeStockAllocation(checkOut.getOrderItemList(), checkOut.getCustOrderID());
								checkOut.updateOrderStatus(checkOut.getOrderItemList(), checkOut.getCustOrderID());
								break;
							case "":
								break;
							}
							break;
						case "N":
							break;
					}
					break;
				case "5":
					//When the user wants to exit the application, set condition to break while loop
					System.out.println("Exiting Application.");
					flag = true;
					break;
			}
		}while (flag != true); //While loop to keep program running until user exits
	}
}