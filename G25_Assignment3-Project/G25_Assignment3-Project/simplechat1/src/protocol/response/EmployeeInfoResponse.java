/**
 * 
 */
package protocol.response;

import java.util.ArrayList;
import client.entities.User;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Shams
 *
 */
public class EmployeeInfoResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private ArrayList<User> Employee;

	public EmployeeInfoResponse(ArrayList<User> employee) {
		this.Employee = employee;
	}
	public ArrayList<User> getEmployee() {
		return Employee;
	}
	
	public void setEmployee(ArrayList<User> employee) {
		this.Employee = employee;
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
		return MessageType.EMPLOYEE_INFO_RESPONSE;
	}
}

