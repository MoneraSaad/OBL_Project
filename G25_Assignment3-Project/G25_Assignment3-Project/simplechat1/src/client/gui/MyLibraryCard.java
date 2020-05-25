package client.gui;

import client.ClientConnectToServer;
import client.controller.ControllerType;
import client.controller.Controllers;
import client.controller.ReaderHomeController;
import client.controller.ReaderInfoController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import protocol.response.LogoutResponse;

public class MyLibraryCard extends Application  {

	

    private static MyLibraryCard INSTANCE = null;
	//Image img = new Image("/images/OrtBraudeLibraryIcon.png");

    /**
	 * Create the application.
	 */
	public MyLibraryCard() {
		
	}

	/** 
	 * This function check if there is an instance for the form,
	 * if no , create it
	 * else, return the INSTANCE
	 */
	public static MyLibraryCard getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MyLibraryCard();
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

			root = FXMLLoader.load(getClass().getResource("/GUI/MyLibraryCard.fxml"));
			Scene scene = new Scene(root);
			PrimaryStage.setTitle("My Library Card");
			PrimaryStage.setScene(scene);

			PrimaryStage.show();
			PrimaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent w) {
					ReaderInfoController.userCard=0;
				}
			});
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//libraryIcon.setImage(img);

	}*/

}

