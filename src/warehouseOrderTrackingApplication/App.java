package warehouseOrderTrackingApplication;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			SwingAppGUI swg = new SwingAppGUI();
			swg.swing();
			
			
			//String testSQL = "INSERT INTO customerorder (orderTotal, deliveryAddress, isCheckedOut, eOrderStatus, idcustomer) VALUES ('234','qwerwqerqwe','0','CONFIRMED','1')";
			//warehouseJDBC testJDBC = new warehouseJDBC();
			//testJDBC.createDB(testSQL);
			
			customerOrder test = new customerOrder();
			test.printOrders();
			
			purchaseOrder test2 = new purchaseOrder();
			test2.printOrders();
			//System.out.println("FINISHED.");
	}

}
