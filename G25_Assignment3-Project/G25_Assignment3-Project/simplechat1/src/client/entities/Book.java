package client.entities;

import java.io.FileInputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

import java.io.File;
import java.io.FileInputStream;


/**
 * 
 * Book This is a Book entity includes this variables like the Book fields,
 * 
 * @author Monera
 *
 */
public class Book implements Serializable {

	/**
	 * 
	 * Serial version unique ID, necessary due to the class implements
	 * {@link Serializable}
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String CatalogNum;
	private String BookName;
	private String Author;
	private String EditionNum;
	private String printingDate;
	private String Subject;
	private String Description;
	private int CopiesNum;
	private String PurchaseDate;
	private String ShelfNum;
	private int CopiesInBorrow;
	private int NumOfOrder;
	private int AvailableCopies;
	private int InDemand;
	private int AvergeDurationLatness;
	private int countLatness;
	
	

	
	private String borrowingDate;
	private String returningDate;
	private File tableOfContent;
	
	private byte[] byteArray;
	

	private FileInputStream fis;


	

	public ArrayList<Book> BooksList = new ArrayList<Book>();

	public String Barcode; 

	
	public Book() {

	}
	
	/**
	 * @author Karam
	 * @param BookName
	 */
	public Book(String BookName) {
		this.BookName=BookName;
	}
	
	/**
	 * @author Karam
	 * @param CatalogNum
	 */
	public void BookCatalog(String CatalogNum) {
		setCatalogNum(CatalogNum);
	}
	
	/**
	 * @author Monera
	 * @param Bookname
	 * @param AvailableCopies
	 * @param shelfNum
	 */
	public Book(String CatalogNum,String Bookname,String Author,String Subject, int AvailableCopies, String shelfNum) {
		this.CatalogNum=CatalogNum;
		this.BookName = Bookname;
		this.Author=Author;
		this.Subject=Subject;
		this.AvailableCopies = AvailableCopies;
		this.ShelfNum = shelfNum; 
	}
	
	public Book(String CatalogNum,String Bookname,String Author,String Subject, int AvailableCopies, String shelfNum,String Description) {
		this.CatalogNum=CatalogNum;
		this.BookName = Bookname;
		this.Author=Author;
		this.Subject=Subject;
		this.AvailableCopies = AvailableCopies;
		this.ShelfNum = shelfNum; 
		this.Description=Description;
	}
	
	/**
	 * @author Monera
	 * @param Author
	 * @param AvailableCopies
	 * @param shelfNum
	 * @return 
	 */
	public void BookAuthor(String name,String Author,String Subject, int AvailableCopies, String shelfNum,String CatalogNum) {
		setBookName(name);
		setAuthor(Author);
		setSubject(Subject);
		setAvailableCopies(AvailableCopies);
		setShelfNum(shelfNum);
		setCatalogNum(CatalogNum);
	}
	
	
	
	/**
	 * @author Monera
	 * @param Subject
	 * @param AvailableCopies
	 * @param shelfNum
	 */
	public void BookSubject(String name,String Author,String Subject, int AvailableCopies, String shelfNum,String CatalogNum ) {
		setBookName(name);
		setAuthor(Author);
		setSubject(Subject);
		setAvailableCopies(AvailableCopies);
		setShelfNum(shelfNum);
		setCatalogNum(CatalogNum);
	}


	/***
	 * @author Monera
	 * @param CatalogNum
	 * @param BookName
	 * @param Author
	 * @param EditionNum
	 * @param printingDate
	 * @param Subject
	 * @param Description
	 * @param CopiesNum
	 * @param PurchaseDate
	 * @param ShelfNum
	 * @param CopiesInBorrow
	 * @param NumOfOrder
	 * @param AvailableCopies
	 * @param BooksList
	 * 
	 */
	public Book(String CatalogNum, String BookName, String Author, String EditionNum, String printingDate,
			String Subject, String Description, int CopiesNum, String PurchaseDate, String ShelfNum,
			int CopiesInBorrow, int NumOfOrder, int AvailableCopies, ArrayList<Book> BooksList) {
		this.CatalogNum = CatalogNum;
		this.BookName = BookName;
		this.Author = Author;
		this.EditionNum = EditionNum;
		this.printingDate = printingDate;
		this.Subject = Subject;
		this.Description = Description;
		this.CopiesNum = CopiesNum;
		this.PurchaseDate = PurchaseDate;
		this.ShelfNum = ShelfNum;
		this.CopiesInBorrow = CopiesInBorrow;
		this.NumOfOrder = NumOfOrder;
		this.AvailableCopies = AvailableCopies;
		this.BooksList = BooksList;

	}

	/***
	 * @author Rania
	 * @param CatalogNum
	 * @param BookName
	 * @param Author
	 * @param EditionNum
	 * @param printingDate
	 * @param Subject
	 * @param Description
	 * @param CopiesNum
	 * @param PurchaseDate
	 * @param ShelfNum
	 * @param CopiesInBorrow
	 * @param NumOfOrder
	 * @param AvailableCopies
	 * 
	 */
	public void AddBookk(String CatalogNum,String BookName, String Author, String Subject,String printingDate,String EditionNumber,String Description,int CopiesNum,String ShelfNum,String purchaseDate) {
		setCatalogNum(CatalogNum);
		setBookName(BookName);
		setAuthor(Author);
		setEditionNum(EditionNum);
		setPrintingDate(printingDate);
		setSubject(Subject);
		setDescription(Description);
		setCopiesNum(CopiesNum);
		setPurchaseDate(PurchaseDate);
		setShelfNum(ShelfNum);
		setCopiesInBorrow(0);
		setNumOfOrder(0);
		setAvailableCopies(0);
	}
	


