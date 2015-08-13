package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class PurchaseOrder {
	//Class member variables
	private int purOrderID;
	private int supplierID;
	private String supplierName;
	private float orderTotal;
	private ArrayList<OrderItem> orderItemList;
//Default constructor
public PurchaseOrder() {}

//Other constructors
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

//Getters and setters for object member variables
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

//Function to printOrders in console mode
public void printOrders()
{
	//Create instance of WarehouseJDBC class to call returnPurchOrder()
	WarehouseJDBC orderPrint = new WarehouseJDBC();
	//Initialise resultPur to the ArrayList of PurchaseOrders returned by returnPurchOrder()
	ArrayList<PurchaseOrder> resultPur = orderPrint.returnPurchOrder();
	//Call function to print ArrayList to screen
	printArrayPur(resultPur);
}

//Function to print ArrayList of Purchase Orders
public void printArrayPur(ArrayList<PurchaseOrder> purList)
{
	System.out.println("#############################################");
	System.out.println("Results obtained from Purchase Order Table:");
	System.out.println("#############################################");
	//Get size of the ArrayList passed into function
	int size = purList.size();
	//Loop through each Purchase Order in ArrayList and print info
	for (int i = 0; i < size; i++)
	{
		PurchaseOrder purOrder = purList.get(i);
		System.out.println("List Item "+ String.valueOf(i+1)+":");
		System.out.println("Purchase Order ID  : "+ String.valueOf(purOrder.purOrderID));
		System.out.println("Supplier ID        : "+ String.valueOf(purOrder.supplierID));
		System.out.println("Supplier name      : "+ purOrder.supplierName);
		System.out.println("Order Total        : £"+ String.valueOf(purOrder.orderTotal));
		System.out.println("###########################");
		System.out.println("Order Item Details");
		//Loop through each OrderItem in ArrayList inside each PurchaseOrder
		ArrayList<OrderItem> orderList = purOrder.orderItemList;
		int size2 = orderList.size();
		for (int j = 0; j < size2; j++) {
			OrderItem ordItem = orderList.get(j);
			System.out.println("###########################");
			System.out.println("Item ID            : "+ String.valueOf(ordItem.getItemID()));
			System.out.println("Order Item Quantity: "+ String.valueOf(ordItem.getOrderItemQuantity()));
			System.out.println("Order Item Cost    : £"+ String.valueOf(ordItem.getOrderItemCost()));
			System.out.println("Total Item Cost    : £"+ String.valueOf(ordItem.getTotalItemCost()));
		}
		System.out.println("#############################################");
	}
}

//Function to add a PurchaseOrder to the DB, called from menu option 3 in console mode
public PurchaseOrder addPurchaseOrder()
{
	//Create instance of PurchaseOrder class
	PurchaseOrder addPurch = new PurchaseOrder();
	//Initialise buffered reader to accept user input
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	//Allow user to enter details about the PurchaseOrder
	try {
		System.out.print("Please enter the Supplier ID: ");
		int suppID = Integer.parseInt(br.readLine());
		System.out.print("Please enter the Supplier Name: ");
		String suppName = br.readLine();
		System.out.print("Please enter the total cost of the Purchase Order: ");
		float purCost = Float.parseFloat(br.readLine());
		System.out.print("Number of different items included on this Purchase Order: ");
		int itemCount = Integer.parseInt(br.readLine());
		ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
		//Loop through for the number of items that need to be added to the PurchaseOrder
		for (int i = 0; i < itemCount; i++)
		{
			System.out.println("Item number "+String.valueOf(i+1)+":");
			System.out.print("Please enter the itemID: ");
			int itID = Integer.parseInt(br.readLine());
			System.out.print("Please enter the Quantity of this item: ");
			int quan = Integer.parseInt(br.readLine());
			System.out.print("Please enter the Unit Cost of this item: ");
			int uCost = Integer.parseInt(br.readLine());
			//Calculate totalItem cost
			float totItem = (quan * uCost);
			OrderItem o = new OrderItem(suppID,itID,quan,uCost,totItem);
			itemList.add(o);
		}
			//Initialise addPurch to accept values that user has entered
			addPurch = new PurchaseOrder(suppID, suppName, purCost, itemList);
			//Create instance of WarehouseJDBC class to insert PurchaseOrder into database
			WarehouseJDBC createPurch = new WarehouseJDBC();
			createPurch.createPurchaseOrderDB(addPurch);		
	} catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//Return the PurchaseOrder that has been created to be used later
	return addPurch;
}

}