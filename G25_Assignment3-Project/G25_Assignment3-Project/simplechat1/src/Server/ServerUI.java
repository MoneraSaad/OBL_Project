package Server;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ServerUI extends Application implements Initializable {
	/***
	 * 
	 */

	@FXML
	private TextField portNumber;

	@FXML
	private TextField dbUsername;

	@FXML
	private TextField schemaName;

	@FXML
	private PasswordField dbPassword;

	@FXML
	Server server;
	ServerUI serverui;
	public static String serverMsg;
	Alert alert;
	public static String msg = "";
	private String DBUsn;
	private String DBPass;
	private String DatabaseName;
	private String Port;
	private static ServerUI INSTANCE;

	/***
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		launch(args);
	}
	
	public ServerUI() {

	}

	public void shutServerDown() {
		System.exit(1);
	}
	
	/***
	 * 
	 * @return
	 */
	public static ServerUI getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ServerUI();
		}
		return INSTANCE;
	}

	/***
	 * 
	 */
	private void show() {
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					showStage();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	/***
	 * 
	 * @throws Exception
	 */
	public void showStage() throws Exception {

		Stage PrimaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/ServerGUI.fxml"));

		Scene scene = new Scene(root);
		PrimaryStage.setTitle("Server Connection");
		PrimaryStage.setScene(scene);

		PrimaryStage.show();

	}

	/***
	 * 
	 * @param s
	 */
	public void display(String s) {
	    System.out.println(s);
	}

	/***
	 * 
	 * @param server
	 */
	public void setServer(Server server) {
		this.server = server;
	}

	/***
	 * 
	 * @throws IOException
	 */
	public void exit() throws IOException {

		System.exit(0);

	}

	/***
	 * 
	 * @throws IOException
	 */
	public void connectServer() throws IOException {
		serverui = new ServerUI();
		Server server = new Server(serverui);
		serverui.setServer(server);
		String portString = portNumber.getText().toString();
		int portNum = Integer.parseInt(portString);
		String dbUser = dbUsername.getText().toString();
		String dbSchema = schemaName.getText().toString();
		String dbPass = dbPassword.getText().toString();

		try {

			server.setPort(portNum);
			boolean c = server.connectToDB(dbSchema, dbUser, dbPass);
			server.listen(); // Start listening for connections
			if (c) {
				String msg = "Server listening for connections on port " + portNum;
				display(msg);
				alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
				alert.setHeaderText("Running the server");
				alert.setTitle("Information");
				alert.show();
			}

		} catch (Exception e1) {
			display("ERROR occured while running the server");
			alert = new Alert(AlertType.ERROR, "ERROR occured while running the server", ButtonType.OK);
			alert.setTitle("Warning");
			alert.show();
		}

	}

	/***
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		show();

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		portNumber.setText("5555");
		dbUsername.setText("root");
		schemaName.setText("library_students");
		dbPassword.setText("Aa123456");
//		String portString = portNumber.getText().toString();
//		int portNum = Integer.parseInt(portString);
//		String dbUser = dbUsername.getText().toString();
//		String dbSchema = schemaName.getText().toString();
//		String dbPass = dbPassword.getText().toString();
	}

}