      /***
       * @author Rania
       * @param book
       */

       public void getBookk(Book book) {
	      setCatalogNum(book.getCatalogNum());
	      setBookName(book.getAuthor());
	      setAuthor(book.getAuthor());
	      setEditionNum(book.getEditionNum());
	     setPrintingDate(book.getPrintingDate());
	     setSubject(book.getSubject());
	     setDescription(book.getDescription());
	     setPurchaseDate(book.getDescription());
	     setShelfNum(book.getShelfNum());
	     setCopiesInBorrow(0);
	     setNumOfOrder(0);
	     setAvailableCopies(0);
	     setTableOfContent(book.getTableOfContent());
	     setByteArray(book.getByteArray());
	     setFis(book.getFis());
          }

	/***
	 * @author Monera
	 * @param CatalogNum
	 * @param BookName
	 * @param Author
	 * @param EditionNum
	 * @param printingDate
	 * @param Subject
	 * @param Description
	 * @param CopiesNum
	 * @param PurchaseDate
	 * @param ShelfNum
	 * @param CopiesInBorrow
	 * @param NumOfOrder
	 * @param AvailableCopies
	 * 
	 */
	public Book(String CatalogNum, String BookName, String Author, String EditionNum, String printingDate,
			String Subject, String Description, int CopiesNum, String PurchaseDate, String ShelfNum,
			int CopiesInBorrow, int NumOfOrder, int AvailableCopies) {
		super();
		this.CatalogNum = CatalogNum;
		this.BookName = BookName;
		this.Author = Author;
		this.EditionNum = EditionNum;
		this.printingDate = printingDate;
		this.Subject = Subject;
		this.Description = Description;
		this.CopiesNum = CopiesNum;
		this.PurchaseDate = PurchaseDate;
		this.ShelfNum = ShelfNum;
		this.CopiesInBorrow = CopiesInBorrow;
		this.NumOfOrder = NumOfOrder;
		this.AvailableCopies = AvailableCopies;
	

	}
	
	/**
	 * @author shams
	 * @param string
	 * @param int1
	 * @param avg
	 */
public Book(String string, int int1, int avg) {
		
		this.AvergeDurationLatness=avg;
		this.BookName=string;
		this.countLatness=int1;

	}

	public String getCatalogNum() {
		return CatalogNum;
	}

	public void setCatalogNum(String catalogNum) {
		CatalogNum = catalogNum;
	}

	public String getBookName() {
		return BookName;
	}

	public void setBookName(String bookName) {
		BookName = bookName;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}

	public String getEditionNum() {
		return EditionNum;
	}

	public void setEditionNum(String editionNum) {
		EditionNum = editionNum;
	}

	public String getPrintingDate() {
		return printingDate;
	}

	public void setPrintingDate(String printingDate) {
		this.printingDate = printingDate;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getCopiesNum() {
		return CopiesNum;
	}

	public void setCopiesNum(int i) {
		CopiesNum = i;
	}

	public String getPurchaseDate() {
		return PurchaseDate;
	}

	public void setPurchaseDate(String purchaseDate) {
		PurchaseDate = purchaseDate;
	}

	public String getShelfNum() {
		return ShelfNum;
	}

	public void setShelfNum(String shelfNum) {
		ShelfNum = shelfNum;
	}

	public int getCopiesInBorrow() {
		return CopiesInBorrow;
	}

	public void setCopiesInBorrow(int copiesInBorrow) {
		CopiesInBorrow = copiesInBorrow;
	}

	public int getNumOfOrder() {
		return NumOfOrder;
	}

	public void setNumOfOrder(int numOfOrder) {
		NumOfOrder = numOfOrder;
	}

	public int getAvailableCopies() {
		return AvailableCopies;
	}

	public void setAvailableCopies(int availableCopies) {
		AvailableCopies = availableCopies;
	}

	public ArrayList<Book> getBooksList() {
		return BooksList;
	}

	public void setBooksList(ArrayList<Book> booksList) {
		BooksList = booksList;
	}

	public String getBarcode() {
		return Barcode;
	}

	public void setBarcode(String barcode) {
		Barcode = barcode;
	}

	
	public String getBorrowingDate() {
		return borrowingDate;
	}

	public void setBorrowingDate(String borrowingDate) {
		this.borrowingDate = borrowingDate;
	}

	public String getReturningDate() {
		return returningDate;
	}

	public void setReturningDate(String returningDate) {
		this.returningDate = returningDate;
	}
	
	public File getTableOfContent() {
		return tableOfContent;
	}

	public void setTableOfContent(File tableOfContent) {
		this.tableOfContent = tableOfContent;
	}

	public byte[] getByteArray() {
		return byteArray;
	}

	public void setByteArray(byte[] byteArray) {
		this.byteArray = byteArray;
	}

	public FileInputStream getFis() {
		return fis;
	}

	public void setFis(FileInputStream fis) {
		this.fis = fis;
	}
	public int getInDemand() {
		return InDemand;
	}

	public void setInDemand(int inDemand) {
		InDemand = inDemand;
	}
	public int getAvergeDurationLatness() {
		return AvergeDurationLatness;
	}

	public void setAvergeDurationLatness(int avergeDurationLatness) {
		AvergeDurationLatness = avergeDurationLatness;
	}

	public int getCountLatness() {
		return countLatness;
	}

	public void setCountLatness(int countLatness) {
		this.countLatness = countLatness;
	}



}
