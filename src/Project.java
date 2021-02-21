import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Project {

    public static void printItemInfo() {
    	System.out.println("item_id | item_code | item_description | price | inventory_amount\n"
    			+ 	       "-----------------------------------------------------------------\n");
    }
	
    public static void printOrderInfo() {
    	System.out.println("order_id | item_code | quantity | order_timestamp | order_total\n"
    			+ 	       "---------------------------------------------------------------\n");
    }
    
    public static void printOrderDetails() {
    	System.out.println("order_id | item_code | description | quantity | price | order_total\n"
    			+ 	       "-------------------------------------------------------------------\n");
    }
    
	public static Item createItem(String item_code, String description, double price, int inventory_amount) throws SQLException {
		Connection connection = null;
		//item_code set to 0 but not inserted into table(auto increments)
		Item item = new Item(0, item_code, description, price, inventory_amount);
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = String.format("INSERT INTO items (item_code, description, price, inventory_amount) VALUES ('%s' , '%s', %s, %s);",
                item.getItem_code(),
                item.getDescription(),
                item.getPrice(),
                item.getInventory_amount());
        sqlStatement.executeUpdate(sql);
        connection.close();
		
        attemptToGetItems(item_code);
		return item;
	}
	
	public static void updateInventory(String item_code, int inventory_amount) throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();
        
        String sql = String.format("UPDATE items SET inventory_amount = '%s' WHERE item_code = '%s' ;",
        				inventory_amount, item_code);

        sqlStatement.executeUpdate(sql);

        connection.close();
        
        attemptToGetItems(item_code);
	}
	
	public static void deleteItem(String item_code) throws SQLException {
		 Connection connection = null;

	     connection = MySqlDatabase.getDatabaseConnection();
	     Statement sqlStatement = connection.createStatement();

	     String sql = String.format("DELETE FROM items WHERE item_code = '%s';", item_code);
	     sqlStatement.executeUpdate(sql);
	     connection.close();
	}
	
	public static List<Item> getAllItems(String Item_code) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql;
        if (Item_code.equals("%")){
        sql = "SELECT * FROM items;";
        }else {
        sql = String.format("SELECT * FROM items Where item_code = '%s';", Item_code);
        }
        ResultSet resultSet = sqlStatement.executeQuery(sql);
        
        
        List<Item> items = new ArrayList<Item>();

        while (resultSet.next()) {
            int item_id = resultSet.getInt(1);
            String item_code = resultSet.getString(2);
            String description = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            int inventory_amount = resultSet.getInt(5);

            Item item = new Item(item_id, item_code, description, price, inventory_amount);
            items.add(item);
        }
        resultSet.close();
        connection.close();
        return items;
	}

	
	public static Order createOrder(String item_code, int quantity)throws SQLException {
		Connection connection = null;
		
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();

		double price = 0;
		
		// get price from items
		try {
			String sql2 = String.format("SELECT price FROM items Where item_code = '%s';", item_code);
			ResultSet resultSet = sqlStatement.executeQuery(sql2);
			resultSet.next();
			price = resultSet.getDouble(1);
		
		}catch (SQLException sqlException) {
			System.out.println("item_code not found in items table");
			System.exit(0);
		}
		double order_total = price * quantity;
		
		// create order
		Order order = new Order(item_code, quantity, order_total);
		
		String sql = String.format("INSERT INTO orders (item_code, quantity, order_total) VALUES ('%s' , '%s' , '%s');",
			
                order.getItem_code(),
                order.getQuantity(), 
				order.getOrder_total());
				
		sqlStatement.executeUpdate(sql);
		

        connection.close();
		return order;
	}
	
	public static void deleteOrder(String item_code) throws SQLException {
		 Connection connection = null;

	     connection = MySqlDatabase.getDatabaseConnection();
	     Statement sqlStatement = connection.createStatement();

	     String sql = String.format("DELETE FROM orders WHERE item_code = '%s';", item_code);
	     sqlStatement.executeUpdate(sql);
	     connection.close();
	    
	}
	
	
	public static List<Order> getAllOrders(String Item_code) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql;
        if (Item_code.equals("%")){
        sql = "SELECT * FROM orders;";
        }else {
        sql = String.format("SELECT * FROM orders Where item_code = '%s';", Item_code);
        }
        ResultSet resultSet = sqlStatement.executeQuery(sql);
        
        
        List<Order> orders = new ArrayList<Order>();

        while (resultSet.next()) {
            int order_id = resultSet.getInt(1);
            String item_code = resultSet.getString(2);
            int quantity = resultSet.getInt(3);
            String order_timeStamp = resultSet.getString(4);
            double order_total = resultSet.getDouble(5);

            Order order = new Order(order_id, item_code, quantity, order_timeStamp, order_total);
            orders.add(order);
        }
        resultSet.close();
        connection.close();
        return orders;
	}
	
	
	public static List<Order> getAllDetails(String Item_code) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

		
        String sql;
        if (Item_code.equals("%")){
        sql = "SELECT * FROM orders left join items ON orders.item_code = items.item_code;";
        }else {
        sql = String.format("SELECT * FROM orders left join items ON orders.item_code = items.item_code Having orders.item_code = '%s';", Item_code);
        }
        ResultSet resultSet = sqlStatement.executeQuery(sql);
        
        
        List<Order> orders = new ArrayList<Order>();

        while (resultSet.next()) {
            int order_id = resultSet.getInt(1);
            String item_code = resultSet.getString(2);
            String description = resultSet.getString(8);
            int quantity = resultSet.getInt(3);
            double order_total = resultSet.getDouble(5);
            double price = resultSet.getDouble(9);

            System.out.println();
            
            Order order = new Order(order_id, item_code, description, quantity, price, order_total);
            orders.add(order);
        }
        resultSet.close();
        connection.close();
        return orders;
	}
	
	public static List<Order> GetLatestOrder(String Item_code) throws SQLException {
        Connection connection = null;


        connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();

        String sql;
      
        //Selects most recent order only
        sql = String.format("SELECT * FROM orders Where order_timestamp = ( SELECT MAX(order_timestamp ) FROM orders Where item_code = '%s');", Item_code);
        
        ResultSet resultSet = sqlStatement.executeQuery(sql);
        
        
        List<Order> orders = new ArrayList<Order>();

        while (resultSet.next()) {
            int order_id = resultSet.getInt(1);
            String item_code = resultSet.getString(2);
            int quantity = resultSet.getInt(3);
            String order_timeStamp = resultSet.getString(4);
            double order_total = resultSet.getDouble(5);

            Order order = new Order(order_id, item_code, quantity, order_timeStamp, order_total);
            orders.add(order);
        }
        resultSet.close();
        connection.close();
        return orders;
	}
	
	
	
	// ATTEMPTS
	
	
	public static void attemptToCreateItem(String item_code, String description, double price, int inventory_amount) {
		try {
			createItem(item_code, description, price, inventory_amount);
		} catch (SQLException sqlException) {
			System.out.println("Failed to create item");
			System.out.println(sqlException.getMessage());
		}
	}
	
	public static void  attemptToUpdateInventory(String item_code, int inventory_amount) {
		try {
			updateInventory(item_code, inventory_amount);
		} catch (SQLException sqlException) {
			System.out.println("Failed to update inventory");
			System.out.println(sqlException.getMessage());
		}
	}
	
	public static void attemptToDeleteItem(String item_code) {
		try {
			deleteItem(item_code);
			System.out.println("Item Deleted!");
		} catch (SQLException sqlException) {
			System.out.println("Failed to delete item");
			System.out.println(sqlException.getMessage());
		}
	}
		
	public static void attemptToGetItems(String item_code) {
		try {
            List<Item> items = getAllItems(item_code);
            printItemInfo();
            for (Item item : items) {
                System.out.println(item.toString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get items");
            System.out.println(sqlException.getMessage());
        }
    }
	
	
	public static void attemptToCreateOrder(String item_code, int quantity) {
		try {
			createOrder(item_code, quantity);
			
			attemptToGetLatestOrder(item_code);
		} catch (SQLException sqlException) {
			System.out.println("Failed to create order");
			System.out.println(sqlException.getMessage());
		}
	}
	
	public static void attemptToDeleteOrder(String item_code) {
		try {
			deleteOrder(item_code);
			 System.out.println("Order Deleted!");
		} catch (SQLException sqlException) {
			System.out.println("Failed to delete order");
			System.out.println(sqlException.getMessage());
		}
	}
	
	
	public static void attemptToGetOrders(String item_code) {
		try {
            List<Order> orders = getAllOrders(item_code);
            printOrderInfo();
            for (Order order : orders) {
                System.out.println(order.toString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get orders");
            System.out.println(sqlException.getMessage());
        }
    }
	
	public static void attemptToGetOrderDetails(String item_code) {
		try {
            List<Order> orders = getAllDetails(item_code);
            printOrderDetails();
            for (Order order : orders) {
                System.out.println(order.DetailToString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get orders");
            System.out.println(sqlException.getMessage());
        }
    }
	
	public static void attemptToGetLatestOrder(String item_code) {
		try {
            List<Order> orders = GetLatestOrder(item_code);
            printOrderInfo();
            for (Order order : orders) {
                System.out.println(order.toString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get latest order");
            System.out.println(sqlException.getMessage());
        }
    }
	
	
	
    public static void main(String[] args){

        if (args[0].equals("CreateItem")) {//done
            String item_code = args[1];
            String item_Description = args[2];
            double price = Double.parseDouble(args[3]);
            int inventory_amount = Integer.parseInt(args[4]);
            attemptToCreateItem(item_code, item_Description, price, inventory_amount);
        
        }else if(args[0].equals("UpdateInventory")) {//done
        	String item_code = args[1];
        	int inventory_amount = Integer.parseInt(args[2]);
        	attemptToUpdateInventory(item_code, inventory_amount);
        	
        }else if(args[0].equals("DeleteItem")) {//done
        	String item_code = args[1];
        	attemptToDeleteItem(item_code);
     	
        }else if(args[0].equals("GetItems")) {
        	String item_code = args[1];
        	attemptToGetItems(item_code);
        	
        }else if(args[0].equals("CreateOrder")) {//done
        	String item_code = args[1];
        	int quantity = Integer.parseInt(args[2]);
        	attemptToCreateOrder(item_code, quantity);
        	
        }else if(args[0].equals("DeleteOrder")) {//done
        	String item_code = args[1];
        	attemptToDeleteOrder(item_code);
        	
        }else if(args[0].equals("GetOrders")) {
        	String item_code = args[1];
        	attemptToGetOrders(item_code);
      	
        }else if(args[0].equals("GetOrderDetails")) {
        	String order_code = args[1];
        	attemptToGetOrderDetails(order_code);
        }else {
        	System.out.println("Failed recognize command");
        }
        	

    }
}
