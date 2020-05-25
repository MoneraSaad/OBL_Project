package client.controller;

import client.ChatClient;
//import client.ClientConnectToServer;
//import client.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import protocol.request.ReturnBookRequest;
import protocol.response.ReturnBookResponse;

public class ReturnBookController extends AbstractController {
	Alert alert;
	
	@FXML
	private TextField barcodeTextField;
	
	public ReturnBookController() {
		super();
	}

	public ReturnBookController(ChatClient client) {
		super(client);
	}
	
	
	public ReturnBookResponse checkBarcodeIsFromGUI(String barcode) {
		
		ReturnBookRequest message = new ReturnBookRequest(barcode);
	//	System.out.println((ReturnBookResponse) client.sendMessage(message));
		return (ReturnBookResponse) client.sendMessage(message);
	}
	@FXML
	public void submitHandler(ActionEvent event){
		String barcode = barcodeTextField.getText().toString();
		
		ReturnBookController ReturnBook = (ReturnBookController) Controllers.getInstance()
				                           .getController(ControllerType.RETURN_BOOK_CONTROLLER);
		ReturnBookResponse resp=ReturnBook.checkBarcodeIsFromGUI(barcode);
		if (resp.getText().equals("OK")) {
			//((Node) event.getSource()).getScene().getWindow().hide();
			ShowMessageSuccessfulReturnBook(resp.getText());
		} else {
			ShowMessagefailReturnBook(resp.getText());
		}
		try {
			Thread.sleep(1500);
		}
		catch(Exception exp)
		{
			
		}
	}
	
	public void exit() {
		System.exit(0);
	}
	
	public void ShowMessageSuccessfulReturnBook(String msg) {
	    alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
		alert.setTitle("Return Book Successful ");
		alert.setHeaderText("Thanks to return the book");
		alert.show();
	
	}
	public void ShowMessagefailReturnBook(String msg) {
	    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Return Book failure");
		alert.setHeaderText("Re-try again");
		alert.show();
	}
	

}
