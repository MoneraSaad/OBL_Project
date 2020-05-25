/**
 * 
 */
package messages.handlers;

import Server.Database;
import messages.MessageType;

/**
 * 
 * @author Monera
 */
public class HandlerFactory {

	/**
	 * Factor for different handlers, and according the msgType parameter we
	 * determine witch handler that we want.
	 * 
	 * @param msgType
	 *            that describe the message request.
	 * @param dbCon
	 * @return message kind of response 
	 */
	public static AbstractRequestHandler create(MessageType msgType, Database dbCon) {
		switch (msgType) {
		// TODO: Add for each new Request
		case LOGIN_REQUEST:
			return new LoginRequestHandler(dbCon);
		case LOGOUT_REQUEST:
			return new LogoutRequestHandler(dbCon);
		case HOME_REQUEST:
			return new HomeRequestHandler(dbCon);
		case SEARCH_BOOK_BY_NAME_REQUEST:
			return new SearchBookbyNameRequestHandler(dbCon);
		case SEARCH_BOOK_BY_AUTHOR_REQUEST:
			return new SearchBookbyAuthorRequestHandler(dbCon);
		case SEARCH_BOOK_BY_SUBJECT_REQUEST:
			return new SearchBookbySubjectRequestHandler(dbCon);
		case FREE_SEARCH_REQUEST:
			return new FreeSearchRequestHandler(dbCon);
		case BOOKS_REQUEST:
			return new BooksRequestHandler(dbCon);
		case ADD_BOOK_REQUEST:
			return new AddBookRequestHandler(dbCon);	
		case DELETE_BOOK_REQUEST:
			return new DeleteBookRequestHandler(dbCon);	
		case ORDER_BOOK_REQUEST:
			return new OrderBookRequestHandler(dbCon);
		case RETURN_BOOK_REQUEST:
			return new ReturnBookRequestHandler(dbCon);
		case REGISTRATION_REQUEST:
			return new RegistrationRequestHandler(dbCon);
		case BORROW_FORM_REQUEST:
			return new BorrowFormRequestHandler(dbCon);
		case READER_INFO_REQUEST:
			return new ReaderInfoRequestHandler(dbCon);
		case EMPLOYEE_INFO_REQUEST:
			return new EmployeeInfoRequestHandler(dbCon);
			
		case EDIT_BOOK_REQUEST:
			return new EditBookRequestHandler(dbCon);
		case MY_LIBRARY_CARD_REQUEST:
			return new MyLibraryCardHandler(dbCon);
			
		case GET_CLOSEST_DEADLINE_REQUEST:
			return new GetClosestDeadlineRequestHandler(dbCon);
			
		case HISTORY_REQUEST:
			return new HistoryHandler(dbCon);
			
		case DEADLINE_EXTENSION_REQUEST:
			return new ExtendDeadlineRequestHandler(dbCon);
		case SHOW_BORROWED_BOOKS_REQUEST:
			return new ShowBorrowedBooksHandler(dbCon);
		case LIBRARIAN_EMAIL_REQUEST:
			return new LibrarianEmailRequestHandler(dbCon);
			
		case REPORTS_REQUEST:
			return new ReportsRequestHandler(dbCon);
		
		case VIEW_TOC_REQUEST:
			return new ViewTOCRequestHandler(dbCon);
			
		case BORROW_FREEZE_REQUEST:
			return new BorrowFreezeRequestHandler(dbCon);
		case LOCK_REQUEST:
			return new LockRequestHandler(dbCon);	
		default:
			System.out.println("ERROR: Invalid handler type");
			return null;
		}
	}
}
