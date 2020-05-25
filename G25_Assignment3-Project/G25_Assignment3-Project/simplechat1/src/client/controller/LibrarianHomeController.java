package client.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


import client.ChatClient;
import client.ClientConnectToServer;
import client.gui.History;
import client.gui.MainMenu;
import client.gui.MyLibraryCard;
import client.gui.ViewAllBooksGUI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import protocol.response.LogoutResponse;

/**
 * 
 * @author Monera LibrarianHomeController This is an LibrarianHomeController class that control
 *         all the request related laboratory
 */

public class LibrarianHomeController extends ReaderHomeController implements Initializable {

	Alert alert;
	ArrayList<String> al;
	ObservableList<String> list;
	
	  @FXML
	    private AnchorPane MainInsideAnchorePane;

	    @FXML
	    private VBox vboxID;

	    @FXML
	    private Label librarianName;

	    @FXML
	    private Button login;

	    @FXML
	    private ComboBox<String> comboBoxMenu;

	    @FXML
	    private AnchorPane LibrarianAnchorePane1;

	    @FXML
	    private FlowPane UserFlowPane;

	    @FXML
	    private AnchorPane librarianAnchorePane;
	    
	    @FXML
	    private ImageView ImageID1;

	    @FXML
	    private Button AddBookButton1;
	
	/**
	 * constructor for this class
	 */

	public LibrarianHomeController() {
		super();
	}
	
	/**
	 * constructor for this class
	 * @param client
	 */

	public LibrarianHomeController(ChatClient client) {
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
		LibrarianAnchorePane1.getChildren().clear();
		//UserAnchorPane.getChildren().clear();
		LibrarianAnchorePane1.getChildren().add(root1);
	}

	@FXML
	void BorrowFormHandler(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();

		try {
				fxmlLoader.load(getClass().getResource("/GUI/BorrowForm.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root1 = fxmlLoader.getRoot(); 
		LibrarianAnchorePane1.getChildren().clear();
		//UserAnchorPane.getChildren().clear();
		LibrarianAnchorePane1.getChildren().add(root1);
	}

	@FXML
	void EditDeadlinesHandler(ActionEvent event) {

	}

	@FXML
	void EditReadersPersonalDetailsHandler(ActionEvent event) {

	}

	@FXML
	void RegisterRequestHandler(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();
		try {
				fxmlLoader.load(getClass().getResource("/GUI/Registration.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root1 = fxmlLoader.getRoot();
		LibrarianAnchorePane1.getChildren().clear();
		//UserAnchorPane.getChildren().clear();
		LibrarianAnchorePane1.getChildren().add(root1);
	}

	@FXML
	void ReturnBookHandler(ActionEvent event) {
		FXMLLoader fxmlLoader = new FXMLLoader();

		try {
				fxmlLoader.load(getClass().getResource("/GUI/ReturnBook.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root1 = fxmlLoader.getRoot();
		LibrarianAnchorePane1.getChildren().clear();
		//UserAnchorPane.getChildren().clear();
		LibrarianAnchorePane1.getChildren().add(root1);

	}


	@FXML
	void ViewAllBooksHandler(ActionEvent event) {
		ViewAllBooksGUI.getInstance();
	}

	@FXML
	void homeHandler(ActionEvent event) {
		
		FXMLLoader fxmlLoader = new FXMLLoader();

		try {
				fxmlLoader.load(getClass().getResource("/GUI/LibrarianHome1.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root = fxmlLoader.getRoot();
		MainInsideAnchorePane.getChildren().clear();
		MainInsideAnchorePane.getChildren().add(root);

	}

	@FXML
	void ViewCardsHandler(ActionEvent event) {
	FXMLLoader fxmlLoader = new FXMLLoader();
	    	try {
				fxmlLoader.load(getClass().getResource("/GUI/ReaderInfo.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root1 = fxmlLoader.getRoot();
		LibrarianAnchorePane1.getChildren().clear();
		//UserAnchorPane.getChildren().clear();
		LibrarianAnchorePane1.getChildren().add(root1);
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

	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		//FXMLLoader fxmlLoader = new FXMLLoader();
		comboBoxMenu();
		
		// System.out.println(ClientConnectToServer.currUser.getName());
		librarianName.setText(ClientConnectToServer.currUser.getName());
		
		FXMLLoader fxmlLoader = new FXMLLoader();

		try {
				fxmlLoader.load(getClass().getResource("/GUI/LibrarianHome1.fxml").openStream());
			
		} catch (IOException e) {

		}
		AnchorPane root = fxmlLoader.getRoot();
		MainInsideAnchorePane.getChildren().clear();
		MainInsideAnchorePane.getChildren().add(root);
		
	} 

}
