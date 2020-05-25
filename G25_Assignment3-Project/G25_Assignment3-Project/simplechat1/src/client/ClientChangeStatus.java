package client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.gui.LoginGUI;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ClientChangeStatus extends Application implements Initializable {

	@FXML
	private Label UserName;

	@FXML
	private Label StatusM;


	private String name;

	ObservableList<String> list;
	
	ArrayList<String> al;
	
    @FXML
    private Label statusLabel;

    @FXML
    private ComboBox<String> comboBox;

    
    @FXML
    void BackController(ActionEvent event) {
    	((Node) event.getSource()).getScene().getWindow().hide();
    	
		LoginGUI L = new LoginGUI();
		L.show();
    }

	@FXML
	void savestatus(ActionEvent event) {
    	ArrayList<String> listToSend = new ArrayList<String>();
    	int index = comboBox.getSelectionModel().getSelectedIndex();
    	//System.out.println(""+index);
    	if(index!=-1) {
    	String newStatus = al.get(index);
    //	String studentName = ClientConnectToServer.client.getUsernameToDisplay();
    	listToSend.add(newStatus);
    	//listToSend.add(studentName);
    	String OperationA= ("ExtendBookRequest,ReturnBookRequest,LendingRequest");
    	String OperationF =("ReturnBookRequest");
    	String OperationNL = ("");
    	String FreezeA = ("false");
    	String FreezeF = ("true");
    	String FreezeNL =("false");
    	if(newStatus.equals("Active")) {
    		listToSend.add(OperationA);
    		listToSend.add(FreezeA);
    	}
    	else if (newStatus.equals("Frozen")) {
    		listToSend.add(OperationF);
    		listToSend.add(FreezeF);
    	}
    	else if (newStatus.equals("Locked")) {
    		listToSend.add(OperationNL);
    		listToSend.add(FreezeNL);
    	}
    	else if (newStatus.equals("NotRegistered")) {
    		listToSend.add(OperationNL);
    		listToSend.add(FreezeNL);
    	}
    	statusLabel.setText(newStatus);
    	//ClientConnectToServer.client.handleMessageFromClientUI(listToSend);
    	Alert alert = new Alert(AlertType.INFORMATION, "Status changed!",ButtonType.OK);
    	alert.setTitle("Information");
    	alert.setHeaderText("Status Successfully changed");
    	alert.show();
    	
    	}
    	else {
    		Alert alert = new Alert(AlertType.WARNING, "you should choose from the combbox ",ButtonType.OK);
        	alert.setTitle("Warning");
        	alert.setHeaderText("Status haven't changed");
        	alert.show();	
    	}
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void show() {
		Stage PrimaryStage = new Stage();

		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("/GUI/ClientG.fxml"));
			Scene scene = new Scene(root);
			PrimaryStage.setTitle("Studen Information");
			PrimaryStage.setScene(scene);
			PrimaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void start(Stage arg0) throws Exception {
		show();

	}
	
  
	public void setComboBox() {
		al = new ArrayList<String>();	
		String str;
		al.add("Locked");
		al.add("Frozen");
		al.add("Active");
		al.add("NotRegistered");
		
		list = FXCollections.observableArrayList(al);
		comboBox.setItems(list);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setComboBox();
		//UserName.setText(ClientConnectToServer.client.getUsernameToDisplay());
		//statusLabel.setText(ClientConnectToServer.client.getStatus());
	
	}

}
