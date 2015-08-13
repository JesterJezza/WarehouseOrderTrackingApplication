package warehouseOrderTrackingApplication;

public class Item {
	//Class variables
	private int itemID;
	private String itemName;
	private String itemDesc;
	private float itemWeight;
	private float itemCost;
	private float itemSaleVal;
	private boolean porous;
	private int stockLevel;
	private int allocatedStock;
	private String warehouseLocation;
	
	//Default constructor
	public Item() {}
	
	public Item(int itemID, String itemName, String itemDesc, float itemWeight, float itemCost, float itemSaleVal, boolean porous, int stockLevel, int allocatedStock, String warehouseLocation)
	{
		setItemID(itemID);
		setItemName(itemName);
		setItemDesc(itemDesc);
		setItemWeight(itemWeight);
		setItemCost(itemCost);
		setItemSaleVal(itemSaleVal);
		setPorous(porous);
		setStockLevel(stockLevel);
		setAllocatedStock(allocatedStock);
		setWarehouseLocation(warehouseLocation);
	}
	
	//Getters and setters for object member variables
	public int getStockLevel() {
		return stockLevel;
	}

	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	public int getAllocatedStock() {
		return allocatedStock;
	}

	public void setAllocatedStock(int allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	public String getWarehouseLocation() {
		return warehouseLocation;
	}

	public void setWarehouseLocation(String warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}

	public int getItemID() {
		return itemID;
	}
	
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	public String getItemDesc() {
		return itemDesc;
	}
	
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public float getItemWeight() {
		return itemWeight;
	}
	
	public void setItemWeight(float itemWeight) {
		this.itemWeight = itemWeight;
	}
	
	public float getItemCost() {
		return itemCost;
	}
	
	public void setItemCost(float itemCost) {
		this.itemCost = itemCost;
	}
	
	public float getItemSaleVal() {
		return itemSaleVal;
	}
	
	public void setItemSaleVal(float itemSaleVal) {
		this.itemSaleVal = itemSaleVal;
	}
	
	public boolean isPorous() {
		return porous;
	}
	
	public void setPorous(boolean porous) {
		this.porous = porous;
	}
	
		

}
