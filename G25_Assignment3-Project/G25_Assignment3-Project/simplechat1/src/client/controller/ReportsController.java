package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.entities.Book;
import client.entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import protocol.request.ReportsRequest;
import protocol.response.ReportsResponse;

public class ReportsController extends AbstractController implements Initializable {
	Alert alert;
	 // ----------------------------------------/---------------/------------------------------------
	   @FXML
	    private Label lockedMembership;

	    @FXML
	    private Label frozenMembership;

	    @FXML
	    private Label lateReturningBook;

	    @FXML
	    private Label activityMembership;

	    @FXML
	    private Label copeisNum;
	    
	    @FXML
	    private Label ActivityReportTitle;

	    @FXML
	    private VBox ActivityReport;
	    
	    
	   // ----------------------------------------/---------------/------------------------------------
	    
	    @FXML
	    private Label BorrowingReportTitle;

	    @FXML
	    private HBox dataOFBorrowingReport;

	    @FXML
	    private Label averageBookInDemand;

	    @FXML
	    private Label averageRegularBook;

	    @FXML
	    private Label medianBookInDemand;

	    @FXML
	    private Label medianRegularBook;

	    @FXML
	    private BarChart<?, ?> graph1;

	    @FXML
	    private BarChart<?, ?> graph2;
	    
	    @FXML
	    private ComboBox<String> month1;

	    @FXML
	    private ComboBox<String> year1;

	    @FXML
	    private Button ok1;
	    // ----------------------------------------/---------------/------------------------------------ 
	    @FXML
	    private Label LateBooksReportTitle;

	    @FXML
	    private HBox dataOFLateBooksReport;

	    @FXML
	    private Label avgOfNumLateReturningBook;

	    @FXML
	    private Label avgOfDurationOfLatness;

	    @FXML
	    private Label medianOfLateRutrningBook;

	    @FXML
	    private Label medianOfDurationOfLateness;

	    @FXML
	    private Label tableOfDate;
	    
	    @FXML
	    private ScrollPane report3;
	    
	    @FXML
	    private BarChart<?, ?> graph11;

	    @FXML
	    private BarChart<?, ?> graph22;
	    
	    
	    @FXML
	    private TableView<Book> booksTable;

	    @FXML
	    private TableColumn<Book,String> bookName;

	    @FXML
	    private TableColumn<Book,Integer> averageOfDuration;

	    @FXML
	    private TableColumn<Book,Integer> countOfLateness;
	    
	    @FXML
	    private ComboBox<String> month2;

	    @FXML
	    private ComboBox<String> year2;

	    @FXML
	    private Button ok2;
	    
	    @FXML
	    private Button ok3;
	    // ----------------------------------------/---------------/------------------------------------ 
	    

	    public ObservableList<Book> books=FXCollections.observableArrayList();
	    
	    public ReportsController() {
			super();
		}

		public ReportsController(ChatClient client) {
			super(client);
		}

		
		public ReportsResponse requestReport(int Request_Report) {
			
			ReportsRequest message = new ReportsRequest(Request_Report);
			return (ReportsResponse) client.sendMessage(message);
		}
		
        public ReportsResponse requestSpecificDateOfReport(int Request_Report,String month,String year) {
			
			ReportsRequest message = new ReportsRequest(Request_Report,month,year);
			return (ReportsResponse) client.sendMessage(message);
		}
		
		
		
		
		
		    
		    
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			
			ReportsController Reports = (ReportsController) Controllers.getInstance()
                    .getController(ControllerType.REPORTS_CONTROLLER);
			
			ObservableList<String> months=FXCollections.observableArrayList();
				months.add("01");
				months.add("02");
				months.add("03");
				months.add("04");
				months.add("05");
				months.add("06");
				months.add("07");
				months.add("08");
				months.add("09");
				months.add("10");
				months.add("11");
				months.add("12");
			
			ObservableList<String> years=FXCollections.observableArrayList();
			for(int i=2010;i<2020;i++)
			{
				years.add(""+i);
			}
			
			
			
