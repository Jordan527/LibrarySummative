package interfaces;

import interfaces.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application{	
	public static void main(String[] args)
	{
		launch();
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		MainController mainController = new MainController();
		mainController.init();
		
		if(mainController.initialised)
		{	
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/interfaces/fxml/Home.fxml"));
			Parent root = loader.load();
			
			HomeController homecontroller = loader.getController();
			homecontroller.init(mainController);
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/interfaces/css/Home.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		
	}
}
