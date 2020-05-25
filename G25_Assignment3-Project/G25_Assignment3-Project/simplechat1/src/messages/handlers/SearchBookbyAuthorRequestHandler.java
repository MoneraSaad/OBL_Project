

package messages.handlers;

import messages.Message;
import protocol.request.SearchBookByAuthorRequest;
import protocol.request.SearchBookByNameRequest;
import protocol.response.LoginResponse;
import protocol.response.SearchBookByAuthorResponse;
import protocol.response.SearchBookByNameResponse;

import java.sql.SQLException;
import java.util.ArrayList;

import Server.Database;
import client.entities.Book;

/**
 * @author Monera
 *
 */
public class SearchBookbyAuthorRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of SearchBookbyAuthorRequest we call the appropriate
	 * method from the database . After that we build SearchBookbyAuthorResponse message
	 * and return it.
	 * 
	 */
	public SearchBookbyAuthorRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) throws SQLException {
		ArrayList<Book> recievedBooks = dbCon.searchBookByAuthor(((SearchBookByAuthorRequest)msg).getAuthor());
		
		SearchBookByAuthorResponse response = new SearchBookByAuthorResponse(recievedBooks);
		return response;
	

}
}