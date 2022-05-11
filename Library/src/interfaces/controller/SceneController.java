package interfaces.controller;

import interfaces.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {
	public MainController controller;
	
	public void loadController(MainController controller)
	{
		this.controller = controller;
	}
	
	public void switchToHome(ActionEvent event) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Home.fxml"));
		Parent root = loader.load();
		
		HomeController homeController = loader.getController();
		homeController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Home.css").toExternalForm());
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}
	public void switchToHome(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Home.fxml"));
		Parent root = loader.load();
		
		HomeController homeController = loader.getController();
		homeController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Home.css").toExternalForm());
		stage.setScene(scene);
	}
	
	public void switchToLogin(Stage stage) throws Exception
	{		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Login.fxml"));
		Parent root = loader.load();
		
		LoginController loginController = loader.getController();
		loginController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Login.css").toExternalForm());
		stage.setScene(scene);
	}
	public void switchToLogin(ActionEvent event) throws Exception
	{		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Login.fxml"));
		Parent root = loader.load();
		
		LoginController loginController = loader.getController();
		loginController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Login.css").toExternalForm());
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}
	
	public void switchToRegister(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Register.fxml"));
		Parent root = loader.load();
		
		RegisterController registerController = loader.getController();
		registerController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Register.css").toExternalForm());
		stage.setScene(scene);
	}
	public void switchToRegister(ActionEvent event) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Register.fxml"));
		Parent root = loader.load();
		
		RegisterController registerController = loader.getController();
		registerController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Register.css").toExternalForm());
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		stage.setScene(scene);
	}

	public void switchToItem(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Item.fxml"));
		Parent root = loader.load();
		
		ItemController itemController = loader.getController();
		itemController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Item.css").toExternalForm());
		stage.setScene(scene);
	}
	public void switchToItem(ActionEvent event) throws Exception
	{
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/interfaces/fxml/Item.fxml"));
			Parent root = loader.load();
			
			ItemController itemController = loader.getController();
			itemController.init(controller);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/interfaces/css/Item.css").toExternalForm());
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			stage.setScene(scene);
		}
	}
	
	public void switchToBasket(Stage stage) throws Exception
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/interfaces/fxml/Basket.fxml"));
		Parent root = loader.load();
		
		BasketController basketController = loader.getController();
		basketController.init(controller);
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/interfaces/css/Basket.css").toExternalForm());
		stage.setScene(scene);
	}
}
