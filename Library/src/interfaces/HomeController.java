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
		
		controller.settingsButtonSetup(settingsButton, true, true);
		if(controller.user != null)
		{
			if(controller.user.getIntAccess() == 2)
			{
				setupAdmin();
			}
		}
		
		addItems();
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
	
	public void addItems()
	{
		Pane pane =  new Pane();
		
		ArrayList<Items> itemList = itemManager.getAll();
		int movieX = -105;
		int bookX = -105;
		int x = 0;
		int y = 23;
		
		
		for(Items item : itemList)
		{
			Movie movieCopy = new Movie();
			Book bookCopy = new Book();
			
			Label title = new Label(item.getName());		
			Label date = new Label("" + item.getRelease());
			Label cost = new Label(item.getCostOutput());
			
			Label creator;
			try
			{
				movieCopy = (Movie) item.getItem();
				creator = new Label(movieCopy.getDirector());
				pane = (Pane) moviesLabel.getParent();
				movieX += 110;
				x = movieX;
			} catch (Exception e) {
				bookCopy = (Book) item.getItem();
				creator = new Label(bookCopy.getAuthor());
				pane = (Pane) booksLabel.getParent();
				bookX += 110;
				x = bookX;
			}
			
			ImageView imageView = loadImage(item.getImage());
			imageView.setFitWidth(80);
			imageView.setFitHeight(110);
			
			imageView.setX(0);
			imageView.setY(0);
			
			title.setId("ItemTitle");
			title.setLayoutX(0);
			title.setLayoutY(110);
			
			creator.setLayoutX(0);
			creator.setLayoutY(125);
			
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
			
			itemPane.getChildren().addAll(imageView, title, creator, date, cost);			
			pane.getChildren().add(itemPane);
			
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
}
