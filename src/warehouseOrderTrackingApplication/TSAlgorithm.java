package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TSAlgorithm {
	//Class member variables
	private boolean[][] locations;
	private ArrayList<String> route;
	private int cost;
	
	//Getters and setters
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
	
	//Default constructor
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
	
	//Other Constructor
	public TSAlgorithm(ArrayList<String> route, int cost)
	{
		this.setCost(cost);
		this.setRoute(route);
	}
	
	//Algorithm to improve pick efficiency CONSOLE MODE ONLY
	public void algorithm(ArrayList<OrderItem> itemList)
	{
		//Create a replica matrix of the warehouse layout
		this.locations = new boolean[4][10];
		
		for (int i = 0; i <4; i++)
		{
			for (int j = 0; j<10;j++)
			{
				//Initialise all locations to false (i.e. we do not need to pick from here)
				this.locations[i][j] = false;
			}
		}
		//Get size of ArrayList passed into function
		int size = itemList.size();
		//Create an ArrayList of Strings to store locations that we need to visit
		ArrayList<String> itemLocations = new ArrayList<String>();
		//Loop through for each item in ArrayList
		for (int i=0;i<size;i++)
		{
			//Get OrderItem from ArrayList
			OrderItem o = itemList.get(i);
			//Get itemID of current OrderItem
			int itemID = o.getItemID();
			//Create instance of WarehouseJDBC class to call function to return item locations within warehouse
			WarehouseJDBC jdbc = new WarehouseJDBC();
			//Create sting to hold location return from jdbc function
			String itemLocation = jdbc.getOrderLocation(itemID);
			//Add string to ArrayList of item locations
			itemLocations.add(itemLocation);
		}
		//Get size of item locations ArrayList
		int size2 = itemLocations.size();
		//Create an array of integers of that size
		int loc[] = new int[size2];
		//Loop through for size of ArrayList
		for (int j=0;j<size2;j++)
		{
			//Get current location string
			String itemLocation = itemLocations.get(j);
			//Parse string, first char will be row letter, next char(s) will be column 
			char shelf = itemLocation.charAt(0);
			int column = Integer.parseInt(itemLocation.substring(1));
			//Switch-case statement to substitute letter for numeric value, instead of location A-1, it will be 1-1
			switch (shelf){
				case 'A':
					//Set location in replica warehouse model as true
					this.locations[0][column] = true;
					//add numeric location to array of orderitem locations
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
		//Get size of the array of locations
		int sizeLoc = loc.length;
		//Create 2 arraylists for algorithm, one of locations to visit and one of visted locations.
		ArrayList<Integer> visited = new ArrayList<Integer>();
		ArrayList<Integer> unvisited = new ArrayList<Integer>();
		//Start point will always be 0 (cannot start directly at the location of an item
		visited.add(0);
		//Pointer to store the location of visited
		int visitedPointer = 0;
		//Store locations that need to be visited in unvisited list
		for (int i=0; i<sizeLoc;i++)
		{
			unvisited.add(loc[i]);
		}
		//Initialise temp pointer
		int tempP = -1;
		//While the unvisited list has items in it
		while (unvisited.size() > 0)
		{
			//Integer for comparison, max value as we are looking for the lowest cost
			int closestNode = Integer.MAX_VALUE;
			//Current size of the unvisited list
			int unSize = unvisited.size();
			//Loop through number of items in the unvisited list
			for (int i = 0; i < unSize; i++)
			{
				//Calculate cost using nearest neighbour, absolute value as direction does not matter, only cost.
				int temp = Math.abs(unvisited.get(i) - visited.get(visitedPointer));
				//If this cost is lower than the current cost, set new lowest
				if ( temp < closestNode)
				{
					closestNode = temp;
					//Set temp pointer to current index of unvisited list
					tempP = i;
				}
			}
			//Once all possibilities have been checked, add the lowest cost to the visited list, remove that node from the unvisited list, and reduce the size of the unvisited list
			visited.add(closestNode);
			unvisited.remove(tempP);
			unvisited.trimToSize();
			//When this while loop has finished, it will store the pick order of the lowest cost route
		}
		System.out.print("Pick Order: ");
		//Loop through pick order and re-substitute letters in place of numeric value assigned earlier
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
		//User can choose to see a visual representation of the items they need to pick, this is done using the array of booleans from the start of this function
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
		//Swtich-case statement for user choosing to display map or not
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
						//Print * if we need to pick there, print - if not
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
	
	//Version of algorithm used in the GUI, same as above without map printing functionality
	//Returns pick order as a string to the GUI to then be used on screen
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
