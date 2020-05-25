package client.controller;

import client.ChatClient;

/**
 * 
 * @author Monera
 *
 */
public class ControllerFactory {
	/*
	 * 
	 */

	public static AbstractController create(ControllerType controllerType, ChatClient client) {
		switch (controllerType) {
		// TODO: Add for each new Request
		case LOGIN_CONTROLLER:
			return new LoginController(client);
//		case USER_SEARCH_BOOKS_CONTROLLER:
//			return new MainMenuController(client);
			
		case BOOKS_CONTROLLER:
			return new BooksController(client);
			
		case ADD_BOOK_CONTROLLER:
			return new AddBookController(client);
		case DELETE_BOOK_CONTROLLER:
			return new DeleteBookController(client);
			
		case ORDER_BOOK_CONTROLLER:
			return new ReaderHomeController(client);
			
		case RETURN_BOOK_CONTROLLER:
			return new ReturnBookController(client);
			
		case REGISTRATION_CONTROLER:
			
			return new RegistrationController(client);	
		case MY_LIBRARY_CARD_CONTROLLER:
			return new MyLibraryCardController(client);
			
		case READER_HOME_CONTROLLER:
			return new ReaderHomeController(client);

		case MANAGER_HOME_CONTROLLER:
			return new ManagerHomeController(client);
			
		case LIBRARIAN_HOME_CONTROLLER:
			return new LibrarianHomeController(client);
			
		case USER_HOME_CONTROLLER:
			return new MainMenuController(client);
		case BORROW_FORM_CONTROLLER:
			return new BorrowFormController(client);
	
		case READER_INFO_CONTROLLER:
			return new ReaderInfoController(client);
		case EMPLOYEE_INFO_CONTROLLER:
			return new EmployeeInfoController(client);
		case HISTORY_CONTROLLER:
			return new HistoryController(client);
			
		case SHOW_BORROWED_BOOKS_CONTROLLER:
			return new ShowBorrowedBooksController(client);
			
		case DEADLINE_EXTENSION:
			return new ShowBorrowedBooksController(client);
		 
		case REPORTS_CONTROLLER:
			return new ReportsController(client);	
			
		case EDIT_BOOK_CONTROLLER:
			return new EditBookController(client);
		case VIEW_TOC_CONTROLLER:
			return new ReaderHomeController(client);
			
		case BORROW_FREEZE_CONTROLLER:
			return new BorrowLateFreeze(client);
			
		case THREE_LATE_RETURNS_CONTROLLER:
			return new threeTimesLAteReturnsController(client);	
		default:
			System.out.println("ERROR: Invalid handler type");
			return null;
		}
	}

}
