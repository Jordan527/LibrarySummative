package interfaces.controller;

import users.*;
import items.*;

import java.io.IOException;

import database.*;

public class MainController {
	public UserManager userManager;
	public ItemManager itemManager;
	public DbConnection library;
	
	public Boolean initialised = false;
	public Users user;
	public Items item;
	
	public MainController()
	{
		library = new DbConnection();
		itemManager = new ItemManager();
		userManager = new UserManager();
	}
	public void init() throws IOException
	{
		library.connect();
		if(library.opened)
		{
			library.initBooks(itemManager);
			library.initMovies(itemManager);
			login("Yorudan", "root");
//			login("Tesla", "Cars");			
			initialised = true;
		}
		library.disconnect();
	}
	
	public Users getUser() {
		return user;
	}
	public void setUser(Users user) {
		this.user = user;
	}
	public Items getItem() {
		return item;
	}
	public void setItem(Items item) {
		this.item = item;
	}
	public void addUser(String forename, String surname, String username, String password)
	{
		library.addUser(forename, surname, username, password);
	}
	public ItemManager getItemManager()
	{
		return itemManager;
	}
	public UserManager getUserManager()
	{
		return userManager;
	}
	public void setItemManager(ItemManager itemManager)
	{
		this.itemManager = itemManager;
	}
	public void addToBasket(Items item)
	{
		int itemID = item.getID();
		int userID = this.user.getID();
		library.addToBasket(itemID, userID);
	}
	public void removeFromBasket(Items item)
	{
		int itemID = item.getID();
		int userID = this.user.getID();
		library.removeFromBasket(itemID, userID);
	}
	public void deleteFromBasket(Items item)
	{
		int itemID = item.getID();
		int userID = this.user.getID();
		library.deleteFromBasket(itemID, userID);
	}
	public void order()
	{
		int userID = this.user.getID();
		library.order(itemManager, userID);
	}
	public void getBasket() throws IOException
	{
		int userID = user.getID();
		itemManager.clearList();
		library.getBasket(userID, itemManager);
	}
	public void getLoaned() throws IOException
	{
		int userID = user.getID();
		itemManager.clearList();
		library.getLoaned(userID, itemManager);
	}
	public void login(String username, String password)
	{
		this.user = library.Login(username, password);
	}
	public int existingUser(String forename, String surname, String username, String password)
	{
		int exists = library.existingUser(forename, surname, username, password);
		return exists;
	}
}
