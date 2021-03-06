
package protocol.response;

import java.util.ArrayList;

import client.entities.Book;
import messages.Message;
import messages.MessageType;
/**
 * 
 * @author Monera
 *
 */

public class FreeSearchResponse implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Book> recievedBooks;

	/**
	 * response message : This is about SearchBook
	 * <p>
	 * This message includes appropriate AarrayList response .
	 * 
	 * @param appropriate response text
	 */
	public FreeSearchResponse(ArrayList<Book> books) {
		this.recievedBooks = books;
	}

	/**
	 * This is a static method
	 * 
	 * @return book found response
	 */

	@Override
	public MessageType getMessageType() {
		return MessageType.FREE_SEARCH_RESPONSE;
	}

	public ArrayList<Book> getRecievedBooks() {
		return recievedBooks;
	}

	public void setRecievedBooks(ArrayList<Book> recievedBooks) {
		this.recievedBooks = recievedBooks;
	}

}
