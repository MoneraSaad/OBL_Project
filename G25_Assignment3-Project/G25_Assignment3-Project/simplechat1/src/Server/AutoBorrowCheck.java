
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

public class AutoBorrowCheck extends TimerTask {
	/***
	 * 
	 */

	private Database dbCon;

	public static int index = 0;

	/***
	 * 
	 * @param dbCon
	 */
	public AutoBorrowCheck(Database dbCon) {
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
					"SELECT Deadline,LenderToID,Barcode,CatalogNum FROM library_students.borrowedcopies WHERE BorrowStatus=1");
			String CatalogNum="";
			Statement stmt2 = dbCon.con.createStatement();
			Statement stmt3 = dbCon.con.createStatement();
			ResultSet rs1=null;
			PreparedStatement pstmt=null;
			/**
			 * if date is now (or after), check the time too, if condition true then update
			 * order status to 1
			 */ 
			while(rs.next()) {
				System.out.println(rs.getString("LenderToID")+"idddddddddd");
				LocalDate dateNow = LocalDate.now();
				String deadline = rs.getString("Deadline");
				java.util.Date dateUtil = rs.getTimestamp("Deadline");
				LocalDate dateInDB = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				LocalDate oneDayAfter = dateInDB.plusDays(1);

				if (dateNow.equals(oneDayAfter)|| dateNow.isAfter(oneDayAfter)) {
					String LenderToID = "";
					//String bookName = "";
					String status="Hasn't returned the book";
					String barcode="";
					
					LenderToID = rs.getString("LenderToID");
					System.out.println(rs.getString("LenderToID")+"idddddddddd");
					CatalogNum=rs.getString("CatalogNum");
					barcode=rs.getString("Barcode");
					
					  rs1 = stmt1.executeQuery("SELECT StudentID,Status,Barcode,CatalogNum,Deadline FROM library_students.borrowfreeze WHERE Barcode=" + "'" + barcode + "'" + "AND CatalogNum='"+ CatalogNum +"'"+"AND StudentID='"+ LenderToID +"'"+"AND Deadline='"+ deadline + "'");
					 if(!rs1.next()) {
					 
					pstmt = dbCon.con.prepareStatement("INSERT INTO library_students.borrowfreeze(StudentID,Status,Barcode,CatalogNum,Deadline)VALUES(?, ?, ?, ?,?)");
					pstmt.setString(1, LenderToID);
					pstmt.setString(2,status);
					pstmt.setString(3, barcode);
					pstmt.setString(4, CatalogNum);
					pstmt.setString(5, deadline);
					pstmt.executeUpdate();
					pstmt.close();

					 }
					
			}
					
			}
			rs1.close();
						rs.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
