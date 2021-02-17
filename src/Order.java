
public class Order {
	private int order_id;
	private String item_code;
	private int quantity;
	private double order_timestamp;
	
	public Order(String item_code, int quantity) {
		this.item_code = item_code;
		this.quantity = quantity;
	}

	public Order(String item_code, int quantity, double order_timestamp) {
		this.item_code = item_code;
		this.quantity = quantity;
		this.order_timestamp = order_timestamp;
		
	}

	 public String toString(){
	        return String.format("(%s, %s, %s, %s)", this.order_id, this.item_code, this.quantity, this.order_timestamp);
	    }
	
}
