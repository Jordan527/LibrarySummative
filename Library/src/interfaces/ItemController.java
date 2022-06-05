package interfaces;

import items.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import interfaces.controller.MainController;
import interfaces.controller.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ItemController {
	@FXML
	public Label titleLabel;
	@FXML
	public Label creatorLabel;
	@FXML
	public Label dateLabel;
	@FXML
	public Label sizeLabel;
	@FXML
	public Label costLabel;
	@FXML
	public Label availableLabel;
	@FXML
	public Label itemCreator;
	@FXML
	public Label itemDate;
	@FXML
	public Label itemSize;
	@FXML
	public Label itemCost;
	@FXML
	public Label itemAvailable;
	@FXML
	public MenuButton settingsButton;
	
	public MainController controller;
	public SceneController sceneController = new SceneController();
	public Items item;
	
	
	public void init(MainController controller)
	{
		this.controller = controller;
		this.item = controller.getItem();
		if(controller.user != null)
		{
			controller.settingsButtonSetup(settingsButton, true, true);
		}
		displayItem();
		
	}
	
	public void displayItem()
	{
		Pane pane =  (Pane) titleLabel.getParent();

		ImageView imageView = loadImage(item.getImage());
		imageView.setFitWidth(160);
		imageView.setFitHeight(215);
		
		imageView.setX(0);
		imageView.setY(60);
		pane.getChildren().add(imageView);
		
		titleLabel.setText(item.getName());
		itemDate.setText("" + item.getRelease());
		itemCost.setText(item.getCostOutput());
		itemAvailable.setText("" + item.getTotalAvailable());
		
		Item copy = item.getItem();
		if(copy.getClass().equals(Book.class))
		{
			displayBook();
		} else
		{
			displaymMovie();
		}
	}
	
	public void displayBook()
	{
		Book copy = (Book) item.getItem();
		
		itemSize.setText("" + copy.getPages());
		itemCreator.setText(copy.getAuthor());
	}
	
	public void displaymMovie()
	{
		creatorLabel.setText("Director:");
		sizeLabel.setText("Duration:");

		Movie copy = (Movie) item.getItem();
		
		String duration = copy.getDurationOutput();
		
		itemSize.setText("" + duration);
		itemCreator.setText(copy.getDirector());
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
	
	public void printInfo()
	{
	    try {
	    	String fileName = "files/" + item.getName() + ".txt";
	        File myFile = new File(fileName);
	        myFile.createNewFile();
	        
	        FileWriter myWriter = new FileWriter(fileName);
	        myWriter.write(item.displayInfo());
	        myWriter.close();
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	}
	
	public void addToBasket(ActionEvent event) throws Exception
	{
		if(controller.user == null)
		{
			sceneController.loadController(controller);
			sceneController.switchToLogin((Stage) titleLabel.getScene().getWindow());
		} else
		{
			controller.addToBasket(item);
		}
	}
	
	
	public void Back(ActionEvent event) throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToHome((Stage) titleLabel.getScene().getWindow());
	}

}
