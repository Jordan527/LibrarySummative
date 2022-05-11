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
		userManager = new UserManager();
		itemManager = new ItemManager();
	}
	public void init() throws IOException
	{
		library.connect();
		if(library.opened)
		{
			library.initBooks(itemManager);
			library.initMovies(itemManager);
			library.initUsers(userManager);
			initialised = true;
			this.user = userManager.getUser(1);
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
	public void addUser(Users user)
	{
		library.addUser(user);
	}
	public UserManager getUserManager()
	{
		return userManager;
	}
	public ItemManager getItemManager()
	{
		return itemManager;
	}
	public void setUserManager(UserManager userManager)
	{
		this.userManager = userManager;
	}
	public void setItemManager(ItemManager itemManager)
	{
		this.itemManager = itemManager;
	}
	public void addToBasket(Items item, Users user)
	{
		int itemID = item.getID();
		int userID = user.getID();
		library.addToBasket(itemID, userID);
	}
	public void getBasket()
	{
		int userID = user.getID();
		library.getBasket(userID);
	}
}
