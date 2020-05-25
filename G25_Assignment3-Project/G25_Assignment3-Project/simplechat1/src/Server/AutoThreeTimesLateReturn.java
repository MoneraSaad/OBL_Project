
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

public class AutoThreeTimesLateReturn extends TimerTask {
	/***
	 * 
	 */

	private Database dbCon;

	public static int index = 0;

	/***
	 * 
	 * @param dbCon
	 */
	public AutoThreeTimesLateReturn(Database dbCon) {
		super();
		this.dbCon = dbCon;
	}
 
	@Override
	public void run() {
		try {
			Statement stmt = dbCon.con.createStatement();
			Statement stmt1 = dbCon.con.createStatement();
			// ResultSet rs = stmt.executeQuery("SELECT * FROM
			// library_students.borrowedcopies");
			ResultSet rs = stmt.executeQuery(
					"SELECT StudentID,LateReturningNum FROM library_students.student WHERE LateReturningNum=3");
			
			ResultSet rs1=null;
			PreparedStatement pstmt=null;
			/**
			 * if date is now (or after), check the time too, if condition true then update
			 * order status to 1
			 */ 
			while(rs.next()) {
				
					String StudentID = "";
					
					String Status="Three Times Late Returns";
					
					StudentID = rs.getString("StudentID");
					  rs1 = stmt1.executeQuery("SELECT StudentID,Status FROM library_students.threetimes_latereturns WHERE StudentID=" + "'" + StudentID + "'" );
					 if(!rs1.next()) {
					 
					pstmt = dbCon.con.prepareStatement("INSERT INTO library_students.threetimes_latereturns(StudentID,Status)VALUES(?, ?)");
					pstmt.setString(1, StudentID);
					pstmt.setString(2,Status);
					
					pstmt.executeUpdate();
					pstmt.close();

					 }
					
			}
					
			
			rs1.close();
			rs.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
