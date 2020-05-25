
package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientConnectToServer;
import client.entities.Book;
import client.entities.User;
import client.gui.AddBookGUI;
import client.gui.BorrowFreezeGUI;
import client.gui.EditBookGUI;
import client.gui.History;
import client.gui.MainMenu;
import client.gui.ManagerMenu;
import client.gui.MyLibraryCard;
import client.gui.ViewAllBooksGUI;
import client.gui.threeTimesLateReturnsGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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
import protocol.request.OrderBookRequest;
import protocol.response.BooksResponse;
import protocol.response.LoginResponse;
import protocol.response.LogoutResponse;
import protocol.response.OrderBookResponse;

/**
 * 
 * @author Monera ManagerHomeController This is an ManagerHomeController class
 *         that control all the request related laboratory
 */

public class ManagerHomeController extends AbstractController implements Initializable {

	Alert alert;
	ArrayList<String> al;
	ObservableList<String> list;
	public static Book book;
	  @FXML
	    private Label managerName;

	    @FXML
	    private Button login;

	    @FXML
	    private ComboBox<String> comboBoxMenu;

	    @FXML
	    private AnchorPane MainInsideAnchorePane;

	//   @FXML
	 //   private FlowPane UserAnchorPane;
	    
	    @FXML
	    private AnchorPane ManagerAnchorePane1;

	/**
	 * constructor for this class
	 */
	public ManagerHomeController() {
		super();
	}

	/**
	 * constructor for this class
	 * 
	 * @param client
	 */
	public ManagerHomeController(ChatClient client) {
		super(client);
	}
	
	


	


	@FXML
	void AddNewBookHandler(ActionEvent event) {
		 FXMLLoader fxmlLoader = new FXMLLoader();

			try {
					fxmlLoader.load(getClass().getResource("/GUI/AddBookForm.fxml").openStream());
				
			} catch (IOException e) {

			}
			AnchorPane root1 = fxmlLoader.getRoot();
			ManagerAnchorePane1.getChildren().clear();
			//UserAnchorPane.getChildren().clear();
			ManagerAnchorePane1.getChildren().add(root1);
	}

	@FXML
	void BorrowFormHandler(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();

			try {
					fxmlLoader.load(getClass().getResource("/GUI/BorrowForm.fxml").openStream());
				
			} catch (IOException e) {

			}
			AnchorPane root1 = fxmlLoader.getRoot();
			ManagerAnchorePane1.getChildren().clear();
			//UserAnchorPane.getChildren().clear();
			ManagerAnchorePane1.getChildren().add(root1);
	}

	


    @FXML
    void readersWithLateReturnsHandler(ActionEvent event) {
    	threeTimesLateReturnsGUI.getInstance();
    }

    @FXML
    void readingHoldingBooksHandler(ActionEvent event) {
    	BorrowFreezeGUI.getInstance();
    }
	@FXML
	void RegisterRequestHandler(ActionEvent event) {
    	FXMLLoader fxmlLoader = new FXMLLoader();

			try {
					fxmlLoader.load(getClass().getResource("/GUI/Registration.fxml").openStream());
				
			} catch (IOException e) {

			}
			AnchorPane root1 = fxmlLoader.getRoot();
			ManagerAnchorePane1.getChildren().clear();
			//UserAnchorPane.getChildren().clear();
			ManagerAnchorePane1.getChildren().add(root1);
	}

	 @FXML
	    void ReturnBookHandler(ActionEvent event) {
	    	FXMLLoader fxmlLoader = new FXMLLoader();

			try {
					fxmlLoader.load(getClass().getResource("/GUI/ReturnBook.fxml").openStream());
				
			} catch (IOException e) {

			}
			AnchorPane root1 = fxmlLoader.getRoot();
			ManagerAnchorePane1.getChildren().clear();
			//UserAnchorPane.getChildren().clear();
			ManagerAnchorePane1.getChildren().add(root1);
	    }
	 void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("/GUI/Reports.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
	    }
	    @FXML
	    void StatisticsHandler(ActionEvent event) {
	    	FXMLLoader fxmlLoader = new FXMLLoader();
			Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
			ManagerHomeController regForm = new ManagerHomeController();
			try {
				regForm.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }

	@FXML
	void ViewAllBooksHandler(ActionEvent event) {
	ViewAllBooksGUI.getInstance();
	}

	@FXML
	void ViewCardsHandler(ActionEvent event) {
	FXMLLoader fxmlLoader = new FXMLLoader();
	    	try {
				fxmlLoader.load(getClass().getResource("/GUI/ReaderInfo.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root1 = fxmlLoader.getRoot();
		ManagerAnchorePane1.getChildren().clear();
		//UserAnchorPane.getChildren().clear();
		ManagerAnchorePane1.getChildren().add(root1);
	}

	@FXML
	void ViewWorkersHandler(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
	    	try {
				fxmlLoader.load(getClass().getResource("/GUI/EmployeeInfo.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root1 = fxmlLoader.getRoot();
		ManagerAnchorePane1.getChildren().clear();
		//UserAnchorPane.getChildren().clear();
		ManagerAnchorePane1.getChildren().add(root1);
	}

	@FXML
	void homeHandler(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();

		try {
				fxmlLoader.load(getClass().getResource("/GUI/ManagerHome1.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root = fxmlLoader.getRoot();
		MainInsideAnchorePane.getChildren().clear();
		MainInsideAnchorePane.getChildren().add(root);
	}



	/**
	 * a method for setting the Menu combobox
	 */
	public void comboBoxMenu() {
		al = new ArrayList<String>();
		// String str;
		al.add("My Libray Card");
		al.add("Action History");
		al.add("sign out");

		list = FXCollections.observableArrayList(al);
		comboBoxMenu.setItems(list);

	}
	
	@FXML
	void MenuOnAction(ActionEvent event) { 
		if (comboBoxMenu.getSelectionModel().getSelectedIndex() == 0) {

			MyLibraryCard.getInstance();

		}
		if (comboBoxMenu.getSelectionModel().getSelectedIndex() == 1) {
			History.getInstance();
		}
		if (comboBoxMenu.getSelectionModel().getSelectedIndex() == 2) {
			// Mark as logged out in the DB
			ReaderHomeController rhc = (ReaderHomeController) Controllers.getInstance()
					.getController(ControllerType.READER_HOME_CONTROLLER);
			LogoutResponse resp = rhc.logoutUser(ClientConnectToServer.currUser);
			if (resp.getText().equals("OK")) {
				ClientConnectToServer.currUser = null;
				((Node) event.getSource()).getScene().getWindow().hide();
				MainMenu.getInstance();
			}

		}
	}
	


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		comboBoxMenu();

		managerName.setText(ClientConnectToServer.currUser.getName());
		
		FXMLLoader fxmlLoader = new FXMLLoader();

		try {
				fxmlLoader.load(getClass().getResource("/GUI/ManagerHome1.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root = fxmlLoader.getRoot();
		MainInsideAnchorePane.getChildren().clear();
		MainInsideAnchorePane.getChildren().add(root);

	}

}
