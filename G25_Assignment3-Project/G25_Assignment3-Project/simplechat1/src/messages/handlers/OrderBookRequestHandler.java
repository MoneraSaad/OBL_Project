
package messages.handlers;

import java.sql.SQLException;

import messages.Message;
import protocol.request.OrderBookRequest;
import protocol.response.LoginResponse;
import protocol.response.OrderBookResponse;
import Server.Database;
import client.entities.Book;
import client.entities.User;

/**
 * @author Karam
 *
 */
public class OrderBookRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of OrderBookRequestRequest we call the appropriate method
	 * from the database .
	 * 
	 */
	public OrderBookRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg)  {

	try 
	{
	User user=((OrderBookRequest) msg).getUser();
	Book book=((OrderBookRequest) msg).getBook();
	String returnedMsg=dbCon.canOrderThisBook(book);
	if (user.getStatusMembership().equals("Active")) {
	if(returnedMsg.equals("ok")) {
	if(dbCon.checkInOrdersTable(user,book)==true) {//---
	dbCon.updateOrdersForBookAndUserInDataBase( user,book );
	String date =dbCon.addToOrderHistoryHistory( ((OrderBookRequest) msg).getUser(),((OrderBookRequest) msg).getBook());
	return new OrderBookResponse("Book ordered successfully, the closest returning date for this book is "+ date,((OrderBookRequest) msg).getUser(),((OrderBookRequest) msg).getBook());
	}
	else////////
	{
	return new OrderBookResponse("You have already ordered this book",((OrderBookRequest) msg).getUser(),((OrderBookRequest) msg).getBook());

	}///////////
	}//////
	else {
		return new OrderBookResponse(returnedMsg,((OrderBookRequest) msg).getUser(),((OrderBookRequest) msg).getBook());

	}
	} 
	else {
		return new OrderBookResponse("Your membership isnt active , cant order the book",((OrderBookRequest) msg).getUser(),((OrderBookRequest) msg).getBook());


	}
	} catch (SQLException e) {
	e.printStackTrace();
	return LoginResponse.ERROR();
	}

}
}