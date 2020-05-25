
package Server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimerTask;

/**
 * this class is about checking if someone returned a book that has orders there's a
 * timer task that checks every hour if there's any returns for an ordered book then 
 * this class automatically sends an email to the first one who ordered the book 
 * telling him that his ordered book is available in the the library and that he should come
 * and pick it up within two days.
 */

public class AutoOrderCheck extends TimerTask {
	/***
	 * 
	 */

	private Database dbCon;

	public static int index = 0;

	/***
	 * 
	 * @param dbCon
	 */
	public AutoOrderCheck(Database dbCon) {
		super();
		this.dbCon = dbCon;
	}
 
	@Override
	public void run() {
		try {
			Statement stmt = dbCon.con.createStatement();
			Statement stmt2 = dbCon.con.createStatement();
			Statement stmt3 = dbCon.con.createStatement();
			Statement stmt4 =dbCon.con.createStatement();
			Statement stmt5 =dbCon.con.createStatement();
			// ResultSet rs = stmt.executeQuery("SELECT * FROM
			// library_students.borrowedcopies");
			ResultSet rs = stmt.executeQuery(
					"SELECT CatalogNum From library_students.returning_book WHERE Status=1");
			
			
			
			/**
			 * if date is now (or after), check the time too, if condition true then update
			 * order status to 1
			 */
			while (rs.next()) {
				String CatalogNummm=rs.getString("CatalogNum");
				ResultSet rs5=stmt5.executeQuery("SELECT OrderDate from library_students.orderhistory");
				if (rs5.next()) {
				ResultSet rs4=stmt4.executeQuery("SELECT min(OrderDate) from library_students.orderhistory where CatalogNum ='"+CatalogNummm+"'");
				if(rs4.next()) {
				String order=rs4.getString("min(OrderDate)");
				ResultSet rs3 = stmt3.executeQuery("SELECT OrderDate, StudentID,BookName from library_students.orderhistory where CatalogNum ='"+CatalogNummm+"'"
				+"AND OrderDate='"+order+"'"+"AND OrderStatus=1");	
					
				if(rs3.next()) {
					String LenderToID = "";
					String StudentEmail = "";
					 LenderToID = rs3.getString("StudentID");
					 System.out.println("lenderssssss "+LenderToID);
					ResultSet rs2 = stmt2.executeQuery(
							"SELECT Email FROM library_students.student WHERE StudentID= '" + LenderToID + "'");

					if (rs2.next()) {
						StudentEmail = rs2.getString("Email");

					// send email of a reminder to the user
					SendEmail emailSend = new SendEmail();
					emailSend.setTextBody("This is an automatic message from Ort Braude Library.\n"
							+ "The book : " + rs3.getString("BookName")
							+ " is in the library you have two days left to pick it up .\n\n" + "Please do not reply to this message.\n");
					emailSend.setToo(StudentEmail);
					emailSend.SendAction();
					}
				}
			}
			
			}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

