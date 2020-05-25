
	package messages.handlers;

	import java.sql.SQLException;
	import java.util.ArrayList;
	import Server.Database;
	import client.entities.User;
	import messages.Message;
import protocol.response.LibrarianEmailResponse;

	/**
	 * @author Monera
	 *
	 */
	public class LibrarianEmailRequestHandler extends AbstractRequestHandler {

		public LibrarianEmailRequestHandler(Database dbCon) {
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
				ArrayList<String> list = dbCon.getLibrarianEmail();
				LibrarianEmailResponse resp = new LibrarianEmailResponse(list);
				return resp;
				
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	   }
	}