package protocol.response;

import messages.Message;
import messages.MessageType;

import java.io.File;

import client.entities.Book;
import client.entities.User;

/**
 * 
 * @author Rania
 *
 */

public class ViewTOCResponse implements Message {

	private static final long serialVersionUID = 5838654890934623730L;
	private String text;
	private Book book;
	private File file;

	public ViewTOCResponse(String text)
	{
		this.text=text;
	}
	


	@Override
	public MessageType getMessageType() {
		// TODO Auto-generated method stub
		return MessageType.VIEW_TOC_RESPONSE;
	}
 
	
	public  ViewTOCResponse(String text, Book book) {
		this.book = book;
		this.text = text;
	}


	public ViewTOCResponse(File file) {
		// TODO Auto-generated constructor stub
		this.file = file;
	}



	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
