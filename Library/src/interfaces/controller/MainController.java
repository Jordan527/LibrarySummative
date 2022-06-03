package interfaces.controller;

import users.*;
import items.*;

import java.io.IOException;

import database.*;

public class MainController {
	public ItemManager itemManager;
	public DbConnection library;
	
	public Boolean initialised = false;
	public Users user;
	public Items item;
	
	public MainController()
	{
		library = new DbConnection();
		itemManager = new ItemManager();
	}
	public void init() throws IOException
	{
		library.connect();
		if(library.opened)
		{
			library.initBooks(itemManager);
			library.initMovies(itemManager);
			login("Yorudan", "root");
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
	public void setItemManager(ItemManager itemManager)
	{
		this.itemManager = itemManager;
	}
	public void addToBasket(Items item, Users user)
	{
		int itemID = item.getID();
		int userID = user.getID();
		System.out.println(userID);
		System.out.println(itemID);
		library.addToBasket(itemID, userID);
	}
	public void getBasket()
	{
		int userID = user.getID();
		library.getBasket(userID);
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
