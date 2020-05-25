package client.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientConnectToServer;
import client.entities.Book;
import client.entities.User;
import client.gui.History;
import client.gui.MainMenu;
import client.gui.MyLibraryCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import protocol.request.BooksRequest;
import protocol.request.GetClosestDeadlineRequest;
import protocol.request.LogoutRequest;
import protocol.request.OrderBookRequest;
import protocol.request.ViewTOCRequest;
import protocol.response.BooksResponse;
import protocol.response.FreeSearchResponse;
import protocol.response.GetClosestDeadlineResponse;
import protocol.response.LogoutResponse;
import protocol.response.OrderBookResponse;
import protocol.response.SearchBookByAuthorResponse;
import protocol.response.SearchBookByNameResponse;
import protocol.response.SearchBookBySubjectResponse;
import protocol.response.ViewTOCResponse;

/**
 * 
 * @author Monera ReaderHomeController This is an ReaderHomeController class
 *         that control all the request related laboratory
 */
public class ReaderHomeController extends AbstractController implements Initializable {

	Alert alert;
	ArrayList<String> al;
	ObservableList<String> list;
	public static Book book;
	@FXML
	private Label readerName;

	@FXML
	private Button login;

	@FXML
	private ComboBox<String> comboBoxMenu;

	@FXML
	private ComboBox<String> comboBoxSearch;

	@FXML
	private TextField enterTerm;

	@FXML
	private AnchorPane MainInsideAnchorePane;

	@FXML
	private FlowPane UserAnchorPane;

	@FXML
    private BorderPane ReaderBorderPane;

	/**
	 * constructor for this class
	 */

	public ReaderHomeController() {
		super();
	}

	/**
	 * constructor for this class
	 * 
	 * @param client
	 */
	public ReaderHomeController(ChatClient client) {
		super(client);
	} 


	
	/**
	 * This method send request message by the client to the server to log out and
	 * return appropriate response message
	 * 
	 * @author Monera
	 * @param user
	 * @return appropriate LogoutResponse
	 */
	public LogoutResponse logoutUser(User user) {
		LogoutRequest message = new LogoutRequest(user);
		return (LogoutResponse) client.sendMessage(message);
	}

	/**
	 * * This method send request message by the client to the server to order a
	 * book the database returns an appropriate response message
	 * 
	 * @author Karam
	 * @param user
	 * @param book
	 * @return
	 */
	public OrderBookResponse makeOrder(User user, Book book) {/////////////

		OrderBookRequest message = new OrderBookRequest(user, book);

		return (OrderBookResponse) client.sendMessage(message);
	}// END

	/**
	 * This method send request message by the client to the server to get all books
	 * Details in from the database and return an appropriate response message
	 *
	 * @param Monera
	 * @return appropriate BooksResponse
	 */
	public BooksResponse getAllBooks() {/////////////
		BooksRequest message = new BooksRequest();
		
		return (BooksResponse) client.sendMessage(message);
	}// END

public ViewTOCResponse viewTOC(Book book) {/////////////
		
		ViewTOCRequest message = new ViewTOCRequest(book);
		
		return (ViewTOCResponse) client.sendMessage(message);
	}// END
	
	
	
	@FXML
	void SearchHandler(ActionEvent event) {
		
		if (!enterTerm.getText().isEmpty()) { 
			if (comboBoxSearch.getSelectionModel().getSelectedIndex() == 0) {
				ReaderBorderPane.getChildren().clear();
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
				ReaderBorderPane.setCenter(scroll);
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
					Button btn = new Button("Order Book");
					btn.setLayoutX(79);
					btn.setLayoutY(0);

					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					Pane BtnPane = new Pane();
					BtnPane.setPrefWidth(236);

					BtnPane.getChildren().add(btn);
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane, BtnPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
					 String btnID = Integer.toString(i);
					
					btn.setId(btnID);
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
					
					
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							orderBook_book(btn.getId());
						}
					});

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
							Button btn = new Button("Order Book");
							btn.setLayoutX(79);
							btn.setLayoutY(0);
							Button btn1 = new Button("Table Of Contents");
							btn1.setLayoutX(79);
							btn1.setLayoutY(0);
							
							Pane BtnPane = new Pane();
							BtnPane.setPrefWidth(236);

							BtnPane.getChildren().add(btn);
							
							Pane BtnPane1 = new Pane();
							BtnPane1.setPrefWidth(236);

							BtnPane1.getChildren().add(btn1); 
							
							box.getChildren().addAll(textPane, BtnPane,BtnPane1);
							pane2.getChildren().add(box);
							show.getChildren().addAll(pane2);
							 String btnID = Integer.toString(i);
							
							btn.setId(btnID);
							
