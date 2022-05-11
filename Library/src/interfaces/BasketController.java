package interfaces;

import java.util.ArrayList;

import interfaces.controller.*;
import items.Book;
import items.Items;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class BasketController {
	@FXML
	public Label titleLabel;
	
	
	public MainController controller;
	public SceneController sceneController = new SceneController();
	
	public void init(MainController controller)
	{
		this.controller = controller;
		displayBasket();
	}
	
	public void displayBasket()
	{
		controller.getBasket();
		Pane pane =  (Pane) titleLabel.getParent();
		
	}
}
