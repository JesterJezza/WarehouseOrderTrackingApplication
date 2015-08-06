package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PurchaseOrder {

	private int purOrderID;
	private int supplierID;
	private String supplierName;
	private float orderTotal;
	private ArrayList<OrderItem> orderItemList;

public PurchaseOrder() {}

public PurchaseOrder(int purOrdID, int suppID, String suppName, float ordTot, ArrayList<OrderItem> ordItLst)
{
	setPurOrderID(purOrdID);
	setSupplierID(suppID);
	setSupplierName(suppName);
	setOrderTotal(ordTot);
	setOrderItemList(ordItLst);
}

public PurchaseOrder(int suppID, String suppName, float ordTot, ArrayList<OrderItem> ordItLst)
{
	setSupplierID(suppID);
	setSupplierName(suppName);
	setOrderTotal(ordTot);
	setOrderItemList(ordItLst);
}

public int getPurOrderID() {
	return purOrderID;
}

public void setPurOrderID(int purOrderID) {
	this.purOrderID = purOrderID;
}

public int getSupplierID() {
	return supplierID;
}

public void setSupplierID(int supplierID) {
	this.supplierID = supplierID;
}

public String getSupplierName() {
	return supplierName;
}

public void setSupplierName(String supplierName) {
	this.supplierName = supplierName;
}

public float getOrderTotal() {
	return orderTotal;
}

public void setOrderTotal(float orderTotal) {
	this.orderTotal = orderTotal;
}

public ArrayList<OrderItem> getOrderItemList() {
	return orderItemList;
}

public void setOrderItemList(ArrayList<OrderItem> orderItemList) {
	this.orderItemList = orderItemList;
}

public void printOrders()
{
	WarehouseJDBC orderPrint = new WarehouseJDBC();
	ArrayList<PurchaseOrder> resultPur = orderPrint.returnPurchOrder();
	printArrayPur(resultPur);
}

public void printArrayPur(ArrayList<PurchaseOrder> purList)
{
	System.out.println("#############################################");
	System.out.println("Results obtained from Purchase Order Table:");
	System.out.println("#############################################");
	int size = purList.size();
	
	for (int i = 0; i < size; i++)
	{
		PurchaseOrder purOrder = purList.get(i);
		System.out.println("List Item "+ String.valueOf(i+1)+":");
		System.out.println("Purchase Order ID  : "+ String.valueOf(purOrder.purOrderID));
		System.out.println("Supplier ID        : "+ String.valueOf(purOrder.supplierID));
		System.out.println("Supplier name      : "+ purOrder.supplierName);
		System.out.println("Order Total        : £"+ String.valueOf(purOrder.orderTotal));
		System.out.println("######################");
		System.out.println("Order Item Details");
		ArrayList<OrderItem> orderList = purOrder.orderItemList;
		int size2 = orderList.size();
		for (int j = 0; j < size2; j++) {
			OrderItem ordItem = orderList.get(j);
			System.out.println("######################");
			System.out.println("Item ID            : "+ String.valueOf(ordItem.getItemID()));
			System.out.println("Order Item Quantity: "+ String.valueOf(ordItem.getOrderItemQuantity()));
			System.out.println("Order Item Cost    : £"+ String.valueOf(ordItem.getOrderItemCost()));
			System.out.println("Total Item Cost    : £"+ String.valueOf(ordItem.getTotalItemCost()));
		}
		System.out.println("#############################################");
	}
}

public void addPurchaseOrder()
{
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	try {
		System.out.println("Please enter the Supplier ID:");
		int suppID = Integer.parseInt(br.readLine());
		System.out.println("Please enter the Supplier Name:");
		String suppName = br.readLine();
		System.out.println("Please enter the total cost of the Purchase Order:");
		float purCost = Float.parseFloat(br.readLine());
		System.out.println("How many different items are included on this Purchase Order?");
		int itemCount = Integer.parseInt(br.readLine());
		ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
		
		for (int i = 0; i < itemCount; i++)
		{
			System.out.println("Item number "+String.valueOf(i+1)+":");
			System.out.println("Please enter the itemID:");
			int itID = Integer.parseInt(br.readLine());
			System.out.println("Please enter the Quantity of this item:");
			int quan = Integer.parseInt(br.readLine());
			System.out.println("Please enter the Unit Cost of this item");
			int uCost = Integer.parseInt(br.readLine());
			float totItem = (quan * uCost);
			OrderItem o = new OrderItem(suppID,itID,quan,uCost,totItem);
			itemList.add(o);
		}
		
			PurchaseOrder addPurch = new PurchaseOrder(suppID, suppName, purCost, itemList);
			WarehouseJDBC createPurch = new WarehouseJDBC();
			createPurch.createPurchaseOrderDB(addPurch);		
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}

}