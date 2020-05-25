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
import protocol.request.EmployeeInfoRequest;
import protocol.response.EmployeeInfoResponse;

public class EmployeeInfoController extends AbstractController implements Initializable{
	Alert alert;
	
	@FXML
    private TableView<User> EmployeeTable;

    @FXML
    private TableColumn<User, String> EmployeeID;

    @FXML
    private TableColumn<User, String> EmployeeName;

    @FXML
    private TableColumn<User, String> Password;

    @FXML
    private TableColumn<User, String> Email;

    @FXML
    private TableColumn<User, String> PhoneNumber;

    @FXML
    private TableColumn<User, String> Status;

    public ObservableList<User> Employees=FXCollections.observableArrayList();
   
    public static int userCard;
    
	public static User wantedUser;


    
	public EmployeeInfoController() {
		super();
	}

	public EmployeeInfoController(ChatClient client) {
		super(client);
	}
	
	
	public EmployeeInfoResponse getEmployee() {
		
		EmployeeInfoRequest message = new EmployeeInfoRequest();
		return (EmployeeInfoResponse) client.sendMessage(message);
	}
	
	
	protected void initEmployees(ArrayList<User> list) {

		for (User user : list) {
			this.Employees.add(user);
		}
		}
	
	protected void initColumns() {
		// init table columns
		Status.setCellValueFactory(new PropertyValueFactory<User, String>("Status"));
		PhoneNumber.setCellValueFactory(new PropertyValueFactory<User, String>("PhoneNumber"));
		EmployeeID.setCellValueFactory(new PropertyValueFactory<User, String>("UserName"));
		EmployeeName.setCellValueFactory(new PropertyValueFactory<User, String>("Name"));
		Password.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
		Email.setCellValueFactory(new PropertyValueFactory<User, String>("Email"));
		
		if(Employees.size()!=0)
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
	                        	System.out.println("//////////////////////////////////"+data.getUserName());
		            			btn.setId(btnID);
		                    	btn.setPrefWidth(145);
	                        	System.out.println(btnID);
	                            System.out.println("ttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");

	                        	
	                            System.out.println("selectedData: " + data.getUserName());
	                            System.out.println("+++++++++++++++++++++++******************************");

	                            ReaderInfoController.userCard=5;
	                            System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
	                            data.setPermission("Librarian");
	                            data.setType(UserPermission.Librarian);

	                            ReaderInfoController.wantedUser=data;
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

	        EmployeeTable.getColumns().add(colBtn);
	        EmployeeTable.setPrefWidth(700);
	        EmployeeTable.setLayoutX(0); 

		}
		userCard=0;
		
	}
	
	protected void initTable() {

		EmployeeTable.setItems(Employees);

		EmployeeTable.refresh();

		// Set up the Table
	}
	
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("66666666666666666666666666666666");
		EmployeeInfoController EmployeeInfo = (EmployeeInfoController) Controllers.getInstance().getController(ControllerType.EMPLOYEE_INFO_CONTROLLER);
        EmployeeInfoResponse resp=EmployeeInfo.getEmployee();
        initEmployees(resp.getEmployee());
        initColumns();
        initTable();
		
	}
	
	
	

}


