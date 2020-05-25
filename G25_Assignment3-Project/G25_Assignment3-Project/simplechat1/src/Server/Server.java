package Server;

import java.util.ArrayList;
import java.util.Timer;

import Server.AutoReturnBookReminder;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import messages.Message;
import javafx.scene.control.ButtonType;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * 
 * @author Monera
 *
 */
public class Server extends AbstractServer {
	ServerUI serverUI;
	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;

	private Database db;

	private Handlers handlers;

	public Server(int port) {
		super(port);
	}

	public Server(ServerUI serverUI) {
		super();
		this.serverUI = serverUI;
	}

	/**
	 * Connect to DB
	 * <p>
	 * this method is used to connect to the DB
	 * <p>
	 * it gets the info from the ClientUI and connect to the db
	 * 
	 * @param host
	 * @param port
	 * @param user
	 * @param pass
	 * @return
	 */

	public boolean connectToDB(String schema, String user, String pass) {
		// db = new Database();

		db = Database.getInstance();

		// Handlers handlers = new Handlers(db);

		try {
			db.connect(schema, user, pass);
			handlers = new Handlers(db);

			/**
			 * Timer task for Return Book Reminder, check every 1 day 
			 */
			AutoReturnBookReminder orders = new AutoReturnBookReminder(Database.getInstance());
			Timer orderTimer = new Timer();
			orderTimer.scheduleAtFixedRate(orders, 0, 24 * 60 * 60 * 1000);// every day
			
			/**
			 * Timer task for checking id the order is ready to pick , check every hour  
			 */
			AutoOrderCheck check = new AutoOrderCheck(Database.getInstance());
			Timer t = new Timer();
			t.scheduleAtFixedRate(check, 0, 60 * 60 * 1000);// every hour
			
			/**
			 * Timer task for saving the order for two days and canceling it after 2 days if the 
			 * reader hasn't picked it up , check every hour  
			 */
			AutoSaveOrderForTwoDays saveOrder = new AutoSaveOrderForTwoDays(Database.getInstance());
			Timer tm = new Timer();
			tm.scheduleAtFixedRate(saveOrder, 0, 60  *60 * 60 * 1000);// every hour
			
			
			/**
			 * Timer task for updating the status of the book if it's in demand or not, check every hour  
			 */
			AutoUpdateInDemandField updateIndemand = new AutoUpdateInDemandField(Database.getInstance());
			Timer tt = new Timer();
			tt.scheduleAtFixedRate(updateIndemand, 0, 60  *60 * 60 * 1000);// every hour
			
			/**
			 * Timer task for checking if the Student has graduated, check every day
			 */
			AutoLockOrFreezeAfterGraduation graduate = new AutoLockOrFreezeAfterGraduation(Database.getInstance());
			Timer ttt = new Timer();
			ttt.scheduleAtFixedRate(graduate, 0, 24 * 60 * 60 * 1000);// every day
			
			/**
			 * Timer task for checking if the Student who graduated returned the books, check every day
			 */
			AutoLockForGraduatedAfterReturning graduateReturn = new AutoLockForGraduatedAfterReturning(Database.getInstance());
			Timer mer = new Timer();
			mer.scheduleAtFixedRate(graduateReturn, 0, 24 * 60 * 60 * 1000);// every day
			
			/**
			 * Timer task for checking if the Student isn't returning the borrowed book, check every day
			 */
			AutoBorrowCheck borrow = new AutoBorrowCheck(Database.getInstance());
			Timer rr = new Timer();
			rr.scheduleAtFixedRate(borrow, 0, 24 * 60 * 60 * 1000);// every day
			
			/**
			 * Timer task for checking if the Student has three times late returns, check every day
			 */
			AutoThreeTimesLateReturn late = new AutoThreeTimesLateReturn(Database.getInstance());
			Timer mmmmm = new Timer();
			mmmmm.scheduleAtFixedRate(late, 0, 24 * 60 * 60 * 1000);// every day

			return true;
		} catch (Exception e) {
			e.printStackTrace(); 
			return false;
		}

	}

	public Database getDbCon() {
		return db;
	}

	/**
	 * Handler incoming requests from client
	 * <p>
	 * we use this method in an abstract way
	 * <p>
	 * we get the message from the client and
	 * <p>
	 * call the suitable handler depending on
	 * <p>
	 * the MessageType
	 * <p>
	 * 
	 */
	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client) {
		try {
//			if (msg instanceof ArrayList) {
//			
//				ArrayList<String> list = db.getDetails(((ArrayList<String>)msg));
//				client.sendToClient(list);
//			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/ServerGUI.fxml"));
			Parent calcRoot = loader.load();

			ServerUI controller = loader.getController();
			Message message = (Message) msg;
			// serverUI.display("Recieved: " +
			// message.getMessageType().toString());
			System.out.println("Recieved: " + message.getMessageType().toString());///////////
			Message response = handlers.getHandler(message.getMessageType()).handle(message);

			// Send response to client
			client.sendToClient(response);

			/*
			 * if (msg instanceof ArrayList) { String status =
			 * ((ArrayList<String>)msg).get(0); String username =
			 * ((ArrayList<String>)msg).get(1); String operation
			 * =((ArrayList<String>)msg).get(2); String Freeze =
			 * ((ArrayList<String>)msg).get(3); db.updateStatusOfStudent(username,
			 * status,operation,Freeze); }
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {

		// serverUI.display("Server listening for connections on port " +
		// getPort());
		// ServerUI.display();

	}

	public void setServerUI(ServerUI serverUI) {
		this.serverUI = serverUI;
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {

		// serverUI.display("Server has stopped listening for connections.");
	}

	/**
	 * This method is responsible for the creation of the server instance (there is
	 * no UI in this phase).
	 *
	 * @param args[0] The port number to listen on. Defaults to 5555 if no argument
	 *        is entered.
	 */
	public void connectTOServer() {
		Alert alert;
		int port = 0; // Port to listen on

		port = DEFAULT_PORT; // Set port to 5555
		Server sv = new Server(port);
		setServerUI(this.serverUI);
		try {

			sv.listen(); // Start listening for connections
			String msg = "Server listening for connections on port " + port + " ---- SQL connection succeeded";

			alert = new Alert(AlertType.INFORMATION, msg, ButtonType.OK);
			alert.setHeaderText("Running the server");
			alert.setTitle("Information");
			alert.show();

		} catch (Exception ex) {
			alert = new Alert(AlertType.WARNING, "ERROR - Could not listen for clients!", ButtonType.OK);

			alert.setHeaderText("Server is already running");
			alert.setTitle("Warning");
			alert.show();
			System.out.println("ERROR - Could not listen for clients!");
		}

	}

}
