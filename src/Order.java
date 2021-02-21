
public class Order {
	private int order_id;
	private String item_code;
	private int quantity;
	private String order_timestamp;
	private double order_total;
	private String description;
	private double price;
	
	public Order(String item_code, int quantity, double order_total) {
		this.item_code = item_code;
		this.quantity = quantity;
		this.order_total = order_total;
	}

	public Order(int order_id, String item_code, int quantity, String order_timestamp, double order_total) {
		this.order_id = order_id;
		this.item_code = item_code;
		this.quantity = quantity;
		this.order_timestamp = order_timestamp;
		this.order_total = order_total;
		
		
	}
	
	public Order(int order_id, String item_code,String description, int quantity, double price, double order_total) {
		this.order_id = order_id;
		this.item_code = item_code;
		this.description = description;
		this.quantity = quantity;
		this.price = price;
		this.order_total = order_total;
		
		
		
		
	}
	
	public String getItem_code() {
		return item_code;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public double getOrder_total() {
		return order_total;
	}
	
	
	
	 public String toString(){
	        return String.format("(%s, %s, %s, %s, %s%s)", this.order_id, this.item_code, this.quantity, this.order_timestamp, '$', this.order_total);
	    }
	 
	 public String DetailToString(){
	        return String.format("(%s, %s, %s, %s, %s%s, %s)", this.order_id, this.item_code, this.description, this.quantity, '$', this.price, this.order_total);
	    }
	
}
