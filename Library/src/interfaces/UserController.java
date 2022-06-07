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
	@FXML
	public Label userUsername;
	@FXML
	public Label userForename;
	@FXML
	public Label userSurname;
	@FXML
	public Label userAccess;
	@FXML
	public Label loanedLabel;
	
	public MainController controller;
	public UserManager userManager;
	public ItemManager itemManager;
	public SceneController sceneController = new SceneController();
	public Users user;
	
	public void init(MainController controller) throws IOException
	{
		loanedLabel.setId("titleLabel");
		
		this.controller = controller;
		this.userManager = controller.getUserManager();
		this.user = userManager.getUserList().get(0);
		
		controller.getLoaned(user.getID());
		this.itemManager = controller.getItemManager();
		
		
		controller.settingsButtonSetup(settingsButton, true, true);
		
		if(user.getIntAccess() == 1)
		{
			verifyButton.setVisible(false);
		}
		
		displayUserInfo();
		displayLoanedInfo();
	}
	
	
	public void displayUserInfo()
	{
		userUsername.setText(user.getUsername());
		userForename.setText(user.getForename());
		userSurname.setText(user.getSurname());
		userAccess.setText(user.getAccess());;
	}
	
	public void displayLoanedInfo()
	{
		ArrayList<Items> itemList = itemManager.getAll();
		int movieX = -105;
		int bookX = -105;
		double x = loanedLabel.getLayoutX();
		double y = loanedLabel.getLayoutY() + 30;
		
		Pane pane = (Pane) titleLabel.getParent(); 
		
		Label titleHead = new Label("Title");
		Label creatorHead = new Label("Creator");	
		Label dateHead = new Label("Date");		
		Label returnDateHead = new Label("Return By Date");
		
		titleHead.setId("itemHeader");
		titleHead.setLayoutX(x);
		titleHead.setLayoutY(y);
		
		creatorHead.setId("itemHeader");
		creatorHead.setLayoutX(x + 180);
		creatorHead.setLayoutY(y);
		
		dateHead.setId("itemHeader");
		dateHead.setLayoutX(x + 300);
		dateHead.setLayoutY(y);
		
		returnDateHead.setId("itemHeader");
		returnDateHead.setLayoutX(x + 350);
		returnDateHead.setLayoutY(y);
		
		y += 30;
		
		pane.getChildren().addAll(titleHead, creatorHead, dateHead, returnDateHead);
		
		for(Items item : itemList)
		{			
			Label title = new Label(item.getName());		
			Label date = new Label("" + item.getRelease());
			Label returnDate = new Label();
			
			Label creator;
			try
			{
				Movie copy = (Movie) item.getItem();
				creator = new Label(copy.getDirector());
				returnDate = new Label(copy.getDate());
			} catch (Exception e) {
				Book copy = (Book) item.getItem();
				creator = new Label(copy.getAuthor());
				returnDate = new Label(copy.getDate());
			}
			
			title.setId("ItemTitle");
			title.setLayoutX(x);
			title.setLayoutY(y);
			
			creator.setLayoutX(x + 180);
			creator.setLayoutY(y);
			
			date.setLayoutX(x + 300);
			date.setLayoutY(y);
			
			returnDate.setLayoutX(x + 350);
			returnDate.setLayoutY(y);

			Button button = new Button("Returned");
			button.setLayoutX(x + 450);
			button.setLayoutY(y-7.5);
			button.setOnAction(event -> {
				try {
					Return(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			});
			
			pane.getChildren().addAll(title, creator, date, returnDate, button);
			y += 40;
		}
	}

	public void Return(Items item) throws Exception
	{
		controller.returnItem(user.getID(), item.getID());
		sceneController.loadController(controller);
		sceneController.switchToUser((Stage) titleLabel.getScene().getWindow());
	}
	
	public void Verify(ActionEvent event) throws Exception
	{
		controller.verifyUser(user.getID());
		userAccess.setText(user.getAccess());
		verifyButton.setVisible(false);
	}
	
	public void Delete(ActionEvent event) throws Exception
	{
		controller.deleteUser(user.getID());
		controller.clearUsers();
		itemManager.clearList();
		controller.setItemManager(itemManager);
		controller.init();
		sceneController.loadController(controller);
		sceneController.switchToAdmin((Stage) titleLabel.getScene().getWindow());
	}
	
	public void Back(ActionEvent event) throws Exception
	{
		controller.clearUsers();
		itemManager.clearList();
		controller.setItemManager(itemManager);
		controller.init();
		sceneController.loadController(controller);
		sceneController.switchToAdmin((Stage) titleLabel.getScene().getWindow());
	}
}
