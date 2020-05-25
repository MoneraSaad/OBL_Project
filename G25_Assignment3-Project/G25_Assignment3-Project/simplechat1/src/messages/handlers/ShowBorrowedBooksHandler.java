



package messages.handlers;

import java.sql.SQLException;
import java.util.ArrayList;
import Server.Database;
import client.entities.Book;
import client.entities.HistoryIntity;
import client.entities.User;
import messages.Message;
import protocol.request.HistoryRequest;
import protocol.request.ShowBorrowedBooksRequest;
import protocol.response.HistoryResponse;
import protocol.response.ShowBorrowedBooksResponse;

/**
 * @author shams
 *
 */
public class ShowBorrowedBooksHandler extends AbstractRequestHandler {

	public ShowBorrowedBooksHandler(Database dbCon) {
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

			ArrayList<Book> list = dbCon.getBorrowedBooks(((ShowBorrowedBooksRequest) msg).getUser());

			ShowBorrowedBooksResponse resp = new ShowBorrowedBooksResponse(list);

			return resp;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
   }
}