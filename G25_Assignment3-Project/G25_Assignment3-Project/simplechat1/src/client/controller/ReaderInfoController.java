package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ChatClient;
import client.entities.User;
import client.gui.MyLibraryCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import protocol.request.ReaderInfoRequest;
import protocol.response.ReaderInfoResponse;

public class ReaderInfoController extends AbstractController implements Initializable{
	Alert alert;
	public static User wantedUser;
	@FXML
    private TableView<User> ReaderTable;

    @FXML
    private TableColumn<User, String> ReaderID;

    @FXML
    private TableColumn<User, String> ReaderName;

    @FXML
    private TableColumn<User, String> Password;

    @FXML
    private TableColumn<User, String> Email;

    @FXML
    private TableColumn<User, String> PhoneNumber;

    @FXML
    private TableColumn<User, String> Status;
    
    public ObservableList<User> Readers=FXCollections.observableArrayList();
    public static int userCard;
	public ReaderInfoController() {
		super();
	}

	public ReaderInfoController(ChatClient client) {
		super(client);
	}
	
	
	public ReaderInfoResponse getReaders() {
		
		ReaderInfoRequest message = new ReaderInfoRequest();
		return (ReaderInfoResponse) client.sendMessage(message);
	}
	
	
	protected void initReaders(ArrayList<User> list) {

		for (User user : list) { 
			this.Readers.add(user);
		}
		}
	
	protected void initColumns() {
		// init table columns
		ReaderID.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
		ReaderName.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
		Password.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		Email.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
		PhoneNumber.setCellValueFactory(new PropertyValueFactory<User, String>("PhoneNumber"));
		Status.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));
		
		
		
		if(Readers.size()!=0)
		{
			
		    TableColumn<User,String> editDeadlin;
		
		    
		    TableColumn<User, Void> colBtn = new TableColumn("Show Reader card");
		    colBtn.setPrefWidth(147);
	        Callback<TableColumn<User, Void>, TableCell<User, Void>> cellFactory = new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
	        	@Override
	            public TableCell<User, Void> call(final TableColumn<User, Void> param) {
	                final TableCell<User, Void> cell = new TableCell<User, Void>() {

	                    private final Button btn = new Button("Show Reader card");
	                    {
	                    	
	                    	MyLibraryCardController a1= new MyLibraryCardController();
	                        btn.setOnAction((ActionEvent event) -> {
	                        	User data = getTableView().getItems().get(getIndex());
	                        	String btnID = data.getUserName();
		            			btn.setId(btnID);
		                    	btn.setPrefWidth(145);
	                        	System.out.println(btnID);

	                            System.out.println("selectedData: " + data.getUserName());
	                            userCard=5;

	                            wantedUser=data;
	                            MyLibraryCard.getInstance();
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

	        ReaderTable.getColumns().add(colBtn);
	        ReaderTable.setPrefWidth(600);
	        ReaderTable.setLayoutX(0); 

		}
		userCard=0;
	}
	
	protected void initTable() {

		ReaderTable.setItems(Readers);

		ReaderTable.refresh();

		// Set up the Table
	}
	
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("1111111111111111111111111111");
		ReaderInfoController ReaderInfo = (ReaderInfoController) Controllers.getInstance().getController(ControllerType.READER_INFO_CONTROLLER);
        ReaderInfoResponse resp=ReaderInfo.getReaders();
        for (User user : resp.getReader()) {
			System.out.println(user.getUserName() + user.getPassword() + "\n");
		}
        initReaders(resp.getReader());
        initColumns();
        initTable();
		
	}
	
	
	

}

