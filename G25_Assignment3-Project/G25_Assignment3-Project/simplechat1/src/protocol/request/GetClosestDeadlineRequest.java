
package protocol.request;

import client.entities.Book;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */
public class GetClosestDeadlineRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Book book;

	public GetClosestDeadlineRequest(Book book) {
		super();
		this.book = book;

	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.GET_CLOSEST_DEADLINE_REQUEST;
	}

}
