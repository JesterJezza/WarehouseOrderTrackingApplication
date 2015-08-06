package warehouseOrderTrackingApplication;

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
	purOrderID = purOrdID;
	supplierID = suppID;
	supplierName = suppName;
	orderTotal = ordTot;
	orderItemList = ordItLst;
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
		System.out.println("Order Total        : "+ String.valueOf(purOrder.orderTotal));
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
}