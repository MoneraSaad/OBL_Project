package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import client.ChatClient;
import client.entities.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import protocol.request.GetClosestDeadlineRequest;
import protocol.request.ViewTOCRequest;
import protocol.response.BooksResponse;
import protocol.response.FreeSearchResponse;
import protocol.response.GetClosestDeadlineResponse;
import protocol.response.SearchBookByAuthorResponse;
import protocol.response.SearchBookByNameResponse;
import protocol.response.SearchBookBySubjectResponse;
import protocol.response.ViewTOCResponse;

/**
 * 
 * @author Monera MainMenuController This is an MainMenuController class that
 *         control all the request related laboratory
 */

public class MainMenuController extends AbstractController implements Initializable {

	Alert alert;
	ArrayList<String> al;
	ObservableList<String> list;
	public static Book book;
	@FXML
	private Button login;


	@FXML
	private ComboBox<String> comboBoxSearch;

	@FXML
	private TextField enterTerm;

	public static ArrayList<Book> myBook = new ArrayList<Book>();

	@FXML
	private AnchorPane MainInsideAnchorePane;
@FXML
    private BorderPane UserBorderPane;


	/**
	 * constructor for this class
	 */

	public MainMenuController() {
		super();
	}

	/**
	 * constructor for this class
	 * 
	 * @param client
	 */

	public MainMenuController(ChatClient client) {
		super(client);
	}

	
	/***
	 * This method sends a request message to the server to get the closest deadline of a specific book
	 * @author Monera 
	 * @param book
	 * @return
	 */
	public GetClosestDeadlineResponse getClosestDeadline(Book book) {
		GetClosestDeadlineRequest message = new GetClosestDeadlineRequest(book);
		return (GetClosestDeadlineResponse) client.sendMessage(message);
	}
	
	
	/**
	 * This method send request message by the client to the server to get the Books
	 * Details from the database and return an appropriate response message
	 *
	 * @param user
	 * @return appropriate HomeResponse
	 */
//	public HomeResponse checkUserPermission(User user) {/////////////
//		HomeRequest message = new HomeRequest(user);
//		//System.out.println((LoginResponse) client.sendMessage(message));//////
//		return (HomeResponse) client.sendMessage(message);
//	}// END
	

public ViewTOCResponse viewTOC(Book book) {/////////////
		
		ViewTOCRequest message = new ViewTOCRequest(book);
		
		return (ViewTOCResponse) client.sendMessage(message);
	}// END
	
	@FXML
	void SearchHandler(ActionEvent event) {
		
		if (!enterTerm.getText().isEmpty()) { 
			if (comboBoxSearch.getSelectionModel().getSelectedIndex() == 0) {
				UserBorderPane.getChildren().clear();
				String bookName = enterTerm.getText(); 

				BooksController booksController = (BooksController) Controllers.getInstance()
						.getController(ControllerType.BOOKS_CONTROLLER);

				SearchBookByNameResponse resp = booksController.searchBookByName(bookName);
				int i = 0;
				FlowPane show = new FlowPane();
				show.setPrefWidth(700);
				show.setPrefHeight(330);
				show.setPadding(new Insets(10, 10, 10, 10));
				show.setVgap(30);
				show.setHgap(15);
				show.setAlignment(Pos.CENTER);
				ScrollPane scroll = new ScrollPane();
				scroll.setPrefSize(700, 330);
				Pane ShowPane = new Pane();
				ShowPane.getChildren().add(show);
				show.setLayoutX(10);
				show.setLayoutY(10);
				scroll.setContent(ShowPane);
				scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
				scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
				//UserAnchorPane.getChildren().add(scroll);
				UserBorderPane.setCenter(scroll);
				
				while (i < resp.getRecievedBooks().size()) {
					
					VBox box = new VBox();
					AnchorPane pane2 = new AnchorPane();
					box.setPrefSize(236, 330);
					String string="";
					if (resp.getRecievedBooks().get(i).getAvailableCopies() != 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null) {
					string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
							+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
							+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
							+ "\nAvailable Copies: " + resp.getRecievedBooks().get(i).getAvailableCopies()
							+ "\nShelf Number: "
							+ resp.getRecievedBooks().get(i).getShelfNum());
					Label text = new Label();
					text.setPrefSize(205, 180);
					Pane textPane = new Pane();
					textPane.setPrefSize(205, 180);
					textPane.getChildren().add(text);
					text.setText(string);
					text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
					text.setWrapText(true);
					
					
					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					
					
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
				
					
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
				

					btn1.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							  view_table(btn1.getId());
						}
					});
					
					} else if ((resp.getRecievedBooks().get(i).getAvailableCopies()) == 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null ) {
						MainMenuController rhm = (MainMenuController) Controllers.getInstance()
								.getController(ControllerType.USER_HOME_CONTROLLER);
						
						GetClosestDeadlineResponse resppp = rhm.getClosestDeadline(resp.getRecievedBooks().get(i));
							
							string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
									+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
									+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
								+ "\n The Closest Deadline for the book is :" + resppp.getDeadline());
							Label text = new Label();
							text.setPrefSize(205, 180);
							Pane textPane = new Pane();
							textPane.setPrefSize(205, 180);
							textPane.getChildren().add(text);
							text.setText(string);
							text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
							text.setWrapText(true);
							
