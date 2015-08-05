package warehouseOrderTrackingApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class warehouseJDBC {

	static final String jdbcDriver = "com.mysql.jdbc.Driver";
	static final String dbURL = "jdbc:mysql://127.0.0.1:3306/mydb";
	static final String user = "jwilks";
	static final String pass = "netbuilder";

	public ArrayList<OrderItem> getItems(String cID, String oID)
	{
		String custOrderID = oID;
		String custID = cID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
		
		try
		{
			Class.forName(jdbcDriver);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL,user,pass);
			stmt = conn.createStatement();
			
			rs = stmt.executeQuery("SELECT * FROM orderitem WHERE orderID = " + String.valueOf(custOrderID) + " AND customerID = " + String.valueOf(custID));
			
			try 
			{
				while(rs.next())
				{
					int orderID = rs.getInt("orderID");
					int customerID = rs.getInt("customerID");
					int itemID = rs.getInt("itemID");
					int orderItemQuantity = rs.getInt("orderItemQuantity");
					float orderItemCost = rs.getFloat("orderItemCost");
					float totalItemCost = rs.getFloat("totalItemCost");
					OrderItem getItem = new OrderItem(orderID, customerID, itemID, orderItemQuantity,orderItemCost, totalItemCost);
					itemList.add(getItem);
				}
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			rs.close();
			
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return itemList;
	}
	
	public ArrayList<customerOrder> returnCustOrder()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		ArrayList<customerOrder> custList = new ArrayList<customerOrder>();
		
		try 
		{
			Class.forName(jdbcDriver);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customerorder");
			
			try 
			{
				while(rs.next())
				{					
					int custOrderID = rs.getInt("idcustomerOrder");
					int custID = rs.getInt("idcustomer");
					float orderTotal = rs.getFloat("orderTotal");
					String deliveryAddress = rs.getString("deliveryAddress");
					boolean isCheckedOut = rs.getBoolean("isCheckedOut");
					String eOrderStatus = rs.getString("eOrderStatus");
					int employeeID = rs.getInt("employeeID");

					ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();	
					itemList = getItems(String.valueOf(custID), String.valueOf(custOrderID));
					customerOrder getCust = new customerOrder(custOrderID, custID, orderTotal, deliveryAddress, isCheckedOut, eOrderStatus, employeeID, itemList);
					custList.add(getCust);
					
				}
				rs.close();
				
				System.out.println("Database connection closed");
			}		
			catch (SQLException se)
			{
				se.printStackTrace();
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return custList;
	}
	
	public ArrayList<purchaseOrder> returnPurchOrder()
	{
		Connection conn = null;
		Statement stmt = null;
		Statement stmt2 = null;
		ResultSet rs;
		ResultSet rs2;
		ArrayList<purchaseOrder> purchaseList = new ArrayList<purchaseOrder>();
		try 
		{
			Class.forName(jdbcDriver);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			stmt2 = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM purchaseorder");
			
			try 
			{
				while(rs.next())
				{					
					int purchaseOrderID = rs.getInt("purOrderID");
					int supplierID = rs.getInt("supplierID");
					String supplierName = rs.getString("supplierName");
					float orderTotal = rs.getFloat("orderTotal");
					
					ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();	
					itemList = getItems(String.valueOf(supplierID), String.valueOf(purchaseOrderID));
					purchaseOrder getPurchase = new purchaseOrder(purchaseOrderID, supplierID, supplierName, orderTotal, itemList);
					purchaseList.add(getPurchase);
					
				}
				rs.close();
				System.out.println("Database connection closed");
			}		
			catch (SQLException se)
			{
				se.printStackTrace();
			}
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return purchaseList;
	}
	
	public void readDB(String sqlRead)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL,user, pass);
	
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlRead);
			
			rs.close();
			System.out.println("Connection to the database has been closed.");
			
			//rs.close();
		}catch (SQLException sqle){
			sqle.printStackTrace();
			//return null;
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if ( stmt != null)
					conn.close();
			}catch (SQLException se){
			}try {
				if (conn != null)
					conn.close();
			} catch (SQLException se ){
				se.printStackTrace();
			}
		}
	}	
	
	public void createDB(String sqlCreate)
	{
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL,user, pass);

			stmt = conn.createStatement();
			stmt.executeUpdate(sqlCreate);
			System.out.println("Successfully inserted record into table");
		}catch (SQLException sqle){
			sqle.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if ( stmt != null)
					conn.close();
			}catch (SQLException se){
			}try {
				if (conn != null)
					conn.close();
			} catch (SQLException se ){
				se.printStackTrace();
			}
		}
		System.out.println("Connection to the database has been closed.");
	}
	
	public void updateDB(String sqlUpdate)
	{
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL,user, pass);
			
			stmt = conn.createStatement();
			stmt.executeUpdate(sqlUpdate);
			System.out.println("Successfully updated record in table"); 
		}catch (SQLException sqle){
			sqle.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if ( stmt != null)
					conn.close();
			}catch (SQLException se){
			}try {
				if (conn != null)
					conn.close();
			} catch (SQLException se ){
				se.printStackTrace();
			}
		}
		System.out.println("Connection to the database has been closed.");
	}
	
	public void deletefromDB(String sqlDelete)
	{
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL,user, pass);
			
			stmt = conn.createStatement();
			stmt.executeQuery(sqlDelete);
			System.out.println("Successfully removed record from table"); 
		}catch (SQLException sqle){
			sqle.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				if ( stmt != null)
					conn.close();
			}catch (SQLException se){
			}try {
				if (conn != null)
					conn.close();
			} catch (SQLException se ){
				se.printStackTrace();
			}
		}
		System.out.println("Connection to the database has been closed.");
	}
}
