package client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddBookGUI extends Application {

    private static AddBookGUI INSTANCE = null;


    /**
	 * Create the application.
	
	/** 
	 * This function check if there is an instance for the form,
	 * if no , create it
	 * else, return the INSTANCE
	 */
	public static AddBookGUI getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AddBookGUI();
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

			root = FXMLLoader.load(getClass().getResource("/GUI/AddBookForm.fxml"));
			Scene scene = new Scene(root);
			PrimaryStage.setTitle("Add Book Form");
			PrimaryStage.setScene(scene);

			PrimaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//libraryIcon.setImage(img);

	}*/

}