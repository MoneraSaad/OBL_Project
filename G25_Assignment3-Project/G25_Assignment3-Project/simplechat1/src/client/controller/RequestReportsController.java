package client.controller;

import client.ChatClient;
//import client.ClientConnectToServer;
//import client.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class RequestReportsController extends AbstractController {
	Alert alert;
	
	public static int Request_Report;
	
	public RequestReportsController() {
		super();
	}

	public RequestReportsController(ChatClient client) {
		super(client);
	}
	
	  @FXML
	    void activityReportRequest(ActionEvent event) {
		  Request_Report=0;
			Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
			RequestReportsController regForm = new RequestReportsController();
			try {
				regForm.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	 
	    @FXML
	    void loanReportRequest(ActionEvent event) {
	    	Request_Report=1;
	    	Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
			RequestReportsController regForm = new RequestReportsController();
			try {
				regForm.start(stage);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
	    
	    @FXML
	    void lateReturnReportRequest(ActionEvent event) {
	    	Request_Report=2;
	    	Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
			RequestReportsController regForm = new RequestReportsController();
			try {
				regForm.start(stage);
			} catch (Exception e) {
				
		      e.printStackTrace();
			}
	    }
	    
	    void start(Stage primaryStage) throws Exception {
				Parent root = FXMLLoader.load(getClass().getResource("/GUI/Report.fxml"));
				Scene scene = new Scene(root);
				primaryStage.setScene(scene);
				primaryStage.show();
		    }
}
