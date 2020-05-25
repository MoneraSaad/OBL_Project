package client.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import client.ChatClient;

//import com.sun.glass.events.MouseEvent;

import client.ClientConnectToServer;
import client.entities.BorrowedBook;
import client.gui.MainMenu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import protocol.request.BorrowFormSubmitRequest;
import protocol.response.BorrowFormSubmitRespone;

	public class BorrowFormController extends AbstractController implements Initializable{
	Alert alert;
		
	@FXML
	private AnchorPane insideBorrowAnchore;
	
	@FXML
	private Label title;
	
	@FXML
	private Button sumbitButton;
	
	@FXML
	private TextField barcode;
	
	@FXML
	private TextField lender;
	
	
	@FXML
	private TextField lenderTo;
	
	@FXML
	private Label status;
	
	/**
	 * This method send request message by the client to the server to Check
	 * Login Details in the database and return an appropriate response message
	 *
	 * @param borrowedBook
	 * @return appropriate LoginResponse
	 */
	public  BorrowFormController() {
		super();
	}

	public BorrowFormController(ChatClient client) {
		super(client);
	}
	public BorrowFormSubmitRespone checkBorrowFormFieldsFromGUI(BorrowedBook borrowedBook) {/////////////
		//System.out.println("YfffO");
		BorrowFormSubmitRequest message = new BorrowFormSubmitRequest(borrowedBook);
		
		//System.out.println((BorrowFormSubmitRespone) client.sendMessage(message));//////
	
		return (BorrowFormSubmitRespone) client.sendMessage(message);
	}// END



	@FXML
	void submitHandler(ActionEvent event) {
	
		if(lender.getText().isEmpty() || lenderTo.getText().isEmpty() || barcode.getText().isEmpty())
			ShowErrorMessage("All fileds must be filled");
		else {
		String lenderText = lender.getText().toString();
		String lenderToText = lenderTo.getText().toString();
		String barcodeText = barcode.getText().toString();	
		BorrowedBook borrowedBook =  new BorrowedBook(lenderText,lenderToText,barcodeText);
		BorrowFormController borrowForm = (BorrowFormController) Controllers.getInstance().getController(ControllerType.BORROW_FORM_CONTROLLER);
		BorrowFormSubmitRespone response = borrowForm.checkBorrowFormFieldsFromGUI(borrowedBook);
		if (response.getText().equals("OK")) {
			ShowSuccessMessage("Borrowing process is completed");
		} else {
			ShowErrorMessage(response.getText());
		}
		}
	}



	public void ShowErrorMessage(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Borrow Error");
		alert.setHeaderText("Re-try again");
		alert.show();
	}
	public void ShowSuccessMessage(String msg) {
		alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.OK);
		alert.setTitle("Borrow Successed");
		alert.setHeaderText("Successful Borrow!");
		alert.show();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		lender.setText(ClientConnectToServer.currUser.getUserName());
		
	}


}





