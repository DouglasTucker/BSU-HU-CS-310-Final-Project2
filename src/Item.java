
public class Item {
	private int item_id;
	private String item_code;
	private String description;
	private double price;
	private int inventory_amount;
	
	public Item(int item_id,String item_code, String description, double price, int inventory_amount ) {
		this.item_id = item_id;
		this.item_code = item_code;
		this.description = description;
		this.price = price;
		this.inventory_amount = inventory_amount;
	}
    
	public String getItem_code() {
		return item_code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getPrice() {
        return price;
    }
	
	public int getInventory_amount() {
		return inventory_amount;
	}
    
    
	 public String toString(){
	        return String.format("(%s, %s, %s, %s, %s)", this.item_id, this.item_code, this.description, this.price, this.inventory_amount);
	    }
}
