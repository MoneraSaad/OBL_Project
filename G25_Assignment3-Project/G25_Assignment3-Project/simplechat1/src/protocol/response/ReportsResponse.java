package protocol.response;

import java.util.ArrayList;

import client.entities.Book;
import client.entities.User;
import messages.Message;
import messages.MessageType;

/**
 * 
 * @author Shams
 *
 */
public class ReportsResponse implements Message {

	private static final long serialVersionUID = -7973601750797747916L;
	private String text;
	private ArrayList<Integer> report;
	private ArrayList<Book> books;

	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.REPORTS_RESPONSE;
	}

	/**
	 * response message : This is about Login
	 * <p>
	 * This message includes appropriate String text response .
	 * 
	 * @param appropriate
	 *            response text
	 */
	public ReportsResponse(ArrayList<Integer> report1) {
		this.report = report1;
	}
	
	public ReportsResponse(ArrayList<Integer> report1,ArrayList<Book> books) {
		this.report = report1;
		this.books = books;
	}
		
	public ReportsResponse(String text) {
		this.text = text;
	}
	
	public ArrayList<Integer> getReport() {
		return report;
	}
	
	public void setReport(ArrayList<Integer> report) {
		this.report = report;
	}

	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
	}
	
	public static ReportsResponse not_exist_Report_in_this_month() {
		return new ReportsResponse("No Reports for requested month.");
	}
}

