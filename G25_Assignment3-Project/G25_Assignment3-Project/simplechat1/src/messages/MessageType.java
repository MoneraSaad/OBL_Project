package messages;

/**
 * @author Monera
 *
 * This enum specifies the type of message includes the requests and the responses messages
 */
public enum MessageType {
	LOGIN_REQUEST,
	LOGIN_RESPONSE,
	
	LOGOUT_REQUEST,
	LOGOUT_RESPONSE,
	
	HOME_REQUEST,
	HOME_RESPONSE,
	
	SIGNIN_REQUEST,
	SINGIN_RESPONSE,
	
	BOOKS_REQUEST,
	BOOKS_RESPONSE,
	
	ADD_BOOK_REQUEST,//Rania
	ADD_BOOK_RESPONSE,//Rania
	
	EDIT_BOOK_REQUEST,//Rania
	EDIT_BOOK_RESPONSE,//Rania
	
	DELETE_BOOK_REQUEST,//Rania
	DELETE_BOOK_RESPONSE,//Rania
	
	
	SEARCH_BOOK_BY_NAME_REQUEST,
	SEARCH_BOOK_BY_NAME_RESPONSE,
	
	SEARCH_BOOK_BY_AUTHOR_REQUEST,
	SEARCH_BOOK_BY_AUTHOR_RESPONSE,
	
	SEARCH_BOOK_BY_SUBJECT_REQUEST,
	SEARCH_BOOK_BY_SUBJECT_RESPONSE,
	
	FREE_SEARCH_REQUEST,
	FREE_SEARCH_RESPONSE,

	ORDER_BOOK_REQUEST,//KARAM
	ORDER_BOOK_RESPONSE,//KARAM
	
	RETURN_BOOK_RESPONSE,//shams
	RETURN_BOOK_REQUEST,//shams
	
	REGISTRATION_REQUEST,//shams
	REGISTRATION_RESPONSE,//shams
	
	BORROW_FORM_REQUEST,//sari
	BORROW_FORM_RESPONE,//sari
	
	DEADLINE_EXTENSION_REQUEST,//sari
	DEADLINE_EXTENSION_RESPONSE,//sari
	
	MY_LIBRARY_CARD_REQUEST,//Karam
	MY_LIBRARY_CARD_RESPONSE,//Karam
	
	GET_CLOSEST_DEADLINE_REQUEST,
	GET_CLOSEST_DEADLINE_RESPONSE,
	
    READER_INFO_REQUEST,//shams
    READER_INFO_RESPONSE,//shams
    
    EMPLOYEE_INFO_REQUEST,//shams
    EMPLOYEE_INFO_RESPONSE,//shams
	
    HISTORY_REQUEST,
	HISTORY_RESPONSE,
	
	
	LIBRARIAN_DEADLINE_EXTENSION_REQUEST,
	LIBRARIAN_DEADLINE_EXTENSION_RESPONSE,
	
	SHOW_BORROWED_BOOKS_REQUEST,
	SHOW_BORROWED_BOOKS_RESPONSE,
	
	LIBRARIAN_EMAIL_REQUEST,
	LIBRARIAN_EMAIL_RESPONSE,
	
	REPORTS_REQUEST,//SHAMS&KAram
	REPORTS_RESPONSE,//SHAMS&KARAM
	
	BORROW_FREEZE_REQUEST,
	BORROW_FREEZE_RESPONSE,
	
	LOCK_REQUEST,
	LOCK_RESPONSE,
	
	VIEW_TOC_REQUEST,
	VIEW_TOC_RESPONSE;
	
}