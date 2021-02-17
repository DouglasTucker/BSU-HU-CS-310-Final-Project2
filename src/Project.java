import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Project {

    
	
	public static Item createItem(String item_code, String description, double price, int inventory_amount) throws SQLException {
		Connection connection = null;
		Item item = new Item(item_code, description, price, inventory_amount);
		
		connection = MySqlDatabase.getDatabaseConnection();
		Statement sqlStatement = connection.createStatement();
		
		String sql = String.format("INSERT INTO items (item_code, description, price, inventory_amount) VALUES ('%s' , '%s', %s, %s);",
                item.getItem_code(),
                item.getDescription(),
                item.getPrice(),
                item.getinventory_amount());
        sqlStatement.executeUpdate(sql);
        connection.close();
		
		return item;
	}
	
	public static void updateInventory(String item_code, int inventory_amount) throws SQLException {
		Connection connection = null;
		
		connection = MySqlDatabase.getDatabaseConnection();
        Statement sqlStatement = connection.createStatement();
        
        String sql = String.format("UPDATE items SET inventory_amount = '%s' WHERE id = %s;",
        				inventory_amount, item_code);

        sqlStatement.executeUpdate(sql);

        connection.close();
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
	
    public static void main(String[] args) throws SQLException{

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
        	
        }else if(args[0].equals("DeleteItem")) {
        	String item_code = args[1];
        	
        }else if(args[0].equals("GetItems")) {
        	String item_code = args[1];
        	
        }else if(args[0].equals("CreateOrder ")) {
        	
        }else if(args[0].equals("DeleteOrder")) {
        	
        }else if(args[0].equals("GetOrders")) {
        	
        }else if(args[0].equals("GetOrderDetails")) {
        	
        }
        	

    }
}
