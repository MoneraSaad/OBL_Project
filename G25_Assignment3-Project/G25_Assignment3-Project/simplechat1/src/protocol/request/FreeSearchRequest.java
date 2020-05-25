
package protocol.request;

import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */

public class FreeSearchRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String description;

	public FreeSearchRequest(String description) {
		super();
		this.description = description;
	}

	public String getdescription() {
		return description;
	}

	public void setdescription(String description) {
		this.description = description;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.FREE_SEARCH_REQUEST;
	}

}