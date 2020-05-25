package messages.handlers;

import messages.Message;
import protocol.request.SearchBookByNameRequest;
import protocol.response.SearchBookByNameResponse;

import java.util.ArrayList;

import Server.Database;
import client.entities.Book;

/**
 * @author Monera
 *
 */
public class SearchBookbyNameRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of UserSearchBookRequest we call the appropriate
	 * method from the database . After that we build UserSearchBookResponse message
	 * and return it.
	 * 
	 */
	public SearchBookbyNameRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		ArrayList<Book> recievedBooks = dbCon.searchBookByName(((SearchBookByNameRequest)msg).getBookName());
		SearchBookByNameResponse response = new SearchBookByNameResponse(recievedBooks);
		return response;
	}
}
