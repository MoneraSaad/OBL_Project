
package Server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.TimerTask;

/**
 * this class is about updating the InDemand field which indicates if the book is in demand
 * or not  there's a timer task that checks every hour if there are no more orders
 * on the book if so it automatically changes the IsDemand field to 0 which means
 * that the book isn't demand because there are no orders on it .
 */

public class AutoUpdateInDemandField extends TimerTask {
	/***
	 * 
	 */

	private Database dbCon;

	public static int index = 0;

	/***
	 * 
	 * @param dbCon
	 */
	public AutoUpdateInDemandField(Database dbCon) {
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
			// ResultSet rs = stmt.executeQuery("SELECT * FROM
			// library_students.borrowedcopies");
			ResultSet rs = stmt.executeQuery(
					"SELECT CatalogNum from library_students.book");
			
			/**
			 * if date is now (or after), check the time too, if condition true then update
			 * order status to 1
			 */
			while (rs.next()) {
				
				
				String CatalogNummm=rs.getString("CatalogNum");
				
				
				ResultSet rs5=stmt5.executeQuery("SELECT CatalogNum from library_students.orderhistory where CatalogNum='"+CatalogNummm+"'" + "AND OrderStatus=1" );
				
				if (rs5.next()) {
				 System.out.println("there are orders , isDemand=1");
						 
						
					}else {
						System.out.println("there are no orders , isDemand=0 ");
						pstmt = dbCon.con.prepareStatement("UPDATE library_students.book SET InDemand=0 where CatalogNum=?");
						 pstmt.setString(1,CatalogNummm);
					
						 pstmt.executeUpdate();
					}
					
				
				
				
				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}


