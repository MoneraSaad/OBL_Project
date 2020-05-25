
	
	package client.controller;

	import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
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
import protocol.request.BorrowFreezeRequest;
import protocol.request.LockRequest;
import protocol.response.BorrowFreezeResponse;
import protocol.response.LockResponse;

	public class threeTimesLAteReturnsController extends AbstractController implements Initializable{

		Alert alert;
		
	    @FXML
	    private TableView<User> lateDeadlineTable;

	    @FXML
	    private TableColumn<User, String> StudentID;

	    @FXML
	    private TableColumn<User, String> Status;

	    public ObservableList<User> Readers=FXCollections.observableArrayList();
	    
	    
	 	public threeTimesLAteReturnsController() {
	 		super();
	 	}

	 	public threeTimesLAteReturnsController(ChatClient client) {
	 		super(client);
	 	}
	 	
		public LockResponse changeStatus(String ID) {
			
			LockRequest message = new LockRequest(ID);
	 			return (LockResponse) client.sendMessage(message);
	 		}
	 	/**
	 	 * 
	 	 * @return
	 	 */
	public LockResponse getReaders() {
			
		LockRequest message = new LockRequest();
			return (LockResponse) client.sendMessage(message);
		}
		
		
		protected void initReaders(ArrayList<User> list) {

			for (User user : list) { 
				this.Readers.add(user);
			}
			}
		
		

		
	protected void initBooks(ArrayList<User> list) {
			
//			for (User user : list) {
//				this.Readers.add(user);
//			}
		
			if(list.size()!=0)
			{
				
			    TableColumn<User,String> ConfirmLock;
			
			    
			    TableColumn<User, Void> colBtn = new TableColumn("Confirm Lock");
			    colBtn.setPrefWidth(147);
		        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
		        	@Override
		            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
		                final TableCell<User, Void> cell = new TableCell<User, Void>() {

		                    private final Button btn = new Button("Confirm Lock");

		                    {
		                    	
		                    	
		                    	
		                        btn.setOnAction((ActionEvent event) -> {
		                        	User data = getTableView().getItems().get(getIndex());
		                        	String btnID = data.getUserName();
			            			btn.setId(btnID);
			                    	btn.setPrefWidth(145);
		                        	System.out.println(btnID);
		                        	
		                            System.out.println("selectedData: " + data.getUserName());
		                            
		                            changing_status(btn.getId());	                           
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

		        lateDeadlineTable.getColumns().add(colBtn);
		        lateDeadlineTable.setPrefWidth(600);
		        lateDeadlineTable.setLayoutX(0); 

			}
			
		}
		

	private void changing_status(String btn) {
		//int BtnID = Integer.parseInt(btn);
		System.out.println(" btn pressed "+btn);
		threeTimesLAteReturnsController statusChange = (threeTimesLAteReturnsController) Controllers.getInstance().getController(ControllerType.THREE_LATE_RETURNS_CONTROLLER);
				LockResponse response = statusChange.changeStatus(btn);
		 		  if (response.getText().equals("OK")) {
		 				ShowExtendDeadlineSuccessMessage("changing status is completed");
		 				
		 			
			
		}   
	}


	public void ShowExtendDeadlineSuccessMessage(String msg) {
		alert = new Alert(AlertType.CONFIRMATION, msg, ButtonType.OK);
		alert.setTitle("The status is changed successfully");
		alert.setHeaderText("Status changed !");
		alert.show();
	}

		
		
		
		
		protected void initColumns() {
			// init table columns
			StudentID.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
			Status.setCellValueFactory(new PropertyValueFactory<User, String>("problem"));
			
		}
		
		protected void initTable() {

			lateDeadlineTable.setItems(Readers);

			lateDeadlineTable.refresh();

			// Set up the Table
		}
		
	   
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			
			threeTimesLAteReturnsController ReaderInfo = (threeTimesLAteReturnsController) Controllers.getInstance().getController(ControllerType.THREE_LATE_RETURNS_CONTROLLER);
			LockResponse resp=ReaderInfo.getReaders();
		
			initReaders(resp.getReader());
		
	        initColumns();
	        initTable();
	        initBooks(resp.getReader());
			
		}

	 	
	    
	}


