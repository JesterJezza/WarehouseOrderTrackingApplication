package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TSAlgorithm {
	private boolean[][] locations;
	private ArrayList<String> route;
	private int cost;
	
	public ArrayList<String> getRoute() {
		return route;
	}

	public void setRoute(ArrayList<String> route) {
		this.route = route;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public TSAlgorithm()
	{
		boolean[][] locations = new boolean[4][10];
		for (int i = 0; i <4; i++)
		{
			for (int j = 0; j<10;j++)
			{
				locations[i][j] = false;
			}
		}
	}
	
	public TSAlgorithm(ArrayList<String> route, int cost)
	{
		this.setCost(cost);
		this.setRoute(route);
	}
	
	public void algorithm(ArrayList<OrderItem> itemList)
	{
		this.locations = new boolean[4][10];
		
		for (int i = 0; i <4; i++)
		{
			for (int j = 0; j<10;j++)
			{
				this.locations[i][j] = false;
			}
		}
		int size = itemList.size();
		ArrayList<String> itemLocations = new ArrayList<String>();
		for (int i=0;i<size;i++)
		{
			OrderItem o = itemList.get(i);
			int itemID = o.getItemID();
			WarehouseJDBC jdbc = new WarehouseJDBC();
			String itemLocation = jdbc.getOrderLocation(itemID);
			itemLocations.add(itemLocation);
		}
		int size2 = itemLocations.size();
		int loc[] = new int[size2];
		
		for (int j=0;j<size2;j++)
		{
			String itemLocation = itemLocations.get(j);
			char shelf = itemLocation.charAt(0);
			int column = Integer.parseInt(itemLocation.substring(1));
			switch (shelf){
				case 'A':
					this.locations[0][column] = true;
					loc[j] = Integer.parseInt("1"+column);
					break;
				case 'B':
					this.locations[1][column] = true;
					loc[j] = Integer.parseInt("2"+column);
					break;
				case 'C':
					this.locations[2][column] = true;
					loc[j] = Integer.parseInt("3"+column);
					break;
				case 'D':
					this.locations[3][column] = true;
					loc[j] = Integer.parseInt("4"+column);
					break;
			}
		}
		int sizeLoc = loc.length;
		ArrayList<Integer> visited = new ArrayList<Integer>();
		visited.add(0);
		int visitedPointer = 0;
		ArrayList<Integer> unvisited = new ArrayList<Integer>();
		for (int i=0; i<sizeLoc;i++)
		{
			unvisited.add(loc[i]);
		}
		
		int tempP = -1;
		while (unvisited.size() > 0)
		{
			int closestNode = Integer.MAX_VALUE;
			int unSize = unvisited.size();
			for (int i = 0; i < unSize; i++)
			{
				int temp = Math.abs(unvisited.get(i) - visited.get(visitedPointer));
				
				if ( temp < closestNode)
				{
					closestNode = temp;
					tempP = i;
				}
			}
			visited.add(closestNode);
			unvisited.remove(tempP);
			unvisited.trimToSize();
		}
		System.out.print("Pick Order: ");
		for (int i=1;i<visited.size();i++)
		{
			String pick = String.valueOf(visited.get(i));
			char c = pick.charAt(0);
			String pickC = pick.substring(1);
			
			switch (c){
				case '1':
					pick = "A"+pickC; 
					break;
				case '2':
					pick = "B"+pickC;
					break;
				case '3':
					pick = "C"+pickC;
					break;
				case '4':
					pick = "D"+pickC;
					break;
			}
			System.out.print(pick+",");
		}
		System.out.println("");
		System.out.print("Would you like to see a map of the items to pick? (Y/N): ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try
		{
			input = br.readLine();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		switch (input){
			case "Y":
				System.out.println("#############################");
				System.out.println("      |1|2|3|4|5|6|7|8|9|10|");						 
				for (int y=0;y<4;y++)
				{
					if (y == 0)
						System.out.print("Row A:");
					else if (y==1)
						System.out.print("Row B:");
					else if (y==2)
						System.out.print("Row C:");
					else if (y==3)
						System.out.print("Row D:");
					for (int x=0;x<10;x++)
					{
						if (locations[y][x] == false && x == 9 )
						{
							System.out.print("| -");
						}
						else if (locations[y][x] == false)
						{
							System.out.print("|-");
						}
						else if (locations[y][x] == true && x==9)
						{
							System.out.print("| *");
						}
						else if (locations[y][x] == true)
						{
							System.out.print("|*");
						}	
					}
					System.out.println("|");
				}
				System.out.println("#############################");
				break;
			case "N":
				break;
		}
	}
	
	public String algorithmGUI(ArrayList<OrderItem> itemList)
	{
		this.locations = new boolean[4][10];
		
		for (int i = 0; i <4; i++)
		{
			for (int j = 0; j<10;j++)
			{
				this.locations[i][j] = false;
			}
		}
		int size = itemList.size();
		ArrayList<String> itemLocations = new ArrayList<String>();
		for (int i=0;i<size;i++)
		{
			OrderItem o = itemList.get(i);
			int itemID = o.getItemID();
			WarehouseJDBC jdbc = new WarehouseJDBC();
			String itemLocation = jdbc.getOrderLocation(itemID);
			itemLocations.add(itemLocation);
		}
		int size2 = itemLocations.size();
		int loc[] = new int[size2];
		
		for (int j=0;j<size2;j++)
		{
			String itemLocation = itemLocations.get(j);
			char shelf = itemLocation.charAt(0);
			int column = Integer.parseInt(itemLocation.substring(1));
			switch (shelf){
				case 'A':
					this.locations[0][column] = true;
					loc[j] = Integer.parseInt("1"+column);
					break;
				case 'B':
					this.locations[1][column] = true;
					loc[j] = Integer.parseInt("2"+column);
					break;
				case 'C':
					this.locations[2][column] = true;
					loc[j] = Integer.parseInt("3"+column);
					break;
				case 'D':
					this.locations[3][column] = true;
					loc[j] = Integer.parseInt("4"+column);
					break;
			}
		}
		int sizeLoc = loc.length;
		ArrayList<Integer> visited = new ArrayList<Integer>();
		visited.add(0);
		int visitedPointer = 0;
		ArrayList<Integer> unvisited = new ArrayList<Integer>();
		for (int i=0; i<sizeLoc;i++)
		{
			unvisited.add(loc[i]);
		}
		
		int tempP = -1;
		while (unvisited.size() > 0)
		{
			int closestNode = Integer.MAX_VALUE;
			int unSize = unvisited.size();
			for (int i = 0; i < unSize; i++)
			{
				int temp = Math.abs(unvisited.get(i) - visited.get(visitedPointer));
				
				if ( temp < closestNode)
				{
					closestNode = temp;
					tempP = i;
				}
			}
			visited.add(closestNode);
			unvisited.remove(tempP);
			unvisited.trimToSize();
		}
		String route = "Pick Order: ";
		String route2="";
		for (int i=1;i<visited.size();i++)
		{
			String pick = String.valueOf(visited.get(i));
			char c = pick.charAt(0);
			String pickC = pick.substring(1);
			
			switch (c){
				case '1':
					pick = "A"+pickC; 
					break;
				case '2':
					pick = "B"+pickC;
					break;
				case '3':
					pick = "C"+pickC;
					break;
				case '4':
					pick = "D"+pickC;
					break;
			}
			route2 = route2+", "+pick;
		}
		route = route+route2;
		return route;
	}
}
