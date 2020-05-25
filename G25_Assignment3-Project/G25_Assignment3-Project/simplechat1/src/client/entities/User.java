package client.entities;

import java.io.Serializable;
import java.util.ArrayList;

import client.controller.UserPermission;


/**
 * 
 * User This is an User entity includes this variables like the User fields,
 * <p>
 * Take the Type field from the enum in UserType class in client.controller,
 * 
 * @author Monera
 *
 */
public class User implements Serializable {
	/**
	 * 
	 */
	/**
	 * Serial version unique ID, necessary due to the class implements
	 * {@link Serializable}
	 */
	private static final long serialVersionUID = -7703076691895192808L;
	/***
	 * 
	 */
	private String UserID;//UserID
	public String Password;
	public String Name;
	public UserPermission type;
	private String PhoneNumber;
	private String Email;
	public String StatusMembership;
	public String Operation;
	public String Freeze;
	private String isLock;
	

	private int Status;
	public String Permission;
	public int BorrowedBookNum;
	public int LateReturningNum;
	private int SignificantClarification;
	private int OrderedBooksNum;

	private int changeLateretuningBooksToZero;


	public ArrayList<User> UsersList = new ArrayList<User>();

	
	private String problem;/////////change mickey
	private String Barcode; ////// change mickey
	private String Deadline;/////// change mickey
	private String CatalogNum;/////// change mickey
	
	
	
	// private int UserNumber;
	/***
	 * 
	 */

	public User() {

	}
	
	
	/***
	 * @author Monera
	 * @param name
	 * @param password
	 * 
	 */
	public User(String password, String name) {
		super();
		Password = password;
		UserID = name;
	}
	
	/***
	 * @author Monera
	 * @param name
	 * @param password
	 * @param type
	 * 
	 */
	public User(String name, String password, UserPermission type) {
		super();
		this.UserID = name;
		this.Password = password;
		this.type = type;
	}
	
	/***
	 * @author Monera
	 * @param name
	 * @param password
	 * @param type
	 * @param UsersList
	 */
	public User(String name, String password, UserPermission type, ArrayList<User> UsersList) {
		super();
		UserID = name;
		Password = password;

		this.type = type;
		
		this.UsersList = UsersList;
	}
	
	
	/***
	 * @author Shams
	 * @param UserName
	 * @param Name
	 * @param password
	 * @param type
	 * @param PhoneNumber
	 * @param Email
	 * @param StatusMembership
	 * @param Operation
	 * @param Freeze
	 * @param isLock
	 * @param Status
	 * @param BorrowedBookNum
	 * @param LateReturningNum
	 * @param SignificantClarification
	 */
	public User(String password, String userName, String name, String phoneNumber, String email) 
	{
		super();
		Password = password;
		UserID = userName;
		this.Name = name;
		this.type = UserPermission.Reader;
		this.PhoneNumber = phoneNumber;
		this.Email = email;
		this.StatusMembership = "Active";
		this.Operation = "ExtendBookRequest,ReturnBookRequest,LendingRequest";
		this.Freeze = "false";
		this.isLock = "false";
		this.Status = 0;
		this.BorrowedBookNum = 0;
		this.LateReturningNum = 0;
		this.SignificantClarification = 0;
		this.OrderedBooksNum=0;
	}
	



	public User(UserPermission type) {
		this.type = type;
	}
	
	
	public UserPermission getType() {
		return type;
	}
	

	/***
	 * 
	 * @return
	 */
	public ArrayList<User> getCustomerList() {
		return UsersList;
	}

	/***
	 * 
	 * @param customerList
	 */
	public void setCustomerList(ArrayList<User> customerList) {
		this.UsersList = customerList;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}



	public void setType(UserPermission type) {
		this.type = type;
	}

	public String getStatusMembership() {
		return StatusMembership;
	}


	public void setStatusMembership(String statusMembership) {
		StatusMembership = statusMembership;
	}


	public String getOperation() {
		return Operation;
	}


	public void setOperation(String operation) {
		Operation = operation;
	}


	public String getFreeze() {
		return Freeze;
	}


	public void setFreeze(String freeze) {
		Freeze = freeze;
	}


	public String getLock() {
		return isLock;
	}


	public void setLock(String islock) {
		isLock = islock;
	}


	public String getPermission() {
		return Permission;
	}


	public void setPermission(String permission) {
		Permission = permission;
	}


	public int getBorrowedBookNum() {
		return BorrowedBookNum;
	}


	public void setBorrowedBookNum(int borrowedBookNum) {
		BorrowedBookNum = borrowedBookNum;
	}


	public int getLateReturningNum() {
		return LateReturningNum;
	}


	public void setLateReturningNum(int lateReturningNum) {
		LateReturningNum = lateReturningNum;
	}


	public ArrayList<User> getUsersList() {
		return UsersList;
	}


	public void setUsersList(ArrayList<User> usersList) {
		UsersList = usersList;
	}
	
	public String getUserName() {
		return UserID;
	}


	public void setUserName(String userName) {
		UserID = userName;
	}


	public String getPhoneNumber() {
		return PhoneNumber;
	}


	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}


	public String getEmail() {
		return Email;
	}


	public void setEmail(String email) {
		Email = email;
	}


	public String getIsLock() {
		return isLock;
	}


	public void setIsLock(String isLock) {
		this.isLock = isLock;
	}


	public int getStatus() {
		return Status;
	}


	public void setStatus(int status) {
		Status = status;
	}


	public int getSignificantClarification() {
		return SignificantClarification;
	}


	public void setSignificantClarification(int significantClarification) {
		SignificantClarification = significantClarification;
	}


	public int getOrderedBooksNum() {
		return OrderedBooksNum;
	}



	public void setOrderedBooksNum(int orderedBooksNum) {
		OrderedBooksNum = orderedBooksNum;
		
	}


	public int getChangeLateretuningBooksToZero() {
		return changeLateretuningBooksToZero;
	}


	public void setChangeLateretuningBooksToZero(int changeLateretuningBooksToZero) {
		this.changeLateretuningBooksToZero = changeLateretuningBooksToZero;
	}

	public String getCatalogNum() {
		return CatalogNum;
	}


	public void setCatalogNum(String catalogNum) {
		CatalogNum = catalogNum;
	}


	public String getDeadline() {
		return Deadline;
	}


	public void setDeadline(String deadline) {
		Deadline = deadline;
	}


	public String getBarcode() {
		return Barcode;
	}


	public void setBarcode(String barcode) {
		Barcode = barcode;
	}


	public String getProblem() {
		return problem;
	}


	public void setProblem(String problem) {
		this.problem = problem;
	}





}
