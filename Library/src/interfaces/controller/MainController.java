package interfaces.controller;

import users.*;
import items.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

import database.*;

public class MainController {
	public UserManager userManager;
	public ItemManager itemManager;
	public DbConnection library;
	public SceneController sceneController = new SceneController();
	public Stage stage;
	
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
			library.initItems(itemManager);
			login("Yorudan", "root");
			initialised = true;
		}
		library.disconnect();
	}	
	
	public void drawUsers()
	{
		library.connect();
		if(library.opened)
		{
			userManager.clear();
			library.initUsers(userManager);
		}
		library.disconnect();
	}
	public void clearUsers()
	{
		userManager.clear();
	}
	public void setUserManager(UserManager manager) 
	{
		this.userManager = manager;
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
	public void getLoaned(int userID) throws IOException
	{
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
	public void deleteUser(int userID)
	{
		library.deleteUser(userID);
	}
	public void verifyUser(int userID)
	{
		library.verifyUser(userID);
		userManager.verifyUser(userID);
	}
	public void returnItem(int userID, int itemID)
	{
		library.returnItem(userID, itemID);
	}
	public void searchItem(String text) throws IOException
	{
		library.connect();
		if(library.opened)
		{
			itemManager.clearList();
			library.searchItems(itemManager, text);
		}
		library.disconnect();
		
	}
	
	public void settingsButtonSetup(MenuButton button, boolean hasBasket, boolean hasLoaned)
	{
		if(this.user == null)
		{
			MenuItem login = new MenuItem("Login");
			login.setOnAction(event -> {
				try {
					Login();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			MenuItem register = new MenuItem("Register");
			register.setOnAction(event -> {
				try {
					Register();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			button.getItems().addAll(login, register);
		} else
		{
			MenuItem account = new MenuItem(this.user.getUsername());
			account.setOnAction(event -> Account());
			
			MenuItem basket = new MenuItem("Basket");
			basket.setOnAction(event -> {
				try {
					Basket();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			MenuItem loaned = new MenuItem("Loaned");
			loaned.setOnAction(event -> {
				try {
					Loaned();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			MenuItem logout = new MenuItem("Logout");
			logout.setOnAction(event -> {
				try {
					Logout();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			
			button.getItems().clear();
			
			if(this.user.getIntAccess() != 0)
			{
				if(hasBasket && hasLoaned)
				{
					button.getItems().addAll(account, basket, loaned, logout);
				} else if(!hasBasket)
				{
					button.getItems().addAll(account, loaned, logout);
				} else if(!hasLoaned)
				{
					button.getItems().addAll(account, basket, logout);
				}
				
			} else
			{
				if(hasBasket)
				{
					button.getItems().addAll(account, basket, logout);
				} else
				{
					button.getItems().addAll(account, logout);
				}
				
			}
		}

		
		
	}
	
	public void Account()
	{
		System.out.println(this.user.getUsername());
	}
	public void Logout() throws Exception
	{
		this.user = null;
		sceneController.loadController(this);
		sceneController.switchToHome(stage);
	}
	public void Basket() throws Exception
	{
		sceneController.loadController(this);
		sceneController.switchToBasket(stage);
	}
	public void Login() throws Exception
	{
		sceneController.loadController(this);
		sceneController.switchToLogin(stage);
	}
	public void Loaned() throws Exception
	{
		sceneController.loadController(this);
		sceneController.switchToLoaned(stage);
	}
	public void Register() throws Exception
	{
		sceneController.loadController(this);
		sceneController.switchToRegister(stage);
	}
	public void setStage(Stage stage)
	{
		this.stage = stage;
	}
}
