package client.entities;

import java.io.Serializable;

public class HistoryIntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String bookName;
	private String action;
	private String actionDate;
	
	public HistoryIntity()
	{
		
	}
	
	public HistoryIntity(String bookName,String action,String actionDate)
	{
		this.bookName=bookName;
		this.action=action;
		this.actionDate=actionDate;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getActionDate() {
		return actionDate;
	}
	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}
	

}
