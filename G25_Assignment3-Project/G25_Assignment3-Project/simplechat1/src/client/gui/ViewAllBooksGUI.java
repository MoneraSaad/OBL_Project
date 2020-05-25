
package client.gui;

import java.net.URL;
import java.util.ResourceBundle;

import client.ClientConnectToServer;
import client.controller.BooksController;
import client.controller.ControllerType;
import client.controller.Controllers;
import client.controller.MainMenuController;
import client.controller.ReaderHomeController;
import client.entities.Book;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import protocol.request.BooksRequest;
import protocol.response.BooksResponse;
import protocol.response.OrderBookResponse;

/**
 * @author Monera
 *         <p>
 *         this is the Main GUI.It is the Users Main Menu.
 */
public class ViewAllBooksGUI extends Application {

	Alert alert;
	public static Book book;
	private static ViewAllBooksGUI INSTANCE = null;
	//Image img = new Image("/images/OrtBraudeLibraryIcon.png");

    /**
	 * Create the application.
	 */
	public ViewAllBooksGUI() {
		
	}

	/** 
	 * This function check if there is an instance for the GUI,
	 * if no , create it
	 * else, return the INSTANCE
	 */
	public static ViewAllBooksGUI getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ViewAllBooksGUI();
		}
		INSTANCE.display();
		return INSTANCE;
	}
	
	
	private void display() {
		Platform.runLater(new Runnable() {
			public void run() {
				try {
					show();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});		
	}
	


	
	@Override
	public void start(Stage arg0) throws Exception {
		//show();

	}

	public void show() {

		Stage PrimaryStage = new Stage();
		Pane root;
		try {

			root = FXMLLoader.load(getClass().getResource("/GUI/ViewAllBooks.fxml"));
			Scene scene = new Scene(root);
			//PrimaryStage.setTitle("Books");
			PrimaryStage.setScene(scene);

			PrimaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	

	

	
}