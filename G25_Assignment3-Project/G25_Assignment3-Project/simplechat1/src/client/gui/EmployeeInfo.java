package client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EmployeeInfo extends Application  {

	

    private static EmployeeInfo INSTANCE = null;
	//Image img = new Image("/images/OrtBraudeLibraryIcon.png");

    /**
	 * Create the application.
	 */
	public EmployeeInfo() {
		
	}

	/** 
	 * This function check if there is an instance for the form,
	 * if no , create it
	 * else, return the INSTANCE
	 */
	public static EmployeeInfo getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new EmployeeInfo();
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

			root = FXMLLoader.load(getClass().getResource("/GUI/EmployeeInfo.fxml"));
			Scene scene = new Scene(root);
			PrimaryStage.setTitle("Reader Info");
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


