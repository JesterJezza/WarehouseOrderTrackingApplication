package warehouseOrderTrackingApplication;

public class OrderItem {
	
	private int orderID;
	private int customerID;
	private int itemID;
	private int orderItemQuantity;
	private float orderItemCost;
	private float totalItemCost;
	
	public OrderItem(){}
	
	public OrderItem(int ordID, int custID, int itID, int ordItQ, float ordItC, float totItC)
	{
		setOrderID(ordID);
		setCustomerID(custID);
		setItemID(itID);
		setOrderItemQuantity(ordItQ);
		setOrderItemCost(ordItC);
		setTotalItemCost(totItC);
	}
	
	public OrderItem(int custID, int itID, int ordItQ, float ordItC, float totItC)
	{
		setCustomerID(custID);
		setItemID(itID);
		setOrderItemQuantity(ordItQ);
		setOrderItemCost(ordItC); 
		setTotalItemCost(totItC);
	}

	@Override
	public String toString() {
		return "OrderItem [orderID=" + getOrderID() + ", customerID=" + customerID
				+ ", itemID=" + getItemID() + ", orderItemQuantity="
				+ getOrderItemQuantity() + ", orderItemCost=" + getOrderItemCost()
				+ ", totalItemCost=" + getTotalItemCost() + "]";
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getOrderItemQuantity() {
		return orderItemQuantity;
	}

	public void setOrderItemQuantity(int orderItemQuantity) {
		this.orderItemQuantity = orderItemQuantity;
	}

	public float getOrderItemCost() {
		return orderItemCost;
	}

	public void setOrderItemCost(float orderItemCost) {
		this.orderItemCost = orderItemCost;
	}

	public float getTotalItemCost() {
		return totalItemCost;
	}

	public void setTotalItemCost(float totalItemCost) {
		this.totalItemCost = totalItemCost;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public int getCustomerID() {
		return customerID;
	}
	
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
}
