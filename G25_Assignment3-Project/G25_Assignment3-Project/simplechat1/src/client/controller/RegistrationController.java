package client.controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.ChatClient;
import client.ClientConnectToServer;
import client.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import protocol.request.LoginRequest;
import protocol.request.RegistrationRequest;
import protocol.response.LoginResponse;
import protocol.response.RegistrationResponse;
import client.controller.UserPermission;

public class RegistrationController extends AbstractController {
	
	@FXML
	TextField NameTextField;
	
	@FXML 
	TextField UserNameTextField;
	
	@FXML 
	TextField PasswordTextField;
	
	@FXML 
	TextField PhoneNumberTextField;
	
	@FXML 
	TextField EmailTextField;
	
	Alert alert;
	Pattern pattern;
    Matcher matcher;
	
	public RegistrationController() {
		super();
	}

	public RegistrationController(ChatClient client) {
		super(client);
	}
	
	public RegistrationResponse registerNewUser(User user) {/////////////
		RegistrationRequest message = new RegistrationRequest(user);
		//System.out.println((LoginResponse) client.sendMessage(message));//////
		return (RegistrationResponse) client.sendMessage(message);
	}// END

	void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/Registration.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	void registerHandler(ActionEvent event) {
		String name = NameTextField.getText().toString();
		String username = UserNameTextField.getText().toString();
		String password = PasswordTextField.getText().toString();
		String phoneNumber = PhoneNumberTextField.getText().toString();
		String email = EmailTextField.getText().toString();
		

		
		if(nameFormatValidator(name) == false)
			ShowMessageInvalidName("The name must contain only letters");
		else if(userNameFormatValidator(username) == false)
			ShowMessageInvalidUserName("The user name you entered is invalid");
		else if(passwordFormatValidator(password) == false)
			ShowMessageInvalidPassword("We want our passwords to :\r\n" + "\r\n" + "- Be between 6 and 18 characters long\r\n" + "- Contain at least one digit.\r\n" + "- Contain at least one lower case character.");
		else if(phoneFormatValidator(phoneNumber) == false)
			ShowMessageInvalidPhone("The phone you entered is Invalid");
		else if(emailFormatValidator(email) == false)
			ShowMessageInvalidEmail("The email you entered is Invalid");
	    else {
		User user = new User(password,username,name,phoneNumber,email);
		
		RegistrationController Registration = (RegistrationController) Controllers.getInstance()
				.getController(ControllerType.REGISTRATION_CONTROLER);

		RegistrationResponse resp = Registration.registerNewUser(user);
		if (resp.getText().equals("Successful registration")) {
			ShowMessageSuccessfulRegistration(resp.getText());
			
			ClientConnectToServer.currUser = resp.getUser();
			//MainMenu.getInstance();
		} else {
			ShowMessageFailRegistration(resp.getText());
		}
		try {
			Thread.sleep(1500);
		}
		catch(Exception exp)
		{
			
		}
		}
		
	}
	
	public void ShowMessageFailRegistration(String msg) {
	alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
	alert.setTitle("Registration failure");
	alert.setHeaderText("Re-try again");
	alert.show();
}
	
	public void ShowMessageSuccessfulRegistration(String msg) {
		alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
		alert.setTitle("Registration failure");
		alert.setHeaderText("");
		alert.show();
	}

	public boolean emailFormatValidator(String email) {

	    final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	    
	    pattern = Pattern.compile(EMAIL_PATTERN);
	    matcher = pattern.matcher(email);

	    return matcher.matches();

	}

	public void ShowMessageInvalidEmail(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Invalid Email");
		alert.setHeaderText("Re-try again");
		alert.show();
	}

	public boolean phoneFormatValidator(String phone) {

	    final String PHONE_PATTERN = "[0]{1}\\d{9}";
	    
	    pattern = Pattern.compile(PHONE_PATTERN);
	    matcher = pattern.matcher(phone);

	    return matcher.matches();

	}

	public void ShowMessageInvalidPhone(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Invalid phone number");
		alert.setHeaderText("Re-try again");
		alert.show();
	}

	public boolean passwordFormatValidator(String password) {

	    final String PASSWORD_PATTERN = "(?=.*[a-z])(?=.*\\d).{6,18}";
	    
	    pattern = Pattern.compile(PASSWORD_PATTERN);
	    matcher = pattern.matcher(password);

	    return matcher.matches();

	}

	public void ShowMessageInvalidPassword(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Invalid password");
		alert.setHeaderText("Re-try again");
		alert.show();
	}

	public boolean userNameFormatValidator(String userName) {

	    final String USERNAME_PATTERN = "^[0-9]{9}$";
	    
	    pattern = Pattern.compile(USERNAME_PATTERN);
	    matcher = pattern.matcher(userName);

	    return matcher.matches();

	}

	public void ShowMessageInvalidUserName(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Invalid User Name");
		alert.setHeaderText("Re-try again");
		alert.show();
	}

	public boolean nameFormatValidator(String name) {

	    final String NAME_PATTERN = "^[\\p{L} .'-]+$";
	    
	    pattern = Pattern.compile(NAME_PATTERN);
	    matcher = pattern.matcher(name);

	    return matcher.matches();

	}

	public void ShowMessageInvalidName(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Invalid Name");
		alert.setHeaderText("Re-try again");
		alert.show();
	}



}

