package client;

import java.io.IOException;
//import com.jfoenix.controls.JFXTextField;

import client.controller.Controllers;
import client.entities.User;
import client.gui.LoginGUI;
import client.gui.MainMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;


import javafx.event.EventHandler;

import javafx.fxml.Initializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.stage.WindowEvent;
/**
 * 
 * @author Monera
 *
 */
public class ClientConnectToServer extends Application implements Initializable {

	/**
	 * variables
	 */
	
	@FXML
	private TextField serverPort;

	@FXML
	private TextField serverIP;

	@FXML

	private int port;
	private String sIP;
	private LoginGUI login;
	
	 /**
	   * The instance of the client that created this ConsoleChat.
	   */
	public ChatClient client;
	
	public static User currUser;
	
	
	/**
	 * Create the frame.
	 */
	public ClientConnectToServer() {

	}

	/***
	 * set the client
	 * 
	 * @param client
	 */
	public void setClient(ChatClient client) {
		this.client = client;
	}


	/**
	 * *connect to the server
	 * 
	 * @param event
	 * @throws IOException
	 */
	
	@FXML
	void connectToServer(ActionEvent event) throws IOException{
		Alert alert;
			port = Integer.parseInt(serverPort.getText().toString());
			sIP = serverIP.getText().toString();
			
			try {
				client = new ChatClient(sIP, port);
				alert = new Alert(AlertType.INFORMATION, "Connected successfully!", ButtonType.OK);
				alert.setTitle("Connection to server");
				alert.setHeaderText("Information");
				alert.show();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			Controllers.newInstance(client);
			boolean rc = client.connect(sIP, port);
			String msg;
			if (rc) {
				((Node) event.getSource()).getScene().getWindow().hide();
				//LoginGUI.getInstance();
				MainMenu.getInstance();

			} else {
				msg = "Connection to server failed";
				alert = new Alert(AlertType.ERROR, msg, ButtonType.CLOSE);
				alert.setTitle("Server Conncetion Error");
				alert.setHeaderText("Failed to connect");
				alert.show();
				return;
			}
			

		//}

	}

	@Override
	public void start(Stage arg0) throws Exception {
		show();

	}
	
	// Login to system

	/***
	 * show method for application()
	 * 
	 */

	public void show() {
		Stage PrimaryStage = new Stage();

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/GUI/ClientConnectToServer.fxml"));
			Scene scene = new Scene(root);
			PrimaryStage.setTitle("Connect To Server");
			PrimaryStage.setScene(scene);
			PrimaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/***
	 * client main
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		launch(args);
	}


	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getsIP() {
		return sIP;
	}

	public void setsIP(String sIP) {
		this.sIP = sIP;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		serverPort.setText("5555");
		serverIP.setText("localhost");
		
	}

}
