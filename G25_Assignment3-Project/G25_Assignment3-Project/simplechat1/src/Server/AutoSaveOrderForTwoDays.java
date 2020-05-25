
package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimerTask;

/**
 * this class is about saving the order in the database for two days  there's a
 * timer task that checks every hour if the reader picked his order that means id the local
 * time ATM is after two days from the returning date and the reader haven't picked the book
 * that means his OrderStatus=1 then we automatically cancel his order by updating his 
 * OrderStatus to 0 and decreasing the NumOfOrders for the book by 1 and increasing the AvailableCopies by 1
 */

public class AutoSaveOrderForTwoDays extends TimerTask {
	/***
	 * 
	 */

	private Database dbCon;

	public static int index = 0;

	/***
	 * 
	 * @param dbCon
	 */
	public AutoSaveOrderForTwoDays(Database dbCon) {
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
			
			PreparedStatement pstmt;
			PreparedStatement pstmtt;
			// ResultSet rs = stmt.executeQuery("SELECT * FROM
			// library_students.borrowedcopies");
			ResultSet rs = stmt.executeQuery(
					"SELECT CatalogNum,ReturningDate From library_students.returning_book WHERE Status=1");
			
			/**
			 * if date is now (or after), check the time too, if condition true then update
			 * order status to 1
			 */
			while (rs.next()) {
				String ID="";
				LocalDate dateNow = LocalDate.now();
				java.util.Date dateUtil = rs.getTimestamp("ReturningDate");
				LocalDate dateInDB = dateUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate afterTwoDays = dateInDB.plusDays(2);
				
				String CatalogNummm=rs.getString("CatalogNum");
				
				ResultSet rs5=stmt5.executeQuery("SELECT OrderDate from library_students.orderhistory");
				if (rs5.next()) {
				ResultSet rs4=stmt4.executeQuery("SELECT min(OrderDate) from library_students.orderhistory where CatalogNum ='"+CatalogNummm+"'");
				if(rs4.next()) {
				String order=rs4.getString("min(OrderDate)");
				ResultSet rs3 = stmt3.executeQuery("SELECT OrderDate, StudentID,BookName from library_students.orderhistory where CatalogNum ='"+CatalogNummm+"'"
				+"AND OrderDate='"+order+"'"+"AND OrderStatus=1");	
					
				if(rs3.next()) {
					ID=rs3.getString("StudentID");
					if (dateNow.equals(afterTwoDays)) {
						pstmt = dbCon.con.prepareStatement("UPDATE library_students.orderhistory SET OrderStatus=0 where (CatalogNum=? AND StudentID=?)");
						 pstmt.setString(1,CatalogNummm);
						 pstmt.setString(2, ID);
						
						 pstmt.executeUpdate();
						 
						 pstmtt = dbCon.con.prepareStatement("UPDATE library_students.book SET AvailableCopies=AvailableCopies+1 , NumOfOrder=NumOfOrder-1 where (CatalogNum=? AND StudentID=?)");
						 pstmtt.setString(1,CatalogNummm);
						 pstmtt.setString(2, ID);
						 pstmtt.executeUpdate();
						
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


