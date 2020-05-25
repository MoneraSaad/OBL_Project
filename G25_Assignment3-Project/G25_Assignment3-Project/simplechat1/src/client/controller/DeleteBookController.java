package client.controller;

import client.ChatClient;
import client.entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import protocol.request.DeleteBookRequest;
import protocol.response.AddBookResponse;
import protocol.response.DeleteBookResponse;

public class DeleteBookController extends AbstractController {

	Alert alert;
	
    @FXML
    private TextField catalogNumberTextField;

    private Book book;
    
	/**
	 * constructor for this class
	 */

	public DeleteBookController() {
		super();
	}

	/**
	 * constructor for this class
	 * @param client
	 */
	public DeleteBookController(ChatClient client) {
		super(client);
	} 
	
	/**
	 * This method send request message by the client to the server to delete book
	 *  from database and return an appropriate response message
	 *
	 * @param user
	 * @return appropriate DeleteBookResponse
	 */
	public DeleteBookResponse DeleteBookForm(Book book) {/////////////
		DeleteBookRequest message = new DeleteBookRequest(book);
		return (DeleteBookResponse) client.sendMessage(message);
	}// END
	
    @FXML
    void submitHandler(ActionEvent event) throws Exception{
    	
    	Book b =new Book();
    	b.setCatalogNum(catalogNumberTextField.getText().toString());
    	this.book=b;
    	System.out.println(book.getCatalogNum());

    	DeleteBookController deleteBookController = (DeleteBookController) Controllers.getInstance().getController(ControllerType.DELETE_BOOK_CONTROLLER);
	
		

		DeleteBookResponse resp = deleteBookController.DeleteBookForm(b);
		if (resp.getText().equals("OK")) {
			//((Node) event.getSource()).getScene().getWindow().hide();
			ShowMessageSuccessfulDeleteBook(resp.getText());
		} else {
			ShowMessagefailDeleteBook(resp.getText());
		}
		try {
			Thread.sleep(1500);
		}
		catch(Exception exp)
		{
			
		} 

    }
    
	public void ShowMessageSuccessfulDeleteBook(String msg) {
	    alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
		alert.setTitle("delete Book Successful ");
		alert.setHeaderText("Thanks for deleting the book");
		alert.show();
	
	}
	public void ShowMessagefailDeleteBook(String msg) {
	    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("delete Book failure");
		alert.setHeaderText("Re-try again");
		alert.show();
	}
//	public void ShowMessageBorrowedCopiesExists(String msg) {
//	    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
//		alert.setTitle("delete Book failure");
//		alert.setHeaderText("This book can`t be deleted.");
//		alert.show();
//	}

}



