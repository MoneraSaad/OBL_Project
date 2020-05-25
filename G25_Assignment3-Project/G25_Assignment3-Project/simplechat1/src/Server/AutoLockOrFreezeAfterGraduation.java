
package Server;

import java.sql.PreparedStatement;
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

public class AutoLockOrFreezeAfterGraduation extends TimerTask {
	/***
	 * 
	 */

	private Database dbCon;

	public static int index = 0;

	/***
	 * 
	 * @param dbCon
	 */
	public AutoLockOrFreezeAfterGraduation(Database dbCon) {
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
					"SELECT * FROM library_students.graduation ");
 
			Statement stmt2 = dbCon.con.createStatement();
			Statement stmt3 = dbCon.con.createStatement();
			PreparedStatement pstmt;
			PreparedStatement pstmtt;
			/**
			 * if date is now (or after), check the time too, if condition true then update
			 * order status to 1
			 */ 
			while (rs.next()) {
				String ID = "";
				
				ID=rs.getString("StudentID");
				
					ResultSet rs2 = stmt2.executeQuery(
							"SELECT * FROM library_students.borrowedcopies where LenderToID='"+ ID+"'"+"AND BorrowStatus=1");
					
					if(rs2.next()) {
						pstmt = dbCon.con.prepareStatement("UPDATE library_students.student SET StatusMembership='Frozen',Freeze='true' where StudentID=?");
						 pstmt.setString(1, ID);
						 pstmt.executeUpdate();
						 
					}
					else {
						pstmtt = dbCon.con.prepareStatement("UPDATE library_students.student SET StatusMembership='Locked',isLock='true'  where StudentID=?");
						 pstmtt.setString(1, ID);
						 pstmtt.executeUpdate();
					}
				}
				
			


		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
