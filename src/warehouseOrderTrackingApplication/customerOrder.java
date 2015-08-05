package warehouseOrderTrackingApplication;
import java.util.ArrayList;

public class customerOrder {
	
	private int custOrderID;
	private int custID;
	private float orderTotal;
	private String deliveryAddress;
	private boolean isCheckedOut = false;
	private orderStatus eOrderStatus;
	private int employeeID;
	private ArrayList<OrderItem> orderItemList;
	
	public customerOrder()
	{
		
	}
	public customerOrder(int oID, int cID, float oTotal, String devAd, boolean checkOut, String oS, int emID, ArrayList<OrderItem> orItLst)
	{
		custOrderID = oID;
		custID = cID;
		orderTotal = oTotal;
		deliveryAddress = devAd;
		isCheckedOut = checkOut;
		eOrderStatus = orderStatus.valueOf(oS);
		employeeID = emID;
		orderItemList = orItLst;
	}
		
	public enum orderStatus
	{
		CONFIRMED, INQUEUE, PICKING, PACKING, DISPATCHING, DISPATCHED
	}
	
	public void orderCheckOut()
	{
		this.isCheckedOut = true;
	}
	
	public void updateOrderStatus()
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
				switchInt = 0;
				break;
			case 3:
				eOrderStatus = orderStatus.PACKING;
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
		warehouseJDBC orderPrint = new warehouseJDBC();
		ArrayList<customerOrder> resultCust = orderPrint.returnCustOrder();
		printArrayCust(resultCust);
	}
	
	public void printArrayCust(ArrayList<customerOrder> custList)
	{
		System.out.println("Results obtained from Customer Order Table:");
		System.out.println("#############################################");
		int size = custList.size();
		
		for (int i = 0; i < size; i++)
		{
			customerOrder custOrder = custList.get(i);
			System.out.println("Customer Order ID: "+ String.valueOf(custOrder.custOrderID));
			System.out.println("Customer ID      : "+ String.valueOf(custOrder.custID));
			System.out.println("Delivery Address : "+ custOrder.deliveryAddress);
			System.out.println("Order Status     : "+ custOrder.eOrderStatus.toString());
			System.out.println("Employee ID      : "+ String.valueOf(custOrder.employeeID));
			System.out.println("Order Cost Total : "+ String.valueOf(custOrder.orderTotal));
			System.out.println("Order Checked Out: "+ String.valueOf(custOrder.isCheckedOut));
			System.out.println("#############################################");
		}
		
	}
	

}
