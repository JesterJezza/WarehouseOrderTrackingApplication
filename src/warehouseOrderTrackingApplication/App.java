package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//SwingAppGUI swg = new SwingAppGUI();
		//swg.swing();
		/*newGUI gui = new newGUI();
		gui.setSize(950,650);
		gui.setVisible(true);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		*/
		//String testSQL = "INSERT INTO customerorder (orderTotal, deliveryAddress, isCheckedOut, eOrderStatus, idcustomer) VALUES ('234','qwerwqerqwe','0','CONFIRMED','1')";
		//warehouseJDBC testJDBC = new warehouseJDBC();
		//testJDBC.createDB(testSQL);
		
		
		boolean flag = false;
		do 
		{
			System.out.println("1. View Customer Orders.");
			System.out.println("2. View Purchase Orders.");
			System.out.println("3. Add Stock Delivery.");
			System.out.println("4. Checkout an Order");
			System.out.println("5. Exit.");
			System.out.print("Please enter the number of the corresponding task to complete: ");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input = "";
			
			try {
				input = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
					createPur = createPur.addPurchaseOrder();
					System.out.print("Would you like to see which items within this purchase Order require porousware treatment? (Y/N): ");
					String choice ="";
					try
					{
						choice = br.readLine();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					switch (choice) {
						case "Y":
							System.out.println("Printing items requiring Porousware treatment...");
							int size = createPur.getOrderItemList().size();
							for (int i=0;i<size;i++)
							{
								int ID = createPur.getOrderItemList().get(i).getItemID();
								WarehouseJDBC p = new WarehouseJDBC();
								ArrayList<Item> nonPorousItems = p.getPorousItems(ID);
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
					WarehouseJDBC r = new WarehouseJDBC();
					ArrayList<CustomerOrder> result = r.getBacklog();
					int size = result.size();
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
						input = br.readLine();
					} 
					catch (IOException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					switch (input){
						case "Y":
							CustomerOrder checkOut = result.get(0);
							checkOut.checkOutOrder(checkOut.getOrderItemList(),checkOut.getCustOrderID());
							checkOut.updateOrderStatus(checkOut.getOrderItemList(), checkOut.getCustOrderID());
							//System.out.println("Order Details:");
							ArrayList<CustomerOrder> checkList = new ArrayList<CustomerOrder>();
							checkList.add(checkOut);
							TSAlgorithm al = new TSAlgorithm();
							al.algorithm(checkOut.getOrderItemList());
							System.out.print("Enter Y when order has been fully picked: ");
							try 
							{
								input = br.readLine();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							
							switch (input){
							case "Y":
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
					System.out.println("Exiting Application.");
					flag = true;
					break;
			}
		}while (flag != true);
	}
}