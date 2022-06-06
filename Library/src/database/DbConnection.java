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
	Statement prodStmt = null;
	public Boolean opened = true;
	
	public DbConnection() {}
	
	
	public void connect() 
	{
		try 
		{
		    // create connection
		    connection = DriverManager.getConnection(url, username, password);
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
	
	public void initItems(ItemManager manager) throws IOException 
	{
		String sql = "select * from view_products";
		
		
		Items item = new Items();
    	double cost, duration;
    	String name, genre, creator, image, date, type;
    	int id, year, pages, quantity;
    	Boolean available;
    	
    	
		try
		{	
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
            	type = resultSet.getString(10);
            	
            	item = new Items(id, name, year, image, genre, cost);
        		manager.addItem(item);
        		
        		String sql2 = "call product_loaned(" + id + ")";
        		ResultSet loaned = loanStmt.executeQuery(sql2);
        		
        
        		
        		for(int i = 0; i < quantity; i++)
        		{
        			available = true;
        			date = null;
        			if(loaned.next())
        			{
        				available = false;
        				Date copyDate = loaned.getDate(1);
        				date = simpleDateFormat.format(copyDate);
        			}
            		id = resultSet.getInt(1);
            		creator = resultSet.getString(6);
                	
                	
                	if(type.equals("Book"))
                	{
                		pages = resultSet.getInt(7);
                		Book copy = new Book(id, creator, pages, available, date);
                		manager.addCopy(copy);
                	} else
                	{
                		duration = resultSet.getInt(7);
                		Movie copy = new Movie(id, creator, duration, available, date);
                		manager.addCopy(copy);
                	}
        		}
            }
            stmt.close();
            loanStmt.close();
		}
		catch (SQLException sqlE)
		{
			System.out.println(sqlE.toString());
		}
	}
	
	public void searchItems(ItemManager manager, String text) throws IOException
	{
		String sql = "SELECT * FROM view_products WHERE name LIKE '%" + text + "%'";
		
		
		Items item = new Items();
    	double cost, duration;
    	String name, genre, creator, image, date, type;
    	int id, year, pages, quantity;
    	Boolean available;
    	
    	
		try
		{	
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
            	type = resultSet.getString(10);
            	
            	item = new Items(id, name, year, image, genre, cost);
        		manager.addItem(item);
        		
        		String sql2 = "call product_loaned(" + id + ")";
        		ResultSet loaned = loanStmt.executeQuery(sql2);
        		
        
        		
        		for(int i = 0; i < quantity; i++)
        		{
        			available = true;
        			date = null;
        			if(loaned.next())
        			{
        				available = false;
        				Date copyDate = loaned.getDate(1);
        				date = simpleDateFormat.format(copyDate);
        			}
            		id = resultSet.getInt(1);
            		creator = resultSet.getString(6);
                	
                	
                	if(type.equals("Book"))
                	{
                		pages = resultSet.getInt(7);
                		Book copy = new Book(id, creator, pages, available, date);
                		manager.addCopy(copy);
                	} else
                	{
                		duration = resultSet.getInt(7);
                		Movie copy = new Movie(id, creator, duration, available, date);
                		manager.addCopy(copy);
                	}
                	
                	
        		}
            }
            stmt.close();
            loanStmt.close();
		}
		catch (SQLException sqlE)
		{
			System.out.println(sqlE.toString());
		}
	}

	
	public void initUsers(UserManager manager) 
	{
		String sql = "select * from view_non_admin";
		
		Items item = new Items();
    	int id;
    	String forename;
    	String surname;
    	String username;
    	String password;
    	int access;
    	
    	
		try
		{
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
            	
            	
            }
            stmt.close();
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
				
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.add_basket(";
	            
	            sql += "\"" + userID + "\"";
	            sql += "," + "\"" + itemID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
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
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.remove_basket(";
	            
	            sql += "\"" + userID + "\"";
	            sql += "," + "\"" + itemID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
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
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.delete_basket(";
	            
	            sql += "\"" + userID + "\"";
	            sql += "," + "\"" + itemID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
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
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
			    Date currentDate = new Date(); 
			    
			    Calendar c = Calendar.getInstance();
		        c.setTime(currentDate);
		        c.add(Calendar.DATE, 30);
		        
		        Date rawDate = c.getTime();
			    String date = formatter.format(rawDate);

				
	            // create statement
	            stmt = connection.createStatement();
	            loanStmt = connection.createStatement();
	            prodStmt = connection.createStatement();
	            
	            String sql2;
	            ResultSet loaned;
	            String sql3;
	            ResultSet prodQuant;
	            
	            for (Items item : manager.getAll())
	            {
	            	int itemID = item.getID();
	        		sql2 = "call quantity_product_loaned(" + itemID + ")";
	        		loaned = loanStmt.executeQuery(sql2);
	        		
	        		sql3 = "call product_quantity(" + itemID + ")";
	        		prodQuant = prodStmt.executeQuery(sql3);
	        		
	        		prodQuant.next();
	        		loaned.next();
	        		
	        		int totalQuantity = prodQuant.getInt(1);
	        		int loanedQuantity = loaned.getInt(1);

	            	for(int i = 0; i < item.getQuantity(); i ++)
	            	{
	            		if(totalQuantity - loanedQuantity > 0)
	            		{
	            			String sql = "call library.add_loaned(";
				            
				            sql += "\"" + itemID + "\"";
				            sql += "," + "\"" + userID + "\"";
				            sql += "," + "\"" + date + "\"" + ");";

				            // execute queries
				            stmt.executeUpdate(sql); 
				            
				            loanedQuantity += 1;
	            		}
		            	
	            	}

	            }
	            
	            stmt.close();
	            loanStmt.close();
	            prodStmt.close();
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
	        		ResultSet loaned = loanStmt.executeQuery(sql2);
	        		
	        		
	        		
	        		if(type == 1)
	        		{
		        		for(int i = 0; i < quantity; i++)
		        		{
		        			available = true;
		        			date = null;
		        			if(loaned.next())
		        			{
		        				available = false;
		        				Date copyDate = loaned.getDate(1);
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
	            			if(loaned.next())
	            			{
	            				available = false;
	            				Date copyDate = loaned.getDate(1);
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
	
	public void deleteUser(int userID)
	{
		connect();
		
		if(opened)
		{
			try
			{
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.delete_user(";
	            
	            sql += "\"" + userID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
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
	
	public void verifyUser(int userID)
	{
		connect();
		
		if(opened)
		{
			try
			{
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.verify_user(";
	            
	            sql += "\"" + userID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
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

	public void returnItem(int userID, int itemID)
	{
		connect();
		
		if(opened)
		{
			try
			{
	            // create statement
	            stmt = connection.createStatement();
	            
				String sql = "call library.delete_loaned(";
	            
	            sql += "\"" + userID + "\"";
	            sql += "," + "\"" + itemID + "\"" + ");";

	            // execute queries
	            stmt.executeUpdate(sql); 
	

	            stmt.close();
	    		
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
