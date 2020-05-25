
package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ChatClient;
import client.ClientConnectToServer;
import client.entities.HistoryIntity;
import client.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import messages.Message;
import protocol.request.HistoryRequest;
import protocol.response.HistoryResponse;

public class HistoryController extends AbstractController implements Initializable{
	Alert alert;
	
	
	
	 @FXML
	    private TableColumn<HistoryIntity, String> bookName;

	    @FXML
	    private TableColumn<HistoryIntity, String> action;

	    @FXML
	    private TableColumn<HistoryIntity, String> actionDate;
	    
	    @FXML
	    private TableView<HistoryIntity> historyTable;
	    
    
    public ObservableList<HistoryIntity> history=FXCollections.observableArrayList();
   
	public HistoryController() {
		super();
	}

	public HistoryController(ChatClient client) {
		super(client);
	}
	
	
	public HistoryResponse getHistory(User user) {
		
		 HistoryRequest message = new  HistoryRequest(user);
		return ( HistoryResponse) client.sendMessage(message);
	}
	
	
	protected void initReaders(ArrayList<HistoryIntity> list) {

		for (HistoryIntity history : list) {
			this.history.add(history);
		}
		}
	
	protected void initColumns() {
		// init table columns
		bookName.setCellValueFactory(new PropertyValueFactory<HistoryIntity, String>("bookName"));
		action.setCellValueFactory(new PropertyValueFactory<HistoryIntity, String>("action"));
		actionDate.setCellValueFactory(new PropertyValueFactory<HistoryIntity, String>("actionDate"));
	}
	
	protected void initTable() {

		this.historyTable.setItems(history);

		this.historyTable.refresh();

		// Set up the Table
	}
	
   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		HistoryController history = (HistoryController) Controllers.getInstance().getController(ControllerType.HISTORY_CONTROLLER);
        HistoryResponse resp=history.getHistory(ClientConnectToServer.currUser);
       
        initReaders(resp.getHistory());
        initColumns();
        initTable();
		
	}
	
	
	

}

