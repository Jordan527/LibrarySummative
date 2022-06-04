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

public class RegisterController 
{
	@FXML 
	public Label titleLabel;
	@FXML
	public Label forenameLabel;
	@FXML
	public Label surnameLabel;
	@FXML
	public Label usernameLabel;
	@FXML
	public Label passwordLabel;
	@FXML
	public Label confPasswordLabel;
	@FXML
	public Label invalidMessage;
	@FXML 
	public TextField forenameField;
	@FXML 
	public TextField surnameField;
	@FXML 
	public TextField usernameField;
	@FXML 
	public TextField passwordField;
	@FXML 
	public TextField confPasswordField;
	@FXML
	public Hyperlink loginlink;
	
	public SceneController sceneController = new SceneController();
	public MainController controller;
	
	
	public void init(MainController controller)
	{
		this.controller = controller;
	}
	public void Register(ActionEvent event) throws Exception
	{
		int exists;
		Boolean empty = emptyField();
		String outputMessage = "";
		
		if(!empty)
		{
			//trim() removes leading and trailing spaces
			String forename = forenameField.getText().trim();
			String surname = surnameField.getText().trim();
			String username = usernameField.getText().trim();
			String password = passwordField.getText().trim();
			String confPassword = confPasswordField.getText().trim();
			
			
			
			if(password.equals(confPassword))
			{
				exists = controller.existingUser(forename, surname, username, password);
				
//				outputMessage = outputContent(exists);
				
				if (exists == 0)	// this section needs finalising
				{			
					controller.addUser(forename, surname, username, password);
					
					sceneController.loadController(controller);
					sceneController.switchToHome((Stage) titleLabel.getScene().getWindow());
				}
			} else
			{
				outputMessage = "\nPasswords do not match";
			}
		} else
		{
			outputMessage = "\nAll fields must be filled";
		}
		
		invalidMessage.setText(outputMessage);
	}
	
	public Boolean emptyField()
	{
		Boolean empty = false;
		
		Boolean forename = forenameField.getText().isEmpty();
		Boolean surname = surnameField.getText().isEmpty();
		Boolean username = usernameField.getText().isEmpty();
		Boolean password = passwordField.getText().isEmpty();
		Boolean confPassword = confPasswordField.getText().isEmpty();
		
		if(forename || surname || username || password || confPassword)
		{
			empty = true;
		}
		
		return empty;
	}
	public String outputContent(String content)
	{
		String outputMessage = "";
		
		Boolean one = content.contains("1");
		Boolean two = content.contains("2");
		Boolean three = content.contains("3");
		
		outputMessage = "The following already exists in other accounts: \n";
		
		if (one && two && three)
		{
			outputMessage += "Name, Username and Password";
		}
		else if (one && two)
		{
			outputMessage += "Name and Username";
		}
		else if (one && three)
		{
			outputMessage += "Name and Password";
		}
		else if (two && three)
		{
			outputMessage += "Username and Password";
		}
		else if (one)
		{
			outputMessage += "Name";
		}
		else if (two)
		{
			outputMessage += "Username";
		}
		else if (three)
		{
			outputMessage += "Password";
		}

		return outputMessage;
	}
	
	public void CancelRegister(ActionEvent event) throws Exception
	{
		sceneController.loadController(controller);
		sceneController.switchToHome((Stage) titleLabel.getScene().getWindow());
	}
	
	public void LoginRedirect(ActionEvent event) throws Exception 
	{
		sceneController.loadController(controller);
		sceneController.switchToLogin((Stage) titleLabel.getScene().getWindow());
	}
	
}
