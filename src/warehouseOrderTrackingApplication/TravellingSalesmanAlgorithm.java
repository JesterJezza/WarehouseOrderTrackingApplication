package warehouseOrderTrackingApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class TravellingSalesmanAlgorithm {
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
	
	public TravellingSalesmanAlgorithm()
	{
		boolean[][] locations = new boolean[4][10];
	}
	
	public TravellingSalesmanAlgorithm(ArrayList<String> route, int cost)
	{
		this.setCost(cost);
		this.setRoute(route);
	}
	
	private void algorithm(ArrayList<OrderItem> itemList)
	{
		int size = itemList.size();
		String pickRoute = "";
		ArrayList<String> itemLocations = new ArrayList<String>();
		Arrays.fill(locations, false);
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
					locations[0][column] = true;
					loc[j] = Integer.parseInt("0"+column);
					break;
				case 'B':
					locations[1][column] = true;
					loc[j] = Integer.parseInt("1"+column);
					break;
				case 'C':
					locations[2][column] = true;
					loc[j] = Integer.parseInt("2"+column);
					break;
				case 'D':
					locations[3][column] = true;
					loc[j] = Integer.parseInt("3"+column);
					break;
			}
		}
		int sizeLoc = loc.length;
		int lowestCost = 999999;
		int nextItem = -1;
		boolean flag = false;
		int currentCost = 999999;
		
		for (int z=0;z<sizeLoc;z++)
		{
		
			int startNode = loc[z];
			int currentNode = loc[z];			
			ArrayList<String> route = new ArrayList<String>();
			
			if (flag == false)
			{
				flag = true;
				int initialCost = (Integer.parseInt(String.valueOf(startNode).substring(0, 1)))+(Integer.parseInt(String.valueOf(startNode).substring(1)));
				currentCost = initialCost;
			}
			
			for (int a=0; a<sizeLoc;a++)
			{
				if ((z+a) > sizeLoc)
				{
					nextItem = loc[(z+a-sizeLoc)];
				}
				else
				{
					nextItem = loc[z+a];
				}
			
				int row = Integer.parseInt(String.valueOf(currentNode).substring(0,1));
				int column = Integer.parseInt(String.valueOf(currentNode).substring(1));
				int nextRow = Integer.parseInt(String.valueOf(nextItem).substring(0, 1));
				int nextColumn = Integer.parseInt(String.valueOf(nextItem).substring(1));
				int costLeft = column;
				int costRight = 10-column;
				int costRow = nextRow - row;
				
				if (costLeft < costRight)
				{
					int nextCost = nextColumn;
					currentCost = currentCost + costLeft + costRow + nextCost;
				}
				else if (costRight < costLeft)
				{
					int nextCost = 9 - nextColumn;
					currentCost = currentCost + costRight + costRow + nextCost;
				}
			}
			
			if (currentCost < lowestCost)
			{
				lowestCost = currentCost;
				pickRoute = pickRoute +" "+String.valueOf(currentNode)+" -->"+ String.valueOf(nextItem);
				route.add(pickRoute);
			}
		}
		System.out.println(pickRoute);
		System.out.println(lowestCost);
		System.out.println("Would you like to see a map of the items to pick? (Y/N");
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
							System.out.print("| -");
						else if (locations[y][x] == false)
							System.out.print("|-");
						else if (locations[y][x] == true && x==9)
							System.out.print("| *");
						else if (locations[y][x] == true)
							System.out.print("|*");
							
					}
					System.out.println("|");
				}
				break;
			case "N":
				break;
		}
	}

}
