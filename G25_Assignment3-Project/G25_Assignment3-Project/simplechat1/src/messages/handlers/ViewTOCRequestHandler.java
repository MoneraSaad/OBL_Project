package messages.handlers;

import java.sql.SQLException;

import Server.Database;
import messages.Message;
import protocol.request.ViewTOCRequest;
import protocol.response.LoginResponse;
import protocol.response.ViewTOCResponse;

public class ViewTOCRequestHandler extends AbstractRequestHandler {

	public ViewTOCRequestHandler(Database dbCon) {
		super(dbCon);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see messages.RequestHandler#handle(messages.Message)
	 */
	@Override
	public Message handle(Message msg) throws SQLException  {
		
		if((dbCon.viewPDFfile(((ViewTOCRequest) msg).getBook())) != null)
		{
			return new ViewTOCResponse(dbCon.viewPDFfile(((ViewTOCRequest) msg).getBook()));
		}
		
		return new ViewTOCResponse("Table of content unavailable");
			
		
	}
}
