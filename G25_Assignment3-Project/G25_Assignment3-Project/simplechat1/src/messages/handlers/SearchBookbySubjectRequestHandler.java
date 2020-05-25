
package messages.handlers;

import messages.Message;
import protocol.request.SearchBookByAuthorRequest;
import protocol.request.SearchBookByNameRequest;
import protocol.request.SearchBookBySubjectRequest;
import protocol.response.SearchBookByAuthorResponse;
import protocol.response.SearchBookByNameResponse;
import protocol.response.SearchBookBySubjectResponse;

import java.util.ArrayList;

import Server.Database;
import client.entities.Book;

/**
 * @author Monera
 *
 */
public class SearchBookbySubjectRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of SearchBookbySubjectRequest we call the appropriate
	 * method from the database . After that we build SearchBookbySubjectResponse message
	 * and return it.
	 * 
	 */
	public SearchBookbySubjectRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		ArrayList<Book> recievedBooks = dbCon.searchBookBySubject(((SearchBookBySubjectRequest)msg).getSubject());
		SearchBookBySubjectResponse response = new SearchBookBySubjectResponse(recievedBooks);
		return response;
	}
}