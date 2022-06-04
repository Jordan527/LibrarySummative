package interfaces;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import interfaces.controller.MainController;
import interfaces.controller.SceneController;
import items.Book;
import items.ItemManager;
import items.Items;
import items.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import users.UserManager;

public class AdminController {
	@FXML
	public Label titleLabel;
	@FXML
	public MenuButton settingsButton;
	@FXML
	public Pane bookPane;
	@FXML
	public Pane moviePane;
	
	public MainController controller;
	public UserManager userManager;
	public SceneController sceneController = new SceneController();
	
	public void init(MainController controller) throws IOException
	{
		this.controller = controller;
		controller.getLoaned();
		this.userManager = controller.getUserManager();
		setupUser();
	}
	

	
	
	public void Back(ActionEvent event) throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToHome((Stage) titleLabel.getScene().getWindow());
	}
	
	
	public void setupUser()
	{

		MenuItem account = new MenuItem(controller.user.getUsername());
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
		
		
		settingsButton.getItems().clear();
		settingsButton.getItems().addAll(account, basket, loaned, logout);
	
	}
	
	public void Account()
	{
		System.out.println(controller.user.getUsername());
	}
	
	public void Logout() throws Exception
	{
		controller.user = null;
		sceneController.loadController(controller);
		sceneController.switchToHome((Stage) titleLabel.getScene().getWindow());
	}
	public void Basket() throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToBasket((Stage) titleLabel.getScene().getWindow());
	}
	public void Loaned() throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToLoaned((Stage) titleLabel.getScene().getWindow());
	}
	
}
