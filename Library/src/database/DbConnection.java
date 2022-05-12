package database;

import java.io.IOException;
import java.sql.*;
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

	public void initUsers(UserManager manager) 
	{
		String sql = "select * from view_users";
		
		Items item = new Items();
    	int id;
    	String forename;
    	String surname;
    	String username;
    	String password;
    	int access;
    	
    	
		try
		{
			System.out.println("Initialising users...");
            // create statement
            stmt = connection.createStatement();

            // execute queries
            ResultSet resultSet = stmt.executeQuery(sql); 
            
            
            
            // print results
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
            		Admin user = new Admin(id, forename, surname, username, password);
            		manager.addUser(user);
            	} else
            	{
            		Standard user = new Standard(id, forename, surname, username, password, access);
            		manager.addUser(user);
            	}
            	
            	
            	System.out.println("\n");
            }
            stmt.close();
            System.out.println("Users initialised!");
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
	
	public void addUser(Users user)
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
	        
	            sql += "\"" + user.getID() + "\"";
	            sql += "," + "\"" + user.getForename() + "\"";
	            sql += "," + "\"" + user.getSurname() + "\"";
	            sql += "," + "\"" + user.getUsername()+ "\"";
	            sql += "," + "\"" + user.getPassword() + "\"";
	            sql += "," + "\"" + user.getAccess() + "\"" + ");";

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
	            
	            sql += "\"" + itemID + "\"";
	            sql += "," + "\"" + userID + "\"" + ");";

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
	
	public void getBasket(int userID)
	{
		connect();
		
		if(opened)
		{
			try
			{
				System.out.println("Getting basket...");
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.user_basket(";
	            
	            sql += "\"" + userID + "\"" + ");";

	            int productID;
	            int quantity;
	            
	            // execute queries
	            ResultSet resultSet = stmt.executeQuery(sql); 
	            
	            while (resultSet.next())
	            {
	            	productID = resultSet.getInt(1);
	            	quantity = resultSet.getInt(2);
	            	
	            	System.out.println("User ID: " + userID +
	            						"\nProduct ID: " + productID +
	            						"\nQuantity: " + quantity + "\n");
	            	
	            }

	            stmt.close();
	            System.out.println("Basker Retrieved!");
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
}
