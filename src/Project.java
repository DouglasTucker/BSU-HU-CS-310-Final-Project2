import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class Project {

    
	public static void createItem(String item_code, String item_Description, double price, int inventory_amount){
		
	}
	
	
    public static void main(String[] args){

        if (args[0].equals("CreateItem")) {
            String item_code = args[1];
            String item_Description = args[2];
            double price = Double.parseDouble(args[3]);
            int inventory_amount = Integer.parseInt(args[4]);
        	createItem(item_code, item_Description, price, inventory_amount);
        	
        } else if (args[0].equals("CreateCar")) {
            String brand = args[1];
            String type = args[2];
            double price = Double.parseDouble(args[3]);
            int owner_id = Integer.parseInt(args[4]);
            attemptToCreateNewCar(brand, type, price, owner_id);
        } else if (args[0].equals("CreateCarSP")) {
            String brand = args[1];
            String type = args[2];
            double price = Double.parseDouble(args[3]);
            int owner_id = Integer.parseInt(args[4]);
            attemptToCreateNewCarUsingSP(brand, type, price, owner_id);
        } else if (args[0].equals("CreateOwner")) {
            String firstName = args[1];
            String lastName = args[2];
            attemptToCreateNewOwner(firstName, lastName);
        } else if (args[0].equals("UpdateOwner")) {
            String firstName = args[1];
            String lastName = args[2];
            int owner_id = Integer.parseInt(args[3]);
            attemptToUpdateOwner(firstName, lastName, owner_id);
        } else if (args[0].equals("DeleteOwner")) {
            int owner_id = Integer.parseInt(args[1]);
            attemptToDeleteOwner(owner_id);
        }
    }
}
