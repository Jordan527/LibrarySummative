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

public class AdminController {
	@FXML
	public Label titleLabel;
	@FXML
	public MenuButton settingsButton;
	@FXML
	public TableView<Users> table;
	@FXML
	public TableColumn<Users, String> usernameColumn;
	@FXML
	public TableColumn<Users, String> forenameColumn;
	@FXML
	public TableColumn<Users, String> surnameColumn;
	@FXML
	public TableColumn<Users, String> accessColumn;
	@FXML
	public Button deleteButton;
	@FXML
	public Button verifyButton;
	
	
	public MainController controller;
	public UserManager userManager;
	public SceneController sceneController = new SceneController();
	
	public void init(MainController controller) throws IOException
	{
		this.controller = controller;
		controller.drawUsers();
		
		this.userManager = controller.getUserManager();
		controller.settingsButtonSetup(settingsButton, true, true);
		
		ArrayList<Users> users = userManager.getUserList();
		table_init();
		setTableContent(users);
	}
	
	public void table_init() {
		usernameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
		forenameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("forename"));
		surnameColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("surname"));
		accessColumn.setCellValueFactory(new PropertyValueFactory<Users, String>("access"));
	}
	
	public void setTableContent(ArrayList<Users> userList)
	{
		table.getItems().setAll(userList);   
	}
	
	public void clickItem(MouseEvent event) throws Exception
	{
	    if (event.getClickCount() == 2) //Checking double click
	    {
	    	Users data = table.getSelectionModel().getSelectedItem();
	    	if(data != null)
	    	{
		    	int userID = data.getID();
		    	
		    	Users user = userManager.getUser(userID);
		    	userManager.clear();
		    	userManager.addUser(user);
		    	controller.setUserManager(userManager);
		    	sceneController.loadController(controller);
		    	sceneController.switchToUser((Stage) titleLabel.getScene().getWindow());
	    	}
	    }
	}
	
	public void Verify(ActionEvent event) throws Exception
	{
		int userID = table.getSelectionModel().getSelectedItem().getID();
		controller.verifyUser(userID);
		ArrayList<Users> users = userManager.getUserList();
		
		int row = table.getSelectionModel().getSelectedIndex();
		System.out.println(row);
		
		Users user = userManager.getUser(userID);
		
		
		table.getItems().set(row, user);
	}
	
	public void Delete(ActionEvent event) throws Exception
	{
		int user = table.getSelectionModel().getSelectedItem().getID();
		controller.deleteUser(user);
		table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
	}
	
	public void Back(ActionEvent event) throws Exception
	{
		controller.clearUsers();
		sceneController.loadController(controller);
		sceneController.switchToHome((Stage) titleLabel.getScene().getWindow());
	}
}
