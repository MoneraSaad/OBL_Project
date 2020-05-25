package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientConnectToServer;
import client.entities.Book;
import client.entities.User;
import client.gui.History;
import client.gui.MyLibraryCard;
import client.gui.ShowBorrowedBooks;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import protocol.request.AddBookRequest;
import protocol.request.MyLibraryCardRequest;
import protocol.response.AddBookResponse;
import protocol.response.MyLibraryCardResponse;

/**
 * 
 * @author Karam
 *
 */

public class MyLibraryCardController extends AbstractController implements Initializable {
	    Alert alert;
	    ArrayList<String> al;
		ObservableList<String> list;
	    @FXML
	    private Label readerName;

	    @FXML
	    private Label readerID;

	    @FXML
	    private Label readerStatus;

	    @FXML
	    private Label numOfBorrowedBooks;

	    @FXML
	    private Label lateReturningTimes;

	    @FXML
	    private Label readerEmail;

	    @FXML
	    private Label phoneNumber;

	    @FXML
	    private Label password;

	    @FXML
	    private Label readerStatus1;

	    @FXML
	    private Label lateReturningTimes1;

	    @FXML
	    private Label numOfBorrowedBooks1;

	    @FXML
	    private Label star1;

	    	    
	    @FXML
	    private TextField phoneNumber1;

	    @FXML
	    private TextField readerEmail1;

	 
	    
	    @FXML
	    private Label readerName1;

	    @FXML
	    private Label readerID1;


	    @FXML
	    private Label password1;
	    
	    @FXML
	    private Button showBooks;
	    @FXML
	    private ComboBox<String> editStatus;
	    
	    @FXML
	    private Label labelPermission;

	    @FXML
	    private Label permission;

	    @FXML
	    private ComboBox<String> editPermission;

	    
		public MyLibraryCardController() {
			super();
		}

		public MyLibraryCardController(ChatClient client) {
			
			super(client);

		}
		
		/**
		 * 	 * This method send request message by the client to the server to save the changes 
	      *  in the database and return an appropriate response message
		 * @param user
		 * @return
		 */
		  public MyLibraryCardResponse saveDetails(User user) {/////////////
			  MyLibraryCardRequest message = new MyLibraryCardRequest(user,ReaderInfoController.userCard);
				return (MyLibraryCardResponse) client.sendMessage(message);
			}
		
		  @FXML
		    void save() {

//			  //if()
//			  readerEmail1.setText(readerEmail1.getText());
//			  phoneNumber1.setText(phoneNumber1.getText());              
			  
			  MyLibraryCardController myLibraryCardController = (MyLibraryCardController) Controllers.getInstance()
						.getController(ControllerType.MY_LIBRARY_CARD_CONTROLLER);
				User user=new User();
				int changeFromeFrozenToActive=0;
				if(readerStatus1.getText().equals("Frozen"))
					changeFromeFrozenToActive=2;		//if it will be 1 in the end so late returned books will change to 0
				System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu-- "+readerStatus1.getText());

			  if(ReaderInfoController.userCard != 5)
				  user=ClientConnectToServer.currUser;
			  else
				  {
				  	user=ReaderInfoController.wantedUser;

				  }
			  user.setPhoneNumber(phoneNumber1.getText());
			  user.setEmail(readerEmail1.getText());
			  

			  if(ReaderInfoController.userCard == 5 && (ClientConnectToServer.currUser.getType()==UserPermission.Manager))
			  {
				  
				  int index1=editStatus.getSelectionModel().getSelectedIndex();
				  
				  if(index1!=-1)
				  { 
					  user.setStatusMembership(editStatus.getValue());

					  if(user.getStatusMembership().equals("Frozen"))
					  {
						  user.setFreeze("true");
						  user.setIsLock("false");
						  user.setOperation("ReturnBookReques");
					  }
					  else
					  {

						  if(user.getStatusMembership().equals("Active"))
						  {
							  user.setFreeze("false");
							  user.setIsLock("false");
							  user.setOperation("ExtendBookRequest,ReturnBookRequest,LendingRequest");
						  }
						 
					  
					  }
				  }
				  else
				  {
					  if(this.readerStatus1.equals("Active"))
					  	{
					  		user.setFreeze("false");
					  		user.setIsLock("false");
					  		user.setOperation("ExtendBookRequest,ReturnBookRequest,LendingRequest");
					  	}else
					  	{
					  		user.setFreeze("true");
					  		user.setIsLock("false");
					  		user.setOperation("ReturnBookReques");
					  	}
					  
				  }
				  
				  int index2=editPermission.getSelectionModel().getSelectedIndex();
				 if(index2==-1)
				 {
					 if(permission.getText().equals("Reader"))
					 {
						 user.setType(UserPermission.Reader);
						    user.setPermission("2");
					 }
					 else
					 {
						 user.setPermission("3");
						  user.setType(UserPermission.Librarian);
					 }
					 
					 
				 }else{
					 if(editPermission.getValue().equals("Reader"))
					 {
						 user.setType(UserPermission.Reader);
						 user.setPermission("2");
					 }
					 else
					 {
						 if(editPermission.getValue().equals("Librarian"))
						 {
							 user.setPermission("3");
							 user.setType(UserPermission.Librarian);
						 }
					  
					 }
				 }
				  
					  
			  }else
			  {
				  ReaderInfoController.userCard=0;
			  }
			  
			  
			  
			  if(ReaderInfoController.userCard == 5 && (ClientConnectToServer.currUser.getType()==UserPermission.Manager)) {
				  if(editStatus.getSelectionModel().getSelectedIndex()!=-1)
				  {
					  if(editStatus.getValue().equals("Active")&&changeFromeFrozenToActive==2)
					  {
						  changeFromeFrozenToActive=1;
						  user.setLateReturningNum(0);
					  		lateReturningTimes1.setText(""+0);
					  
				  		}

				  }
			  }

			  

			  if(changeFromeFrozenToActive==1)
				  user.setChangeLateretuningBooksToZero(1);
			  
			  
			  
				MyLibraryCardResponse resp = myLibraryCardController.saveDetails(user);
				  if(ReaderInfoController.userCard == 5 && (ClientConnectToServer.currUser.getType()==UserPermission.Manager))
				{
					readerStatus1.setText(resp.getUser().getStatusMembership());
					permission.setText(user.getType().toString());

				}
				
				 if(ReaderInfoController.userCard != 5)
				 {
					 ClientConnectToServer.currUser.setEmail(resp.getUser().getEmail());
					 ClientConnectToServer.currUser.setPhoneNumber(resp.getUser().getPhoneNumber());
				
				 }			
				 user.setChangeLateretuningBooksToZero(0);
		    }
	
		  
		  @FXML
			public void showBooks()
			{
				ShowBorrowedBooks.getInstance();
				
			}

		  
		  /**
			 * a method for setting the status combobox
			 */
			public void editStatus() {
				al = new ArrayList<String>();
				// String str;
				al.add("Active");
				al.add("Frozen");

				list = FXCollections.observableArrayList(al);
				editStatus.setItems(list);

			}
			
			  
			  /**
				 * a method for setting the permission combobox
				 */
				public void editPermission() {
					al = new ArrayList<String>();
					// String str;
					al.add("Reader");
					al.add("Librarian");

					list = FXCollections.observableArrayList(al);
					editPermission.setItems(list);

				}



