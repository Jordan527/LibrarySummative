package interfaces;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import interfaces.controller.*;
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

public class BasketController {
	@FXML
	public Label titleLabel;
	@FXML
	public Label booksLabel;
	@FXML
	public Label moviesLabel;
	@FXML
	public MenuButton settingsButton;
	@FXML
	public Pane bookPane;
	@FXML
	public Pane moviePane;
	@FXML
	public Button orderButton;
	
	public MainController controller;
	public ItemManager itemManager;
	public SceneController sceneController = new SceneController();
	
	public void init(MainController controller) throws IOException
	{
		this.controller = controller;
		controller.getBasket();
		this.itemManager = controller.getItemManager();
		controller.settingsButtonSetup(settingsButton, false, true);
		displayBasket();
	}
	
	public void displayBasket()
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
		
		int movies = itemManager.getMovies().size();
		y += 23;
		
		if (movies > 0)
		{
			y += movies * 130;
		}
		else
		{
			y += 20;
		}
		
		addTotal(y);
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
			Label quantityHead = new Label("Quantity");		
			Label costHead = new Label("Cost");
			Label totalCostHead = new Label("Total Cost");
			
			
			Label title = new Label(item.getName());
			Label author = new Label(copy.getAuthor());
			Label date = new Label("" + item.getRelease());
			Label quantity = new Label("" + item.getQuantity());
			Label cost = new Label(item.getCostOutput());
			Label totalCost = new Label(item.getTotalCostOutput());
			
			Button deleteButton = new Button("Delete");
			Button removeButton = new Button("-");
			Button addButton = new Button("+");
			
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
			
			quantityHead.setId("itemHeader");
			quantityHead.setLayoutX(265);
			quantityHead.setLayoutY(0);
			
			quantity.setLayoutX(265);
			quantity.setLayoutY(20);
			
			costHead.setId("itemHeader");
			costHead.setLayoutX(265);
			costHead.setLayoutY(40);
			
			cost.setLayoutX(265);
			cost.setLayoutY(60);
			
			totalCostHead.setId("itemHeader");
			totalCostHead.setLayoutX(265);
			totalCostHead.setLayoutY(80);
			
			totalCost.setLayoutX(265);
			totalCost.setLayoutY(100);
			
			removeButton.setLayoutX(310);
			removeButton.setLayoutY(40);
			removeButton.setOnAction(event -> {
				try {
					RemoveItem(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			});
			
			addButton.setLayoutX(380);
			addButton.setLayoutY(40);
			addButton.setOnAction(event -> {
				try {
					AddItem(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			});
			
			deleteButton.setLayoutX(330);
			deleteButton.setLayoutY(40);
			deleteButton.setOnAction(event -> {
				try {
					DeleteItem(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			});
			
			
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
			
			itemPane.getChildren().addAll(titleHead, authorHead, dateHead, quantityHead, costHead, totalCostHead);
			itemPane.getChildren().addAll(imageView, title, author, date, quantity, cost, totalCost);
			itemPane.getChildren().addAll(removeButton, addButton, deleteButton);
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
			Label quantityHead = new Label("Quantity");		
			Label costHead = new Label("Cost");
			Label totalCostHead = new Label("Total Cost");
			
			
			Label title = new Label(item.getName());
			Label director = new Label(copy.getDirector());
			Label date = new Label("" + item.getRelease());
			Label quantity = new Label("" + item.getQuantity());
			Label cost = new Label(item.getCostOutput());
			Label totalCost = new Label(item.getTotalCostOutput());
			
			Button deleteButton = new Button("Delete");
			Button removeButton = new Button("-");
			Button addButton = new Button("+");
			
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
			
			quantityHead.setId("itemHeader");
			quantityHead.setLayoutX(265);
			quantityHead.setLayoutY(0);
			
			quantity.setLayoutX(265);
			quantity.setLayoutY(20);
			
			costHead.setId("itemHeader");
			costHead.setLayoutX(265);
			costHead.setLayoutY(40);
			
			cost.setLayoutX(265);
			cost.setLayoutY(60);
			
			totalCostHead.setId("itemHeader");
			totalCostHead.setLayoutX(265);
			totalCostHead.setLayoutY(80);
			
			totalCost.setLayoutX(265);
			totalCost.setLayoutY(100);
			
			removeButton.setLayoutX(310);
			removeButton.setLayoutY(40);
			removeButton.setOnAction(event -> {
				try {
					RemoveItem(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			});
			
			addButton.setLayoutX(380);
			addButton.setLayoutY(40);
			addButton.setOnAction(event -> {
				try {
					AddItem(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			});
			
			deleteButton.setLayoutX(330);
			deleteButton.setLayoutY(40);
			deleteButton.setOnAction(event -> {
				try {
					DeleteItem(item);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			});
			
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
			
			itemPane.getChildren().addAll(titleHead, directorHead, dateHead, quantityHead, costHead, totalCostHead);
			itemPane.getChildren().addAll(imageView, title, director, date, quantity, cost, totalCost);
			itemPane.getChildren().addAll(removeButton, addButton, deleteButton);
			moviePane.getChildren().add(itemPane);
			
			y += 130;
		}
	}
	
	public void addTotal(int y)
	{
		Pane pane = (Pane) titleLabel.getParent();
		
		Label itemsHead = new Label("Items: ");
		Label totalHead = new Label("Total: ");
		
		Label items = new Label("" + itemManager.getTotalQuantity());
		Label total = new Label(itemManager.getTotalCostOutput());
		
		itemsHead.setId("itemHeader");
		itemsHead.setLayoutX(110);
		itemsHead.setLayoutY(y);
		
		items.setLayoutX(150);
		items.setLayoutY(y);
		
		totalHead.setId("itemHeader");
		totalHead.setLayoutX(175);
		totalHead.setLayoutY(y);
		
		total.setLayoutX(215);
		total.setLayoutY(y);
		
		pane.getChildren().addAll(itemsHead, items, totalHead, total);
		orderButton.setLayoutY(y-5);
	}
	
	public void itemSelected(Items item) throws Exception
	{
		controller.setItem(item);
		sceneController.loadController(controller);
		sceneController.switchToItem((Stage) titleLabel.getScene().getWindow());
	}
	
	public void itemAdded(Items item) throws Exception
	{
		controller.addToBasket(item);
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
	
	public void AddItem(Items item) throws Exception
	{
		controller.addToBasket(item);
		sceneController.loadController(controller);
		sceneController.switchToBasket((Stage) titleLabel.getScene().getWindow());
	}
	public void RemoveItem(Items item) throws Exception
	{
		controller.removeFromBasket(item);
		sceneController.loadController(controller);
		sceneController.switchToBasket((Stage) titleLabel.getScene().getWindow());
	}
	public void DeleteItem(Items item) throws Exception
	{
		controller.deleteFromBasket(item);
		sceneController.loadController(controller);
		sceneController.switchToBasket((Stage) titleLabel.getScene().getWindow());
	}
	
	public void Order(ActionEvent event) throws Exception
	{
		if(controller.getUser().getIntAccess() > 0)	
		{
			controller.order();
			sceneController.loadController(controller);
			sceneController.switchToLoaned((Stage) titleLabel.getScene().getWindow());
		} else
		{
			double y = orderButton.getLayoutY();
			Pane pane = (Pane) titleLabel.getParent();
			
			Label errorMessage = new Label("Your account has not been approved by admin yet!");
			errorMessage.setId("errorMessage");
			errorMessage.setLayoutX(80);
			errorMessage.setLayoutY(y+25);
			
			pane.getChildren().addAll(errorMessage);
		}
	}
	
	}
