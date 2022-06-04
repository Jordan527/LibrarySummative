package database;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;  
import java.text.SimpleDateFormat;

import items.*;
import users.*;

public class DbConnection {
	String url = "jdbc:mysql://localhost:3306/library"; // location of database – update for your own implementation
	String username = "root"; // username for database – update for your own implementation
	String password = "Jajo3003"; // password for database – update for your own implementation
	
	String dateFormat = "dd-MM-yyyy";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

	Connection connection = null;
	Statement stmt = null;
	Statement loanStmt = null;
	public Boolean opened = true;
	
	public DbConnection() {}
	
	
	public void connect() 
	{
		try 
		{
		    // create connection
		    System.out.println("Connecting to the MySQL database...");
		    connection = DriverManager.getConnection(url, username, password);
		    System.out.println("MySQL database connected!");
		    opened = true;
		}
		catch (SQLException sqlE)
		{
			opened = false;
            System.out.println(sqlE.toString());
		}
	}
	
	public void disconnect() 
	{
        // close connection
        System.out.println("Closing connection...");
        if(connection != null)
        {
            try
            {
            	opened = false;
                connection.close();
            }
            catch (SQLException ignore)
            {}

            connection= null;
        }
	}
	
	
	
	public void initBooks(ItemManager manager) throws IOException 
	{
		String sql = "select * from view_books";
		
		
		Items item = new Items();
    	double cost;
    	String name, genre, author, image, date;
    	int id, year, pages, quantity;
    	Boolean available;
    	
    	
		try
		{	
			System.out.println("Initialising books...");
            // create statement
            stmt = connection.createStatement();
            loanStmt = connection.createStatement();

            // execute queries
            ResultSet resultSet = stmt.executeQuery(sql); 
            
            
            
            // print results
            while (resultSet.next())
            {
            	id = resultSet.getInt(1);
            	name = resultSet.getString(2);
            	year = resultSet.getInt(3);
            	genre = resultSet.getString(4);
            	cost = resultSet.getDouble(5);
            	image = resultSet.getString(8);
            	quantity = resultSet.getInt(9);
            	
            	item = new Items(id, name, year, image, genre, cost);
        		manager.addItem(item);
        		
        		String sql2 = "call product_loaned(" + id + ")";
        		ResultSet basket = loanStmt.executeQuery(sql2);
        		
        
        		
        		for(int i = 0; i < quantity; i++)
        		{
        			available = true;
        			date = null;
        			if(basket.next())
        			{
        				available = false;
        				Date copyDate = basket.getDate(1);
        				date = simpleDateFormat.format(copyDate);
        			}
            		id = resultSet.getInt(1);
            		author = resultSet.getString(6);
                	pages = resultSet.getInt(7);
                	
                	Book copy = new Book(id, author, pages, available, date);
                	manager.addCopy(copy);
        		}
            }
            stmt.close();
            loanStmt.close();
            System.out.println("\nBooks initialised!");
		}
		catch (SQLException sqlE)
		{
            System.out.println(sqlE.toString());
		}
	}
	
	public void initMovies(ItemManager manager) throws IOException 
	{
		String sql = "select * from view_movies";
		
		Items item = new Items();
    	double cost, duration;
    	String name, genre, director, image, date;
    	int id, year, quantity;
    	Boolean available;
    	
		try
		{
			System.out.println("Initialising movies...");
            // create statement
            stmt = connection.createStatement();
            loanStmt = connection.createStatement();

            // execute queries
            ResultSet resultSet = stmt.executeQuery(sql); 
            
  
            // print results
            while (resultSet.next())
            {
            	id = resultSet.getInt(1);
            	name = resultSet.getString(2);
            	year = resultSet.getInt(3);
            	genre = resultSet.getString(4);
            	cost = resultSet.getDouble(5);
            	image = resultSet.getString(8);
            	quantity = resultSet.getInt(9);

        		item = new Items(id, name, year, image, genre, cost);
        		manager.addItem(item);
        		
        		String sql2 = "call product_loaned(" + id + ")";
        		ResultSet basket = loanStmt.executeQuery(sql2);
        		
        		for(int i = 0; i < quantity; i++)
        		{
        			available = true;
        			date = null;
        			if(basket.next())
        			{
        				available = false;
        				Date copyDate = basket.getDate(1);
        				date = simpleDateFormat.format(copyDate);
        			}
	        		id = resultSet.getInt(1);
	        		director = resultSet.getString(6);
	            	duration = resultSet.getDouble(7);

	            	Movie copy = new Movie(id, director, duration, available, date);
	            	manager.addCopy(copy);
        		}
            	
            }
            stmt.close();
            loanStmt.close();
            System.out.println("\nMovies initialised!");
		}
		catch (SQLException sqlE)
		{
            System.out.println(sqlE.toString());
		}
	}


