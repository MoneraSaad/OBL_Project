package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import Server.SendEmail;
import client.ChatClient;
import client.ClientConnectToServer;
import client.entities.Book;
import client.entities.BorrowedBook;
import client.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import protocol.request.LibrarianEmailRequest;
import protocol.request.ManualExtendDeadlineRequest;
import protocol.request.ShowBorrowedBooksRequest;
import protocol.response.ExtendDeadlineResponse;
import protocol.response.LibrarianEmailResponse;
import protocol.response.ReaderInfoResponse;
import protocol.response.ShowBorrowedBooksResponse;

public class ShowBorrowedBooksController extends AbstractController implements Initializable{
	Alert alert;
	 
	public static ArrayList<String> LibrariansEmails=new ArrayList<String>();
	public static ArrayList<Book> books;
	
	BorrowedBook book;
	@FXML
    private TableView<Book> borrowedBooksTable;

    @FXML
    private TableColumn<Book,String> bookName;

    @FXML
    private TableColumn<Book,String> borrowingDate;

    @FXML
    private TableColumn<Book, String> returningDate;
	    
    
    public ObservableList<Book> borrowedBooks=FXCollections.observableArrayList();
   
	public ShowBorrowedBooksController() {
		super();
	}

	public ShowBorrowedBooksController(ChatClient client) {
		super(client);
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public ShowBorrowedBooksResponse getBooks(User user) {

		ShowBorrowedBooksRequest message = new  ShowBorrowedBooksRequest(user);

		return ( ShowBorrowedBooksResponse) client.sendMessage(message);
	}
	
	/**
	 * 
	 * @author Monera
	 * @return
	 */
	public LibrarianEmailResponse getEmailsss() {
		
		LibrarianEmailRequest message = new LibrarianEmailRequest();
		return (LibrarianEmailResponse) client.sendMessage(message);
	}
	
	
	 /**
	    * 
	    * @author Sri
	    * @param requestedBookToExtendDeadline
	    * @param userWhoseRequestedToExtendDeadline
	    * @return
	    */
		public ExtendDeadlineResponse checkExtendDeadlineRequirments(BorrowedBook book,User user) {
	    	ManualExtendDeadlineRequest message = new ManualExtendDeadlineRequest(book,user);	
			return (ExtendDeadlineResponse) client.sendMessage(message);
		}// END
	
	protected void initBooks(ArrayList<Book> list) {
		
		for (Book book : list) {
			this.borrowedBooks.add(book);
		}
		
		if(list.size()!=0)
		{
			
		    TableColumn<Book,String> editDeadlin;
		
		    
		    TableColumn<Book, Void> colBtn = new TableColumn("Extending Deadline");
		    colBtn.setPrefWidth(147);
	        Callback<TableColumn<Book, Void>, TableCell<Book, Void>> cellFactory = new Callback<TableColumn<Book, Void>, TableCell<Book, Void>>() {
	        	@Override
	            public TableCell<Book, Void> call(final TableColumn<Book, Void> param) {
	                final TableCell<Book, Void> cell = new TableCell<Book, Void>() {

	                    private final Button btn = new Button("Extend Deadline");

	                    {
	                    	
	                    	
	                    	
	                        btn.setOnAction((ActionEvent event) -> {
	                        	Book data = getTableView().getItems().get(getIndex());
	                        	String btnID = data.getBarcode();
		            			btn.setId(btnID);
		                    	btn.setPrefWidth(145);
	                        	System.out.println(btnID);
	                        	
	                            System.out.println("selectedData: " + data.getBarcode());
	                            System.out.println("nameeeeeeeeeee: " + data.getBookName());
	                            System.out.println("6666666666666666666--" + btn.getId());

	                            extendBook_book(btn.getId());	                           
	                        });
	                    }

	                    @Override
	                    public void updateItem(Void item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if (empty) {
	                            setGraphic(null);
	                        } else {
	                            setGraphic(btn);
	                        }
	                    }
	                };
	                return cell;
	            }
	        };

	        colBtn.setCellFactory(cellFactory);

	        borrowedBooksTable.getColumns().add(colBtn);
	        borrowedBooksTable.setPrefWidth(600);
	        borrowedBooksTable.setLayoutX(0); 

		}
		
	}
	
	protected void initColumns() {
		// init table columns
		
		bookName.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
		borrowingDate.setCellValueFactory(new PropertyValueFactory<Book, String>("borrowingDate"));
		returningDate.setCellValueFactory(new PropertyValueFactory<Book, String>("returningDate"));
	}
	
	protected void initTable() {

		this.borrowedBooksTable.setItems(borrowedBooks);

		this.borrowedBooksTable.refresh();

		// Set up the Table
	}
	
	private void extendBook_book(String btn) {
		//int BtnID = Integer.parseInt(btn);
		Book b=new Book();
		b.setBarcode(btn);

		for(int k=0;k<books.size();k++)
		{
			Book t= new Book();
			t.setBarcode(books.get(k).getBarcode());
			t.setBookName(books.get(k).getBookName());
			
			if(t.getBarcode().equals(btn))
			{
				book=new BorrowedBook(t.getBarcode());
				book.setBookName(t.getBookName());
				
				  System.out.println("1111111111111111111111111"+ReaderInfoController.userCard);
				  System.out.println("2222222222222222222222222"+book.getCopybarcode());
				  System.out.println("33333333333333333333333333"+book.getBookName());
				  ShowBorrowedBooksController BookToExtendDeadline = (ShowBorrowedBooksController) Controllers.getInstance().getController(ControllerType.DEADLINE_EXTENSION);
	     		  ExtendDeadlineResponse response;
				  if(ReaderInfoController.userCard==5)
					{
					  System.out.println("44444444444444444444444444444"+ReaderInfoController.userCard);
					  System.out.println("55555555555555555555555555555"+book.getCopybarcode());
					  System.out.println("666666666666666666666666666666"+book.getBookName());
					   response = BookToExtendDeadline.checkExtendDeadlineRequirments(book,ReaderInfoController.wantedUser,ClientConnectToServer.currUser);
					}
					else
					{
						   response = BookToExtendDeadline.checkExtendDeadlineRequirments(book,ClientConnectToServer.currUser);
					}
				  
		 		  if (response.getText().equals("OK")) {
		 				ShowExtendDeadlineSuccessMessage("Extending deadline process is completed");
		 				for(int i=0;i<LibrariansEmails.size();i++) {
		 			

							// send email of a reminder to the user
		 					SendEmailToLibrarian emailSend = new SendEmailToLibrarian();
							emailSend.setTextBody("This is an automatic message from Ort Braude Library.\n"
									+ "The Reader " + ClientConnectToServer.currUser.getUserName()
									+ " has just extended deadline for the book : " +books.get(k).getBookName() +" \nPlease do not reply to this message.\n");
							emailSend.setToo(LibrariansEmails.get(i));
							emailSend.SendAction();
		 				}
		 				
		 			} else  if (response.getText().equals("can't extend deadline")){
		 				ShowExtenedDeadlineErrorMessage(response.getText());
		 			} else {
		 				ShowExtenedDeadlineErrorMessage(response.getText());
		 			}
			}
			
		}
		
		int index = books.indexOf(b);
		
//		b=books.get(index);
//		System.out.println(b.getCatalogNum());
//		book.setBarcode(btn);
	    
           
	}
	
  
	
    private ExtendDeadlineResponse checkExtendDeadlineRequirments(BorrowedBook book2, User wantedUser, User currUser) {
    	ManualExtendDeadlineRequest message = new ManualExtendDeadlineRequest(book2,wantedUser,currUser);	
    	message.setFlag(1);
		return (ExtendDeadlineResponse) client.sendMessage(message);
	}

	public void ShowExtenedDeadlineErrorMessage(String msg) {
		alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
		alert.setTitle("Extending deadline Error");
		alert.setHeaderText("Book Is In Demand So You Can't Extend Deadline!");
		alert.show();
	}
    
	public void ShowExtendDeadlineSuccessMessage(String msg) {
		alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.OK);
		alert.setTitle("Extending deadline Successed");
		alert.setHeaderText("Successful Extention!");
		alert.show();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ShowBorrowedBooksController b = (ShowBorrowedBooksController) Controllers.getInstance().getController(ControllerType.SHOW_BORROWED_BOOKS_CONTROLLER);
		ShowBorrowedBooksResponse resp=new ShowBorrowedBooksResponse();
		if(ReaderInfoController.userCard==5)
		{
			resp=b.getBooks(ReaderInfoController.wantedUser);
		}
		else
			 {
				resp=b.getBooks(ClientConnectToServer.currUser);
			 }
		books=resp.getBorrowedBooks();
        initBooks(resp.getBorrowedBooks());
        initColumns();
        initTable();
        
        ShowBorrowedBooksController Librarians = (ShowBorrowedBooksController) Controllers.getInstance().getController(ControllerType.SHOW_BORROWED_BOOKS_CONTROLLER);
        LibrarianEmailResponse respooo=Librarians.getEmailsss();
        LibrariansEmails=respooo.getEmails();
        System.out.println(LibrariansEmails);

        
		
	} 
	
	
	
	

}

