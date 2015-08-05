package warehouseOrderTrackingApplication;

import java.util.ArrayList;

public class purchaseOrder {

	private int purOrderID;
	private int supplierID;
	private String supplierName;
	private float orderTotal;
	private ArrayList<OrderItem> orderItemList;

public purchaseOrder() {}

public purchaseOrder(int purOrdID, int suppID, String suppName, float ordTot, ArrayList<OrderItem> ordItLst)
{
	purOrderID = purOrdID;
	supplierID = suppID;
	supplierName = suppName;
	orderTotal = ordTot;
	orderItemList = ordItLst;
}

public void printOrders()
{
	warehouseJDBC orderPrint = new warehouseJDBC();
	ArrayList<purchaseOrder> resultPur = orderPrint.returnPurchOrder();
	printArrayPur(resultPur);
}

public void printArrayPur(ArrayList<purchaseOrder> purList)
{
	System.out.println("Results obtained from Purchase Order Table:");
	System.out.println("#############################################");
	int size = purList.size();
	
	for (int i = 0; i < size; i++)
	{
		purchaseOrder purOrder = purList.get(i);
		System.out.println("Purchase Order ID: "+ String.valueOf(purOrder.purOrderID));
		System.out.println("Supplier ID      : "+ String.valueOf(purOrder.supplierID));
		System.out.println("Supplier name    : "+ purOrder.supplierName);
		System.out.println("Order Total      : "+ String.valueOf(purOrder.orderTotal));
		for (OrderItem o : purOrder.orderItemList) {
			System.out.println(o.toString());
		}
	}
}
}