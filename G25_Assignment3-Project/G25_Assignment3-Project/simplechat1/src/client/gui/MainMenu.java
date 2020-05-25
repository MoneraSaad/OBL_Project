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
 *         this is the Main GUI.It is the Users Main Menu.
 */
public class MainMenu extends Application  {


	private static MainMenu INSTANCE = null;
	//Image img = new Image("/images/OrtBraudeLibraryIcon.png");

    /**
	 * Create the application.
	 */
	public MainMenu() {
		
	}

	/** 
	 * This function check if there is an instance for the GUI,
	 * if no , create it
	 * else, return the INSTANCE
	 */
	public static MainMenu getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new MainMenu();
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

			root = FXMLLoader.load(getClass().getResource("/GUI/UserMain.fxml"));
			Scene scene = new Scene(root);
			PrimaryStage.setTitle("UserMainMenu");
			PrimaryStage.setScene(scene);

			PrimaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
