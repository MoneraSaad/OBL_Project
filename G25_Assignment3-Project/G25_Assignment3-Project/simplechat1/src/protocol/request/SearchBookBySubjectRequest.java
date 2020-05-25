
package protocol.request;

import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */

public class SearchBookBySubjectRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String subject;

	public SearchBookBySubjectRequest(String subject) {
		super();
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.SEARCH_BOOK_BY_SUBJECT_REQUEST;
	}

}