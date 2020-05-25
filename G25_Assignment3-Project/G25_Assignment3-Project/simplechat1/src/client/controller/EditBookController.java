
package client.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import client.ChatClient;
import client.entities.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import protocol.request.EditBookRequest;
import protocol.response.EditBookResponse;


/**
 * 
 * @author Rania AddBookController This is an AddBookController class that control
 *         all the request related laboratory
 */

public class EditBookController extends AbstractController  implements Initializable{
	Alert alert;

	

	    @FXML
	    private TextField bookNameTextField;

	    @FXML
	    private TextField AuthorTextField;

	    @FXML
	    private TextField subjectTextField;

	    @FXML
	    private TextField EditionNumberTextField;

	    @FXML
	    private TextField catalogNumberTextField;

	    @FXML
	    private TextArea DescriptionTextField;

	

	    @FXML
	    private DatePicker purchaseDateDatePicker;

	    @FXML
	    private DatePicker printingDateDatePicker;

	    @FXML
	    private TextField ShelfNumberTextField;
	    
	    
	    
	    


	    @FXML
	    private TextField NumberOfCopiesTextField1;



	    
	    

	    private byte[] byteArray;
	    private Book book;
	    private FileInputStream fis;
	    
		/**
		 * constructor for this class
		 */

		public EditBookController() {
			super();
		}

		/**
		 * constructor for this class
		 * @param client
		 */
		public EditBookController(ChatClient client) {
			super(client);
		} 
		
		/**
		 * This method send request message by the client to the server to Add new book
		 * to the  database and return an appropriate response message
		 *
		 * @param user
		 * @return appropriate AddBookResponse
		 */
		public EditBookResponse EditBookForm(Book book) {/////////////
			EditBookRequest message = new EditBookRequest(book);
			return (EditBookResponse) client.sendMessage(message);
		}// END
		
	    @FXML
	    void submitHandler(ActionEvent event) throws Exception {
	
	    	
	    	if(bookNameTextField.getText().isEmpty()||AuthorTextField.getText().isEmpty()||subjectTextField.getText().isEmpty()||purchaseDateDatePicker.getPromptText().isEmpty()||EditionNumberTextField.getText().isEmpty()||DescriptionTextField.getText().isEmpty()||ShelfNumberTextField.getText().isEmpty()||catalogNumberTextField.getText().isEmpty()||printingDateDatePicker.getPromptText().isEmpty()||NumberOfCopiesTextField1.getText().isEmpty())
	    		 ShowMessageEmptyAddBook("editting book failed");
	    	else{
		    	System.out.println("cataloggggggggg"+catalogNumberTextField.getText());

	    	book.setCatalogNum(catalogNumberTextField.getText());
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
			book.setCopiesNum(Integer.parseInt(NumberOfCopiesTextField1.getText().toString()));
			
			FileOutputStream fos = new FileOutputStream(book.getTableOfContent());
			 BufferedOutputStream bos = new BufferedOutputStream(fos);
			// int bytesRead = is.read(mybytearray, 0, mybytearray.length);
			 bos.write(book.getByteArray(), 0, book.getByteArray().length);
			 
			 if(checkFields(book) == false)
				 ShowMessageMessingFields("You must fill all fields");
			 
			EditBookController editBookController = (EditBookController) Controllers.getInstance()
					.getController(ControllerType.EDIT_BOOK_CONTROLLER);
			//Book b=new Book();
			//b.getBook();
			EditBookResponse resp = editBookController.EditBookForm(book);
			if (resp.getText().equals("OK")) {
				//((Node) event.getSource()).getScene().getWindow().hide();
				ShowMessageSuccessfulEditBook(resp.getText());
			} else {
				ShowMessagefailEditBook(resp.getText());
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
			alert.setTitle("edit Book failure");
			alert.setHeaderText("At least one or more of the fields are empty\n Please fill all the fields");
			alert.show();
		}
	    

		public void ShowMessageLogin(String msg) {
			alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
			alert.setTitle("Edit book is successfull");
			alert.setHeaderText("The book is edited!!");
			alert.show();
		}
	    
//	    public void setForm(Book book) {
//	    	System.out.println(book.getBookName());
//	    	bookNameTextField.setText(book.getBookName());
//			AuthorTextField.setText(book.getAuthor());
//			subjectTextField.setText(book.getSubject());
//			printingDateTextField.setText(book.getPrintingDate());
//			EditionNumberTextField.setText(book.getEditionNum());
//			DescriptionTextField.setText(book.getDescription());
//			ShelfNumberTextField.setText(book.getShelfNum());
//			catalogNumberTextField.setText(book.getCatalogNum());
//			purchaseDateTextField.setText(book.getPurchaseDate());
//		
//
//	    }

		public void ShowMessageSuccessfulEditBook(String msg) {
		    alert = new Alert(AlertType.INFORMATION, "", ButtonType.OK);
			alert.setTitle("Edit Book is Successful ");
			alert.setHeaderText("Thanks for editing the book");
			alert.show();
		
		}
		public void ShowMessagefailEditBook(String msg) {
		    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
			alert.setTitle("Edit Book failed");
			alert.setHeaderText("Re-try again");
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
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
//			System.out.println(ReaderHomeController.book.getAuthor());
//		System.out.println(ReaderHomeController.book);
//			setForm(ReaderHomeController.book);
			bookNameTextField.setText(ViewAllBooksController.book.getBookName());
			AuthorTextField.setText(ViewAllBooksController.book.getAuthor());
			subjectTextField.setText(ViewAllBooksController.book.getSubject());
			printingDateDatePicker.setPromptText(ViewAllBooksController.book.getPrintingDate());
			EditionNumberTextField.setText(ViewAllBooksController.book.getEditionNum());
			DescriptionTextField.setText(ViewAllBooksController.book.getDescription());
			ShelfNumberTextField.setText(ViewAllBooksController.book.getShelfNum());
			catalogNumberTextField.setText(ViewAllBooksController.book.getCatalogNum());
			
			purchaseDateDatePicker.setPromptText(ViewAllBooksController.book.getPurchaseDate());
			NumberOfCopiesTextField1.setText(Integer.toString(ViewAllBooksController.book.getCopiesNum()));
		
			
		}
		
		public void ShowMessageMessingFields(String msg) {
		    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
			alert.setTitle("Messing fields");
			alert.setHeaderText("Please fill messing fields");
			alert.show();
		}
		
		public boolean checkFields(Book book) {
			
			if(book.getCatalogNum() == null || book.getAuthor() ==  null || book.getBookName() == null || book.getSubject() == null || book.getDescription() == null || book.getCopiesNum() < 1 || book.getPrintingDate() == null || book.getPurchaseDate() == null || book.getShelfNum() == null || book.getEditionNum() == null)
				return false;
			return true;
		}
    }
	


	
	
	
	


