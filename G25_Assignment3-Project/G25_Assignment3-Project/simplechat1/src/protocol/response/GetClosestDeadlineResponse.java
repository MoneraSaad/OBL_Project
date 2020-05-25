
package protocol.response;

import java.util.ArrayList;

import client.entities.Book;
import client.entities.User;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Monera
 *
 */
public class GetClosestDeadlineResponse implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * response message : This is about the get the closest deadline
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate response text
	 */
	private String deadline;

	public GetClosestDeadlineResponse(String deadline) {
		super();
		this.deadline = deadline;
	} 

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Override
	public MessageType getMessageType() {
		return MessageType.GET_CLOSEST_DEADLINE_RESPONSE;
	}

}
