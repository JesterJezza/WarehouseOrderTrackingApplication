package warehouseOrderTrackingApplication;

public class OrderItem {
	
	private int orderID;
	private int customerID;
	private int itemID;
	private int orderItemQuantity;
	private float orderItemCost;
	private float totalItemCost;

	
	public OrderItem()
	{
		
	}
	
	public OrderItem(int ordID, int custID, int itID, int ordItQ, float ordItC, float totItC)
	{
		orderID = ordID;
		customerID = custID;
		itemID = itID;
		orderItemQuantity = ordItQ;
		orderItemCost = ordItC;
		totalItemCost = totItC;
		
	}

	@Override
	public String toString() {
		return "OrderItem [orderID=" + orderID + ", customerID=" + customerID
				+ ", itemID=" + itemID + ", orderItemQuantity="
				+ orderItemQuantity + ", orderItemCost=" + orderItemCost
				+ ", totalItemCost=" + totalItemCost + "]";
	}
	
}
