

package protocol.response;

import java.util.ArrayList;

import com.mysql.jdbc.Blob;

import client.entities.HistoryIntity;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Shams
 *
 */
public class HistoryResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private ArrayList<HistoryIntity> history;

	public HistoryResponse(ArrayList<HistoryIntity> history) {
		this.history = history;
	}
	public ArrayList<HistoryIntity> getHistory() {
		return history;
	}
	
	public void setHistory(ArrayList<HistoryIntity> history) {
		this.history = history;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * response message : This is about Return Book
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.HISTORY_RESPONSE;
	}
}

