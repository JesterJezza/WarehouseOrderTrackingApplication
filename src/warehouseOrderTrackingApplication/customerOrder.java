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

	public CustomerOrder() {}
	
	@Override
	public String toString() {
		return "CustomerOrder [custOrderID=" + custOrderID + ", custID="
				+ custID + ", orderTotal=" + orderTotal + ", deliveryAddress="
				+ deliveryAddress + ", isCheckedOut=" + isCheckedOut
				+ ", eOrderStatus=" + eOrderStatus + ", employeeID="
				+ employeeID + ", orderItemList=" + orderItemList + "]";
	}
	
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
		
	public enum orderStatus
	{
		CONFIRMED, INQUEUE, PICKING, PACKING, DISPATCHING, DISPATCHED
	}
	
	public void updateOrderStatus(ArrayList<OrderItem> itemList, int orderID)
	{
		int switchInt = 0;
		if (eOrderStatus == orderStatus.CONFIRMED)
			switchInt = 1;
		else if (eOrderStatus == orderStatus.INQUEUE)
			switchInt = 2;
		else if (eOrderStatus == orderStatus.PICKING)
			switchInt = 3;
		else if (eOrderStatus == orderStatus.PACKING)
			switchInt = 4;
		else if (eOrderStatus == orderStatus.DISPATCHING)
			switchInt = 5;
		else
			switchInt = 6;
		
		switch (switchInt) {
			case 1:
				eOrderStatus = orderStatus.INQUEUE;
				switchInt = 0;
				break;
			case 2: 
				eOrderStatus = orderStatus.PICKING;
				this.setCheckedOut(true);
				updateStockLevel(itemList, orderID);
				switchInt = 0;
				break;
			case 3:
				eOrderStatus = orderStatus.PACKING;
				removeStockAllocation(itemList, orderID);
				switchInt = 0;
				break;
			case 4:
				eOrderStatus = orderStatus.DISPATCHING;
				switchInt = 0;
				break;
			case 5:
				eOrderStatus = orderStatus.DISPATCHED;
				switchInt = 0;
				break;
			case 6:
				System.out.println("Could not update order status, order is already marked as 'DISPATCHED'!");
				switchInt = 0;
				break;
		}
	}
	
	public void printOrders()
	{
		WarehouseJDBC orderPrint = new WarehouseJDBC();
		ArrayList<CustomerOrder> resultCust = orderPrint.returnCustOrder();
		printArrayCust(resultCust);
	}
	
	public void printArrayCust(ArrayList<CustomerOrder> custList)
	{
		System.out.println("#############################################");
		System.out.println("Results obtained from Customer Order Table:");
		System.out.println("#############################################");
		int size = custList.size();
		
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
			System.out.println("#############################################");
		}
		
		System.out.println("Would you like to see the list of Customer Orders currently being worked upon? (Y/N)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input ="";
		try 
		{
			input = br.readLine();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch (input) 
		{
			case "Y": 
				System.out.println("#############################################");
				System.out.println("Customer Orders currently being worked upon:");
				System.out.println("#############################################");
				
				for (int i = 0; i < size; i++)
				{
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
	
	public void updateStockLevel(ArrayList<OrderItem> itemList, int orderID)
	{
		int size = itemList.size();
		for (int i=0;i<size;i++)
		{
			OrderItem item = itemList.get(i);
			WarehouseJDBC upStock = new WarehouseJDBC();
			int currentStock = upStock.getCurrentStock(item.getItemID());
			upStock.updateStock(item, currentStock);
		}
	}
	
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