	public Users Login(String username, String password)
	{
		connect();
		
		if(opened)
		{
			Users user = null;
			int id;
	    	String forename;
	    	String surname;
	    	int access;
	    	
			try
			{
				System.out.println("Logging in...");
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.user_login(";
	        
	            sql += "\"" + username + "\"";
	            sql += "," + "\"" + password + "\"" + ");";

	            // execute queries
	            ResultSet resultSet = stmt.executeQuery(sql); 
	

	            while (resultSet.next())
	            {
	            	id = resultSet.getInt(1);
	            	forename = resultSet.getString(2);
	            	surname = resultSet.getString(3);
	            	username = resultSet.getString(4);
	            	password = resultSet.getString(5);
	            	access = resultSet.getInt(6);
	            	if (access == 2)
	            	{
	            		user = new Admin(id, forename, surname, username, password);
	            	} else
	            	{
	            		user = new Standard(id, forename, surname, username, password, access);
	            	}
	            }
	            
	            stmt.close();
	            System.out.println("Logged in!");
	            return user;
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
		return null;
	}

	public int existingUser(String forename, String surname, String username, String password)
	{
		connect();
		
		if(opened)
		{	    	
			try
			{
				int exists = 0;
				System.out.println("Logging in...");
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.existing_user(";
		        
	            sql += "\"" + forename + "\"";
	            sql += "," + "\"" + surname + "\"";
	            sql += "," + "\"" + username + "\"";
	            sql += "," + "\"" + password + "\"" + ");";

	            // execute queries
	            ResultSet resultSet = stmt.executeQuery(sql); 
	

	            while (resultSet.next())
	            {
	            	exists = 1;
	            }
	            
	            stmt.close();
	            System.out.println("Logged in!");
	            return exists;
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
		return 0;
	}
	
	public void addUser(String forename, String surname, String username, String password)
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Inserting new user into database...");
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.add_user(";
	        
	            sql += "\"" + forename + "\"";
	            sql += "," + "\"" + surname + "\"";
	            sql += "," + "\"" + username + "\"";
	            sql += "," + "\"" + password + "\"";
	            sql += "," + "\"" + 0 + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
	            System.out.println("User Inserted!");
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
	}
	
	public void addToBasket(int itemID, int userID)
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Adding new item to basket...");
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.add_basket(";
	            
	            sql += "\"" + userID + "\"";
	            sql += "," + "\"" + itemID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
	            System.out.println("Item Added!");
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
	}

	public void removeFromBasket(int itemID, int userID)
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Removing an item from the basket...");
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.remove_basket(";
	            
	            sql += "\"" + userID + "\"";
	            sql += "," + "\"" + itemID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
	            System.out.println("Item Removed!");
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
	}
	
	public void deleteFromBasket(int itemID, int userID)
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Deleting an item from the basket...");
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.delete_basket(";
	            
	            sql += "\"" + userID + "\"";
	            sql += "," + "\"" + itemID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
	            System.out.println("Item Deleted!");
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
	}
	
	public void order(ItemManager manager, int userID)
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Loaning Items...");
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			    Date currentDate = new Date(); 
			    
			    Calendar c = Calendar.getInstance();
		        c.setTime(currentDate);
		        c.add(Calendar.DATE, 30);
		        
		        Date rawDate = c.getTime();
			    String date = formatter.format(rawDate);

				
	            // create statement
	            stmt = connection.createStatement();
	            
	            for (Items item : manager.getAll())
	            {
	            	int itemID = item.getID();
	            	
	            	for(int i = 0; i < item.getQuantity(); i ++)
	            	{
		            	String sql = "call library.add_loaned(";
			            
			            sql += "\"" + itemID + "\"";
			            sql += "," + "\"" + userID + "\"";
			            sql += "," + "\"" + date + "\"" + ");";

			            // execute queries
			            stmt.executeUpdate(sql); 
	            	}

	            }
	            

	

	            stmt.close();
	            System.out.println("Items Loaned!");
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
	}
	
	public void getBasket(int userID, ItemManager manager) throws IOException
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Getting basket...");
				
				Items item = new Items();
		    	double cost, duration;
		    	String name, genre, creator, image, date;
		    	int id, year, pages, quantity, type;
		    	Boolean available;
		    	
	            // create statement
	            stmt = connection.createStatement();
	            loanStmt = connection.createStatement();
	            
				String sql = "call library.user_basket(";
	            
	            sql += "\"" + userID + "\"" + ");";

	            
	            // execute queries
	            ResultSet resultSet = stmt.executeQuery(sql); 
	            
	            while (resultSet.next())
	            {
	            	id = resultSet.getInt(1);
	            	name = resultSet.getString(2);
	            	year = resultSet.getInt(3);
	            	genre = resultSet.getString(4);
	            	cost = resultSet.getDouble(5);
	            	creator = resultSet.getString(6);
	            	image = resultSet.getString(8);
	            	type = resultSet.getInt(9);
	            	quantity = resultSet.getInt(10); 
	            	
	            	item = new Items(id, name, year, image, genre, cost);
	        		manager.addItem(item);
	        		
	        		String sql2 = "call product_loaned(" + id + ")";
	        		ResultSet laoned = loanStmt.executeQuery(sql2);
	        		
	        		if(type == 1)
	        		{
		        		for(int i = 0; i < quantity; i++)
		        		{
		        			available = true;
		        			date = null;
		        			if(laoned.next())
		        			{
		        				available = false;
		        				Date copyDate = laoned.getDate(1);
		        				date = simpleDateFormat.format(copyDate);
		        			}
		            		id = resultSet.getInt(1);
		                	pages = resultSet.getInt(7);
		                	
		                	Book copy = new Book(id, creator, pages, available, date);
		                	manager.addCopy(copy);
		        		}
	        		} else
	        		{
	            		for(int i = 0; i < quantity; i++)
	            		{
	            			available = true;
	            			date = null;
	            			if(laoned.next())
	            			{
	            				available = false;
	            				Date copyDate = laoned.getDate(1);
	            				date = simpleDateFormat.format(copyDate);
	            			}
	    	        		id = resultSet.getInt(1);
	    	            	duration = resultSet.getDouble(7);

	    	            	Movie copy = new Movie(id, creator, duration, available, date);
	    	            	manager.addCopy(copy);
	            		}
	        		}
	            }

	            stmt.close();
	            loanStmt.close();
	            System.out.println("Basket Retrieved!");
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
		
	}
	
	public void getLoaned(int userID, ItemManager manager) throws IOException
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Getting loaned...");
				
				Items item = new Items();
		    	double cost, duration;
		    	String name, genre, creator, image, date;
		    	int id, year, pages, quantity, type;
		    	Boolean available;
		    	
	            // create statement
	            stmt = connection.createStatement();
	            loanStmt = connection.createStatement();
	            
				String sql = "call library.user_loaned(";
	            
	            sql += "\"" + userID + "\"" + ");";

	            
	            // execute queries
	            ResultSet resultSet = stmt.executeQuery(sql); 
	            
	            while (resultSet.next())
	            {
	            	id = resultSet.getInt(1);
	            	name = resultSet.getString(2);
	            	year = resultSet.getInt(3);
	            	genre = resultSet.getString(4);
	            	cost = resultSet.getDouble(5);
	            	creator = resultSet.getString(6);
	            	image = resultSet.getString(8);
	            	type = resultSet.getInt(9);
	            	date = resultSet.getString(10);
	            	
	            	item = new Items(id, name, year, image, genre, cost);
	        		manager.addItem(item);
	        		

	        		if(type == 1)
	        		{
	        			available = true;

	            		id = resultSet.getInt(1);
	                	pages = resultSet.getInt(7);
	                	
	                	Book copy = new Book(id, creator, pages, available, date);
	                	manager.addCopy(copy);		        		
	        		} else
	        		{
            			available = true;

    	        		id = resultSet.getInt(1);
    	            	duration = resultSet.getDouble(7);

    	            	Movie copy = new Movie(id, creator, duration, available, date);
    	            	manager.addCopy(copy);
	        		}
	            }

	            stmt.close();
	            loanStmt.close();
	            System.out.println("Loaned Retrieved!");
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
		
	}
	
	public int newUserID()
	{
		connect();
		
		if(opened)
		{
			Users user = null;
			int id = 0;
	    	
			try
			{		
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "SELECT * FROM library.view_users;";

	            // execute queries
	            ResultSet resultSet = stmt.executeQuery(sql); 
	

	            while (resultSet.next())
	            {
	            	id = resultSet.getInt(1);
	            }
	            
	            stmt.close();
	            return (id + 1);
			}
			catch (SQLException sqlE)
			{
	            System.out.println(sqlE.toString());
			}
			finally
			{
				disconnect();
			}
		}
		return 0;
	}
}
