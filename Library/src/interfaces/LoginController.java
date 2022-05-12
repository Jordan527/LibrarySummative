package interfaces;

import users.*;

import interfaces.controller.MainController;
import interfaces.controller.SceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController extends MainApp
{
	@FXML
	public Label titleLabel;
	@FXML
	public Label usernameLabel;
	@FXML
	public Label passwordLabel;
	@FXML
	public Label invalidMessage;
	@FXML
	public TextField usernameField;
	@FXML
	public PasswordField passwordField;
	@FXML 
	public Hyperlink registerLink;

	
	public MainController controller;
	public SceneController sceneController = new SceneController();
	public UserManager userManager;
	
	public void init(MainController controller)
	{
		this.controller = controller;
		this.userManager = controller.getUserManager();
	}
	
	public void Login(ActionEvent event) throws Exception
	{
		String name = usernameField.getText();
		String pass = passwordField.getText();
		
	
		controller.login(name, pass);
		Users user = controller.getUser();
		
		if(user == null)
		{
			invalidMessage.setText("Invalid username or password");
		} else 
		{
			sceneController.loadController(controller);
			sceneController.switchToHome(event);
		}

	}

	public void CancelLogin(ActionEvent event) throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToHome(event);
	}
	
	public void RegisterDirect(ActionEvent event) throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToRegister(event);
	}
}