		@Override
		public void initialize(URL location, ResourceBundle resources) {
			
		    if(ReaderInfoController.userCard != 5)
		    {
			readerName1.setText(ClientConnectToServer.currUser.getName());
			readerID1.setText(ClientConnectToServer.currUser.getUserName());
			readerStatus1.setText(ClientConnectToServer.currUser.getStatusMembership());
			numOfBorrowedBooks1.setText(""+(ClientConnectToServer.currUser.getBorrowedBookNum()));
			lateReturningTimes1.setText(""+ClientConnectToServer.currUser.getLateReturningNum());
			readerEmail1.setText(ClientConnectToServer.currUser.getEmail());
			phoneNumber1.setText(ClientConnectToServer.currUser.getPhoneNumber());
			password1.setText(ClientConnectToServer.currUser.getPassword());
			if(numOfBorrowedBooks1.getText().equals("0")) {
				showBooks.setVisible(false);
			}	
			}else
			{
		    	if(!(ClientConnectToServer.currUser.getType()==UserPermission.Librarian)) {
		    		
		    		editStatus.setVisible(true);
		    		editPermission.setVisible(true);
		    		labelPermission.setVisible(true);
		    		permission.setVisible(true);
		    		editStatus();
		    		editPermission();
		    	    permission.setText(ReaderInfoController.wantedUser.getType().toString());

		    		
		    	}

				readerStatus1.setText(ReaderInfoController.wantedUser.getStatusMembership());
			    readerStatus1.setVisible(true);
				readerName1.setText(ReaderInfoController.wantedUser.getName());
				readerID1.setText(ReaderInfoController.wantedUser.getUserName());
				readerStatus1.setText(ReaderInfoController.wantedUser.getStatusMembership());
				numOfBorrowedBooks1.setText(""+(ReaderInfoController.wantedUser.getBorrowedBookNum()));
				lateReturningTimes1.setText(""+ReaderInfoController.wantedUser.getLateReturningNum());
				readerEmail1.setText(ReaderInfoController.wantedUser.getEmail());
				phoneNumber1.setText(ReaderInfoController.wantedUser.getPhoneNumber());
				password1.setText(ReaderInfoController.wantedUser.getPassword());
				if(numOfBorrowedBooks1.getText().equals("0")) 
				{
				showBooks.setVisible(false);
				}
				
				this.phoneNumber1.setEditable(false);
				this.readerEmail1.setEditable(false);
//				ReaderInfoController.userCard=0;
		    	

			}
		    
	
		}

}
