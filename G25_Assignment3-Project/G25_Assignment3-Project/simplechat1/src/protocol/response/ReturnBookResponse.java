/**
 * 
 */
package protocol.response;

import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Shams
 *
 */
public class ReturnBookResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;

	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.RETURN_BOOK_RESPONSE;
	}

	/**
	 * response message : This is about Return Book
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	public ReturnBookResponse(String text) {
		this.text = text;
	}

	/**
	 * This is a static method
	 * 
	 * @return Return Successfully response
	 */
	public static ReturnBookResponse OK() {
		return new ReturnBookResponse("Returning Successfull!");
	}

	/**
	 * This is a static method
	 * 
	 * @return Book not found response
	 */
	public static ReturnBookResponse NO_Barcode() {
		return new ReturnBookResponse("Book not found!/Not approved yet by the system manager");
	}
	
	public static ReturnBookResponse isReturned() {
		return new ReturnBookResponse(" The Book has already been returned");
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
