/**
 * 
 */
package messages.handlers;

import java.sql.SQLException;
import java.text.ParseException;

import Server.Database;
import messages.Message;

/**
 * 
 * @author Monera
 *
 */
public abstract class AbstractRequestHandler {
	/**
	 * DB Connection
	 */
	protected Database dbCon;

	public AbstractRequestHandler(Database dbCon) {
		this.dbCon = dbCon;
	}

	public Database getDbCon() {
		return dbCon;
	}

	public void setDbCon(Database dbCon) {
		this.dbCon = dbCon;
	}

	/**
	 * Implement in every handler
	 * 
	 * @param msg
	 * @return
	 * @throws ParseException 
	 * @throws SQLException 
	 */
	abstract public Message handle(Message msg) throws SQLException, ParseException;

}
