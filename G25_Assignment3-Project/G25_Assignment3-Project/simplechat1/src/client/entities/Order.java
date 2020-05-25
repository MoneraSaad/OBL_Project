
package client.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Order {
	private String studentID;
	private String catalogNum;
	private String bookName;
	private Date orderDate;
	private int orderStatus;

	public Order(String studentID, String catalogNum, String bookName) {
		this.setStudentID(studentID);
		this.setCatalogNum(catalogNum);
		this.setBookName(bookName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date date = new Date();
		this.setOrderDate(date);
		this.setOrderStatus(1);
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getCatalogNum() {
		return catalogNum;
	}

	public void setCatalogNum(String catalogNum) {
		this.catalogNum = catalogNum;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}

}
