// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import java.io.*;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import messages.Message;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Monera
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************
	/**
	 * await response flag
	 */
	private boolean awaitResponse = false;
	/**
	 * response object
	 */
	private Object response;
	
	//private String usernameToDisplay;
	//private String status;

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host     The server to connect to.
	 * @param port     The port number to connect on.
	 * @param clientUI The interface type variable.
	 */
	public ChatClient() throws IOException {
		super(); // Call the superclass constructor
	}

	public ChatClient(String host, int port) throws IOException {
		super(host, port); // Call the superclass constructor
	}

	/**
	 * Connect to server
	 * 
	 * @param host
	 * @param port
	 * @return
	 */
	public boolean connect(String host, int port) {
		setHost(host);
		setPort(port);
		// here we create the controllers map and every time we need a specific
		// controller we create it
		try {
			openConnection();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * Send Message to server
	 * 
	 * @param msg
	 * @return
	 */
	public Message sendMessage(Message msg) {
		try {
			awaitResponse = true;
			sendToServer(msg);
			// wait for response
			while (awaitResponse) {
				try { 
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return (Message) response;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ERROR: Couldnt not send message to server");
			return null;
		}
	}

	
	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg The message from the server.
	 */

	public void handleMessageFromServer(Object msg) {

		awaitResponse = false;
		response = msg;
		
		System.out.println(msg);//////////
	}

	/*public String getUsernameToDisplay() {
		return usernameToDisplay;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}*/

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message The message from the UI.
	 */
//	public void handleMessageFromClientUI(Object message) {
//		try {
//			sendToServer(message);
//		} catch (IOException e) {
//			System.out.println("Could not send message to server.  Terminating client.");
//			quit();
//		}
//	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}
}
//End of ChatClient class