							String btnID1 =Integer.toString(i);
							btn1.setId(btnID1);
							
							
							btn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									orderBook_book(btn.getId());
								}
							});

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
				ReaderBorderPane.getChildren().clear();
				String author = enterTerm.getText();

				BooksController booksController = (BooksController) Controllers.getInstance()
						.getController(ControllerType.BOOKS_CONTROLLER);

				SearchBookByAuthorResponse resp = booksController.searchBookByAuthor(author);
				
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
				ReaderBorderPane.setCenter(scroll);
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
					Button btn = new Button("Order Book");
					btn.setLayoutX(79);
					btn.setLayoutY(0);
					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					Pane BtnPane = new Pane();
					BtnPane.setPrefWidth(236);

					BtnPane.getChildren().add(btn);
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane, BtnPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
					 String btnID = Integer.toString(i);
					
					btn.setId(btnID);
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
					
					
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							orderBook_book(btn.getId());
						}
					});

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
							Button btn = new Button("Order Book");
							btn.setLayoutX(79);
							btn.setLayoutY(0);
							Button btn1 = new Button("Table Of Contents");
							btn1.setLayoutX(79);
							btn1.setLayoutY(0);
							
							Pane BtnPane = new Pane();
							BtnPane.setPrefWidth(236);

							BtnPane.getChildren().add(btn);
							
							Pane BtnPane1 = new Pane();
							BtnPane1.setPrefWidth(236);

							BtnPane1.getChildren().add(btn1); 
							
							box.getChildren().addAll(textPane, BtnPane,BtnPane1);
							pane2.getChildren().add(box);
							show.getChildren().addAll(pane2);
							 String btnID = Integer.toString(i);
							
							btn.setId(btnID);
							
							String btnID1 =Integer.toString(i);
							btn1.setId(btnID1);
							
							
							btn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									orderBook_book(btn.getId());
								}
							});

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
				ReaderBorderPane.getChildren().clear();
				String Subject = enterTerm.getText();

				BooksController booksController = (BooksController) Controllers.getInstance()
						.getController(ControllerType.BOOKS_CONTROLLER);

				SearchBookBySubjectResponse resp = booksController.searchBookBySubject(Subject);
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
				ReaderBorderPane.setCenter(scroll);
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
					Button btn = new Button("Order Book");
					btn.setLayoutX(79);
					btn.setLayoutY(0);
					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					Pane BtnPane = new Pane();
					BtnPane.setPrefWidth(236);

					BtnPane.getChildren().add(btn);
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane, BtnPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
					 String btnID = Integer.toString(i);
					
					btn.setId(btnID);
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
					
					
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							orderBook_book(btn.getId());
						}
					});

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
							Button btn = new Button("Order Book");
							btn.setLayoutX(79);
							btn.setLayoutY(0);
							Button btn1 = new Button("Table Of Contents");
							btn1.setLayoutX(79);
							btn1.setLayoutY(0);
							
							Pane BtnPane = new Pane();
							BtnPane.setPrefWidth(236);

							BtnPane.getChildren().add(btn);
							
							Pane BtnPane1 = new Pane();
							BtnPane1.setPrefWidth(236);

							BtnPane1.getChildren().add(btn1); 
							
							box.getChildren().addAll(textPane, BtnPane,BtnPane1);
							pane2.getChildren().add(box);
							show.getChildren().addAll(pane2);
							 String btnID = Integer.toString(i);
							
							btn.setId(btnID);
							
							String btnID1 =Integer.toString(i);
							btn1.setId(btnID1);
							
							
							btn.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									orderBook_book(btn.getId());
								}
							});

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
				ReaderBorderPane.getChildren().clear();
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
				ReaderBorderPane.setCenter(scroll);
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
					Button btn = new Button("Order Book");
					btn.setLayoutX(79);
					btn.setLayoutY(0);
					Button btn1 = new Button("Table Of Contents");
					btn1.setLayoutX(79);
					btn1.setLayoutY(0);
					
					Pane BtnPane = new Pane();
					BtnPane.setPrefWidth(236);

					BtnPane.getChildren().add(btn);
					
					Pane BtnPane1 = new Pane();
					BtnPane1.setPrefWidth(236);

					BtnPane1.getChildren().add(btn1); 
					
					box.getChildren().addAll(textPane, BtnPane,BtnPane1);
					pane2.getChildren().add(box);
					show.getChildren().addAll(pane2);
					 String btnID = Integer.toString(i);
					
					btn.setId(btnID);
					
					String btnID1 =Integer.toString(i);
					btn1.setId(btnID1);
					
					
					btn.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							orderBook_book(btn.getId());
						}
					});

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
						Button btn = new Button("Order Book");
						btn.setLayoutX(79);
						btn.setLayoutY(0);
						Button btn1 = new Button("Table Of Contents");
						btn1.setLayoutX(79);
						btn1.setLayoutY(0);
						
						Pane BtnPane = new Pane();
						BtnPane.setPrefWidth(236);

						BtnPane.getChildren().add(btn);
						
						Pane BtnPane1 = new Pane();
						BtnPane1.setPrefWidth(236);

						BtnPane1.getChildren().add(btn1); 
						
						box.getChildren().addAll(textPane, BtnPane,BtnPane1);
						pane2.getChildren().add(box);
						show.getChildren().addAll(pane2);
						 String btnID = Integer.toString(i);
						
						btn.setId(btnID);
						
						String btnID1 =Integer.toString(i);
						btn1.setId(btnID1);
						
						
						btn.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								orderBook_book(btn.getId());
							}
						});

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



	@FXML
	void homeHandler(ActionEvent event) {

		ReaderBorderPane.getChildren().clear();

		loadBooks();
	}

	@FXML
	void MenuOnAction(ActionEvent event) {
		if (comboBoxMenu.getSelectionModel().getSelectedIndex() == 0) {

			MyLibraryCard.getInstance(); 

		}
		if (comboBoxMenu.getSelectionModel().getSelectedIndex() == 1) {
			History.getInstance();
		}
		if (comboBoxMenu.getSelectionModel().getSelectedIndex() == 2) {
			// Mark as logged out in the DB
			ReaderHomeController rhc = (ReaderHomeController) Controllers.getInstance()
					.getController(ControllerType.READER_HOME_CONTROLLER);
			LogoutResponse resp = rhc.logoutUser(ClientConnectToServer.currUser);
			if (resp.getText().equals("OK")) {
				ClientConnectToServer.currUser = null;
				((Node) event.getSource()).getScene().getWindow().hide();
				MainMenu.getInstance();
			}

		}
	}

	/**
	 * a method for setting the Menu combobox
	 */
	public void comboBoxMenu() {
		al = new ArrayList<String>();
		// String str;
		al.add("My Libray Card");
		al.add("Action History");
		al.add("sign out");

		list = FXCollections.observableArrayList(al);
		comboBoxMenu.setItems(list);

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

	/**
	 * 
	 * a method that loads all the books in the front page
	 * 
	 * @author Monera
	 */
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
		ReaderBorderPane.setCenter(scroll);
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
			text.setPrefSize(205, 250);
			Pane textPane = new Pane();
			textPane.setPrefSize(205, 250);
			textPane.getChildren().add(text);
			text.setText(string);
			text.setFont(Font.font("Josefin Sans", FontWeight.BOLD, 16));
			text.setWrapText(true);
			Button btn = new Button("Order Book");
			btn.setLayoutX(79);
			btn.setLayoutY(0);

			Button btn1 = new Button("Table Of Contents");
			btn1.setLayoutX(79);
			btn1.setLayoutY(0);
			
			Pane BtnPane = new Pane();
			BtnPane.setPrefWidth(236);

			BtnPane.getChildren().add(btn);
			
			Pane BtnPane1 = new Pane();
			BtnPane1.setPrefWidth(236);

			BtnPane1.getChildren().add(btn1); 
			
			box.getChildren().addAll(textPane, BtnPane,BtnPane1);
			pane2.getChildren().add(box);
			show.getChildren().addAll(pane2);
			 String btnID = Integer.toString(i);
			
			btn.setId(btnID);
			
			String btnID1 =Integer.toString(i);
			btn1.setId(btnID1);
			
			
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					orderBook_book(btn.getId());
				}
			});

			btn1.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					  view_table(btn1.getId());
				}
			});

			i++;
		}

	}

	/**
	 * @author Karam
	 * @param msg
	 */
	public void ShowMessageOrderBookRequest(String msg) {
		alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
		alert.setTitle("Order book failure");
		// alert.setHeaderText("Re-try again");
		alert.show();
	}

	/**
	 * @author Monera
	 * @param btn
	 */
	private void orderBook_book(String btn) {
		int BtnID = Integer.parseInt(btn);
		book = MainMenuController.myBook.get(BtnID);
		
		ReaderHomeController order = (ReaderHomeController) Controllers.getInstance()
				.getController(ControllerType.ORDER_BOOK_CONTROLLER);
		OrderBookResponse response = order.makeOrder(ClientConnectToServer.currUser, book);
		ShowMessageOrderBookRequest(response.getText());
	}



	public  void view_table(String btn1 ) {
		int BtnId = Integer.parseInt(btn1);
		book= MainMenuController.myBook.get(BtnId);
		
		ReaderHomeController view = (ReaderHomeController) Controllers.getInstance().getController(ControllerType.VIEW_TOC_CONTROLLER);
		ViewTOCResponse response = view.viewTOC(book);
		//ShowMessageViewTOCRequest(response.getText());
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		comboBoxMenu();
		comboBoxSearch();
	
		readerName.setText(ClientConnectToServer.currUser.getName());

		BooksController booksController = (BooksController) Controllers.getInstance()
				.getController(ControllerType.BOOKS_CONTROLLER);

		BooksResponse resp = booksController.getAllBooks();

		MainMenuController.myBook = resp.getBooks();
		
		loadBooks();
	}
}
