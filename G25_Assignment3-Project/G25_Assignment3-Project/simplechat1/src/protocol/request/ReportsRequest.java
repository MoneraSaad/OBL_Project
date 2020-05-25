package protocol.request;

import messages.Message;
import messages.MessageType;

public class ReportsRequest implements Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7228862891624675294L;
	/**
	 * 
	 */
	private int RR;
	private String Month;
	private String Year;

	public ReportsRequest(int RR) {
		this.RR = RR;
	}

	public ReportsRequest(int request_Report, String month, String year) {
		this.RR=request_Report;
		this.Month = month;
	    this.Year = year;
	}

	public String getMonth() {
		return Month;
	}

	public void setMonth(String month) {
		Month = month;
	}

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public int getRR() {
		return RR;
	}

	public void setRR(int RR) {
		this.RR = RR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.Message#getMessageType()
	 */
	@Override
	public MessageType getMessageType() {
		return MessageType.REPORTS_REQUEST;
	}

}

