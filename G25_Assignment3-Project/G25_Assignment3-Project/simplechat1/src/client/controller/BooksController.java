package client.controller;

import client.ChatClient;
import protocol.request.BooksRequest;
import protocol.request.FreeSearchRequest;
import protocol.request.SearchBookByAuthorRequest;
import protocol.request.SearchBookByNameRequest;
import protocol.request.SearchBookBySubjectRequest;
import protocol.response.BooksResponse;
import protocol.response.FreeSearchResponse;
import protocol.response.SearchBookByAuthorResponse;
import protocol.response.SearchBookByNameResponse;
import protocol.response.SearchBookBySubjectResponse;

/**
 * 
 * @author Monera
 *
 */
public class BooksController extends AbstractController{
	/**
	 * constructor for this class
	 */

	public BooksController() {
		super();
	}

	/**
	 * constructor for this class
	 * @param client
	 */
	public BooksController(ChatClient client) {
		super(client);
	}
	
	
	/**
	 * This method send request message by the client to the server to search the by book name
	 * and to get it's Details from the database and return an appropriate response message
	 *
	 * @param bookName
	 * @return appropriate BookResponse
	 */
	public SearchBookByNameResponse searchBookByName(String bookName) {
		SearchBookByNameRequest message = new SearchBookByNameRequest(bookName);
		return (SearchBookByNameResponse) client.sendMessage(message);
	}// END
	
	/**
	 * This method send request message by the client to the server to search the by Author name
	 * and to get the book Details from the database and return an appropriate response message
	 *
	 * @param author
	 * @return appropriate BookResponse
	 */
	public SearchBookByAuthorResponse searchBookByAuthor(String author) {
		SearchBookByAuthorRequest message = new SearchBookByAuthorRequest(author);
		return (SearchBookByAuthorResponse) client.sendMessage(message);
	}// END
	
	
	/**
	 * This method send request message by the client to the server to search the by Subject
	 * and to get the book Details from the database and return an appropriate response message
	 *
	 * @param subject
	 * @return appropriate BookResponse
	 */
	public SearchBookBySubjectResponse searchBookBySubject(String subject) {
		SearchBookBySubjectRequest message = new SearchBookBySubjectRequest(subject);
		return (SearchBookBySubjectResponse) client.sendMessage(message);
	}// END
	
	/**
	 * This method send request message by the client to the server to free search
	 * and to get the book Details from the database and return an appropriate response message
	 *
	 * @param subject
	 * @return appropriate BookResponse
	 */
	public FreeSearchResponse freesearchBook(String description) {
		 FreeSearchRequest message = new FreeSearchRequest(description);
		return (FreeSearchResponse) client.sendMessage(message);
	}// END
	
	
	/**
	 * This method send request message by the client to the server to get all books
	 * Details in from the database and return an appropriate response message
	 *
	 * @return appropriate BooksResponse
	 */
	public BooksResponse getAllBooks() {/////////////
		BooksRequest message = new BooksRequest();
		return (BooksResponse) client.sendMessage(message);
	}// END
}
