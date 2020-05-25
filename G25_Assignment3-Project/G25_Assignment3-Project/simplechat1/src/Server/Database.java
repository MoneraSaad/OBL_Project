package Server;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import client.controller.UserPermission;
import client.entities.Book;
import client.entities.BorrowedBook;
import client.entities.HistoryIntity;
import client.entities.User;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;




import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Database {
	public Connection con;
	private static Database instance = null;

	public void connect(String dbName, String user, String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
			/* handle the error */}

		try {

			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName, user, pass);

			// System.out.println("SQL connection succeed");
		} catch (SQLException ex) {/* handle any errors */
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	/**
	 * This function check if there is an instance for the form, if no , create it
	 * else, return the INSTANCE
	 */
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}

		return instance;
	}

	public ArrayList<String> getDetails(ArrayList<String> msg) { 
		PreparedStatement stmt;
		String username = "";
		String status = "";
		ArrayList<String> list = new ArrayList<String>();

		try {
			stmt = con.prepareStatement("SELECT * FROM library_students.student WHERE Password = ?,StudentID=?");
			stmt.setString(1, (String) msg.get(1));
			stmt.setString(2, (String) msg.get(0));
			ResultSet rs = stmt.executeQuery();
			// System.out.println("msg = " + msg);
			// ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.student
			// WHERE StudentID = '2'");
			while (rs.next()) {
				username = rs.getString(2); // get username from column 2 (StudentName col)
				status = rs.getString(3); // get status from column 3 (StatusMembership col)
				list.add(username); // index 0 in list has 'username'
				list.add(status); // index 1 in list has 'status'

			}

			rs.close();
			stmt.close();
			return list;
		} catch (SQLException e) {

			e.printStackTrace();
			return null;

		}

	}

	public void updateStatusOfStudent(String userName, String status, String operation, String Freeze) {
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(
					"UPDATE library_students.student SET StatusMembership=? ,Operation=? , Freeze =? WHERE StudentName=?");
			pstmt.setString(1, status);
			pstmt.setString(2, operation);
			pstmt.setString(3, Freeze);
			pstmt.setString(4, userName);
			pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	/**
	 * @author Monera
	 * 
	 * @param user : to check if the user ID exist in the database
	 * @return true if ID exist, else false
	 * @throws SQLException
	 */
	public boolean isUser(User user) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT * FROM library_students.student WHERE StudentID=" + "'" + user.getUserName() + "'");
			if (!(rs.next())) {
				return false;
			}
			rs.close();
			return true;

		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * @author Monera
	 * @param user : has the ID and the password that the user insert in the GUI
	 * @return true if the password is Suitable to the ID, else return false
	 * @throws SQLException
	 */
	public boolean checkPassword(User user) throws SQLException {
		try {

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.student WHERE StudentID=" + "'"
					+ user.getUserName() + "'" + " AND Password=" + "'" + user.getPassword() + "'");
			if (!(rs.next())) {
				return false;
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * @author Monera
	 * @param user : has the ID and the password that the user insert in the GUI
	 * @return true if the user is already logged In, els return false
	 * @throws SQLException
	 */
	public boolean isLoggedIn(User user) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.student WHERE StudentID=" + "'"
					+ user.getUserName() + "'" + " AND status=1");
			if (!(rs.next())) {
				return false;
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * this method check if the user is locked to prevent him from logining in 
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean isLocked(User user) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT StatusMembership FROM library_students.student WHERE StudentID=" + "'"
					+ user.getUserName()+"'");
			if ((rs.next())) {
				if(rs.getString("StatusMembership").equals("Locked")) {
				return false;
			}
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	/**
	 * @author Monera
	 * 
	 *         markAsLoggedIn-- change the status to 1 that marks the user is logged
	 *         in
	 * 
	 * @param user : has the ID and the password that the user insert in the GUI
	 * @throws SQLException
	 */
	public void markAsLoggedIn(User user) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"UPDATE library_students.student SET status=1 WHERE StudentID=" + "'" + user.getUserName() + "'");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @author Monera if there's a successfull login then we fill in the details of
	 *         the user
	 * 
	 * @param user : the user we're filling in
	 * @return User : the object of user (contains the details of him)
	 */

	public User fillUser(User user) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			User currUser = new User(user.getPassword(), user.getUserName());
			ResultSet rs = stmt.executeQuery(
					"SELECT StudentID,Permission,StatusMembership,Operation,Freeze,StudentName,isLock,BorrowedBookNum,LateReturningNum,PhoneNumber,Email,OrderedBooksNum,SignificantClarification FROM library_students.student WHERE StudentID = "
							+ "'" + user.getUserName() + "'");
			rs.first();

			currUser.setName(rs.getString("StudentName"));
			currUser.setStatusMembership(rs.getString("StatusMembership"));
			currUser.setOperation(rs.getString("Operation"));
			currUser.setFreeze(rs.getString("Freeze"));
			int permission = rs.getInt("Permission");
			UserPermission type = UserPermission.getByValue(permission);
			currUser.type = type;
			currUser.setIsLock(rs.getString("isLock"));
			currUser.setBorrowedBookNum(rs.getInt("BorrowedBookNum"));
			currUser.setLateReturningNum(rs.getInt("LateReturningNum"));
			currUser.setPhoneNumber(rs.getString("PhoneNumber"));
			currUser.setEmail(rs.getString("Email"));

			currUser.setOrderedBooksNum(rs.getInt("OrderedBooksNum"));
			currUser.setSignificantClarification(rs.getInt("SignificantClarification"));

			rs.close();

			return currUser;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Monera
	 * 
	 *         Get all Books from the table in the database in order to let the
	 *         manager/ Librarian have the option to update/edit them
	 * 
	 * @return ArrayList<User> list of users
	 * @throws SQLException
	 */
	public ArrayList<Book> getBooks() throws SQLException {

		try {
			ArrayList<Book> Books = new ArrayList<Book>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from library_students.book");
			while (rs.next()) {
				String CatalogNum = rs.getString("CatalogNum");
				String BookName = rs.getString("BookName");
				String Author = rs.getString("Author");
				String EditionNum = rs.getString("EditionNum");
				String printingDate1 = rs.getString("printingDate");
				// Date printingDate=new SimpleDateFormat("yyyy-MM-dd").parse(printingDate1);
				String Subject = rs.getString("Subject");
				String Description = rs.getString("Description");
				int CopiesNum = rs.getInt("CopiesNum");
				String PurchaseDate1 = rs.getString("PurchaseDate");
				// Date PurchaseDate=new SimpleDateFormat("yyyy-MM-dd").parse(PurchaseDate1);
				String ShelfNum = rs.getString("ShelfNum");
				int CopiesInBorrow = rs.getInt("CopiesInBorrow");
				int NumOfOrder = rs.getInt("NumOfOrder");
				int AvailableCopies = rs.getInt("AvailableCopies");

				Book book = new Book(CatalogNum, BookName, Author, EditionNum, printingDate1, Subject, Description,
						CopiesNum, PurchaseDate1, ShelfNum, CopiesInBorrow, NumOfOrder, AvailableCopies);
				// book.BooksList.add(book);
				Books.add(book);

			}
			rs.close();

			return Books;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}

	}

	/***
	 * This is about searching a book by it's name
	 * 
	 * @author Monera
	 * @param book : the book to search
	 */

	public ArrayList<Book> searchBookByName(String Name) {

		ArrayList<Book> l = new ArrayList<Book>();
		try {

			PreparedStatement pst = con.prepareStatement("select * from library_students.book where BookName like ?");
			pst.setString(1, Name + "%");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String bookName1 = rs.getString("BookName");
				String authorName = rs.getString("Author");
				
				String subject1 = rs.getString("Subject");
				System.out.println(subject1);
				
				int AvailableCopies = rs.getInt("AvailableCopies");
				String ShelfNum = rs.getString("ShelfNum");
				String CatalogNum = rs.getString("CatalogNum");
				/**
				 * TODO: Deadline of a book
				 */
				Book b = new Book(CatalogNum, bookName1, authorName, subject1, AvailableCopies, ShelfNum);
				// book.BookName = rs.getString("BookName");
				// book.CopiesNum = rs.getString("CopiesNum");
				// book.ShelfNum = rs.getString("ShelfNum");

				l.add(b);
				// book.BooksList.addAll(l);

			}
			rs.close();
			return l;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/***
	 * This is about searching a book by it's author
	 * 
	 * @author Monera
	 * @param book : the book to search
	 */

	public ArrayList<Book> searchBookByAuthor(String author) {

		ArrayList<Book> l = new ArrayList<Book>();
		try {

			PreparedStatement pst = con.prepareStatement("select * from library_students.book where Author like ?");
			pst.setString(1,author + "%");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				String bookName1 = rs.getString("BookName");
				String authorName = rs.getString("Author");
				String subject1 = rs.getString("Subject");
				int AvailableCopies = rs.getInt("AvailableCopies");
				String ShelfNum = rs.getString("ShelfNum");
				String CatalogNum = rs.getString("CatalogNum");
				/**
				 * TODO: Deadline of a book
				 */
				Book b = new Book();
				b.BookAuthor(bookName1, authorName, subject1, AvailableCopies, ShelfNum,CatalogNum);
				// book.BookName = rs.getString("BookName");
				// book.CopiesNum = rs.getString("CopiesNum");
				// book.ShelfNum = rs.getString("ShelfNum");

				l.add(b);
				// book.BooksList.addAll(l);

			}
			rs.close();
			return l;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/***
	 * This is about searching a book by it's subject
	 * 
	 * @author Monera
	 * @param book : the book to search
	 */

	public ArrayList<Book> searchBookBySubject(String subject) {

		ArrayList<Book> l = new ArrayList<Book>();
		try {
			System.out.println("---------------------------------"+subject);
			PreparedStatement pst = con.prepareStatement("select * from library_students.book where Subject like ?");
			pst.setString(1,subject + "%");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {

				String bookName1 = rs.getString("BookName");
				String authorName = rs.getString("Author");
				
				String subject1 = rs.getString("Subject");
				System.out.println(subject1);
				
				int AvailableCopies = rs.getInt("AvailableCopies");
				String ShelfNum = rs.getString("ShelfNum");
				String CatalogNum = rs.getString("CatalogNum");
				String Description = rs.getString("Description");
				
				/**
				 * TODO: Deadline of a book
				 */
				Book b = new Book();
				
				b.BookSubject(bookName1, authorName, subject1, AvailableCopies, ShelfNum,CatalogNum);
				// book.BookName = rs.getString("BookName");
				// book.CopiesNum = rs.getString("CopiesNum");
				// book.ShelfNum = rs.getString("ShelfNum");

				l.add(b);
				// book.BooksList.addAll(l);

			}
			rs.close();
			return l;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/***
	 * This is about searching a book by it's name
	 * 
	 * @author Monera
	 * @param book : the book to search
	 */

	public ArrayList<Book> freeSearchBook(String description) {

		ArrayList<Book> l = new ArrayList<Book>();
		try {

			PreparedStatement pst = con.prepareStatement("select * from library_students.book where Description like ?");
			pst.setString(1, "%" + description + "%");
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				String bookName1 = rs.getString("BookName");
				String authorName = rs.getString("Author");
				
				String subject1 = rs.getString("Subject");
				System.out.println(subject1);
				
				int AvailableCopies = rs.getInt("AvailableCopies");
				String ShelfNum = rs.getString("ShelfNum");
				String CatalogNum = rs.getString("CatalogNum");
				String Description = rs.getString("Description");
				/**
				 * TODO: Deadline of a book
				 */
				Book b = new Book(CatalogNum, bookName1, authorName, subject1, AvailableCopies, ShelfNum ,Description);
				// book.BookName = rs.getString("BookName");
				// book.CopiesNum = rs.getString("CopiesNum");
				// book.ShelfNum = rs.getString("ShelfNum");

				l.add(b);
				// book.BooksList.addAll(l);

			}
			rs.close();
			return l;
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * @author Monera
	 * 
	 *         Get all users from the table in the database in order to let the
	 *         system manager have the option to update/freeze/unlock/edit them
	 * 
	 * @return ArrayList<User> list of users
	 * @throws SQLException
	 */
	public ArrayList<User> getUsers() throws SQLException {

		try {
			ArrayList<User> users = new ArrayList<User>();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * from library_students.student");
			while (rs.next()) {
				String username = rs.getString("StudentID");
				String password = rs.getString("Password");
				int permission = rs.getInt("permission");
				UserPermission type = UserPermission.getByValue(permission);
				User user = new User(username, password);
				user.UsersList.add(user);
				users.add(user);

				System.out.println(user);
			}
			rs.close();

			return users;

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * markAsLoggedOut-- change the status to 0 that marks the user is logged out
	 * 
	 * @param user : has the ID that the user insert in the GUI
	 * @throws SQLException
	 */
	public void markAsLoggedOut(User user) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"UPDATE library_students.student SET status=0 WHERE StudentID=" + "'" + user.getUserName() + "'");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}// end markAsLoggedOut

	

	/**
	 * @author Monera
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> sortDate(Book book) throws SQLException {

		ArrayList<String> sort = new ArrayList<String>();
		Statement stmt = con.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.borrowedcopies WHERE CatalogNum = '"
				+ book.getCatalogNum() + "' GROUP BY CatalogNum ORDER BY Deadline ASC ");

		while (rs.next()) {

			sort.add(rs.getString("Deadline"));

			return sort;
		}
		System.out.println(sort);
		rs.close();
		return sort;
	}

	/**
	 * this method is for getting the closets deadline for the specific book 
	 * @author Monera
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public String getClosestDeadline(Book book) throws SQLException {
		try { 
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT MIN(Deadline) FROM library_students.borrowedcopies WHERE CatalogNum="
				+ "'" + book.getCatalogNum() + "'" + " And BorrowStatus=1");
		if (rs.next()) {
			String rdate = (rs.getString(1)).toString();
			rs.close(); 
			return rdate;
		}else {
			rs.close();
		return "No";
		}
		}catch (SQLException ex) 
        {
			ex.printStackTrace();
			return null;
		}
	}

	
	
	/**
	 * this method updates the readers status 
	 * @author shamsdiab
	 * @param ReaderID
	 * @param status
	 * @param operation
	 * @param Freeze
	 */
	public void updateStatusOfReader(String ReaderID, String status, String operation, String Freeze) {
		String newStatus= new String();
		       newStatus=status;
		String OperationA= ("ExtendBookRequest,ReturnBookRequest,LendingRequest");
    	String OperationF =("ReturnBookRequest");
    	String OperationNL = ("");
    	String FreezeT = ("true");
    	String FreezeNL =("false");
    	String BlockT = ("true");
    	String BlockNL = ("false");
		PreparedStatement pstmt;
		
		try {
	    	if(newStatus.equals("Active")) {
	    		System.out.println(ReaderID);
	    		pstmt = con.prepareStatement("UPDATE library_students.student SET StatusMembership=? ,Operation=? , Freeze=? , isLock =? WHERE StudentID=?");
				pstmt.setString(1, newStatus);
				pstmt.setString(2, OperationA);
				pstmt.setString(3, FreezeNL);
				pstmt.setString(4, BlockNL);
				pstmt.setString(5, ReaderID);
				
				pstmt.executeUpdate();
	    		
	    	}
	    	else if (newStatus.equals("Frozen")) {
	    		pstmt = con.prepareStatement("UPDATE library_students.student SET StatusMembership=? ,Operation=? , Freeze=? , isLock =? WHERE StudentID=?");
				pstmt.setString(1, newStatus);
				pstmt.setString(2, OperationF);
				pstmt.setString(3, FreezeT);
				pstmt.setString(4, BlockNL);
				pstmt.setString(5, ReaderID);
				pstmt.executeUpdate();
	    		
	    	}
	    	
	    	
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}
	


	/**
	 * this methods checks if the reader returned the book in time or not
	 * @author shamsdiab
	 * @param barcode
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public boolean isDate(String barcode) throws SQLException, ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
		    sdf.format(date);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'" + " AND " + "Deadline>="+ "'" + sdf.format(date) + "'");
			if (!(rs.next())) {
				return false;
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}
	
	/**
	 * this method checks the barcode exists 
	 * @author shamsdiab
	 * @param barcode
	 * @return
	 * @throws SQLException
	 */
	public boolean isExistBarcode(String barcode) throws SQLException {
		  try {
			    Statement stmt = con.createStatement();
			    ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'");
			    if(!(rs.next()))
			    {
			        return false;
			    }
			    
			    rs.close();
			    return true;
		      }catch (SQLException ex) 
		        {
					ex.printStackTrace();
					return false;
				}

		}
	
	/**
	 * this method checks if the librarian already returned the book
	 * @author shamsdiab
	 * @param barcode
	 * @return
	 * @throws SQLException
	 */
	public boolean isBookReturned(String barcode) throws SQLException {
		Integer BorrowStatus = new 	Integer(0);
		try {
			Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT BorrowStatus FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'" + "AND BorrowStatus= '1' ");
		    if((rs.next()))
		    	BorrowStatus=rs.getInt(1);	
		    
		    if(BorrowStatus == 0)
	        	return true;
	        else
	        	return false;
		    
		    }catch (SQLException ex) 
        {
			ex.printStackTrace();
			return true;
		}
	}
	
	/**
	 * this method checks if the Registration has successeded 
	 * @author shamsdiab
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public int isSuccessfulRegistration(User user) throws SQLException {
		PreparedStatement pstmt;
		int status;
		try {
		    pstmt = con.prepareStatement("INSERT INTO library_students.student(StudentID,Password,PhoneNumber,Email,StudentName,StatusMembership,Operation,Freeze,isLock,status,Permission,BorrowedBookNum,LateReturningNum,SignificantClarification,OrderedBooksNum)VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)");
		    pstmt.setString(1,user.getUserName());
		    pstmt.setString(2,user.getPassword());
		    pstmt.setString(3,user.getPhoneNumber());
		    pstmt.setString(4,user.getEmail());
		    pstmt.setString(5,user.getName());
		    pstmt.setString(6,user.getStatusMembership());
		    pstmt.setString(7,user.getOperation());
		    pstmt.setString(8,user.getFreeze());
		    pstmt.setString(9,user.getIsLock());
		    pstmt.setInt(10,user.getStatus());
		    pstmt.setInt(11,2);
		    pstmt.setInt(12,user.getBorrowedBookNum());
		    pstmt.setInt(13,user.getLateReturningNum());
		    pstmt.setInt(14,user.getSignificantClarification());
		    pstmt.setInt(15,user.getOrderedBooksNum());
		    
		    status=pstmt.executeUpdate();
		    return status;
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    	return 0;
		    }	
	}
	
	/**
	 * this methods checks if the user already exists in the database
	 * @author shamsdiab
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean isExistUser(User user) throws SQLException{
		try {
			Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.student WHERE StudentID=" + "'" + user.getUserName() + "'");
		    if(!(rs.next()))
		    	return true;
		    rs.close();
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		      	return true;	
		    }
		return false;
	}
	
	
	/**
	 * this method updates the status of the borrowed book if the reader returned it or not
	 * @author shamsdiab
	 * @param barcode
	 * @throws SQLException
	 */
	public void updateStatusOfBorrowedBook(String barcode ) throws SQLException{
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement("UPDATE library_students.borrowedcopies SET BorrowStatus= ? where Barcode=?");
		    pstmt.setInt(1,0);
		    pstmt.setString(2,barcode);
		    
		    pstmt.executeUpdate();
		    }catch(SQLException ex) {
		    	ex.printStackTrace();
		    }
	}
	
	/**
	 * this method is for increasing the num of late returns for each book
	 * @author shamsdiab
	 * @param barcode
	 * @throws SQLException
	 */
	public void increaseNumOfLateReturning(String barcode ) throws SQLException{
		PreparedStatement pstmt;
		String  readerID = new String();
		Integer lateReturning = new Integer(0);
		try {
			Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT LenderToID FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'");
		    if((rs.next()))
		    	readerID  = rs.getString(1);
		    
		    rs = stmt.executeQuery("SELECT LateReturningNum FROM library_students.student  WHERE StudentID=" + "'" + readerID + "'");
		    if((rs.next()))
		    	lateReturning = rs.getInt(1);    
		    
		    pstmt = con.prepareStatement("UPDATE library_students.student SET LateReturningNum= ? where StudentID=?");
		    pstmt.setInt(1,++lateReturning);
		    pstmt.setString(2,readerID);
		    pstmt.executeUpdate();
		    
		    rs.close();
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	}
	
	/**
	 * this method is for inserting new return 
	 * @author shamsdiab
	 * @param barcode
	 * @throws SQLException
	 */
	public void insertNewReturning(String barcode ) throws SQLException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
	    String Cdate = sdf.format(date);
		String  readerID = new String();
		String BookName = new String();
		String CatalogNum = new String();
		String Deadline = new String();
		boolean flag = false;
		PreparedStatement pstmt;
		try {
			Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT LenderToID,Deadline FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'" + "AND BorrowStatus= '1' ");
		    if((rs.next()))
		    {
		    	readerID  = rs.getString(1);
		    	Deadline = rs.getString(2);
		    	System.out.println("uuuuuuuuuuuuuuuuuuuuuuu" + readerID);
		    }
		    rs = stmt.executeQuery("SELECT BookName,CatalogNum FROM library_students.copies WHERE Barcode=" + "'" + barcode + "'");
		    if((rs.next())) {
		    	BookName  = rs.getString(1);
		    	CatalogNum = rs.getString(2);
		    }
		    try {
		    if(!isDate(barcode))
		    	flag = true;
		    }catch(ParseException ex) {
		    	ex.printStackTrace();
		    }
		    pstmt = con.prepareStatement("INSERT INTO library_students.returning_book(StudentID,BookName,ReturningDate,isLateReturn,CatalogNum,Barcode,Deadline,Status)VALUES(?, ?, ?, ?,?, ?,?,?)");
		    pstmt.setString(1,readerID);
		    pstmt.setString(2,BookName);
		    pstmt.setString(3,Cdate);
		    if(flag == true)
		    pstmt.setString(4,"true");
		    else
		    pstmt.setString(4,"false");
		    pstmt.setString(5,CatalogNum);
		    pstmt.setString(6,barcode);
		    pstmt.setString(7,Deadline);
		    pstmt.setInt(8,1);
            pstmt.executeUpdate();
		    
		    rs.close();
		    }catch (SQLException ex) {
		    	ex.printStackTrace();	
		    }
	}
	
	/**
	 * this method is for checking the date for the late returned book 
	 *@author shamsdiab
	 * @param barcode
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public boolean checkDateOfAllBooksIfLateReturningBook(String barcode ) throws SQLException, ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
	    sdf.format(date);
		String  readerID = new String();
		int c=0;
		try {
		     Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery("SELECT LenderToID FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'");
	         if((rs.next()))
	    	     readerID  = rs.getString(1);
	         
	         rs = stmt.executeQuery("SELECT barcode FROM library_students.borrowedcopies WHERE LenderToID=" + readerID + " AND Deadline < " + "'" + sdf.format(date) + "'" );
	         while(rs.next())
	         {
	        	 if(!rs.getString(1).equals(barcode))
	        		 c++;
	         }
	         
	         if(c == 0)
	        	 return false;
	         
	         rs.close();
	         return true;
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    	return false;
		    }
	}
	
	/**
	 * this method checks the num of late returning book
	 * @author shamsdiab
	 * @param barcode
	 * @return
	 * @throws SQLException
	 */
	public boolean checkNumOfLateReturningBookFromReader(String barcode ) throws SQLException{
		String  readerID = new String();
		Integer LateReturningNum = new Integer(0);
		try {
			Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT LenderToID FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'");
		    if((rs.next()))
		    	readerID  = rs.getString(1);
			
		    rs = stmt.executeQuery("SELECT LateReturningNum FROM library_students.student WHERE StudentID=" + "'" + readerID + "'");
		    if((rs.next()))
		    	LateReturningNum  = rs.getInt(1);
		    if(LateReturningNum > 3)
		    	return false;
		   
		    rs.close();
		    return true;
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    	return false;
		    }   
	}
	
	/**
	 * this method gets the reader's id
	 * @author shamsdiab
	 * @param barcode
	 * @return
	 * @throws SQLException
	 */
	public String getReaderID(String barcode ) throws SQLException{
		String  readerID = new String();
		try {
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT LenderToID FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'");
			 if((rs.next()))
	    	     readerID  = rs.getString(1);
			 
			 rs.close();
			return readerID;
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    	return null;
		    }
		
	}
	
	/**
	 * this method is for updating the copies in borrow num and the available copies num
	 * @author shamsdiab
	 * @param barcode
	 * @throws SQLException
	 */
	public void updateCopiesInBorrowAndAvailableCopies(String barcode ) throws SQLException{
		String CatalogNum = new String();
		Integer CopiesInBorrow = new Integer(0);
		Integer AvailableCopies = new Integer(0);
		PreparedStatement pstmt;
		try {
			 Statement stmt = con.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT CatalogNum FROM library_students.copies WHERE Barcode=" + "'" + barcode + "'");
			 if((rs.next()))
				 CatalogNum=rs.getString(1);		
			 rs = stmt.executeQuery("SELECT CopiesInBorrow,Availablecopies FROM library_students.book WHERE CatalogNum=" + "'" + CatalogNum + "'");
			 if((rs.next())) 
			 {
				 CopiesInBorrow = rs.getInt(1);
				 AvailableCopies = rs.getInt(2);
			 }
			 pstmt = con.prepareStatement("UPDATE library_students.book SET CopiesInBorrow= ?, AvailableCopies= ? where CatalogNum=?");
			 pstmt.setInt(1,--CopiesInBorrow);
			 pstmt.setInt(2,++AvailableCopies);
			 pstmt.setString(3,CatalogNum);
			 pstmt.executeUpdate();
			 
			 rs.close();
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	}
	
	/**
	 * this method is for decreasing the num of borrowed books when we return a book 
	 * @author shamsdiad
	 * @param barcode
	 * @throws SQLException
	 */
	public void lessNumOfBorrowedBook(String barcode ) throws SQLException{
		PreparedStatement pstmt;
		String  readerID = new String();
		int borrowedBookNum=0;
		try {
			Statement stmt = con.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT LenderToID FROM library_students.borrowedcopies WHERE Barcode=" + "'" + barcode + "'");
		    if((rs.next())) 
		    	readerID  = rs.getString(1);

		    rs = stmt.executeQuery("SELECT BorrowedBookNum FROM library_students.student  WHERE StudentID=" + "'" + readerID + "'");
		    if((rs.next()))
		    	borrowedBookNum = rs.getInt(1);
		    
		    pstmt = con.prepareStatement("UPDATE library_students.student SET BorrowedBookNum= ? WHERE StudentID=?");
		    pstmt.setInt(1,--borrowedBookNum);
		    pstmt.setString(2,readerID);
		    pstmt.executeUpdate();
		    
		    rs.close();
		    }catch (SQLException ex) {
		    	ex.printStackTrace();
		    }
	}



	/**********************Rania*************************/
	/**
	 * this method is for adding a book to the library
	 * 
	 * @author Rania
	 * @param book
	 * @throws SQLException
	 */
	public boolean addBook(Book book) throws SQLException, FileNotFoundException{

		FileInputStream fis = new FileInputStream(book.getTableOfContent());
		try {
			if(checkBookExistence(book) == false)
			{
				
				PreparedStatement stmt = con.prepareStatement("INSERT INTO library_students.book(CatalogNum, BookName, Author, EditionNum, printingDate, Subject, Description, PurchaseDate, ShelfNum ,CopiesInBorrow, NumOfOrder, AvailableCopies, CopiesNum, InDemand, filepdf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			    stmt.setString(1,book.getCatalogNum());
			    stmt.setString(2,book.getBookName());
			    stmt.setString(3,book.getAuthor()); 
			    stmt.setString(4,book.getEditionNum());
			    stmt.setString(5, book.getPrintingDate());
			    stmt.setString(6,book.getSubject());
			    stmt.setString(7,book.getDescription());
			    stmt.setString(8, book.getPurchaseDate());
			    stmt.setString(9,book.getShelfNum());
			    stmt.setInt(10,book.getCopiesInBorrow());
			    stmt.setInt(11,book.getNumOfOrder());
			    stmt.setInt(12,book.getCopiesNum());
			    stmt.setInt(13,book.getCopiesNum());
			    stmt.setInt(14,book.getInDemand());
			    stmt.setBinaryStream(15, fis, book.getByteArray().length);
			    
			    
			    stmt.executeUpdate();
			    stmt.close();
			    return true;
			}
			else if(checkBookExistence(book) == true){
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT CopiesNum,AvailableCopies FROM library_students.book WHERE CatalogNum=" + "'" + book.getCatalogNum() + "'");
				if(rs.next()) {
				int old=rs.getInt("CopiesNum");
				int oldA=rs.getInt("AvailableCopies");
				PreparedStatement pstmt = con.prepareStatement("UPDATE library_students.book SET CopiesNum=?,AvailableCopies=? where CatalogNum = " + "'"
						+ book.getCatalogNum() + "'");
				int newcopies = book.getCopiesNum()+old;
				int newAvailableCopies=oldA+newcopies;
				pstmt.setInt(1,newcopies);
				pstmt.setInt(2,newAvailableCopies);
				pstmt.executeUpdate();
				pstmt.close();
			    return true;
				}
			}

	} catch (Exception ex) {
		ex.printStackTrace();
		return false;
	}
		return false;
	
	}

	/**
	 * this methods edits a book that already exists in the library
	 * 
	 * @author Rania
	 * @param book
	 * @throws SQLException
	 */
	public void editBook(Book book) throws SQLException, FileNotFoundException{
		FileInputStream fis = new FileInputStream(book.getTableOfContent());
		
		try {
			PreparedStatement pstmt = con.prepareStatement("UPDATE library_students.book SET BookName = ? ,Author=? ,EditionNum=? , printingDate=?, Subject=?, Description=? , PurchaseDate=? ,ShelfNum=?,CatalogNum=? , CopiesNum=?, filepdf=? where CatalogNum = " + "'"
					+ book.getCatalogNum() + "'");
			pstmt.setString(1, book.getBookName());
			pstmt.setString(2, book.getAuthor());
			pstmt.setString(3, book.getEditionNum());
			pstmt.setString(4, book.getPrintingDate());
			pstmt.setString(5, book.getSubject());
			pstmt.setString(6, book.getDescription());
			pstmt.setString(7, book.getPurchaseDate());
			pstmt.setString(8, book.getShelfNum());
			pstmt.setString(9, book.getCatalogNum());
			plusMinusCopies(book);
			pstmt.setInt(10, book.getCopiesNum());
			pstmt.setBinaryStream(11, fis, book.getByteArray().length);
			
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {

			e.printStackTrace();
		
	}
	}
	/**
	 * this method deletes the book
	 * @param book
	 * @return
	 */
	public boolean deleteBook(Book book) {
		try {
			if(checkBookExistence(book) == false || book.getCopiesInBorrow() == 0)
			{
				System.out.println("database delete book");
				PreparedStatement pstmt = con.prepareStatement("DELETE FROM library_students.book WHERE CatalogNum = ?");
			    pstmt.setString(1,book.getCatalogNum());  
			    
			    pstmt.executeUpdate();
			    
			    pstmt = con.prepareStatement("DELETE FROM library_students.copies WHERE CatalogNum = ?");
			    pstmt.setString(1,book.getCatalogNum());  
			    
			    pstmt.executeUpdate();
			    return true;
			}
			

	} catch (Exception ex) {
		ex.printStackTrace();
		return false;
	}
		return false;
		
	}
	/**
	 * this method deletes the copiesof th deleted book
	 * @param book
	 * @return
	 */
	public boolean deleteCopy(Book book) {
		try {
			
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			ResultSet rs = stmt2.executeQuery("SELECT CopiesNum from library_students.book where CatalogNum='"+book.getCatalogNum()+"'");
			int barcodeInt;
			String barcodeString="";
			if(rs.next()) {
			int newcopies=rs.getInt("CopiesNum");
			for(int i=0;i<newcopies;i++) {
				 ResultSet rs1 = stmt1.executeQuery("SELECT MAX(Barcode) from library_students.copies where CatalogNum='"+book.getCatalogNum()+"'");
					if(rs1.next())
						barcodeString = rs1.getString(1);
				PreparedStatement pst = con.prepareStatement("Delete from library_students.copies where Barcode=?");
				pst.setString(1,barcodeString );
				pst.executeUpdate();
				pst.close();
			
			}
			}
			

	} catch (Exception ex) {
		ex.printStackTrace();
		return false;
	}
		return false;
		
	}
	/** this method deletes or add copies depends on the edit book num of copies
	 * @param book
	 * @throws SQLException
	 */
	public void plusMinusCopies(Book book)throws SQLException
	{
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT CopiesNum,AvailableCopies FROM library_students.book WHERE CatalogNum=" + "'" + book.getCatalogNum() + "'");
		if(rs.next()) {
		int old=rs.getInt("CopiesNum");
		int oldA=rs.getInt("AvailableCopies");
		int newAvailableCopies;
		String barcodeString ="";
		PreparedStatement pstmt = con.prepareStatement("UPDATE library_students.book SET CopiesNum=?,AvailableCopies=? where CatalogNum = " + "'"
				+ book.getCatalogNum() + "'");
		
		if(book.getCopiesNum()>old) {
			int newcopies=  book.getCopiesNum()-old ;
			
			int barcodeInt;
			PreparedStatement stmtt = con.prepareStatement("INSERT INTO library_students.copies (Barcode, BookName, CatalogNum) VALUES (?, ?, ?)");
			Statement stmt1 = con.createStatement();
			
			 ResultSet rs1 = stmt1.executeQuery("SELECT MAX(Barcode) from library_students.copies");
			if(rs1.next())
				barcodeString = rs1.getString(1);
			System.out.println("barcodeeeeeeee"+barcodeString);
			
			for(int i=0;i<newcopies;i++) {
			 barcodeInt = Integer.parseInt(barcodeString);
			 barcodeInt++;
			 barcodeString = Integer.toString(barcodeInt);
			 stmtt.setString(1,barcodeString);
			 stmtt.setString(2,book.getBookName());
			 stmtt.setString(3,book.getCatalogNum()); 
			 stmtt.executeUpdate();
			}
			newAvailableCopies=oldA+newcopies;
			stmtt.close();
			stmt1.close();
		}else
		{
			int newcopies=old- book.getCopiesNum();
			
			int barcodeInt;
			Statement stmt1 = con.createStatement();
			Statement stmt2 = con.createStatement();
			
			for(int i=0;i<newcopies;i++) {
				 ResultSet rs1 = stmt1.executeQuery("SELECT MAX(Barcode) from library_students.copies where CatalogNum='"+book.getCatalogNum()+"'");
					if(rs1.next())
						barcodeString = rs1.getString(1);
				PreparedStatement pst = con.prepareStatement("Delete from library_students.copies where Barcode=?");
				pst.setString(1,barcodeString );
				pst.executeUpdate();
				pst.close();
			
//			 barcodeInt = Integer.parseInt(barcodeString);
//			 
//			  barcodeInt--;
//			 barcodeString = Integer.toString(barcodeInt);
			}
			
			newAvailableCopies=oldA-newcopies;
			stmt1.close();
			stmt2.close();
		}
		
		pstmt.setInt(1,book.getCopiesNum());
		
		pstmt.setInt(2,newAvailableCopies);
		pstmt.executeUpdate();
		pstmt.close();
		}
	
	}
		
	/**
	 * this method check if the book exists when we add a book 
	 * @author Rania
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public boolean checkBookExistence(Book book) throws SQLException {
		try {

			Statement stmt = con.createStatement();
			System.out.println(book.getCatalogNum());
			ResultSet rs = stmt.executeQuery("SELECT CatalogNum FROM library_students.book WHERE CatalogNum=" + "'" + book.getCatalogNum() + "'");
			if (!(rs.next())) {
				return false;
				
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}

	}


	// ------------------------------Karammmm
	/**
	 * this method is for checking if the reader can order the book or not depends
	 * on the number of orders and the book status if it's requested or not
	 * 
	 * @author Karam
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public String canOrderThisBook(Book book) throws SQLException {

		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(
					"SELECT CopiesNum,NumOfOrder,CopiesInBorrow,CatalogNum FROM library_students.book WHERE CatalogNum='"
							+ book.getCatalogNum() + "'");
			if (rs.next()) {

				if (rs.getInt("CopiesNum") > rs.getInt("NumOfOrder")) {
					if (rs.getInt("CopiesNum") == rs.getInt("CopiesInBorrow")) {
						rs.close();
						return "ok";
					} else {
						rs.close();
						return "You can borrow the book , no need to order";
					}
				} else {
					rs.close();
					return "The book is requested , you cant order it Currently";
				}
			}

			rs.close();
			return "not found";
		} catch (SQLException ex) {
			ex.printStackTrace();
			return "";
		}

	}

	/**
	 * this method updates the order details in the book table in the database
	 * 
	 * @author Karam
	 * @param user
	 * @param book
	 * @throws SQLException
	 */
	public void updateOrdersForBookAndUserInDataBase(User user, Book book) throws SQLException {
		PreparedStatement pstmt;
		Integer numOfOrder = new Integer(0);
		String CatalogNum = book.getCatalogNum();
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(
					"SELECT NumOfOrder FROM library_students.book WHERE CatalogNum=" + "'" + CatalogNum + "'");
			if (rs.next()) {
				numOfOrder = rs.getInt("NumOfOrder");
			}
			pstmt = con.prepareStatement("UPDATE library_students.book SET NumOfOrder = ? WHERE CatalogNum= ?");
			pstmt.setInt(1, ++numOfOrder);
			pstmt.setString(2, CatalogNum);
			pstmt.executeUpdate();
			rs.close();
			rs = stmt.executeQuery("SELECT OrderedBooksNum FROM library_students.student WHERE StudentID=" + "'"
					+ user.getUserName() + "'");
			if (rs.next()) {
				numOfOrder = rs.getInt("OrderedBooksNum");
			}
			pstmt = con.prepareStatement("UPDATE library_students.student SET OrderedBooksNum = ? WHERE StudentID= ?");
			pstmt.setInt(1, ++numOfOrder);
			pstmt.setString(2, user.getUserName());
			pstmt.executeUpdate();
			rs.close();
		} catch (SQLException ex) {

			ex.printStackTrace();
		}
	}

	/**
	 * this method saves the order details to the order history
	 * 
	 * @author Karam
	 * @param user
	 * @param book
	 */
	public String addToOrderHistoryHistory(User user, Book book) {
		int st = 0;
		String sql = "INSERT INTO library_students.orderhistory(StudentID,CatalogNum,BookName,OrderDate,OrderStatus)VALUES(?,?,?,?,?)";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
			Date date = new Date();
			PreparedStatement preparedStatement = (PreparedStatement) con.prepareStatement(sql);
			java.sql.Date ttt = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, book.getCatalogNum());
			preparedStatement.setString(3, book.getBookName());
			preparedStatement.setDate(4, ttt);
			preparedStatement.setInt(5, 1);
			st = preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement
					.executeQuery("SELECT MIN(Deadline) FROM library_students.borrowedcopies WHERE CatalogNum=" + "'"
							+ book.getCatalogNum() + "'" + " And BorrowStatus=1");
			if (rs.next()) {
				String rdate = (rs.getString(1)).toString();
				rs.close();
				return rdate;
			}
			return "";
		} catch (SQLException ex) {

			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * this method checks if the Reader already ordered the book
	 * 
	 * @author Karam
	 * @param user
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public boolean checkInOrdersTable(User user, Book book) throws SQLException {
		try {

			PreparedStatement stmt;
			stmt = con.prepareStatement(
					"SELECT * FROM library_students.orderhistory WHERE StudentID = " + "'" + user.getUserName() + "'"
							+ " AND CatalogNum=" + "'" + book.getCatalogNum() + "'" + " AND OrderStatus = 1");
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return true;
		}

	}

	/**
	 * this method edits and saves the changes that the Reader did to his library
	 * card
	 * 
	 * @author Karam
	 * @param user
	 * @throws SQLException
	 */
	public void saveEditForReader(User user) throws SQLException {// on edit the detils
		PreparedStatement pstmt;
		try {
			pstmt = con.prepareStatement(
					"UPDATE library_students.student SET PhoneNumber = ?, Email=? WHERE StudentID= ?");
			pstmt.setString(1, user.getPhoneNumber());
			pstmt.setString(2, user.getEmail());

			pstmt.setString(3, user.getUserName());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}
	
	
	/**
	 * this method gets all the readers history
	 * @author Karam
	 * @param user
	 * @return
	 * @throws SQLException
	 */
public ArrayList<HistoryIntity> getreaderHistory(User user) throws SQLException{
		
		
		
		
		try {
			
			Statement stmt = con.createStatement();
			ArrayList<HistoryIntity> history = new ArrayList<HistoryIntity>();
			ResultSet rs = stmt.executeQuery("SELECT BookName,OrderDate FROM library_students.OrderHistory WHERE StudentID ='"+user.getUserName()+"'");
			while (rs.next()) {
				HistoryIntity h = new HistoryIntity();
				h.setBookName(rs.getString("BookName"));
				h.setAction("order");
				h.setActionDate(rs.getString("OrderDate"));
				

				history.add(h);
			}
			
			rs = stmt.executeQuery("SELECT B.BookName, O.BorrowDate FROM library_students.BorrowedCopies AS O join library_students.book AS B using (CatalogNum) WHERE O.LenderToID ='"+user.getUserName()+"'");
			while (rs.next()) {
				HistoryIntity h = new HistoryIntity();
				h.setBookName(rs.getString("BookName"));
				h.setAction("borrow");
				h.setActionDate(rs.getString("BorrowDate"));
				

				history.add(h);
			}
			rs = stmt.executeQuery("SELECT BookName, ReturningDate FROM library_students.returning_book WHERE StudentID ='"+user.getUserName()+"'");
			while (rs.next()) {
				HistoryIntity h = new HistoryIntity();
				h.setBookName(rs.getString("BookName"));
				h.setAction("returning");
				h.setActionDate(rs.getString("ReturningDate"));
				

				history.add(h);
			}
			if(user.getType().equals(UserPermission.Librarian)||user.getType().equals(UserPermission.Manager))
			{
				rs = stmt.executeQuery("SELECT BookName, DateOfExtend,ReaderID FROM library_students.details_manual_extend WHERE LibrarianID ='"+user.getUserName()+"'");
				while (rs.next()) {
					HistoryIntity h = new HistoryIntity();
					h.setBookName(rs.getString("BookName"));
					h.setAction("extinding deadline for: "+rs.getString("ReaderID"));
					h.setActionDate(rs.getString("DateOfExtend"));
					

					history.add(h);
				}
			}
			rs = stmt.executeQuery("SELECT BookName, DateOfExtend FROM library_students.details_manual_extend WHERE ReaderID ='"+user.getUserName()+"'");
			while (rs.next()) {
				HistoryIntity h = new HistoryIntity();
				h.setBookName(rs.getString("BookName"));
				h.setAction("extinding deadline");
				h.setActionDate(rs.getString("DateOfExtend"));
				

				history.add(h);
			}
			
			rs = stmt.executeQuery("SELECT BookName, ExtendDate FROM library_students.extend_books_byreader WHERE StudentID ='"+user.getUserName()+"'");
			while (rs.next()) {
				HistoryIntity h = new HistoryIntity();
				h.setBookName(rs.getString("BookName"));
				h.setAction("manual extinding deadline");
				h.setActionDate(rs.getString("ExtendDate"));
				

				history.add(h);
			}

			
			
			rs.close();
			return history;
		
		    } catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
	}

/**
 * this method is for getting the borrow history 
 * @author Karam
 * @param user
 * @return
 * @throws SQLException
 */
	public ArrayList<Book> getBorrowedBooks(User user) throws SQLException{
		
		
			try {
			
			Statement stmt = con.createStatement();
			ArrayList<Book> books = new ArrayList<Book>();
			
			ResultSet rs = stmt.executeQuery("SELECT B.BookName, O.BorrowDate, O.Deadline ,O.Barcode FROM library_students.BorrowedCopies AS O join library_students.book AS B using (CatalogNum) WHERE BorrowStatus=1 AND O.LenderToID ='"+user.getUserName()+"'");
			while (rs.next()) {
				
				Book b = new Book();
				b.setBookName(rs.getString("BookName"));
				b.setBorrowingDate(rs.getString("BorrowDate"));
				b.setReturningDate(rs.getString("Deadline"));
				b.setBarcode(rs.getString("Barcode"));
				books.add(b);
			}
			
			
			rs.close();
			return books;
		
		    } catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
	}
	
	/**
	 * this method is for checking if the book already exists 
	 * @author Sari
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public boolean copyExists(BorrowedBook borrowedBook) throws SQLException {
	try {
	Statement stmt = con.createStatement();
	System.out.println(borrowedBook.getCopybarcode());
	ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.copies WHERE Barcode=" + "'" + borrowedBook.getCopybarcode()+ "'");
	if (!(rs.next())) {
	return false;
	}
	rs.close();
	return true;
	} catch (SQLException ex) {
	ex.printStackTrace();
	return false;
	}
	
	}
	/**
	 * this method checks how much copies are available 
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public boolean availabiltyOfTheBook(BorrowedBook borrowedBook) throws SQLException {
		try {
		Statement stmt = con.createStatement();
		System.out.println(borrowedBook.getCopybarcode());
		ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.book WHERE CatalogNum=" + "'" + getBookID(borrowedBook)+ "'"+"AND NumOfOrder<AvailableCopies");
		if (!(rs.next())) {
		return false;
		}
		rs.close();
		return true;
		} catch (SQLException ex) {
		ex.printStackTrace();
		return false;
		}
	
	}
	
	/**
	 * this method checks if the book is available
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public boolean ifItIsAvailable(BorrowedBook borrowedBook) throws SQLException {
		try {
		Statement stmt = con.createStatement();
		System.out.println(borrowedBook.getCopybarcode());
		ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.book WHERE CatalogNum=" + "'" + getBookID(borrowedBook)+ "'"+"AND 0<AvailableCopies");
		if (!(rs.next())) {
		return false;
		}
		rs.close();
		return true;
		} catch (SQLException ex) {
		ex.printStackTrace();
		return false;
	}

}
	/**
	 * this method is checking if the lender is one of the students who ordered the book depends on the availability 
	 * @author Sari
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public boolean orderBelongsToLenderTo(BorrowedBook book) throws SQLException{
		try {
			int available = 0;
			Statement stmt = con.createStatement();
			Statement stmt2 = con.createStatement();
			Statement stmt3 = con.createStatement();
			Statement stmt4 =con.createStatement();
			PreparedStatement pstmtt;
			ResultSet rs = stmt.executeQuery("SELECT CatalogNum From library_students.returning_book WHERE Status=1");
			while (rs.next()) {
				String CatalogNumber=rs.getString("CatalogNum");
				//rs.close();
				if(getBookID(book).equals(CatalogNumber)) {
					ResultSet rs2=stmt2.executeQuery("SELECT AvailableCopies from library_students.book where CatalogNum ='"+CatalogNumber+"'");
					if(rs2.next())
						available = rs2.getInt(1);
					ResultSet rs4=stmt4.executeQuery("SELECT * FROM library_students.orderhistory WHERE CatalogNum='"+CatalogNumber+"'" +"AND OrderStatus=1 ORDER BY OrderDate ");
				for(int i=0;i<available;i++)
				if(rs4.next()) {
				String order=rs4.getString("OrderDate");
				//rs4.close();
				ResultSet rs3 = stmt3.executeQuery("SELECT OrderDate, StudentID,BookName from library_students.orderhistory where CatalogNum ='"+CatalogNumber+"'"+"AND OrderDate='"+order+"'" +"AND StudentID='"+book.getLenderTo()+"'" +"AND OrderStatus=1");	
				if(rs3.next()) {
					PreparedStatement pstmt = con.prepareStatement("UPDATE library_students.orderhistory Set OrderStatus=0 WHERE StudentID='"  + book.getLenderTo()+ "'  And CatalogNum=" + getBookID(book));			
					pstmt.executeUpdate();
					pstmt = con.prepareStatement("UPDATE library_students.book SET NumOfOrder = NumOfOrder - 1 WHERE CatalogNum = ?");
					pstmt.setString(1, getBookID(book));
					pstmt.executeUpdate();
					pstmtt = con.prepareStatement("UPDATE library_students.returning_book Set Status=0 WHERE CatalogNum='" + getBookID(book)+"'");			
					pstmtt.executeUpdate();
					pstmtt.close();
					pstmt.close();
					// rs3.close();
					 return true;
					
						}
				rs3.close();
				}
				
				rs4.close();
				}
			}
			rs.close();
			} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
			}
	
		return false;

	}
	
	
	/**
	 * this method is for checking the lended to / the reader who wants to borrow the book
	 * @author Sari
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public boolean lenderToExists(BorrowedBook borrowedBook) throws SQLException {
		try {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.student WHERE StudentID	=" + "'" + borrowedBook.getLenderTo()+ "'");
		if (!(rs.next())) {
		return false;
		}
		rs.close();
		return true;
		} catch (SQLException ex) {
		ex.printStackTrace();
		return false;
		}
	
	}
	/**
	 * this method is for checking the lender 
	 * @author Sari 
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public boolean lenderExists(BorrowedBook borrowedBook) throws SQLException {
		try {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.student WHERE StudentID	=" + "'" + borrowedBook.getLender()+ "'"+"And (Permission=3 OR Permission=4)");
		if (!(rs.next())) {
		
		return false;
		}
		rs.close();
		return true;
		} catch (SQLException ex) {
		ex.printStackTrace();
		return false;
		}
	
	}
	
	/**
	 * this method is for updating the borrowed book
	 * @author Sari 
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public boolean updateBorrowedBook(BorrowedBook borrowedBook)  throws SQLException{
		String inDemand;
		Date date=new Date();
		SimpleDateFormat today=new SimpleDateFormat("yyyy-MM-dd");
		String str=today.format(date);
		//System.out.print(str);
		PreparedStatement pstmt;
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.borrowedcopies WHERE Barcode	=" + "'" + borrowedBook.getCopybarcode()+ "'"+"And BorrowStatus=1");
			if(rs.next())
				return false;
		pstmt = con.prepareStatement("INSERT INTO library_students.borrowedcopies(Barcode,BorrowDate,Deadline,Lender,LenderToID,BorrowStatus,CatalogNum) VALUES (?, ?, ?,?,?,?,?)");			
		pstmt.setString(1, borrowedBook.getCopybarcode());
		pstmt.setString(2, str);
		if(chooseCorrectDeadline(borrowedBook).equals("1"))
		date.setDate(date.getDate()+3);
		else 
		    date.setDate(date.getDate()+14);
		str=today.format(date);
		pstmt.setString(3, str);
		pstmt.setString(4, borrowedBook.getLender());
		pstmt.setString(5, borrowedBook.getLenderTo());
		pstmt.setInt(6, 1);
		pstmt.setString(7,getBookID(borrowedBook));
		pstmt.executeUpdate();
		return true;
		
		} catch (SQLException e) {
		e.printStackTrace();
		return false;
		}
	
	}
	
	/**
	 * this method is for choosing correct deadline
	 * @author Sari
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public String chooseCorrectDeadline(BorrowedBook borrowedBook) throws SQLException {
		String bookID = null,inDemand = null;
		try {
		Statement stmt = con.createStatement();
		//ResultSet rs =stmt.executeQuery("SELECT BookName FROM library_students.book WHERE CatalogNum=" + "'" + getBookID(borrowedBook)+ "'");
		ResultSet rs = stmt.executeQuery("SELECT InDemand FROM library_students.book WHERE CatalogNum="+"'"+getBookID(borrowedBook)+"'");
		
		if((rs.next()))
		inDemand=rs.getString(1);
		rs.close();
		return inDemand;
		} catch (SQLException ex) {
		ex.printStackTrace();
		return null;
	}
	}
	
	/**
	 * this method checks if the user is active
	 * @author Sari
	 * @param user
	 * @return
	 * @throws SQLException
	 */
	public boolean checkLenderToIfExistsAutomatic(User user) throws SQLException {
		try {
			Statement stmt = con.createStatement();
			PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM "+ "library_students.student WHERE StudentID= ? "+ "AND StatusMembership='Active'");
			preparedStatement.setString(1, user.getUserName());
			ResultSet rs = preparedStatement.executeQuery();
			if (!(rs.next())) {
				return false;
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		
	}
	/**
	 * this method checks if one week left to return the book 
	 * @author Sari
	 * @param userWhoWantsToExtendDeadline
	 * @param book
	 * @return
	 * @throws ParseException
	 * @throws SQLException
	 */
	public boolean checkLessThanWeek(User user,BorrowedBook book) throws ParseException,SQLException {
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT Deadline FROM library_students.borrowedcopies WHERE LenderToID	='"  + user.getUserName()+ "'  And Barcode=" + book.getCopybarcode());
			if (rs.next()) {    
				 	SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		            Date deadline = rs.getDate(1);
		            Date date=new Date();
		            String today=format.format(date);
		            date = format.parse(today);
		            date.setDate(date.getDate()+7);
		            if(date.before(deadline)) {
		            rs.close();
		            	return false;
		            }
			}else {
				rs.close();
				return false;
			}
			rs.close();
			return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	} 
	
	/**
	 * this method is for checking if there are orders for the book if so the user can't extend the deadline
	 * @author Sari
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public boolean checkIfThereIsOrders(BorrowedBook book) throws SQLException
	{
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.orderhistory WHERE CatalogNum="+"'"  +getBookID(book)+"'");
			System.out.println(getBookID(book));
			if(!rs.next()) { 
				rs.close();
			return true;
			} 
			else {
				rs.close();
				return false;
			}
		
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	
	/**
	 * this method is for updating after extending deadline
	 * @author Sari
	 * @param userWhoWantsToExtendDeadline
	 * @param book
	 * @return
	 * @throws SQLException
	 */
	public boolean updateField(User user,BorrowedBook book) throws SQLException
	{
		try {
			PreparedStatement pstmtt;
			Date date=new Date();
			Statement stmt = con.createStatement();
			String newDeadline;
			ResultSet rs = stmt.executeQuery("SELECT Deadline FROM library_students.borrowedcopies WHERE LenderToID	='"  + user.getUserName()+ "'  And Barcode=" + book.getCopybarcode());
			if(rs.next()) 
			date=rs.getDate(1);
			SimpleDateFormat today=new SimpleDateFormat("yyyy-MM-dd");
			rs.close();
			PreparedStatement pstmt = con.prepareStatement("UPDATE library_students.borrowedcopies Set Deadline=? WHERE LenderToID='"  + user.getUserName()+ "'  And Barcode=" + book.getCopybarcode());			

			date.setDate(date.getDate()+14);
			newDeadline=today.format(date);
			pstmt.setString(1, newDeadline);
			pstmt.executeUpdate();
			pstmt.close();
			pstmtt = con.prepareStatement("INSERT INTO library_students.extend_books_byreader (StudentID, CatalogNum, BookName,ExtendDate) VALUES (?, ?, ?,?)");
			pstmtt.setString(1,user.getUserName());
			pstmtt.setString(2, getBookID(book));
			pstmtt.setString(3, book.getBookName());
			pstmtt.setString(4, newDeadline);
			pstmtt.executeUpdate();
			 pstmtt.close();
			 return true;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	/**
	 * this method insert a copy
	 * @param book
	 * @throws SQLException
	 */
	public void insertBookCopies(Book book) throws SQLException{
		String barcodeString =" 0";
		int barcodeInt;
		PreparedStatement stmt = con.prepareStatement("INSERT INTO library_students.copies (Barcode, BookName, CatalogNum) VALUES (?, ?, ?)");
		Statement stmt1 = con.createStatement();
		ResultSet rs = stmt1.executeQuery("SELECT Barcode from library_students.copies");
		if(!rs.next())
			barcodeString="500";//check it 
		else rs = stmt1.executeQuery("SELECT MAX(Barcode) from library_students.copies");
		if(rs.next())
			barcodeString = rs.getString(1);
		System.out.print("yoo"+barcodeString);
		for(int i=0;i<book.getCopiesNum();i++) {
		 barcodeInt = Integer.parseInt(barcodeString);
		 barcodeInt++;
		 barcodeString = Integer.toString(barcodeInt);
		 stmt.setString(1,barcodeString);
		 stmt.setString(2,book.getBookName());
		 stmt.setString(3,book.getCatalogNum()); 
		 stmt.executeUpdate();
		}
		stmt.close();
		stmt1.close();
	}
	
	
	/**
	 * this method gets the book id for the return operation 
	 * @author shamsdiab
	 * @param borrowedBook
	 * @return
	 * @throws SQLException
	 */
	public String getBookID(BorrowedBook borrowedBook)throws SQLException{
		String bookID = null;
		try {
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT CatalogNum FROM library_students.copies WHERE Barcode=" + "'" + borrowedBook.getCopybarcode()+ "'");
		if((rs.next()))
		bookID = rs.getString(1);
		rs.close();
		return bookID;
		}catch (SQLException ex) {
		ex.printStackTrace();
		return null;
		}
		
	}
	
	
	/**
	 * this method updates the fields when we do borrow 
	 * @author Sari
	 * @param borrowedBook
	 * @throws SQLException
	 */
	public void updateFields(BorrowedBook borrowedBook) throws SQLException {
		PreparedStatement pstmt,pstmt2;
		try {
		Statement stmt = con.createStatement();
		pstmt = con.prepareStatement("UPDATE library_students.book SET CopiesInBorrow = CopiesInBorrow + 1, AvailableCopies = AvailableCopies - 1 WHERE CatalogNum = ?");
		pstmt.setString(1, getBookID(borrowedBook));
		pstmt.executeUpdate();
		pstmt.close();
		Statement stmt2 = con.createStatement();
		pstmt2 = con.prepareStatement("UPDATE library_students.student SET BorrowedBookNum = BorrowedBookNum + 1 WHERE StudentID = ?");
		pstmt2.setString(1, borrowedBook.getLenderTo());
		pstmt2.executeUpdate();
		pstmt2.close();
		}catch (SQLException ex) {
		ex.printStackTrace();
		}
	}
	


			
	/**
	 * this method is for getting the info for all readers 
	 * @author shamsdiab
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<User> getReaderInfo() throws SQLException {
		try {
			Statement stmt = con.createStatement();
			ArrayList<User> reader = new ArrayList<User>();
			ResultSet rs = stmt.executeQuery("SELECT StudentID,Password,PhoneNumber,Email,StudentName,StatusMembership,BorrowedBookNum,LateReturningNum,Permission FROM library_students.student WHERE Permission= '2' ");
			while (rs.next()) {
				User user = new User();
				user.setUserName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setPhoneNumber(rs.getString(3));
				user.setEmail(rs.getString(4)); 
				user.setName(rs.getString(5));
				user.setStatusMembership(rs.getString(6));
				user.setBorrowedBookNum(rs.getInt(7));
				user.setLateReturningNum(rs.getInt(8));
				int permission = rs.getInt(9);
				UserPermission type = UserPermission.getByValue(permission);
				user.type = type;

				

				reader.add(user);
			}
			
			rs.close();
			return reader;
		
		    } catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		
	}
	
	
	/**
	 * this method is for getting the workers records 
	 * @author shamsdiab
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<User> getEmployeeInfo() throws SQLException {
		try {
			Statement stmt = con.createStatement();
			ArrayList<User> Employee = new ArrayList<User>();
			ResultSet rs = stmt.executeQuery("SELECT StudentID,Password,PhoneNumber,Email,StudentName,StatusMembership FROM library_students.student WHERE Permission= '3' ");
			while (rs.next()) {
				User user = new User();
				user.setUserName(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setPhoneNumber(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setName(rs.getString(5));
				user.setStatusMembership(rs.getString(6));
				Employee.add(user);
			}
			rs.close();
			return Employee;
		
		    } catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		
	}
	
	/**
	 * this method is for getting Librarians Emails
	 * @author Monera
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getLibrarianEmail() throws SQLException {
		try {
			Statement stmt = con.createStatement();
			ArrayList<String> Emails = new ArrayList<String>();
			ResultSet rs = stmt.executeQuery("SELECT Email FROM library_students.student WHERE Permission= '3' ");
			while (rs.next()) {
				Emails.add(rs.getString("Email")); 
			}
			
			rs.close();
			return Emails;
		
		    } catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		
	}
	
	/**
	 * this method gets the activity report
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getActivityReport() throws SQLException{
		Integer Active = new Integer(0);
		Integer Frozen = new Integer(0);
		Integer Locked = new Integer(0);
		Integer LateReturning = new Integer(0);
		Integer CopiesNum = new Integer(0);
		ArrayList<Integer> Arguments = new ArrayList<Integer>();
		PreparedStatement pstmt;
		try {
			SimpleDateFormat format = new SimpleDateFormat("MM.yyyy");
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MONTH, -1);
		    String mounth = (format.format(cal.getTime()));
		    
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT count(StudentID) FROM library_students.student WHERE StatusMembership='Locked'");
			if(rs.next()) 
			{ 
				Locked=rs.getInt(1);
			    Arguments.add(Locked);
			}
			
			rs = stmt.executeQuery("SELECT count(StudentID) FROM library_students.student WHERE StatusMembership='Frozen'");
			if(rs.next()) 
			{
				Frozen=rs.getInt(1);
			    Arguments.add(Frozen);
			}
			
			rs = stmt.executeQuery("SELECT count(StudentID) FROM library_students.student WHERE LateReturningNum>0");
			if(rs.next())
			{
				LateReturning=rs.getInt(1);
			    Arguments.add(LateReturning);
			}
			
			 rs = stmt.executeQuery("SELECT count(StudentID) FROM library_students.student WHERE StatusMembership='Active'");
			if(rs.next()) 
			{
				Active=rs.getInt(1);
				Arguments.add(Active);
			}
			
			rs = stmt.executeQuery("SELECT count(*) FROM library_students.copies");
			if(rs.next()) 
			{
				CopiesNum=rs.getInt(1);
			    Arguments.add(CopiesNum);
			}
			rs = stmt.executeQuery("SELECT * FROM library_students.activity_report WHERE ReportMonth='" + mounth +"'");
			if(rs.next())
			{
				rs.close();
		        return Arguments;
			}
			else
			{
			pstmt = con.prepareStatement("INSERT INTO library_students.activity_report(ReportMonth,ActiveMembership,FreezeMembership,LockedMembership,Copies,LateReaders)VALUES(?,?,?,?,?,?)");
			pstmt.setString(1,mounth);
			pstmt.setInt(2,Active);
			pstmt.setInt(3,Frozen);
			pstmt.setInt(4,Locked);
			pstmt.setInt(5,CopiesNum);
			pstmt.setInt(6,LateReturning);
			pstmt.executeUpdate();
			}
			rs.close();
	        return Arguments;
		    } catch (Exception ex) {
				ex.printStackTrace();
				 return null;
			}
		
	}

	/**
	 * this method gets the borrowing report
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getBorrowingReport() throws SQLException{
		SimpleDateFormat format = new SimpleDateFormat("MM.yyyy");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
	    String mounth = (format.format(cal.getTime()));
		ArrayList<Integer> DurationOfBorrowingInDemandBooks = new ArrayList<Integer>();
		ArrayList<Integer> DurationOfBorrowingNotInDemandBooks = new ArrayList<Integer>();
		int avg1,avg2;
		int sum1=0,sum2=0,median1,median2;
		int[] arr1 = new int[10];
		int[] arr2 = new int[10];
		PreparedStatement pstmt;
	   try {		
		   Statement stmt = con.createStatement();
		   ResultSet rs = stmt.executeQuery("SELECT C.BorrowDate, R.ReturningDate FROM library_students.book AS B JOIN  library_students.borrowedcopies AS C JOIN library_students.returning_book AS R WHERE C.CatalogNum=B.CatalogNum AND R.CatalogNum=B.CatalogNum AND B.InDemand=1 AND c.Barcode=R.Barcode AND C.Deadline=R.Deadline");
		   while(rs.next())
		   {
			   System.out.println(rs.getString(2) +"-------1--------"+ rs.getString(1));
			   DurationOfBorrowingInDemandBooks.add(duration(rs.getString(2),rs.getString(1)));
			   sum1+=duration(rs.getString(2),rs.getString(1));
		   }
		   
		   rs = stmt.executeQuery("SELECT C.BorrowDate, R.ReturningDate,B.CatalogNum FROM library_students.book AS B JOIN  library_students.borrowedcopies AS C JOIN library_students.returning_book AS R WHERE C.CatalogNum=B.CatalogNum AND R.CatalogNum=B.CatalogNum AND B.InDemand=0 AND c.Barcode=R.Barcode AND C.Deadline=R.Deadline");
		   while(rs.next())
		   {
			  System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"+rs.getString(3));
			   DurationOfBorrowingNotInDemandBooks.add(duration(rs.getString(2),rs.getString(1)));
			   sum2+=duration(rs.getString(2),rs.getString(1));
		   }
		  
		   if(DurationOfBorrowingInDemandBooks.size() == 0)
		   {
			  avg1=0;
		      median1=0;
		   }
		   else
		   {
			  avg1=sum1/DurationOfBorrowingInDemandBooks.size();
			  Collections.sort(DurationOfBorrowingInDemandBooks);
			  median1=DurationOfBorrowingInDemandBooks.get(DurationOfBorrowingInDemandBooks.size()/2);
		      
		   }
		   if(DurationOfBorrowingNotInDemandBooks.size() == 0)
		   {
		      avg2=0;
		      median2=0;
		   }
		   else
		   {
		      avg2=sum2/DurationOfBorrowingNotInDemandBooks.size();
		      Collections.sort(DurationOfBorrowingNotInDemandBooks);
		      median2=DurationOfBorrowingNotInDemandBooks.get(DurationOfBorrowingNotInDemandBooks.size()/2);
		   }
		   
		 
		   for(int i=0;i<DurationOfBorrowingInDemandBooks.size();i++)
		   {
			   if(DurationOfBorrowingInDemandBooks.get(i)<100)
			   arr1[DurationOfBorrowingInDemandBooks.get(i)/10]++;
			   else
				   arr1[9]++;
		   }
		   
		   for(int i=0;i<DurationOfBorrowingNotInDemandBooks.size();i++)
		   {
			   if(DurationOfBorrowingNotInDemandBooks.get(i)<100)
			   arr2[DurationOfBorrowingNotInDemandBooks.get(i)/10]++;
			   else
				   arr2[9]++;
		   }
		   
		   ArrayList<Integer> arguments =new ArrayList<Integer>();
		   arguments.add(avg1);
		   arguments.add(median1);
		   
		   for(int value : arr1 )
		   {
			   arguments.add(value);
		   }
		   arguments.add(avg2);
		   arguments.add(median2);
		   
		   for(int value : arr2)
		   {
			   arguments.add(value);
		   }
		   rs = stmt.executeQuery("SELECT * FROM library_students.borrowing_report WHERE ReportMonth='" + mounth +"' AND" + " BookType='InDemand'");
			if(rs.next())
			{
				rs.close();
		        return arguments;
			}
			else
			{
		   pstmt = con.prepareStatement("INSERT INTO library_students.borrowing_report(ReportMonth,BookType,Average,_0_9_days,_10_19_days,_20_29_days,_30_39_days,_40_49_days,_50_59_days,_60_69_days,_70_79_days,_80_89_days,_90_days_and_more,Median)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1,mounth);
			pstmt.setString(2,"InDemand");
			pstmt.setInt(3,avg1);
			pstmt.setInt(4,arr1[0]);
			pstmt.setInt(5,arr1[1]);
			pstmt.setInt(6,arr1[2]);
			pstmt.setInt(7,arr1[3]);
			pstmt.setInt(8,arr1[4]);
			pstmt.setInt(9,arr1[5]);
			pstmt.setInt(10,arr1[6]);
			pstmt.setInt(11,arr1[7]);
			pstmt.setInt(12,arr1[8]);
			pstmt.setInt(13,arr1[9]);
			pstmt.setInt(14,median1);
			pstmt.executeUpdate();
			}
			
			 rs = stmt.executeQuery("SELECT * FROM library_students.borrowing_report WHERE ReportMonth='" + mounth +"' AND" + " BookType='Regular'");
				if(rs.next())
				{
					rs.close();
			        return arguments;
				}
				else
				{
			   pstmt = con.prepareStatement("INSERT INTO library_students.borrowing_report(ReportMonth,BookType,Average,_0_9_days,_10_19_days,_20_29_days,_30_39_days,_40_49_days,_50_59_days,_60_69_days,_70_79_days,_80_89_days,_90_days_and_more,Median)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1,mounth);
				pstmt.setString(2,"Regular");
				pstmt.setInt(3,avg2);
				pstmt.setInt(4,arr2[0]);
				pstmt.setInt(5,arr2[1]);
				pstmt.setInt(6,arr2[2]);
				pstmt.setInt(7,arr2[3]);
				pstmt.setInt(8,arr2[4]);
				pstmt.setInt(9,arr2[5]);
				pstmt.setInt(10,arr2[6]);
				pstmt.setInt(11,arr2[7]);
				pstmt.setInt(12,arr2[8]);
				pstmt.setInt(13,arr2[9]);
				pstmt.setInt(14,median2);
				pstmt.executeUpdate();
				}
			rs.close();
		   return arguments;

		   
		    }catch(Exception ex) {
				ex.printStackTrace();
				return null;
		    }
	        }
	/**
	 * this method gets the duration 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public int duration(String d1, String d2) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int diff2 =0 ;
		try {
			 Date d11 = dateFormat.parse(d1);
			 Date d22 = dateFormat.parse(d2);
			 Long diff = new Long((d11.getTime() - d22.getTime())/(1000*24*60*60));
			 diff2 = diff.intValue();
		}catch(ParseException ex) {
			ex.printStackTrace();
	}
	 return diff2;
	}

	/**
	 * this method gets the late returning book report
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Integer> getLateReturningBookgReport() throws SQLException{
		    SimpleDateFormat format = new SimpleDateFormat("MM.yyyy");
		    Calendar cal = Calendar.getInstance();
		    cal.add(Calendar.MONTH, -1);
	        String mounth = (format.format(cal.getTime()));
	        ArrayList<Integer> durationOfLateReturningBooks = new ArrayList<Integer>();
	        ArrayList<Integer> CountForAllBooks = new ArrayList<Integer>();
	        int sum=0,avg1,avg2,median1,median2;
	        int numOfBooks=0;
	        int[] arr1 = new int[10];
			int[] arr2 = new int[10];
			PreparedStatement pstmt;
		   try {
			   Statement stmt = con.createStatement();
			   ResultSet rs = stmt.executeQuery("SELECT ret.ReturningDate,ret.Deadline FROM library_students.returning_book AS ret join library_students.borrowedcopies AS bo WHERE ret.Barcode=bo.Barcode AND ret.Deadline=bo.Deadline AND ret.isLateReturn='true'");
			   
			   while(rs.next())
			   {
				   durationOfLateReturningBooks.add(duration(rs.getString(1),rs.getString(2)));
				   sum+=duration(rs.getString(1),rs.getString(2));
			   }
			   if(durationOfLateReturningBooks.size() == 0)
			   {
				   avg1=0;
				   median1=0;
			   }
			   else
			   {
				   avg1=sum/durationOfLateReturningBooks.size();
				   Collections.sort(durationOfLateReturningBooks);
				   median1=durationOfLateReturningBooks.get(durationOfLateReturningBooks.size()/2);
			   }
			   
			   rs = stmt.executeQuery("SELECT count(ret.Barcode) AS Count FROM library_students.returning_book AS ret WHERE ret.isLateReturn='true' GROUP BY ret.CatalogNum HAVING count(*)>0");
			   while(rs.next())
			   {
				   CountForAllBooks.add(rs.getInt(1));
				   sum+=rs.getInt(1);
			   }

			   rs = stmt.executeQuery("SELECT count(*) FROM library_students.book");
			   if(rs.next())
			   	   numOfBooks=rs.getInt(1);
			   if(numOfBooks == 0)
			   {
				   avg2=0;
				   median2=0;
			   }
			   else
			   {
			   avg2=sum/numOfBooks;
			   Collections.sort(CountForAllBooks);
			   median2=CountForAllBooks.get(CountForAllBooks.size()/2);
			   }
	
			   for(int i=0;i<durationOfLateReturningBooks.size();i++)
			   {
				   if(durationOfLateReturningBooks.get(i)<100)
				   arr1[durationOfLateReturningBooks.get(i)/10]++;
				   else
					   arr1[9]++;
			   }

			   for(int i=0;i<CountForAllBooks.size();i++)
			   {
				   if(CountForAllBooks.get(i)<100)
				   arr2[CountForAllBooks.get(i)/10]++;
				   else
					   arr2[9]++;
			   }
			   
			   ArrayList<Integer> arguments= new ArrayList<Integer>();
			   
			   
			   arguments.add(avg2);
			   arguments.add(median2);
			   
			   for(int value : arr2 )
			   {
				   arguments.add(value);
			   }
			   arguments.add(avg1);
			   arguments.add(median1);

			   for(int value : arr1)
			   {
				   arguments.add(value);
			   }
			   
			   
			   rs = stmt.executeQuery("SELECT * FROM library_students.late_returning_report WHERE ReportMonth='" + mounth +"' AND" + " ActionType='CountOfLateReturning'");
				if(rs.next())
				{	

					rs.close();
			        return arguments;
				}
				else
				{

			    pstmt = con.prepareStatement("INSERT INTO library_students.late_returning_report(ReportMonth,ActionType,Average,Median,_0_9_days,_10_19_days,_20_29_days,_30_39_days,_40_49_days,_50_59_days,_60_69_days,_70_79_days,_80_89_days,_90_AndMoreDays)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstmt.setString(1,mounth);
				pstmt.setString(2,"CountOfLateReturning");
				pstmt.setInt(3,avg1);
				pstmt.setInt(4,median1);
				pstmt.setInt(5,arr1[0]);
				pstmt.setInt(6,arr1[1]);
				pstmt.setInt(7,arr1[2]);
				pstmt.setInt(8,arr1[3]);
				pstmt.setInt(9,arr1[4]);
				pstmt.setInt(10,arr1[5]);
				pstmt.setInt(11,arr1[6]);
				pstmt.setInt(12,arr1[7]);
				pstmt.setInt(13,arr1[8]);
				pstmt.setInt(14,arr1[9]);
				pstmt.executeUpdate();
				}

				 rs = stmt.executeQuery("SELECT * FROM library_students.late_returning_report WHERE ReportMonth='" + mounth +"' AND" + " ActionType='DurationOfLateReturning'");
					if(rs.next())
					{		

						rs.close();
				        return arguments;
					}
					else
					{			

				   pstmt = con.prepareStatement("INSERT INTO library_students.late_returning_report(ReportMonth,ActionType,Average,Median,_0_9_days,_10_19_days,_20_29_days,_30_39_days,_40_49_days,_50_59_days,_60_69_days,_70_79_days,_80_89_days,_90_AndMoreDays)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					pstmt.setString(1,mounth);
					pstmt.setString(2,"DurationOfLateReturning");
					pstmt.setInt(3,avg2);
					pstmt.setInt(4,median2);
					pstmt.setInt(5,arr2[0]);
					pstmt.setInt(6,arr2[1]);
					pstmt.setInt(7,arr2[2]);
					pstmt.setInt(8,arr2[3]);
					pstmt.setInt(9,arr2[4]);
					pstmt.setInt(10,arr2[5]);
					pstmt.setInt(11,arr2[6]);
					pstmt.setInt(12,arr2[7]);
					pstmt.setInt(13,arr2[8]);
					pstmt.setInt(14,arr2[9]);
					pstmt.executeUpdate();
					}
			   
				   return arguments;
			}catch(Exception ex) {
				ex.printStackTrace();
				return null;		
			}
	}

	/**
	 * this method gets the late returning books
	 * @return ArrayList<Book>
	 */
	public  ArrayList<Book> getLateBooks() {
		SimpleDateFormat format = new SimpleDateFormat("MM.yyyy");
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.MONTH, -1);
        String mounth = (format.format(cal.getTime()));
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<Book> books=new ArrayList<Book>();
		   try {
			   System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");

			   Statement stmt = con.createStatement();
			   ResultSet rs = stmt.executeQuery("SELECT ret.Deadline, ret.ReturningDate, ret.BookName FROM library_students.returning_book AS ret WHERE ret.isLateReturn='true'");
			   System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");

			   while(rs.next())
			   {
				   if(!map.containsKey(rs.getString(3)))
				       map.put(rs.getString(3),duration(rs.getString(2),rs.getString(1)));
				   else
					   {
					   	int x=map.get(rs.getString(3));
					   	x+=duration(rs.getString(2),rs.getString(1));
					    map.put(rs.getString(3),x);
					   }
				   
				   
			   }
			   System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");

			   rs = stmt.executeQuery("SELECT ret.BookName, count(ret.Barcode) AS Count FROM library_students.returning_book AS ret WHERE ret.isLateReturn='true' GROUP BY ret.CatalogNum");
			   System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");

			   while(rs.next())
			   {
				   int avg=map.get(rs.getString(1))/rs.getInt(2);
				   
				   books.add(new Book(rs.getString(1),rs.getInt(2),avg));
			   }
			   System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");

				PreparedStatement pstmt;

				rs = stmt.executeQuery("SELECT * FROM library_students.late_books_by_date WHERE month='" + mounth +"'");
				if(rs.next())
				{		

					rs.close();
			        return books;
				}
				else
				{	
				
				for(Book book : books) {
			   pstmt = con.prepareStatement("INSERT INTO library_students.late_books_by_date(month, AverageOfDuration, CountOfCopies,BookName)VALUES(?,?,?,?)");
				pstmt.setString(1,mounth);
				pstmt.setInt(2,book.getAvergeDurationLatness());
				pstmt.setInt(3,book.getCountLatness());
				pstmt.setString(4,book.getBookName());
				pstmt.executeUpdate();
				}
				   System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF");

			   return books;
			   
		   }
		   }catch(Exception ex) {
			   ex.printStackTrace();
				return null;
		   }
		
	}
	
	
	//______________________________________________________________________________________________________________________________
	
	/**
	 * this method gets the date of the report 
	 * @param report
	 * @return
	 * @throws SQLException
	 */
public ArrayList<String> getComboBox(String report) throws SQLException{
		
		
		try {
		
		Statement stmt = con.createStatement();
		ArrayList<String> dates = new ArrayList<String>();
		ResultSet rs = stmt.executeQuery("SELECT distinct ReportMonth  FROM library_students."+report);
		while (rs.next()) {
			

			dates.add(rs.getString(1));
		}
		
		
		rs.close();
		return dates;
	
	    } catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
}
		
	/**
	 * this method gets the activity report
	 * @param requested
	 * @return
	 * @throws SQLException
	 */
public ArrayList<Integer> getActivityReport(String requested) throws SQLException{
		
		
		try {
		
		Statement stmt = con.createStatement();
		ArrayList<Integer> data = new ArrayList<Integer>();
		ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.activity_report WHERE ReportMonth='"+requested+"'");
		if (rs.next()) {
			

			data.add(rs.getInt(2));
			data.add(rs.getInt(3));
			data.add(rs.getInt(4));
			data.add(rs.getInt(5));
			data.add(rs.getInt(6));
		}
		else
		{
			return null;
		}
		
		
		rs.close();
		return data;
	
	    } catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
}

/**
 * this method gets the borrowing report
 * @param date
 * @return
 */
   public ArrayList<Integer> getBorrowingReport(String date) {
     try {
		
		Statement stmt = con.createStatement();
		ArrayList<Integer> data = new ArrayList<Integer>();
		ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.borrowing_report WHERE ReportMonth='"+date+"' AND BookType='InDemand'");
		if(rs.next()) {
			data.add(rs.getInt(3));
			data.add(rs.getInt(14));
			data.add(rs.getInt(4));
			data.add(rs.getInt(5));
			data.add(rs.getInt(6));
			data.add(rs.getInt(7));
			data.add(rs.getInt(8));
			data.add(rs.getInt(9));
			data.add(rs.getInt(10));
			data.add(rs.getInt(11));
			data.add(rs.getInt(12));
			data.add(rs.getInt(13));
		}
			rs = stmt.executeQuery("SELECT * FROM library_students.borrowing_report WHERE ReportMonth='"+date+"' AND BookType='Regular'");
			if(rs.next()) {

			data.add(rs.getInt(3));
			data.add(rs.getInt(14));
			data.add(rs.getInt(4));
			data.add(rs.getInt(5));
			data.add(rs.getInt(6));
			data.add(rs.getInt(7));
			data.add(rs.getInt(8));
			data.add(rs.getInt(9));
			data.add(rs.getInt(10));
			data.add(rs.getInt(11));
			data.add(rs.getInt(12));
			data.add(rs.getInt(13));
			}
		rs.close();
		if(data.size()  == 0)
			return null;
		return data;
	
	    } catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
     }

   /**
    * this method gets the lare returning book report 
    * @param date
    * @return
    */
   public ArrayList<Integer> getLateReturningBookgReport(String date) {
	   
	   
	   try {
		   System.out.println("11111111111111111111111111111111111111");
			Statement stmt = con.createStatement();
			ArrayList<Integer> data = new ArrayList<Integer>();
			ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.late_returning_report WHERE ReportMonth='"+date+"' AND ActionType='CountOfLateReturning'");
			   System.out.println("222222222222222222222222222222222222");

			if(rs.next()) {

				data.add(rs.getInt(3));
				data.add(rs.getInt(4));
				data.add(rs.getInt(5));
				data.add(rs.getInt(6));
				data.add(rs.getInt(7));
				data.add(rs.getInt(8));
				data.add(rs.getInt(9));
				data.add(rs.getInt(10));
				data.add(rs.getInt(11));
				data.add(rs.getInt(12));
				data.add(rs.getInt(13));
				data.add(rs.getInt(14));
			}
			   System.out.println("33333333333333333333333333333333333333333333");

				rs = stmt.executeQuery("SELECT * FROM library_students.late_returning_report WHERE ReportMonth='"+date+"' AND ActionType='DurationOfLateReturning'");
				if(rs.next()) {

				data.add(rs.getInt(3));
				data.add(rs.getInt(4));
				data.add(rs.getInt(5));
				data.add(rs.getInt(6));
				data.add(rs.getInt(7));
				data.add(rs.getInt(8));
				data.add(rs.getInt(9));
				data.add(rs.getInt(10));
				data.add(rs.getInt(11));
				data.add(rs.getInt(12));
				data.add(rs.getInt(13));
				data.add(rs.getInt(14));
				}
				   System.out.println("444444444444444444444444444444444444444");

			rs.close();
			if(data.size()==0)
				return null;
			return data;
		
		    } catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}	  
}

   /**
    * this method is for getting the late books
    * @param date
    * @return
    */
public ArrayList<Book> getLateBooks(String date) {
	
	ArrayList<Book> books=new ArrayList<Book>();

	try {
		   System.out.println("5555555555555555555555555555555");

	Statement stmt = con.createStatement();
	ResultSet rs = stmt.executeQuery("SELECT * FROM library_students.late_books_by_date WHERE month='"+date+"'");
	   System.out.println("666666666666666666666666666666666666");

	while(rs.next())
	{
		Book b=new Book();
		b.setAvergeDurationLatness(2);
		b.setCountLatness(3);
		b.setBookName(rs.getString(4));
		books.add(b);
		
	}
	   System.out.println("777777777777777777777777777777777777777777");

	rs.close();
	return books;
	} catch (SQLException ex) {
	ex.printStackTrace();
	return null;
	}

   }

/**
 * this method is for editing details for the reader
 * @param user
 * @throws SQLException
 */
public void managerEditDetailsForReader(User user) throws SQLException {// on edit the detils
	PreparedStatement pstmt;
	try {
		pstmt = con.prepareStatement("UPDATE library_students.student SET StatusMembership=?,Operation=?,Freeze=?,isLock=?,Permission=? WHERE StudentID= ?");
		

		pstmt.setString(1, user.getStatusMembership());
		pstmt.setString(2, user.getOperation());
		pstmt.setString(3, user.getFreeze());
		pstmt.setString(4, user.getIsLock());
		int permission=Integer.parseInt(user.getPermission());
		pstmt.setInt(5, permission);
		pstmt.setString(6, user.getUserName());
		pstmt.executeUpdate();
		
		System.out.println("444444444444444444444444444444444444444444444444444");
		if(user.getChangeLateretuningBooksToZero()==1)
		{
			System.out.println("777777777777777777777777777777777777777777777");

			pstmt = con.prepareStatement("UPDATE library_students.student SET LateReturningNum=? WHERE StudentID= ?");
			pstmt.setInt(1, 0);
			pstmt.setString(2, user.getUserName());
			pstmt.executeUpdate();
			System.out.println("8888888888888888888888888888888888888888888888888888");


		}
	} catch (SQLException ex) {
		ex.printStackTrace();
	}

}

/**
 * this method inserts the details for both requester and the one who's making it 
 * @param Requester
 * @param Maker
 * @param book
 */
public void insertDetailsForBothRequesterAndMaker(User Requester, User Maker ,BorrowedBook book) {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
    String date1=sdf.format(date);
	PreparedStatement pstmt;
	try {
		pstmt = con.prepareStatement("INSERT INTO library_students.details_manual_extend(LibrarianID,DateOfExtend,ReaderID,BookName)VALUES(?, ?, ?,?)");
	    pstmt.setString(1,Maker.getUserName());
	    pstmt.setString(2,date1);
	    pstmt.setString(3,Requester.getUserName());
	    pstmt.setString(4,book.getBookName());
        pstmt.executeUpdate();
	    }catch (SQLException ex) {
			ex.printStackTrace();
		}
	
}

/**
 * this method is for viewing the PDF file
 * @param book
 * @return
 */
public File viewPDFfile(Book book) {
	
	Statement stmt;
	ResultSet rs = null;
	System.out.println("catalog number: " + book.getCatalogNum());
	try {
		File file = new File("tableOFcontent"+book.getCatalogNum()+".pdf");
		System.out.println("Hola1");
		FileOutputStream fos = new FileOutputStream(file);
		System.out.println("Hola2");
		stmt = con.createStatement();
		rs = stmt.executeQuery("SELECT filepdf from library_students.book where CatalogNum = " + "'" + book.getCatalogNum() + "'");

		System.out.println("Hola3");
		

		while (rs.next()) {
			System.out.println("Hola4");

			InputStream input = rs.getBinaryStream("filepdf");
			
			byte[] buffer = new byte[1024];

		    while (input.read(buffer) > 0) {
		        fos.write(buffer);
		    }
		}
		Desktop desktop = null;
	      
		
        if(Desktop.isDesktopSupported()){
        	 desktop = Desktop.getDesktop();
        }
		
		if(file.exists())
			desktop.open(file);
		System.out.println("Saved to file: " + file.getAbsolutePath());
		return file;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	return null;
	
}

/**
 * this method is for getting the readers who didn't return the book 
 * @author Monera
 * @return
 */
public ArrayList<User> getReaderWhoDidntReturn(){
	try {
		Statement stmt = con.createStatement();
		ArrayList<User> reader = new ArrayList<User>();
		ResultSet rs = stmt.executeQuery("SELECT StudentID,Status,Barcode,CatalogNum,Deadline FROM library_students.borrowfreeze");
		while (rs.next()) {
			User user = new User();
			user.setUserName(rs.getString(1));
			user.setProblem(rs.getString(2));
			user.setBarcode(rs.getString(3));
			user.setCatalogNum(rs.getString(4)); 
			user.setDeadline(rs.getString(5));

			reader.add(user);
		}
		
		rs.close();
		return reader;
	
	    } catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	
}

/**
 * this method is for changing the status 
 * @param ID
 */
public void changeStatus(String ID) {
	


PreparedStatement pstmt;
try {
	pstmt = con.prepareStatement(
			"UPDATE library_students.student SET StatusMembership=? ,Freeze=? WHERE StudentID=?");
	pstmt.setString(1, "Frozen");
	pstmt.setString(2, "true");
	pstmt.setString(3, ID);
	pstmt.executeUpdate();
	pstmt.close();
} catch (SQLException e) {

	e.printStackTrace();
}

}

/**
 * this method is for getting the readers who didn't return the book in time up to 3 times
 * @author Mickey
 * @return
 */
public ArrayList<User> getThreeDaysLate(){
	try {
		Statement stmt = con.createStatement();
		ArrayList<User> reader = new ArrayList<User>();
		ResultSet rs = stmt.executeQuery("SELECT StudentID,Status FROM library_students.threetimes_latereturns");
		while (rs.next()) {
			User user = new User();
			user.setUserName(rs.getString(1));
			user.setProblem(rs.getString(2));

			reader.add(user);
		}
		
		rs.close();
		return reader;
	
	    } catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	
}

/**
 * this method is for changing the status to lock 
 * @param ID
 */
public void changeStatusLock(String ID) {
	


PreparedStatement pstmt;
try {
	pstmt = con.prepareStatement(
			"UPDATE library_students.student SET StatusMembership=?,isLock=? WHERE StudentID=?");
	pstmt.setString(1, "Locked");
	pstmt.setString(2, "true");
	pstmt.setString(3, ID);
	pstmt.executeUpdate();
	pstmt.close();
} catch (SQLException e) {

	e.printStackTrace();
}

}

/**
 * this method if for deleting the user from the list when we change his status  
 * @param ID
 */
public void deleteTheUser(String ID) {
	try {
	PreparedStatement pst = con.prepareStatement("Delete from library_students.borrowfreeze where StudentID=?");
	pst.setString(1,ID );
	pst.executeUpdate();
	pst.close();
	} catch (SQLException e) {

		e.printStackTrace();
	}
}

/**
 * this method is for deleting the user from the list when we change his status
 * @param ID
 */
public void deleteTheReader(String ID) {
	try {
		PreparedStatement pst = con.prepareStatement("Delete from library_students.threetimes_latereturns where StudentID=?");
		pst.setString(1,ID );
		pst.executeUpdate();
		pst.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
}




}
	
	


	
	
	
		


	
	

