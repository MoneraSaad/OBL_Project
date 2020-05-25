
package protocol.request;

import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */

public class SearchBookByAuthorRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String author;

	public SearchBookByAuthorRequest(String author) {
		super();
		this.author = author;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SEARCH_BOOK_BY_AUTHOR_REQUEST;
	}

}