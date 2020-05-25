package messages.handlers;
import java.util.ArrayList;

import Server.Database;
import client.entities.Book;
import messages.Message;
import protocol.response.BooksResponse;

/**
 * @author Monera
 *
 */
public class BooksRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of BooksRequest we call the appropriate
	 * method from the database .
	 * After that we build BooksResponse message and return it.
	 * 
	 */
	public BooksRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) {
		try {
			ArrayList<Book> books = dbCon.getBooks();
			BooksResponse resp = new BooksResponse(books);
			return resp;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	
	}

}
