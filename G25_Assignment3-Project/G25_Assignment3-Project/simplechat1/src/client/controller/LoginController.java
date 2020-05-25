package client.controller;

import java.io.IOException;

import client.ChatClient;
import client.ClientConnectToServer;
import client.entities.User;
import client.gui.LibrarianMenu;
import client.gui.MainMenu;
import client.gui.ManagerMenu;
import client.gui.ReaderMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import protocol.request.LoginRequest;
import protocol.response.LoginResponse;


/**
 * 
 * @author Monera LoginController This is an LoginController class that control
 *         all the request related laboratory
 */

public class LoginController extends AbstractController {

	Alert alert;

	@FXML
	private Label UserNameU;

	@FXML
	private TextField userName;

	@FXML
	private Label Password;

	@FXML
	private TextField PasswordU;

	@FXML
	private Label welcome;

	@FXML
	private ImageView libraryIcon;

	/**
	 * constructor for this class
	 */

	public LoginController() {
		super();
	}

	/**
	 * constructor for this class
	 * @param client
	 */
	public LoginController(ChatClient client) {
		super(client);
	}

	/**
	 * This method send request message by the client to the server to Check Login
	 * Details in the database and return an appropriate response message
	 *
	 * @param user
	 * @return appropriate LoginResponse
	 */
	public LoginResponse checkLoginDetailsFromGUI(User user) {/////////////
		LoginRequest message = new LoginRequest(user);
		// System.out.println((LoginResponse) client.sendMessage(message));//////
		return (LoginResponse) client.sendMessage(message);
	}// END

	@FXML
	void loginHandler(ActionEvent event) throws IOException {
		String userN = userName.getText().toString();
		String pass = PasswordU.getText().toString();

		User user = new User(pass, userN);

		LoginController login = (LoginController) Controllers.getInstance()
				.getController(ControllerType.LOGIN_CONTROLLER);
     
		LoginResponse resp = login.checkLoginDetailsFromGUI(user);
		//System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1"+resp.getUser().getUserName());
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+resp.getUser().getPassword());
		System.out.println("AHAAAA");///////
		System.out.println(resp.getUser());
		// System.out.println(resp.getText());////
		if (resp.getUser()!=null) {
			//System.out.println("YESSSSSSSSSSSS");//////////
			((Node) event.getSource()).getScene().getWindow().hide();
			ClientConnectToServer.currUser = resp.getUser();
			System.out.println(ClientConnectToServer.currUser.getName());
			System.out.println(ClientConnectToServer.currUser.getUserName());
			System.out.println(ClientConnectToServer.currUser.getPassword());
			System.out.println(ClientConnectToServer.currUser.getType().toString());
			switch (ClientConnectToServer.currUser.getType()) {
			case User:
				MainMenu.getInstance();
				break;

			case Reader:
				ReaderMenu.getInstance();
				break;

			case Librarian:
				
				LibrarianMenu.getInstance();

				break;
			case Manager:
				
				ManagerMenu.getInstance();

				break;

			default:
				break;
			}

		} else {
			ShowMessageLogin(resp.getText());
		}
		try {
			Thread.sleep(1500);
		} catch (Exception exp) {

		}

	}

	@FXML
	void backHandler(ActionEvent event) {

	}

	@FXML
	void registerHandler(ActionEvent event) {

	}

	public void exit() {
		System.exit(0);
	}

	public void ShowMessageLogin(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Login failure");
		alert.setHeaderText("Re-try again");
		alert.show();
		MainMenu.getInstance();
	}

}
