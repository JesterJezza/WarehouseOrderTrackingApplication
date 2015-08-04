package warehouseOrderTrackingApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class customerOrder {
	
	private int custOrderID;
	//private array of items needs adding here
	private float orderTotal;
	private String deliveryAddress;
	private boolean isCheckedOut;
	private orderStatus eOrderStatus;
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
		ArrayList<String> custResult = new ArrayList<String>();
		String sqlReadCustomer = "SELECT * FROM customerOrder";
		custResult = orderPrint.readDB(sqlReadCustomer);
		//HANDLE PRINT OF ARRAYLIST
		System.out.println("##################################################");
		System.out.println("Records obtained from the Customer Order table:");
		printArrayList(custResult);
		
		ArrayList<String> purchResult = new ArrayList<String>();
		String sqlReadPurchase = "SELECT * FROM purchaseOrder";
		purchResult = orderPrint.readDB(sqlReadPurchase);
		System.out.println("##################################################");
		System.out.println("Records obtained from the Purchase Order table:");
		printArrayList(purchResult);
		
	}
	
	public void printArrayList(ArrayList<String> arrayList)
	{
		int loop = arrayList.size();
		for (int i = 0; i < loop; i++)
		{
			System.out.println(arrayList.get(i).toString());
		}
		System.out.println("##################################################");
		System.out.println("End of records.");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
