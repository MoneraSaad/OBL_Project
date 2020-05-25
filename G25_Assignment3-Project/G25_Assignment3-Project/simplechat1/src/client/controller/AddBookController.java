package client.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import Server.Database;
import client.ChatClient;
import client.entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import protocol.request.AddBookRequest;

import protocol.response.AddBookResponse;


/**
 * 
 * @author Rania AddBookController This is an AddBookController class that control
 *         all the request related laboratory
 */

public class AddBookController extends AbstractController {
	Alert alert;
	
	
	
	 @FXML
	    private TextField bookNameTextField;

	    @FXML
	    private TextField AuthorTextField;

	    @FXML
	    private TextField subjectTextField;

	    @FXML
	    private DatePicker printingDateDatePicker;

	    @FXML
	    private TextField EditionNumberTextField;

	    @FXML
	    private TextField DescriptionTextField;

	    @FXML
	    private TextField ShelfNumberTextField;

	    @FXML
	    private TextField catalogNumberTextField;

	    @FXML
	    private DatePicker purchaseDateDatePicker;
	    
	    @FXML
	    private TextField NumberOfCopiesTextField;

	    private byte[] byteArray;
	    private Book book;
	    private FileInputStream fis;
	    
		/**
		 * constructor for this class
		 */

		public AddBookController() {
			super();
		}

		/**
		 * constructor for this class
		 * @param client
		 */
		public AddBookController(ChatClient client) {
			super(client);
		} 
		
		/**
		 * This method send request message by the client to the server to Add new book
		 * to the  database and return an appropriate response message
		 *
		 * @param user
		 * @return appropriate AddBookResponse
		 */
		public AddBookResponse AddBookForm(Book book) {/////////////
			AddBookRequest message = new AddBookRequest(book);
			return (AddBookResponse) client.sendMessage(message);
		}// END
		
	    @FXML
	    void submitHandler(ActionEvent event) throws Exception {
	    	
	    	if(bookNameTextField.getText().isEmpty()||AuthorTextField.getText().isEmpty()||subjectTextField.getText().isEmpty()||purchaseDateDatePicker.getPromptText().isEmpty()||EditionNumberTextField.getText().isEmpty()||DescriptionTextField.getText().isEmpty()||ShelfNumberTextField.getText().isEmpty()||catalogNumberTextField.getText().isEmpty()||printingDateDatePicker.getPromptText().isEmpty()||NumberOfCopiesTextField.getText().isEmpty())
	    		 ShowMessageEmptyAddBook("Adding book failed");
	    	else{
	    	
	    	//book.setCatalogNum(catalogNumberTextField1.getText().toString());//changed
			book.setCatalogNum(catalogNumberTextField.getText().toString());
			book.setBookName(bookNameTextField.getText().toString());
			book.setAuthor(AuthorTextField.getText().toString());
			book.setSubject(subjectTextField.getText().toString());
			LocalDate printingDate1 = printingDateDatePicker.getValue();
			book.setPrintingDate(printingDate1.toString());
			book.setEditionNum(EditionNumberTextField.getText().toString());
			book.setDescription(DescriptionTextField.getText().toString());
			book.setShelfNum(ShelfNumberTextField.getText().toString());
			LocalDate purchaseDate1 = purchaseDateDatePicker.getValue();
			book.setPurchaseDate(purchaseDate1.toString());
			book.setCopiesNum(Integer.parseInt(NumberOfCopiesTextField.getText().toString()));
			
			//tableOfContent(event);
			
			 FileOutputStream fos = new FileOutputStream(book.getTableOfContent());
			 BufferedOutputStream bos = new BufferedOutputStream(fos);
			// int bytesRead = is.read(mybytearray, 0, mybytearray.length);
			 bos.write(book.getByteArray(), 0, book.getByteArray().length);
			 
			 if(checkFields(book) == false)
				 ShowMessageMessingFields("You must fill all fields");
				 
			AddBookController addBookController = (AddBookController) Controllers.getInstance()
					.getController(ControllerType.ADD_BOOK_CONTROLLER);
			//Book b=new Book();
			//b.AddBookk(catalogNumber, bookName, Author, subject, printingDate, EditionNumber, Description, ShelfNumber, purchaseDate);
			
			System.out.println("*******");
			AddBookResponse resp = addBookController.AddBookForm(book);
			if (resp.getText().equals("OK")) {
				//((Node) event.getSource()).getScene().getWindow().hide();
				ShowMessageSuccessfulAddBook(resp.getText());
			} else {
				ShowMessagefailAddBook(resp.getText());
			}
			try {
				Thread.sleep(1500);
			}
			catch(Exception exp)
			{
				
			}
			
	    }
	    }
	    
	    public void ShowMessageEmptyAddBook(String msg) {
		    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
			alert.setTitle("add Book failure");
			alert.setHeaderText("At least one or more of the fields are empty\n Please fill all the fields");
			alert.show();
		}
	    
			public void ShowMessageSuccessfulAddBook(String msg) {
			    alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
				alert.setTitle("add Book Successful ");
				alert.setHeaderText("Thanks to add the book");
				alert.show();
			
			}
			public void ShowMessagefailAddBook(String msg) {
			    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
				alert.setTitle("add Book failure");
				alert.setHeaderText("Re-try again");
				alert.show();
			}
			public void ShowMessageMessingFields(String msg) {
			    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
				alert.setTitle("Messing fields");
				alert.setHeaderText("Please fill messing fields");
				alert.show();
			}
		

			@FXML
		    void tableOfContent(ActionEvent event) throws SQLException, IOException {
				
				
		    	FileChooser fc = new FileChooser();
		    	fc.getExtensionFilters().add(new ExtensionFilter("PDF Files", "*.pdf"));
		        File file = fc.showOpenDialog(null);
		    	//textArea.setText(file.getAbsolutePath());
		    	fis = new FileInputStream(file);
		    	BufferedInputStream bis = new BufferedInputStream(fis);
		    	byteArray = new byte[(int)file.length()];
		        bis.read(byteArray, 0, (int)file.length());
		        book = new Book();
		        book.setTableOfContent(file);
		        book.setByteArray(byteArray);
		    	/*String catalogNum = catalogNumberTextField.getText().toString();
		    	PreparedStatement stmt = db.con.prepareStatement("INSERT INTO library_students.book(filepdf) VALUES (? WHERE CatalogNum = ?)");
			    stmt.setString(2, catalogNum);
			    stmt.setBinaryStream(1, fis, file.length());*/
		    	System.out.println("reached here");
		    	/*for(File f : file) {
		    		System.out.println(f.getAbsolutePath());
		    	}*/

		    }
	
	
			public boolean checkFields(Book book) {
				
				if(book.getCatalogNum() == null || book.getAuthor() ==  null || book.getBookName() == null || book.getSubject() == null || book.getDescription() == null || book.getCopiesNum() < 1 || book.getPrintingDate() == null || book.getPurchaseDate() == null || book.getShelfNum() == null || book.getEditionNum() == null)
					return false;
				return true;
			}
	
	

}

