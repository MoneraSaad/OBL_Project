package client.gui;

import client.ClientConnectToServer;
import client.controller.ControllerType;
import client.controller.Controllers;
import client.controller.ReaderHomeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import protocol.response.LogoutResponse;

/**
 * @author Monera
 *         <p>
 *         this is the Librarian GUI.It is the Librarian Main Menu.
 */
public class LibrarianMenu extends Application {

	private static LibrarianMenu INSTANCE = null;
	// Image img = new Image("/images/OrtBraudeLibraryIcon.png");

	/**
	 * Create the application.
	 */
	public LibrarianMenu() {

	}

	/**
	 * This function check if there is an instance for the GUI, if no , create it
	 * else, return the INSTANCE
	 */
	public static LibrarianMenu getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new LibrarianMenu();
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
		// show();

	}

	public void show() {

		Stage PrimaryStage = new Stage();
		Pane root;
		try {

			root = FXMLLoader.load(getClass().getResource("/GUI/LibrarianHome.fxml"));
			Scene scene = new Scene(root);
			PrimaryStage.setTitle("LibrarianMainMenu");
			PrimaryStage.setScene(scene);

			PrimaryStage.show();
			PrimaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent w) {
					ReaderHomeController rhc = (ReaderHomeController) Controllers.getInstance()
							.getController(ControllerType.READER_HOME_CONTROLLER);
					LogoutResponse resp = rhc.logoutUser(ClientConnectToServer.currUser);

					ClientConnectToServer.currUser = null;
					System.exit(0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