							Button btn1 = new Button("Table Of Contents");
							btn1.setLayoutX(79);
							btn1.setLayoutY(0);
							
							
							
							
							Pane BtnPane1 = new Pane();
							BtnPane1.setPrefWidth(236);

							BtnPane1.getChildren().add(btn1); 
							
							box.getChildren().addAll(textPane,BtnPane1);
							pane2.getChildren().add(box);
							show.getChildren().addAll(pane2);
						
							
							
							String btnID1 =Integer.toString(i);
							btn1.setId(btnID1);
						

							btn1.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									  view_table(btn1.getId());
								}
							});

							
						}
					 
					
					
					
						
				
					i++;

				}

			
			}else if (comboBoxSearch.getSelectionModel().getSelectedIndex() == 1) {
				UserBorderPane.getChildren().clear();
				String bookName = enterTerm.getText(); 

				BooksController booksController = (BooksController) Controllers.getInstance()
						.getController(ControllerType.BOOKS_CONTROLLER);

				SearchBookByAuthorResponse resp = booksController.searchBookByAuthor(bookName);
				int i = 0;
				FlowPane show = new FlowPane();
				show.setPrefWidth(700);
				show.setPrefHeight(330);
				show.setPadding(new Insets(10, 10, 10, 10));
				show.setVgap(30);
				show.setHgap(15);
				show.setAlignment(Pos.CENTER);
				ScrollPane scroll = new ScrollPane();
				scroll.setPrefSize(700, 330);
				Pane ShowPane = new Pane();
				ShowPane.getChildren().add(show);
				show.setLayoutX(10);
				show.setLayoutY(10);
				scroll.setContent(ShowPane);
				scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
				scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
				//UserAnchorPane.getChildren().add(scroll);
				UserBorderPane.setCenter(scroll);
				

				while (i < resp.getRecievedBooks().size()) {
					
					VBox box = new VBox();
					AnchorPane pane2 = new AnchorPane();
					box.setPrefSize(236, 330);
					String string="";
					
					if (resp.getRecievedBooks().get(i).getAvailableCopies() != 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null) {
					string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
							+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
							+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
							+ "\nAvailable Copies: " + resp.getRecievedBooks().get(i).getAvailableCopies()
							+ "\nShelf Number: "
							+ resp.getRecievedBooks().get(i).getShelfNum());
					
					
					Label text = new Label();
					text.setPrefSize(205, 180);
					Pane textPane = new Pane();
					textPane.setPrefSize(205, 180);
					textPane.getChildren().add(text);
					text.setText(string);
					text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
					text.setWrapText(true);
					
					
					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					
					
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
				
					
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
				

					btn1.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							  view_table(btn1.getId());
						}
					});
					
					} else if ((resp.getRecievedBooks().get(i).getAvailableCopies()) == 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null ) {
						MainMenuController rhm = (MainMenuController) Controllers.getInstance()
								.getController(ControllerType.USER_HOME_CONTROLLER);
						
						GetClosestDeadlineResponse resppp = rhm.getClosestDeadline(resp.getRecievedBooks().get(i));
							
							string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
									+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
									+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
								+ "\n The Closest Deadline for the book is :" + resppp.getDeadline());
							Label text = new Label();
							text.setPrefSize(205, 180);
							Pane textPane = new Pane();
							textPane.setPrefSize(205, 180);
							textPane.getChildren().add(text);
							text.setText(string);
							text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
							text.setWrapText(true);
							
							Button btn1 = new Button("Table Of Contents");
							btn1.setLayoutX(79);
							btn1.setLayoutY(0);
							
							
							
							
							Pane BtnPane1 = new Pane();
							BtnPane1.setPrefWidth(236);

							BtnPane1.getChildren().add(btn1); 
							
							box.getChildren().addAll(textPane,BtnPane1);
							pane2.getChildren().add(box);
							show.getChildren().addAll(pane2);
						
							
							
							String btnID1 =Integer.toString(i);
							btn1.setId(btnID1);
						

							btn1.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									  view_table(btn1.getId());
								}
							});

							
						}
					 
					
					
					
						
				
					i++;

				}
		
			} else if (comboBoxSearch.getSelectionModel().getSelectedIndex() == 2) {
				UserBorderPane.getChildren().clear();
				String bookName = enterTerm.getText(); 

				BooksController booksController = (BooksController) Controllers.getInstance()
						.getController(ControllerType.BOOKS_CONTROLLER);

				SearchBookBySubjectResponse resp = booksController.searchBookBySubject(bookName);
				int i = 0;
				FlowPane show = new FlowPane();
				show.setPrefWidth(700);
				show.setPrefHeight(330);
				show.setPadding(new Insets(10, 10, 10, 10));
				show.setVgap(30);
				show.setHgap(15);
				show.setAlignment(Pos.CENTER);
				ScrollPane scroll = new ScrollPane();
				scroll.setPrefSize(700, 330);
				Pane ShowPane = new Pane();
				ShowPane.getChildren().add(show);
				show.setLayoutX(10);
				show.setLayoutY(10);
				scroll.setContent(ShowPane);
				scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
				scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
				//UserAnchorPane.getChildren().add(scroll);
				UserBorderPane.setCenter(scroll);
			
				while (i < resp.getRecievedBooks().size()) {
					
					VBox box = new VBox();
					AnchorPane pane2 = new AnchorPane();
					box.setPrefSize(236, 330);
					String string="";
					if (resp.getRecievedBooks().get(i).getAvailableCopies() != 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null) {
					string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
							+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
							+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
							+ "\nAvailable Copies: " + resp.getRecievedBooks().get(i).getAvailableCopies()
							+ "\nShelf Number: "
							+ resp.getRecievedBooks().get(i).getShelfNum());
					Label text = new Label();
					text.setPrefSize(205, 180);
					Pane textPane = new Pane();
					textPane.setPrefSize(205, 180);
					textPane.getChildren().add(text);
					text.setText(string);
					text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
					text.setWrapText(true);
					
					
					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					
					
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
				
					
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
				

					btn1.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							  view_table(btn1.getId());
						}
					});
					
					} else if ((resp.getRecievedBooks().get(i).getAvailableCopies()) == 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null ) {
						MainMenuController rhm = (MainMenuController) Controllers.getInstance()
								.getController(ControllerType.USER_HOME_CONTROLLER);
						
						GetClosestDeadlineResponse resppp = rhm.getClosestDeadline(resp.getRecievedBooks().get(i));
							
							string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
									+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
									+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
								+ "\n The Closest Deadline for the book is :" + resppp.getDeadline());
							Label text = new Label();
							text.setPrefSize(205, 180);
							Pane textPane = new Pane();
							textPane.setPrefSize(205, 180);
							textPane.getChildren().add(text);
							text.setText(string);
							text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
							text.setWrapText(true);
							
							Button btn1 = new Button("Table Of Contents");
							btn1.setLayoutX(79);
							btn1.setLayoutY(0);
							
							
							
							
							Pane BtnPane1 = new Pane();
							BtnPane1.setPrefWidth(236);

							BtnPane1.getChildren().add(btn1); 
							
							box.getChildren().addAll(textPane,BtnPane1);
							pane2.getChildren().add(box);
							show.getChildren().addAll(pane2);
						
							
							
							String btnID1 =Integer.toString(i);
							btn1.setId(btnID1);
						

							btn1.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									  view_table(btn1.getId());
								}
							});

							
						}
					 
					
					
					
						
				
					i++;

				}
			
				
			}else if (comboBoxSearch.getSelectionModel().getSelectedIndex() == -1|| comboBoxSearch.getSelectionModel().getSelectedIndex() == 3) {
				UserBorderPane.getChildren().clear();
				String description = enterTerm.getText();

				BooksController booksController = (BooksController) Controllers.getInstance()
						.getController(ControllerType.BOOKS_CONTROLLER);

				FreeSearchResponse resp = booksController.freesearchBook(description);
				
				int i = 0;
				FlowPane show = new FlowPane();
				show.setPrefWidth(700);
				show.setPrefHeight(330);
				show.setPadding(new Insets(10, 10, 10, 10));
				show.setVgap(30);
				show.setHgap(15);
				show.setAlignment(Pos.CENTER);
				ScrollPane scroll = new ScrollPane();
				scroll.setPrefSize(700, 330);
				Pane ShowPane = new Pane();
				ShowPane.getChildren().add(show);
				show.setLayoutX(10);
				show.setLayoutY(10);
				scroll.setContent(ShowPane);
				scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
				scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
				//UserAnchorPane.getChildren().add(scroll);
				UserBorderPane.setCenter(scroll);
				

				while (i < resp.getRecievedBooks().size()) {
					
					VBox box = new VBox();
					AnchorPane pane2 = new AnchorPane();
					box.setPrefSize(236, 330);
					String string="";
					if (resp.getRecievedBooks().get(i).getAvailableCopies() != 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null) { 
					string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
							+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
							+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
							+ "\nDescription: " + resp.getRecievedBooks().get(i).getDescription()
							+ "\nAvailable Copies: " + resp.getRecievedBooks().get(i).getAvailableCopies()
							+ "\nShelf Number: "
							+ resp.getRecievedBooks().get(i).getShelfNum());
					Label text = new Label();
					text.setPrefSize(205, 180);
					Pane textPane = new Pane();
					textPane.setPrefSize(205, 180);
					textPane.getChildren().add(text);
					text.setText(string);
					text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
					text.setWrapText(true);
					
					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					
					
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
				
					
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
				

					btn1.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							  view_table(btn1.getId());
						}
					});
					
					} else if ((resp.getRecievedBooks().get(i).getAvailableCopies()) == 0 && resp.getRecievedBooks().get(i).getCatalogNum()!=null) {
						MainMenuController rhm = (MainMenuController) Controllers.getInstance()
								.getController(ControllerType.USER_HOME_CONTROLLER);
						
						GetClosestDeadlineResponse resppp = rhm.getClosestDeadline(resp.getRecievedBooks().get(i));
						string = new String("" + "Book Name: " + resp.getRecievedBooks().get(i).getBookName()
								+ "\nAuthor : " + resp.getRecievedBooks().get(i).getAuthor() 
								+ "\nSubject : " + resp.getRecievedBooks().get(i).getSubject()
							+ "\n The Closest Deadline for the book is :" + resppp.getDeadline());
						Label text = new Label();
						text.setPrefSize(205, 180);
						Pane textPane = new Pane();
						textPane.setPrefSize(205, 180);
						textPane.getChildren().add(text);
						text.setText(string);
						text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
						text.setWrapText(true);
						
						
						Button btn1 = new Button("Table Of Contents");
						btn1.setLayoutX(79);
						btn1.setLayoutY(0);
						
						
						
						
						Pane BtnPane1 = new Pane();
						BtnPane1.setPrefWidth(236);

						BtnPane1.getChildren().add(btn1); 
						
						box.getChildren().addAll(textPane,BtnPane1);
						pane2.getChildren().add(box);
						show.getChildren().addAll(pane2);
					
						
						
						String btnID1 =Integer.toString(i);
						btn1.setId(btnID1);
					

						btn1.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								  view_table(btn1.getId());
							}
						});
					}
				
					
				i++;
				}
		
		}
		}

	}
	void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/UserMain.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	void homeHandler(ActionEvent event) {
		//FXMLLoader fxmlLoader = new FXMLLoader();
		Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
		MainMenuController regForm = new MainMenuController();
		try {
			regForm.start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		comboBoxSearch();

		BooksController booksController = (BooksController) Controllers.getInstance()
				.getController(ControllerType.BOOKS_CONTROLLER);

		BooksResponse resp = booksController.getAllBooks();

		MainMenuController.myBook = resp.getBooks();
		
		loadBooks();
	}

	void start1(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/GUI/login.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
		}
	@FXML
	void singinHandler(ActionEvent event) {
			//FXMLLoader fxmlLoader = new FXMLLoader();
		Stage stage = ((Stage) ((Node) event.getSource()).getScene().getWindow());
		MainMenuController regForm = new MainMenuController();
		((Node) event.getSource()).getScene().getWindow().hide();
		try {
			regForm.start1(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	/**
	 * a method for setting the Search combobox
	 */
	public void comboBoxSearch() {
		al = new ArrayList<String>();
		// String str;
		al.add("Book Name");
		al.add("Author");
		al.add("Subject");
		al.add("Free text");

		list = FXCollections.observableArrayList(al);
		comboBoxSearch.setItems(list);

	}

	public void loadBooks() {

		int i = 0;
		FlowPane show = new FlowPane();
		show.setPrefWidth(700);
		show.setPrefHeight(330);
		show.setPadding(new Insets(10, 10, 10, 10));
		show.setVgap(30);
		show.setHgap(15);
		show.setAlignment(Pos.CENTER);
		ScrollPane scroll = new ScrollPane();
		scroll.setPrefSize(700, 330);
		Pane ShowPane = new Pane();
		ShowPane.getChildren().add(show);
		show.setLayoutX(10);
		show.setLayoutY(10);
		scroll.setContent(ShowPane);
		scroll.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scroll.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		//UserAnchorPane.getChildren().add(scroll);
UserBorderPane.setCenter(scroll);
		while (i < MainMenuController.myBook.size()) {
			VBox box = new VBox();
			AnchorPane pane2 = new AnchorPane();
			box.setPrefSize(236, 330);
			String string = new String("" + "Book Name: " + MainMenuController.myBook.get(i).getBookName()
					+ "\nAuthor Name: " + MainMenuController.myBook.get(i).getAuthor() + "\nSubject: "
					+ MainMenuController.myBook.get(i).getSubject() + "\nEdition Number: "
					+ MainMenuController.myBook.get(i).getEditionNum() + "\nCatatlog Number: "
					+ MainMenuController.myBook.get(i).getCatalogNum() + "\nPrinting Date: "
					+ MainMenuController.myBook.get(i).getPrintingDate() + "\nDescription: "
					+ MainMenuController.myBook.get(i).getDescription()

			);

			Label text = new Label();
            text.setTextFill(Color.web("black"));/////////////shams
			text.setPrefSize(205, 250);
			Pane textPane = new Pane();
			textPane.setPrefSize(205, 250);
			textPane.getChildren().add(text);
			text.setText(string);
			text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
			text.setWrapText(true);
			
			
			Button btn1 = new Button("Table Of Contents");
			btn1.setLayoutX(79);
			btn1.setLayoutY(0);
			
			
			
			
			Pane BtnPane1 = new Pane();
			BtnPane1.setPrefWidth(236);

			BtnPane1.getChildren().add(btn1); 
			
			box.getChildren().addAll(textPane,BtnPane1);
			pane2.getChildren().add(box);
			show.getChildren().addAll(pane2);
		
			
			
			String btnID1 =Integer.toString(i);
			btn1.setId(btnID1);
		

			btn1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					  view_table(btn1.getId());
				}
			});

			i++;
		}

	}
	
	public  void view_table(String btn ) {
		int BtnId = Integer.parseInt(btn);
		book= MainMenuController.myBook.get(BtnId);
		
		ReaderHomeController view = (ReaderHomeController) Controllers.getInstance().getController(ControllerType.VIEW_TOC_CONTROLLER);
		ViewTOCResponse response = view.viewTOC(book);
		//ShowMessageViewTOCRequest(response.getText());
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		comboBoxSearch();

		BooksController booksController = (BooksController) Controllers.getInstance()
				.getController(ControllerType.BOOKS_CONTROLLER);

		BooksResponse resp = booksController.getAllBooks();

		MainMenuController.myBook = resp.getBooks();
		
		loadBooks();
		

	}

}