			if(RequestReportsController.Request_Report==0)
			{
				month1.setVisible(true);
				year1.setVisible(true);
				ok1.setVisible(true);

				month1.setItems(months);
				year1.setItems(years);

                ReportsResponse resp=Reports.requestReport(RequestReportsController.Request_Report);
                ActivityReportTitle.setVisible(true);
                ActivityReport.setVisible(true);
                lockedMembership.setText(resp.getReport().get(0).toString());
                frozenMembership.setText(resp.getReport().get(1).toString());
                lateReturningBook.setText(resp.getReport().get(2).toString());
                activityMembership.setText(resp.getReport().get(3).toString());
                copeisNum.setText(resp.getReport().get(4).toString());
			}
			
			if(RequestReportsController.Request_Report==1)
			{
				ReportsResponse resp=Reports.requestReport(RequestReportsController.Request_Report);
				BorrowingReportTitle.setVisible(true);
				dataOFBorrowingReport.setVisible(true);
				graph1.setVisible(true);
				graph2.setVisible(true);
				month1.setVisible(true);
				year1.setVisible(true);


				month1.setItems(months);
				year1.setItems(years);
				
				ok3.setVisible(true);

				averageBookInDemand.setText(resp.getReport().get(0).toString());
				medianBookInDemand.setText(resp.getReport().get(1).toString());

				XYChart.Series set = new XYChart.Series();
				set.getData().add(new XYChart.Data("0-9",resp.getReport().get(2)));
				set.getData().add(new XYChart.Data("10-19",resp.getReport().get(3)));
				set.getData().add(new XYChart.Data("20-29",resp.getReport().get(4)));
				set.getData().add(new XYChart.Data("40-49",resp.getReport().get(6)));
				set.getData().add(new XYChart.Data("50-59",resp.getReport().get(7)));
				set.getData().add(new XYChart.Data("60-69",resp.getReport().get(8)));
				set.getData().add(new XYChart.Data("70-79",resp.getReport().get(9)));
				set.getData().add(new XYChart.Data("80-89",resp.getReport().get(10)));
				set.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(11)));
				
				
				graph2.getData().addAll(set);

				averageRegularBook.setText(resp.getReport().get(12).toString());
				medianRegularBook.setText(resp.getReport().get(13).toString());

				 XYChart.Series set2 = new XYChart.Series();
				set2.getData().add(new XYChart.Data("0-9",resp.getReport().get(14)));
				set2.getData().add(new XYChart.Data("10-19",resp.getReport().get(15)));
				set2.getData().add(new XYChart.Data("20-29",resp.getReport().get(16)));
				set2.getData().add(new XYChart.Data("30-39",resp.getReport().get(17)));
				set2.getData().add(new XYChart.Data("40-49",resp.getReport().get(18)));
				set2.getData().add(new XYChart.Data("50-59",resp.getReport().get(19)));
				set2.getData().add(new XYChart.Data("60-69",resp.getReport().get(20)));
				set2.getData().add(new XYChart.Data("70-79",resp.getReport().get(21)));
				set2.getData().add(new XYChart.Data("80-89",resp.getReport().get(22)));
				set2.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(23)));
						
				graph1.getData().addAll(set2);
				System.out.println("gggggggggggggggggggggggggggggg");

			}
					
			if(RequestReportsController.Request_Report==2)
			{
				ReportsResponse resp=Reports.requestReport(RequestReportsController.Request_Report);
				report3.setVisible(true);
				
				month2.setVisible(true);
				year2.setVisible(true);
				
				month2.setItems(months);
				year2.setItems(years);
				ok2.setVisible(true);
                System.out.println("222222222222222222222222222222222222"+resp.getReport().get(0));
				avgOfNumLateReturningBook.setText(resp.getReport().get(0).toString());
				medianOfLateRutrningBook.setText(resp.getReport().get(1).toString());
				
				XYChart.Series set = new XYChart.Series();
				set.getData().add(new XYChart.Data("0-9",resp.getReport().get(2)));
				set.getData().add(new XYChart.Data("10-19",resp.getReport().get(3)));
				set.getData().add(new XYChart.Data("20-29",resp.getReport().get(4)));
				set.getData().add(new XYChart.Data("40-49",resp.getReport().get(6)));
				set.getData().add(new XYChart.Data("50-59",resp.getReport().get(7)));
				set.getData().add(new XYChart.Data("60-69",resp.getReport().get(8)));
				set.getData().add(new XYChart.Data("70-79",resp.getReport().get(9)));
				set.getData().add(new XYChart.Data("80-89",resp.getReport().get(10)));
				set.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(11)));
				
				
				graph22.getData().addAll(set);
				
				avgOfDurationOfLatness.setText(resp.getReport().get(12).toString());
				medianOfDurationOfLateness.setText(resp.getReport().get(13).toString());
		
				 XYChart.Series set2 = new XYChart.Series();
				set2.getData().add(new XYChart.Data("0-9",resp.getReport().get(14)));
				set2.getData().add(new XYChart.Data("10-19",resp.getReport().get(15)));
				set2.getData().add(new XYChart.Data("20-29",resp.getReport().get(16)));
				set2.getData().add(new XYChart.Data("30-39",resp.getReport().get(17)));
				set2.getData().add(new XYChart.Data("40-49",resp.getReport().get(18)));
				set2.getData().add(new XYChart.Data("50-59",resp.getReport().get(19)));
				set2.getData().add(new XYChart.Data("60-69",resp.getReport().get(20)));
				set2.getData().add(new XYChart.Data("70-79",resp.getReport().get(21)));
				set2.getData().add(new XYChart.Data("80-89",resp.getReport().get(22)));
				set2.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(23)));
						
				graph11.getData().addAll(set2);
				
				for(Book book : resp.getBooks())
				{
					books.add(book);
				}
				
				bookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
				averageOfDuration.setCellValueFactory(new PropertyValueFactory<Book, Integer>("AvergeDurationLatness"));
				countOfLateness.setCellValueFactory(new PropertyValueFactory<Book, Integer>("countLatness"));
				
				booksTable.setItems(books);

				booksTable.refresh();
			}
		}
		
		 @FXML
		    void Showreport2(ActionEvent event) {
			  graph1.getData().clear();
			  graph2.getData().clear();
			 String month = month1.getValue();
				String year = year1.getValue();
				
				ReportsController Reports = (ReportsController) Controllers.getInstance()
	                    .getController(ControllerType.REPORTS_CONTROLLER);
				ReportsResponse resp=Reports.requestSpecificDateOfReport(4,month,year);
				if(!(resp.getReport()==null))
				{
				averageBookInDemand.setText(resp.getReport().get(0).toString());
				medianBookInDemand.setText(resp.getReport().get(1).toString());
				
				XYChart.Series set = new XYChart.Series();
				set.getData().add(new XYChart.Data("0-9",resp.getReport().get(2)));
				set.getData().add(new XYChart.Data("10-19",resp.getReport().get(3)));
				set.getData().add(new XYChart.Data("20-29",resp.getReport().get(4)));
				set.getData().add(new XYChart.Data("40-49",resp.getReport().get(6)));
				set.getData().add(new XYChart.Data("50-59",resp.getReport().get(7)));
				set.getData().add(new XYChart.Data("60-69",resp.getReport().get(8)));
				set.getData().add(new XYChart.Data("70-79",resp.getReport().get(9)));
				set.getData().add(new XYChart.Data("80-89",resp.getReport().get(10)));
				set.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(11)));
				
				
				graph2.getData().addAll(set);
				
				averageRegularBook.setText(resp.getReport().get(12).toString());
				medianRegularBook.setText(resp.getReport().get(13).toString());
		
				 XYChart.Series set2 = new XYChart.Series();
				set2.getData().add(new XYChart.Data("0-9",resp.getReport().get(14)));
				set2.getData().add(new XYChart.Data("10-19",resp.getReport().get(15)));
				set2.getData().add(new XYChart.Data("20-29",resp.getReport().get(16)));
				set2.getData().add(new XYChart.Data("30-39",resp.getReport().get(17)));
				set2.getData().add(new XYChart.Data("40-49",resp.getReport().get(18)));
				set2.getData().add(new XYChart.Data("50-59",resp.getReport().get(19)));
				set2.getData().add(new XYChart.Data("60-69",resp.getReport().get(20)));
				set2.getData().add(new XYChart.Data("70-79",resp.getReport().get(21)));
				set2.getData().add(new XYChart.Data("80-89",resp.getReport().get(22)));
				set2.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(23)));
						
				graph1.getData().addAll(set2);
				}else
					ShowMessagenotReports(resp.getText());
		    }

		    @FXML
		    void showReport1(ActionEvent event) {
		    	
		    	String month = month1.getValue();
				String year = year1.getValue();
				
				ReportsController Reports = (ReportsController) Controllers.getInstance()
	                    .getController(ControllerType.REPORTS_CONTROLLER);
				
					ReportsResponse resp=Reports.requestSpecificDateOfReport(3,month,year);
					if(!(resp.getReport()==null))
					{
		                lockedMembership.setText(resp.getReport().get(0).toString());
		                frozenMembership.setText(resp.getReport().get(1).toString());
		                lateReturningBook.setText(resp.getReport().get(2).toString());
		                activityMembership.setText(resp.getReport().get(3).toString());
		                copeisNum.setText(resp.getReport().get(4).toString());
					}else
						ShowMessagenotReports(resp.getText());

		    }
		    
		    @FXML
		    void ShowReport3(ActionEvent event) {
				graph11.getData().clear();
				graph22.getData().clear();
				booksTable.getItems().clear();
		    	 String month = month2.getValue();
				 String year = year2.getValue();
				 
				 ReportsController Reports = (ReportsController) Controllers.getInstance()
		                    .getController(ControllerType.REPORTS_CONTROLLER);
				 ReportsResponse resp=Reports.requestSpecificDateOfReport(5,month,year);
				 if(!(resp.getReport()==null))
					{
				 avgOfNumLateReturningBook.setText(resp.getReport().get(0).toString());
					medianOfLateRutrningBook.setText(resp.getReport().get(1).toString());
					
					XYChart.Series set = new XYChart.Series();
					set.getData().add(new XYChart.Data("0-9",resp.getReport().get(2)));
					set.getData().add(new XYChart.Data("10-19",resp.getReport().get(3)));
					set.getData().add(new XYChart.Data("20-29",resp.getReport().get(4)));
					set.getData().add(new XYChart.Data("40-49",resp.getReport().get(6)));
					set.getData().add(new XYChart.Data("50-59",resp.getReport().get(7)));
					set.getData().add(new XYChart.Data("60-69",resp.getReport().get(8)));
					set.getData().add(new XYChart.Data("70-79",resp.getReport().get(9)));
					set.getData().add(new XYChart.Data("80-89",resp.getReport().get(10)));
					set.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(11)));
					
					
					graph22.getData().addAll(set);
					
					avgOfDurationOfLatness.setText(resp.getReport().get(12).toString());
					medianOfDurationOfLateness.setText(resp.getReport().get(13).toString());
			
					 XYChart.Series set2 = new XYChart.Series();
					set2.getData().add(new XYChart.Data("0-9",resp.getReport().get(14)));
					set2.getData().add(new XYChart.Data("10-19",resp.getReport().get(15)));
					set2.getData().add(new XYChart.Data("20-29",resp.getReport().get(16)));
					set2.getData().add(new XYChart.Data("30-39",resp.getReport().get(17)));
					set2.getData().add(new XYChart.Data("40-49",resp.getReport().get(18)));
					set2.getData().add(new XYChart.Data("50-59",resp.getReport().get(19)));
					set2.getData().add(new XYChart.Data("60-69",resp.getReport().get(20)));
					set2.getData().add(new XYChart.Data("70-79",resp.getReport().get(21)));
					set2.getData().add(new XYChart.Data("80-89",resp.getReport().get(22)));
					set2.getData().add(new XYChart.Data("90 and Moore",resp.getReport().get(23)));
							
					graph11.getData().addAll(set2);
					
					for(Book book : resp.getBooks())
					{
						books.add(book);
					}
					
					bookName.setCellValueFactory(new PropertyValueFactory<Book, String>("BookName"));
					averageOfDuration.setCellValueFactory(new PropertyValueFactory<Book, Integer>("AvergeDurationLatness"));
					countOfLateness.setCellValueFactory(new PropertyValueFactory<Book, Integer>("countLatness"));
					
					booksTable.setItems(books);

					booksTable.refresh();
					}else
						ShowMessagenotReports(resp.getText());
		    }
		
		    public void ShowMessagenotReports(String msg) {
			    alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
				alert.setTitle("");
				alert.setHeaderText("");
				alert.show();
		    }
	    
	    

}
