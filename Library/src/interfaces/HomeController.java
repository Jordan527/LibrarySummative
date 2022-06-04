package interfaces;

import items.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.imageio.stream.FileImageInputStream;

import interfaces.controller.MainController;
import interfaces.controller.SceneController;
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

public class HomeController {
	@FXML
	public Label titleLabel;
	@FXML
	public Label booksLabel;
	@FXML
	public Label moviesLabel;
	@FXML
	public MenuButton settingsButton;
	
	public MainController controller;
	public SceneController sceneController = new SceneController();
	public ItemManager itemManager;
	
	
	public void init(MainController controller)
	{
		this.controller = controller;
		this.itemManager = controller.getItemManager();
		if(controller.user != null)
		{
			setupUser();
			if(controller.user.getAccess() == 2)
			{
				setupAdmin();
			}
		}
		
		addBooks();
		addMovies();
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

	public void setupAdmin()
	{
		Button adminButton = new Button("Admin");
		
		double x = settingsButton.getLayoutX();
		double y = settingsButton.getLayoutY();

		adminButton.setLayoutX(x-80);
		adminButton.setLayoutY(y-3);
		adminButton.setOnAction(event -> {
			try {
				Admin();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		Pane pane = (Pane) titleLabel.getParent();
		pane.getChildren().addAll(adminButton);
	}
	
	public void Admin() throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToAdmin((Stage) titleLabel.getScene().getWindow());
	}
	
	public void addBooks()
	{
		Pane pane =  (Pane) booksLabel.getParent();
		
		ArrayList<Items> booksList = itemManager.getBooks();
		int x = 5;
		int y = 23;
		
		
		for(Items item : booksList)
		{
			Book copy = (Book) item.getItem();
			
			ImageView imageView = loadImage(item.getImage());
			imageView.setFitWidth(80);
			imageView.setFitHeight(110);
			
			
			Label title = new Label(item.getName());
			Label author = new Label(copy.getAuthor());
			Label date = new Label("" + item.getRelease());
			Label cost = new Label(item.getCostOutput());
			
			
			imageView.setX(0);
			imageView.setY(0);
			
			title.setId("ItemTitle");
			title.setLayoutX(0);
			title.setLayoutY(110);
			
			author.setLayoutX(0);
			author.setLayoutY(125);
			
			date.setLayoutX(0);
			date.setLayoutY(140);
			
			cost.setLayoutX(0);
			cost.setLayoutY(155);
			
			Pane itemPane = new Pane();
			itemPane.setId("ItemPane");
			itemPane.setLayoutX(x);
			itemPane.setLayoutY(y);
			itemPane.setOnMouseClicked(event -> {
				try {
					itemSelected(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			itemPane.getChildren().addAll(imageView, title, author, date, cost);			
			pane.getChildren().add(itemPane);
			
			x += 110;
		}
	}
	
	public void addMovies()
	{
		Pane pane =  (Pane) moviesLabel.getParent();
		
		ArrayList<Items> moviesList = itemManager.getMovies();
		int x = 5;
		int y = 23;
		
		
		for(Items item : moviesList)
		{
			Movie copy = (Movie) item.getItem();
			
			ImageView imageView = loadImage(item.getImage());
			imageView.setFitWidth(80);
			imageView.setFitHeight(110);
			
			
			Label title = new Label(item.getName());
			Label director = new Label(copy.getDirector());
			Label date = new Label("" + item.getRelease());
			Label cost = new Label(item.getCostOutput());
			
			
			imageView.setX(0);
			imageView.setY(0);
			
			title.setId("ItemTitle");
			title.setLayoutX(0);
			title.setLayoutY(110);
			
			director.setLayoutX(0);
			director.setLayoutY(125);
			
			date.setLayoutX(0);
			date.setLayoutY(140);
			
			cost.setLayoutX(0);
			cost.setLayoutY(155);
			
			Pane itemPane = new Pane();
			itemPane.setId("ItemPane");
			itemPane.setLayoutX(x);
			itemPane.setLayoutY(y);
			itemPane.setOnMouseClicked(event -> {
				try {
					itemSelected(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			
			itemPane.getChildren().addAll(imageView, title, director, date, cost);			
			pane.getChildren().add(itemPane);
			
			x += 110;
		}
	}
	
	public ImageView loadImage(String source)
	{
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(source);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image image = new Image(inputStream);
		ImageView imageView = new ImageView(image);
		return imageView;
	}

	public void itemSelected(Items item) throws Exception
	{
		controller.setItem(item);
		sceneController.loadController(controller);
		sceneController.switchToItem((Stage) titleLabel.getScene().getWindow());
	}
	
	public void Loaned() throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToLoaned((Stage) titleLabel.getScene().getWindow());
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
	
	public void Login(ActionEvent event) throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToLogin((Stage) titleLabel.getScene().getWindow());
	}
	public void Register(ActionEvent event) throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToRegister((Stage) titleLabel.getScene().getWindow());
	}
}
