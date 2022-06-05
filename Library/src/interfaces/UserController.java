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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import users.UserManager;
import users.Users;

public class UserController {
	@FXML
	public Label titleLabel;
	@FXML
	public MenuButton settingsButton;
	@FXML
	public Button deleteButton;
	@FXML
	public Button verifyButton;
	
	
	public MainController controller;
	public UserManager userManager;
	public SceneController sceneController = new SceneController();
	public Users user;
	
	public void init(MainController controller) throws IOException
	{
		this.controller = controller;
		
		this.userManager = controller.getUserManager();
		controller.settingsButtonSetup(settingsButton, true, true);
		
		this.user = userManager.getUserList().get(0);
		
		displayUserInfo();
	}
	
	
	public void displayUserInfo()
	{
		user.displayAll();
	}

	public void Verify(ActionEvent event) throws Exception
	{
		System.out.println("Verified");
	}
	
	public void Delete(ActionEvent event) throws Exception
	{
		System.out.println("Deleted");
	}
	
	public void Back(ActionEvent event) throws Exception
	{
		controller.clearUsers();
		sceneController.loadController(controller);
		sceneController.switchToAdmin((Stage) titleLabel.getScene().getWindow());
	}

}
