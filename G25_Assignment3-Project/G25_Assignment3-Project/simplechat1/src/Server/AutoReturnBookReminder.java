package Server;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimerTask;

/**
 * this class is about reminding the reader who borrowed a book one day before the deadline 
 * that he should return the book until tomorrow .this class has a 
 * timer task that checks every day if the local is one day before the deadline for 
 * the borrow if so it automatically sends an email to the borrower reminding him 
 * that he should return the book by tomorrow.
 */

public class AutoReturnBookReminder extends TimerTask {
	/***
	 * 
	 */

	private Database dbCon;

	public static int index = 0;

	/***
	 * 
	 * @param dbCon
	 */
	public AutoReturnBookReminder(Database dbCon) {
		super();
		this.dbCon = dbCon;
	}
 
	@Override
	public void run() {
		try {
			Statement stmt = dbCon.con.createStatement();
			// ResultSet rs = stmt.executeQuery("SELECT * FROM
			// library_students.borrowedcopies");
			ResultSet rs = stmt.executeQuery(
					"SELECT B.BookName, O.Deadline, O.LenderToID FROM library_students.BorrowedCopies AS O join library_students.copies AS B using (Barcode)");
 
			Statement stmt2 = dbCon.con.createStatement();
			Statement stmt3 = dbCon.con.createStatement();
			/**
			 * if date is now (or after), check the time too, if condition true then update
			 * order status to 1
			 */ 
			while (rs.next()) {
				System.out.println(rs.getString("LenderToID"));
				LocalDate dateNow = LocalDate.now();
				java.util.Date dateUtil = rs.getTimestamp("Deadline");
				LocalDate dateInDB = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				LocalDate oneDayBefore = dateInDB.minusDays(1);

				if (dateNow.equals(oneDayBefore)) {
					String LenderToID = "";
					String StudentEmail = "";

					LenderToID = rs.getString("LenderToID");

					ResultSet rs3 = stmt3.executeQuery(
							"SELECT Email FROM library_students.student WHERE StudentID= '" + LenderToID + "'");

					if (rs3.next())
						StudentEmail = rs3.getString("Email");

					// send email of a reminder to the user
					SendEmail emailSend = new SendEmail();
					emailSend.setTextBody("This is an automatic message from Ort Braude Library.\n"
							+ "We are reminding you that you have to return the book: " + rs.getString("BookName")
							+ " until tomorrow.\n\n" + "Please do not reply to this message.\n");
					emailSend.setToo(StudentEmail);
					emailSend.SendAction();

				}
			}


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
