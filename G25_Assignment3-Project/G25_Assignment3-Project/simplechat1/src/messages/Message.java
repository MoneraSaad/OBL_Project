package messages;

import java.io.Serializable;

/**
 * 
 * @author Monera
 *
 */
public interface Message extends Serializable {

	/**
	 * Return Message Type
	 * 
	 * @return
	 */
	public MessageType getMessageType();
}
