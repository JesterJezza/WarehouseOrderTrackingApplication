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

	public ArrayList<String> readDB(String sqlRead)
	{
		Connection conn = null;
		Statement stmt = null;
		ArrayList <String> result = new ArrayList<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(dbURL,user, pass);
	
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sqlRead);
			System.out.println(rs.toString());
			try {
				int resultLength = rs.getFetchSize();
				
				if (resultLength > 0)
				{
					//Cast results to an array list of type string 
					//Loop through results, adding each record to the array list
					for (int i=0;rs.next();i++)
					{
						result.add(rs.getString(i));
					}
					result.trimToSize();
				}
			}catch (SQLException resulte)
			{
				resulte.printStackTrace();
			}	
			rs.close();
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
		return result;
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
