package warehouseOrderTrackingApplication;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.IOException;

public class CustomerOrder {
	
	private int custOrderID;
	private int custID;
	private float orderTotal;
	private String deliveryAddress;
	private boolean isCheckedOut = false;
	private orderStatus eOrderStatus;
	private int employeeID;
	private ArrayList<OrderItem> orderItemList;
	
	//Getters and setters for class member variables
	public int getCustOrderID() {
		return custOrderID;
	}

	public void setCustOrderID(int custOrderID) {
		this.custOrderID = custOrderID;
	}

	public int getCustID() {
		return custID;
	}

	public void setCustID(int custID) {
		this.custID = custID;
	}

	public float getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public boolean isCheckedOut() {
		return isCheckedOut;
	}

	public void setCheckedOut(boolean isCheckedOut) {
		this.isCheckedOut = isCheckedOut;
	}

	public orderStatus geteOrderStatus() {
		return eOrderStatus;
	}

	public void seteOrderStatus(orderStatus eOrderStatus) {
		this.eOrderStatus = eOrderStatus;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public ArrayList<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(ArrayList<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	//Default constructor
	public CustomerOrder() {}
	
	//Override of toString() used in testing
	@Override
	public String toString() {
		return "CustomerOrder [custOrderID=" + custOrderID + ", custID="
				+ custID + ", orderTotal=" + orderTotal + ", deliveryAddress="
				+ deliveryAddress + ", isCheckedOut=" + isCheckedOut
				+ ", eOrderStatus=" + eOrderStatus + ", employeeID="
				+ employeeID + ", orderItemList=" + orderItemList + "]";
	}
	
	//Other Constructors
	public CustomerOrder(int oID, int cID, float oTotal, String devAd, boolean checkOut, String oS, int emID, ArrayList<OrderItem> orItLst)
	{
		setCustOrderID(oID);
		setCustID(cID);
		setOrderTotal(oTotal);
		setDeliveryAddress(devAd);
		isCheckedOut = checkOut;
		seteOrderStatus(orderStatus.valueOf(oS));
		setEmployeeID(emID);
		setOrderItemList(orItLst);
	}
	
	public CustomerOrder(int oID, int cID, float oTotal, String devAd, String oS, ArrayList<OrderItem> itLst)
	{
		setCustOrderID(oID);
		setCustID(cID);
		setOrderTotal(oTotal);
		setDeliveryAddress(devAd);
		seteOrderStatus(orderStatus.valueOf(oS));
		setOrderItemList(itLst);
	}
	
	//Enum used for order status, so that it can only ever be these values
	public enum orderStatus
	{
		CONFIRMED, PICKING, PACKING, DISPATCHING, DISPATCHED
	}
	
	//Funciton to checkout an order
	public void checkOutOrder(ArrayList<OrderItem> itemList, int orderID)
	{
		//Update the order status of the object which called the function
		this.updateOrderStatus(itemList, orderID);
		//Create instance of WarehouseJDBC class to change this value in the database
		WarehouseJDBC jdbc = new WarehouseJDBC();
		jdbc.checkOutOrder(orderID, this.getCustID(), String.valueOf(this.geteOrderStatus()));
	}
	//Called by previous function
	public void updateOrderStatus(ArrayList<OrderItem> itemList, int orderID)
	{
		//Code to set the path an order can take, it will only change from CONFIRMED to PICKING, not CONFIRMED to DISPATCHING for example
		int switchInt = 0;
		if (this.geteOrderStatus() == orderStatus.CONFIRMED)
			switchInt = 1;
		else if (this.geteOrderStatus() == orderStatus.PICKING)
			switchInt = 2;
		else if (this.geteOrderStatus() == orderStatus.PACKING)
			switchInt = 3;
		else if (this.geteOrderStatus() == orderStatus.DISPATCHING)
			switchInt = 4;
		else
			switchInt = 5;
		
		//Switch-case for what to do depending on the current order status
		switch (switchInt) {
			case 1:
				//If the order is CONFIRMED, change to PICKING, checkout the order and update the stock level
				eOrderStatus = orderStatus.PICKING;
				this.setCheckedOut(true);
				updateStockLevel(this.getOrderItemList(), this.custOrderID);
				switchInt = 0;
				break;
			case 2:
				//If the order is PICKING, change to PACKING and remove the allocated stock for this order in the database
				eOrderStatus = orderStatus.PACKING;
				removeStockAllocation(itemList, orderID);
				WarehouseJDBC jdbc = new WarehouseJDBC();
				jdbc.checkOutOrder(this.getCustOrderID(), this.getCustID(), String.valueOf(this.geteOrderStatus()));
				switchInt = 0;
				break;
			case 3:
				eOrderStatus = orderStatus.DISPATCHING;
				switchInt = 0;
				break;
			case 4:
				eOrderStatus = orderStatus.DISPATCHED;
				switchInt = 0;
				break;
			case 5:
				System.out.println("Could not update order status, order is already marked as 'DISPATCHED'!");
				switchInt = 0;
				break;
		}
	}
	
	//Funciton to print customer orders
	public void printOrders()
	{
		//Create instance of the WarehouseJDBC class, call function in database to return all customer orders
		WarehouseJDBC orderPrint = new WarehouseJDBC();
		ArrayList<CustomerOrder> resultCust = orderPrint.returnCustOrder();
		printArrayCust(resultCust);
	}
	//Called by function above
	public void printArrayCust(ArrayList<CustomerOrder> custList)
	{
		System.out.println("#############################################");
		System.out.println("Results obtained from Customer Order Table:");
		System.out.println("#############################################");
		//Get size of ArrayList passed into function
		int size = custList.size();
		//Loop throught for size of ArrayList and print info of each order
		for (int i = 0; i < size; i++)
		{
			CustomerOrder custOrder = custList.get(i);
			System.out.println("Customer Order ID  : "+ String.valueOf(custOrder.custOrderID));
			System.out.println("Customer ID        : "+ String.valueOf(custOrder.custID));
			System.out.println("Delivery Address   : "+ custOrder.deliveryAddress);
			System.out.println("Order Status       : "+ custOrder.eOrderStatus.toString());
			System.out.println("Employee ID        : "+ String.valueOf(custOrder.employeeID));
			System.out.println("Order Cost Total   : £"+ String.valueOf(custOrder.orderTotal));
			System.out.println("Order Checked Out  : "+ String.valueOf(custOrder.isCheckedOut));
			System.out.println("######################");
			System.out.println("Order Item details:");
			ArrayList<OrderItem> orderList = custOrder.orderItemList;
			//Get number of items within the order, and loop through and print info for each item
			int size2 = orderList.size();
			
			for (int j = 0; j < size2; j++) 
			{
				OrderItem ordItem = orderList.get(j);
				System.out.println("######################");
				System.out.println("Item ID            : "+ String.valueOf(ordItem.getItemID()));
				System.out.println("Order Item Quantity: "+ String.valueOf(ordItem.getOrderItemQuantity()));
				System.out.println("Order Item Cost    : £"+ String.valueOf(ordItem.getOrderItemCost()));
				System.out.println("Total Item Cost    : £"+ String.valueOf(ordItem.getTotalItemCost()));
			}
		}
		//Option to print order backlog
		System.out.println("Would you like to see the list of Customer Orders currently being worked upon? (Y/N)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input ="";
		try 
		{
			//Set input to user input
			input = br.readLine();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		//Switch-case statement for orders currently being worked upon
		switch (input) 
		{
			case "Y": 
				System.out.println("#############################################");
				System.out.println("Customer Orders currently being worked upon:");
				System.out.println("#############################################");
				
				for (int i = 0; i < size; i++)
				{
					//From all of the orders that have been returned, only print orders where checkedout = true
					CustomerOrder workedOn = custList.get(i);
					if (workedOn.isCheckedOut == true)
					{
						System.out.println("Customer Order ID  : "+ String.valueOf(workedOn.custOrderID));
						System.out.println("Customer ID        : "+ String.valueOf(workedOn.custID));
						System.out.println("Order Status       : "+ workedOn.eOrderStatus.toString());
						System.out.println("######################");							
					}
				}
				
				System.out.println("#############################################");
				break;
			case "N":
				break;
			case "":
				break;
		}
	}
	
	//Function to update stock level in the database
	public void updateStockLevel(ArrayList<OrderItem> itemList, int orderID)
	{
		//Get size of arraylist passed into function
		int size = itemList.size();
		//Loop through each item
		for (int i=0;i<size;i++)
		{
			//Get OrderItem at current index
			OrderItem item = itemList.get(i);
			//Create instance of WarehouseJDBC for database manipulation
			WarehouseJDBC jdbc = new WarehouseJDBC();
			//Get the current stock level, this is used later for the correct stock adjustment
			int currentStock = jdbc.getCurrentStock(item.getItemID());
			//Call function to adjust stock levels
			jdbc.updateStock(item, currentStock);
		}
	}
	
	//Function to remove stock that has been allocated to an order in the database (when order is picked)
	public void removeStockAllocation(ArrayList<OrderItem> itemList, int orderID)
	{
		
		int size = itemList.size();
		for (int i=0;i<size;i++)
		{
			OrderItem item = itemList.get(i);
			WarehouseJDBC remAlloc = new WarehouseJDBC();
			int quantity = item.getOrderItemQuantity();
			int itemID = item.getItemID();
			remAlloc.removeStockAllocation(itemID, quantity);
		}
	}
}
