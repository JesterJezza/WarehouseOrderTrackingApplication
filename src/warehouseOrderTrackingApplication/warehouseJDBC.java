package warehouseOrderTrackingApplication;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class WarehouseJDBC {

	//Initialise variables for database connection
	static final String jdbcDriver = "com.mysql.jdbc.Driver";
	static final String dbURL = "jdbc:mysql://127.0.0.1:3306/mydb";
	static final String user = "jwilks";
	static final String pass = "netbuilder";

	public ArrayList<OrderItem> getItems(String cID, String oID)
	{
		//Initialise variables for connection, same for all functions
		String custOrderID = oID;
		String custID = cID;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
		//System.out.println("Connecting to database...");
		
		try
		{
			//Create statement to execute on connection
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL,user,pass);
			stmt = conn.createStatement();
			//Sql query
			rs = stmt.executeQuery("SELECT * FROM orderitem WHERE orderID = " + String.valueOf(custOrderID) + " AND customerID = " + String.valueOf(custID));
			
			try 
			{
				//Rs.next points to the next element in a result set, while it is true, loop through
				while(rs.next())
				{
					//Get information from sql results
					int orderID = rs.getInt("orderID");
					int customerID = rs.getInt("customerID");
					int itemID = rs.getInt("itemID");
					int orderItemQuantity = rs.getInt("orderItemQuantity");
					float orderItemCost = rs.getFloat("orderItemCost");
					float totalItemCost = rs.getFloat("totalItemCost");
					//Create instance of OrderItem with values obtained from database
					OrderItem getItem = new OrderItem(orderID, customerID, itemID, orderItemQuantity,orderItemCost, totalItemCost);
					//Add each OrderItem to arraylist
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
			//close connection
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
		//Return arraylist of OrderItem
		return itemList;
	}
	
	public ArrayList<CustomerOrder> returnCustOrder()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		//Create arraylist of CustomerOrder to store results
		ArrayList<CustomerOrder> custList = new ArrayList<CustomerOrder>();
		//System.out.println("Connecting to database...");
		try 
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM customerorder");
			
			try 
			{
				//loop through results, creating a CustomerOrder for each item in result set and add to arraylist
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
					//Call get items function to get the items within this order
					itemList = getItems(String.valueOf(custID), String.valueOf(custOrderID));
					
					CustomerOrder getCust = new CustomerOrder(custOrderID, custID, orderTotal, deliveryAddress, isCheckedOut, eOrderStatus, employeeID, itemList);
					custList.add(getCust);
					
				}
				//close connection
				rs.close();
				
				//System.out.println("Database connection closed");
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
		//Return arraylist
		return custList;
	}
	
	public ArrayList<PurchaseOrder> returnPurchOrder()
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
	
		ArrayList<PurchaseOrder> purchaseList = new ArrayList<PurchaseOrder>();
		//System.out.println("Connecting to database...");
		
		try 
		{
			Class.forName(jdbcDriver);	
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM purchaseorder");
			
			try 
			{
				//Loop through result set, create a PurchaseOrder for each item in resultset
				while(rs.next())
				{					
					int purchaseOrderID = rs.getInt("purOrderID");
					int supplierID = rs.getInt("supplierID");
					String supplierName = rs.getString("supplierName");
					float orderTotal = rs.getFloat("orderTotal");
					
					ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
					//Call getitems function to get the items within this order
					itemList = getItems(String.valueOf(supplierID), String.valueOf(purchaseOrderID));
					PurchaseOrder getPurchase = new PurchaseOrder(purchaseOrderID, supplierID, supplierName, orderTotal, itemList);
					purchaseList.add(getPurchase);
					
				}
				//Close connection to the database
				rs.close();
				//System.out.println("Database connection closed");
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
		//Return arraylist of PurchaseOrder
		return purchaseList;
	}
	
	//Function to get the order backlog
	public ArrayList<CustomerOrder> getBacklog()
	{
		Connection conn = null; 
		Statement stmt = null;
		//Return all records where order status is CONFIRMED
		String sql = "SELECT * FROM customerorder WHERE eOrderStatus='CONFIRMED'";
		ResultSet rs;
		ArrayList<CustomerOrder> backlogList = new ArrayList<CustomerOrder>();
		try 
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			//Lopp through resultset and create a customerorder for each element with data obtained from database
			while (rs.next())
			{
				int custOrderID = rs.getInt("idcustomerOrder");
				int custID = rs.getInt("idcustomer");
				float orderTotal = rs.getFloat("orderTotal");
				String deliveryAddress = rs.getString("deliveryAddress");
				String eOrderStatus = rs.getString("eOrderStatus");
				//Call function to get arraylist of orderitem for order in backlog
				ArrayList<OrderItem> itemList = getBacklogItems(custOrderID, custID);
				CustomerOrder backlogItem = new CustomerOrder(custOrderID,custID,orderTotal,deliveryAddress,eOrderStatus, itemList);
				backlogList.add(backlogItem);
			}
			//Close connection
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
		//Return ArrayList of customer orders
		return backlogList;
	}

	//Function to get backlog items
	public ArrayList<OrderItem> getBacklogItems(int oID, int cID)
	{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		ArrayList<OrderItem> itemList = new ArrayList<OrderItem>();
		//System.out.println("Connecting to database...");
		
		try
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL,user,pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM orderitem WHERE orderID = " + String.valueOf(oID) + " AND customerID = " + String.valueOf(cID));
			
			try 
			{
				//Lopp through resultset, creating an OrderItem for each item in the result set using data obtained from database
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
			//Close connection
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
		//Return ArrayList of OrderItems
		return itemList;
	}

	//Function to getorderid of a purchase order that has been created in the database (this is autoincremented, so the id is needed to insert items into the orderitem table)
	public int getOrderID(PurchaseOrder purch)
	{
		int retOrderID = 0;
		Connection conn = null;
		Statement stmt = null;
		String supplierID = String.valueOf(purch.getSupplierID());
		String orderTotal = String.valueOf(purch.getOrderTotal());
		//Given the supplierid and the order total, return the id of the order
		String sql = "SELECT purOrderID FROM purchaseorder WHERE supplierID ='"+supplierID+"' AND orderTotal ='"+orderTotal+"'";
		ResultSet rs;
		
		try
		{
			Class.forName(jdbcDriver);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				//This should only return one record, so only one iteration is required
				retOrderID = rs.getInt("purOrderID");
				//System.out.println(retOrderID);
				break;
			}
			//Close connection
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
		//Return orderid of the purchaseorder
		return retOrderID;
	}
	
	//Function to get the location of an item within the warehouse
	public String getOrderLocation(int itemID)
	{
		String itemLocation = "";
		Connection conn = null;
		Statement stmt = null;
		//Return the warehouseLocation of an item, given its id
		String sql = "SELECT warehouseLocation FROM item WHERE iditem ='"+String.valueOf(itemID)+"';";
		ResultSet rs;
		try 
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				itemLocation = rs.getString("warehouseLocation");
			}
			//Close connection
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
		//Return the item location
		return itemLocation;
	}
	
	//Function to insert a new record into the PurchaseOrder table in the database
	public void createPurchaseOrderDB(PurchaseOrder purch)
	{
		Connection conn = null;
		Statement stmt = null;
		//Insert into purchase order table the values of the PurchaseOrder which is passed into the function
		String sql = "INSERT INTO purchaseorder (supplierID,supplierName,orderTotal) VALUES ('"+String.valueOf(purch.getSupplierID())+"','"+purch.getSupplierName()+"','"+String.valueOf(purch.getOrderTotal())+"')";
		try 
		{
			Class.forName(jdbcDriver);
			//System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		//Get the orderID of the record we just inserted as this is auto-incremented
		int orderID = getOrderID(purch);
		//Call function to insert items into the orderitem table in the database
		createOrderItemDB(purch, orderID);
	}
	
	//Function to insert orderItems into the database of a purchase order that has been previously inserted
	public void createOrderItemDB(PurchaseOrder purch, int orderID)
	{
		Connection conn = null;
		Statement stmt = null;
		ArrayList<OrderItem> itemList = purch.getOrderItemList();
		int size = itemList.size();
		
		//For each item in the itemList
		for (int i = 0; i < size; i++)
		{
			//Construct string here depending on the values which are passed into the function
			String sql = "INSERT INTO orderitem VALUES ('";
			OrderItem o = itemList.get(i);
			sql = sql + String.valueOf(orderID)+"','"+String.valueOf(o.getCustomerID())+"','"+String.valueOf(o.getItemID())+"','"+String.valueOf(o.getOrderItemQuantity())+"','"+String.valueOf(o.getOrderItemCost())+"','"+String.valueOf(o.getTotalItemCost())+"');";
			
			try 
			{
				Class.forName(jdbcDriver);
				//System.out.println("Connecting to database...");
				conn = DriverManager.getConnection(dbURL, user, pass);
				stmt = conn.createStatement();
				stmt.executeUpdate(sql);
			}
			catch (SQLException sqle)
			{
				sqle.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}	
}
	
	//Function to return which items need porousware treatment
	public ArrayList<Item> getPorousItems(int iditem)
	{
		Connection conn = null;
		Statement stmt = null;
		ArrayList<Item> nonPorousItems = new ArrayList<Item>();
		ResultSet rs;
		String sql = "SELECT * FROM item WHERE isPorous ='0';";
		try 
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			//Loop through resultset and create item based on the results returned from the database
			while (rs.next())
			{
				int itemID = rs.getInt("iditem");
				String itemName = rs.getString("itemName");
				String itemDesc = rs.getString("itemDesc");
				float itemWeight = rs.getFloat("itemWeight");
				float itemCost = rs.getFloat("itemCost");
				float itemSaleVal = rs.getFloat("itemSaleVal");
				boolean porous = rs.getBoolean("isPorous");
				int stockLevel = rs.getInt("stockLevel");
				int allocatedStock = rs.getInt("allocatedStock");
				String warehouseLocation = rs.getString("warehouseLocation");
				Item i = new Item(itemID, itemName, itemDesc, itemWeight, itemCost, itemSaleVal, porous, stockLevel, allocatedStock, warehouseLocation);
				nonPorousItems.add(i);
			}
			//Close connection
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
		//Return arrayList of non porous items
		return nonPorousItems;
	}
	
	//Function to return the current stock level of an item
	public int getCurrentStock(int itemID)
	{
		int currentStock=0;
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		//Return the stock level of an item given its id
		String sql = "SELECT stockLevel FROM item WHERE iditem='"+String.valueOf(itemID)+"';";
		try 
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				//Set currentStock to value returned from database
				currentStock = rs.getInt("stockLevel");
				flag = true;
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
		if (flag == false)
			System.out.println("Could not retrieve information from database.");
		//Return currentStock level
		return currentStock;
	}
	
	//Function to get the current AllocatedStock level, similar to above function
	public int getAllocatedStock(int itemID)
	{
		int allocatedStock=0;
		boolean flag = false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs;
		//Return current allocatedStock level given an items id
		String sql = "SELECT allocatedStock FROM item WHERE iditem='"+String.valueOf(itemID)+"';";
		
		try
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while (rs.next())
			{
				allocatedStock = rs.getInt("allocatedStock");
				flag = true;
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
		if (flag == false)
			System.out.println("Could not retrieve information from database.");
		//Return allocatedStock level
		return allocatedStock;
	}
	
	//Function to check out an order from the backlog
	public void checkOutOrder(int orderID, int custID, String status)
	{
		Connection conn = null;
		Statement stmt = null;
		//Update order status and ischeckedout of a customer given the id
		String sql = "UPDATE customerorder SET eOrderStatus ='"+status+"', isCheckedOut='1' WHERE idcustomerorder='"+String.valueOf(orderID)+"';";
	
		try
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//Function to update the stock levels of an item within the database
	public void updateStock(OrderItem item, int currentStock)
	{
		Connection conn = null;
		Statement stmt = null;
		//Maths to adjust stock correctly
		int stockAdjustment = currentStock - item.getOrderItemQuantity();
		try
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			//Update stockLevel of an item given its id
			String sql ="UPDATE item SET stockLevel='"+String.valueOf(stockAdjustment)+"' WHERE iditem='"+String.valueOf(item.getItemID())+"';";
			stmt.executeUpdate(sql);
			updateStockAllocation(item.getItemID(),item.getOrderItemQuantity());
		}catch (SQLException sqle){
			sqle.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	//Function to update the allocatedStock of an item, similar to above function
	public void updateStockAllocation(int itemID, int itemQuantity)
	{
		Connection conn = null;
		Statement stmt = null;
		//Statement stmt2 = null;
		ResultSet rs;
		int allocatedStock = 0;
		try
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			//Get allocatedStock level of an item given its id
			String sql2 = "SELECT allocatedStock FROM item WHERE iditem='"+itemID+"';";
			rs = stmt.executeQuery(sql2);
			while (rs.next())
			{
				
				allocatedStock = rs.getInt("allocatedStock");
				break;
			}
			//New allocatedStock level = old + quantity of item within order
			int newStockAl = allocatedStock + itemQuantity;
			//Update stock level of this item in the database
			String sql ="UPDATE item SET allocatedStock='"+String.valueOf(newStockAl)+"' WHERE iditem='"+String.valueOf(itemID)+"';";
			stmt.executeUpdate(sql);
			conn.close();			
		}
		catch (SQLException sqle)
		{
			sqle.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	//function to remove the allocated stock of an item when the order has been picked
	public void removeStockAllocation(int itemID, int itemQuantity)
	{
		Connection conn = null;
		Statement stmt = null;
		//Need the current allocated stock level
		int allocatedStock = getAllocatedStock(itemID);
		//New allocated stock level = old-quantity of item in order
		int allStockAdj = allocatedStock - itemQuantity;
		//Update allocated stock of an item given its id
		String sql = "UPDATE item SET allocatedStock='"+String.valueOf(allStockAdj)+"' WHERE iditem ='"+String.valueOf(itemID)+"';";
		try 
		{
			Class.forName(jdbcDriver);
			conn = DriverManager.getConnection(dbURL, user, pass);
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		}catch (SQLException sqle){
			sqle.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
