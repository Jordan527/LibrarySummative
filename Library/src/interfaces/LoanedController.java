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

public class LoanedController {
	@FXML
	public Label titleLabel;
	@FXML
	public MenuButton settingsButton;
	@FXML
	public Pane bookPane;
	@FXML
	public Pane moviePane;
	
	public MainController controller;
	public ItemManager itemManager;
	public SceneController sceneController = new SceneController();
	
	public void init(MainController controller) throws IOException
	{
		this.controller = controller;
		controller.getLoaned();
		this.itemManager = controller.getItemManager();
		setupUser();
		displayLoaned();
	}
	
	public void displayLoaned()
	{
		addBooks();
		
		int books = itemManager.getBooks().size();
		int y = 100;
		
		if (books > 0)
		{
			y += books * 130;
		}
		else
		{
			y += 20;
		}
		
		moviePane.setLayoutY(y);
		addMovies();
		
	}
	
	public int addBooks()
	{		
		ArrayList<Items> booksList = itemManager.getBooks();
		int x = 5;
		int y = 23;
		
		for(Items item : booksList)
		{
			Book copy = (Book) item.getItem();
			
			ImageView imageView = loadImage(item.getImage());
			imageView.setFitWidth(80);
			imageView.setFitHeight(120);
			
			
			Label titleHead = new Label("Title");
			Label authorHead = new Label("Author");	
			Label dateHead = new Label("Date");		
			Label returnDateHead = new Label("Return By Date");
			
			Label title = new Label(item.getName());
			Label author = new Label(copy.getAuthor());
			Label date = new Label("" + item.getRelease());
			Label returnDate = new Label(copy.getDate());
			
			imageView.setX(0);
			imageView.setY(0);
			
			titleHead.setId("itemHeader");
			titleHead.setLayoutX(85);
			titleHead.setLayoutY(0);
			
			title.setId("ItemTitle");
			title.setLayoutX(85);
			title.setLayoutY(20);
			
			authorHead.setId("itemHeader");
			authorHead.setLayoutX(85);
			authorHead.setLayoutY(40);
			
			author.setLayoutX(85);
			author.setLayoutY(60);
			
			dateHead.setId("itemHeader");
			dateHead.setLayoutX(85);
			dateHead.setLayoutY(80);
			
			date.setLayoutX(85);
			date.setLayoutY(100);
			
			returnDateHead.setId("itemHeader");
			returnDateHead.setLayoutX(260);
			returnDateHead.setLayoutY(40);
			
			returnDate.setLayoutX(260);
			returnDate.setLayoutY(60);
			
			Pane itemPane = new Pane();
			itemPane.setLayoutX(x);
			itemPane.setLayoutY(y);
			
			itemPane.getChildren().addAll(titleHead, authorHead, dateHead, returnDateHead);
			itemPane.getChildren().addAll(imageView, title, author, date, returnDate);
			bookPane.getChildren().add(itemPane);
			
			y += 130;
		}
		return booksList.size();
	}
	
	public void addMovies()
	{
		ArrayList<Items> moviesList = itemManager.getMovies();
		int x = 5;
		int y = 23;
		
		
		for(Items item : moviesList)
		{
			Movie copy = (Movie) item.getItem();
			
			ImageView imageView = loadImage(item.getImage());
			imageView.setFitWidth(80);
			imageView.setFitHeight(120);
			
			
			Label titleHead = new Label("Title");
			Label directorHead = new Label("Author");	
			Label dateHead = new Label("Date");	
			Label returnDateHead = new Label("Return By Date");
	
			Label title = new Label(item.getName());
			Label director = new Label(copy.getDirector());
			Label date = new Label("" + item.getRelease());
			Label returnDate = new Label(copy.getDate());
			
			imageView.setX(0);
			imageView.setY(0);
			
			titleHead.setId("itemHeader");
			titleHead.setLayoutX(85);
			titleHead.setLayoutY(0);
			
			title.setId("ItemTitle");
			title.setLayoutX(85);
			title.setLayoutY(20);
			
			directorHead.setId("itemHeader");
			directorHead.setLayoutX(85);
			directorHead.setLayoutY(40);
			
			director.setLayoutX(85);
			director.setLayoutY(60);
			
			dateHead.setId("itemHeader");
			dateHead.setLayoutX(85);
			dateHead.setLayoutY(80);
			
			date.setLayoutX(85);
			date.setLayoutY(100);
			
			returnDateHead.setId("itemHeader");
			returnDateHead.setLayoutX(260);
			returnDateHead.setLayoutY(40);
			
			returnDate.setLayoutX(260);
			returnDate.setLayoutY(60);
			
			Pane itemPane = new Pane();
			itemPane.setLayoutX(x);
			itemPane.setLayoutY(y);
			
			itemPane.getChildren().addAll(titleHead, directorHead, dateHead, returnDateHead);
			itemPane.getChildren().addAll(imageView, title, director, date, returnDate);
			moviePane.getChildren().add(itemPane);
			
			y += 130;
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
	
	
	public void Back(ActionEvent event) throws Exception
	{
		itemManager.clearList();
		controller.setItemManager(itemManager);
		controller.init();
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
