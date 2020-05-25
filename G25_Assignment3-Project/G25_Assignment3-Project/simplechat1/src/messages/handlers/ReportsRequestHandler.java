package messages.handlers;

import java.sql.SQLException;
import java.util.ArrayList;

import messages.Message;
import protocol.request.RegistrationRequest;
import protocol.request.ReportsRequest;
import protocol.response.ReaderInfoResponse;
import protocol.response.RegistrationResponse;
import protocol.response.ReportsResponse;
import Server.Database;
import client.entities.Book;
import client.entities.User;

/**
 * @author Mickey
 *
 */
public class ReportsRequestHandler extends AbstractRequestHandler {

	/**
	 * If the message request kind of LoginRequest we call the appropriate
	 * method from the database .
	 * <P>
	 * <isUser()> check if the user ID exist .
	 * <P>
	 * <checkPassword()> check if the user password is correct .
	 * <P>
	 * <isLoggedIn()> check if the user is logged in .
	 * <P>
	 * After that we build LoginResponse message and return it.
	 * 
	 */
	public ReportsRequestHandler(Database dbCon) {
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
			
			if((((ReportsRequest)msg).getRR())==0)
			{
				ArrayList<Integer> list = dbCon.getActivityReport();
				ReportsResponse resp = new ReportsResponse(list);
				return resp;

			}
			if((((ReportsRequest)msg).getRR())==1)
			{
				ArrayList<Integer> list = dbCon.getBorrowingReport();
				ReportsResponse resp = new ReportsResponse(list);
				return resp;
				
			}
			
			if((((ReportsRequest)msg).getRR())==2)
			{
				ArrayList<Integer> list = dbCon.getLateReturningBookgReport();
				ArrayList<Book> books = dbCon.getLateBooks();
				ReportsResponse resp = new ReportsResponse(list,books);
				return resp;
			}
			//------------------------------
			if((((ReportsRequest)msg).getRR())==3)
			{
				String date = ""+((ReportsRequest)msg).getMonth()+"."+((ReportsRequest)msg).getYear();
				ArrayList<Integer> list = dbCon.getActivityReport(date);
				if(list == null)
					return  ReportsResponse.not_exist_Report_in_this_month();
				ReportsResponse resp = new ReportsResponse(list);
				return resp;
			}
			if((((ReportsRequest)msg).getRR())==4)
			{
				String date = ""+((ReportsRequest)msg).getMonth()+"."+((ReportsRequest)msg).getYear();
				ArrayList<Integer> list = dbCon.getBorrowingReport(date);
				System.out.println();
				if(list == null)
					return  ReportsResponse.not_exist_Report_in_this_month();
				ReportsResponse resp = new ReportsResponse(list);
				return resp;
				
			}
			
			if((((ReportsRequest)msg).getRR())==5)
			{
				String date = ""+((ReportsRequest)msg).getMonth()+"."+((ReportsRequest)msg).getYear();
				ArrayList<Integer> list = dbCon.getLateReturningBookgReport(date);
				if(list == null)
					return  ReportsResponse.not_exist_Report_in_this_month();
				ArrayList<Book> books = dbCon.getLateBooks(date);
				ReportsResponse resp = new ReportsResponse(list,books);
				return resp;
			}
			
			
			
         return null;
		} catch (Exception e) {
			e.printStackTrace();
			return RegistrationResponse.user_Exist();
		}
	}
}

