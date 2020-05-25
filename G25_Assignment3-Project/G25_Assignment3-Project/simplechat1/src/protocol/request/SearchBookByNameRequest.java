package protocol.request;

import messages.Message;
import messages.MessageType;

public class SearchBookByNameRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String bookName;

	public SearchBookByNameRequest(String bookName) {
		super();
		this.bookName = bookName;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SEARCH_BOOK_BY_NAME_REQUEST;
	}

}
