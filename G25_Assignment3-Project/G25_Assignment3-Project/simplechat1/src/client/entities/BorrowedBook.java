package client.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class BorrowedBook extends Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1204742475013223390L;
	private String borrowDate,lenderTo,lender,book,Copybarcode;
	
	private ArrayList<BorrowedBook> Books = new ArrayList<BorrowedBook>();
	
	public BorrowedBook()
	{
		
	}
	public BorrowedBook(String barcode)
	{
		this.Copybarcode=barcode;
	}
	
	public BorrowedBook(String lenderToText,String catalogNumber) {
		super();
		this.lenderTo=lenderToText;
		setCatalogNum(catalogNumber);
	}
	
	public BorrowedBook(String lenderText, String lenderToText,String barcodeText) {
		super();
		this.lender=lenderText;
		this.lenderTo=lenderToText;
		this.Copybarcode=barcodeText;
	}
	
	public String getCopybarcode() {
		return Copybarcode;
	}
	public void setCopybarcode(String copybarcode) {
		Copybarcode = copybarcode;
	}
	public String getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	public String getLenderTo() {
		return lenderTo;
	}
	public void setLenderTo(String lenderTo) {
		this.lenderTo = lenderTo;
	}
	public String getLender() {
		return lender;
	}
	public void setLender(String lender) {
		this.lender = lender;
	}
	public ArrayList<BorrowedBook> getBooks() {
		return Books;
	}
	public void setBooks(ArrayList<BorrowedBook> books) {
		Books = books;
	}
	

}
