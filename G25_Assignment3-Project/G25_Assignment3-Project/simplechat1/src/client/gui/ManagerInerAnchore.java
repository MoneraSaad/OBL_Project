
package client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * @author Monera
 *         <p>
 *         this is the Librarian GUI.It is the Librarian Main Menu.
 */
public class ManagerInerAnchore extends Application  {

	
	private static ManagerInerAnchore INSTANCE = null;
	//Image img = new Image("/images/OrtBraudeLibraryIcon.png");

    /**
	 * Create the application.
	 */
	public ManagerInerAnchore() {
		
	}

	/** 
	 * This function check if there is an instance for the GUI,
	 * if no , create it
	 * else, return the INSTANCE
	 */
	public static ManagerInerAnchore getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ManagerInerAnchore();
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

			root = FXMLLoader.load(getClass().getResource("/GUI/ManagerHome1.fxml"));
			Scene scene = new Scene(root);
			//PrimaryStage.setTitle("LibrarianMainMenu1");
			PrimaryStage.setScene(scene);

			PrimaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}


