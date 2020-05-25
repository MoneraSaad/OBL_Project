package protocol.request;

import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */
public class BooksRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BooksRequest() {
		super();

	}

	@Override
	public MessageType getMessageType() {
		return MessageType.BOOKS_REQUEST;
	}

}
