package client.controller;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 
 * @author Monera This enum specifies the type of user
 */
public enum UserPermission implements Serializable {

	User(1),
	Reader(2),
	Librarian(3),
	Manager(4);
	



	  private static final HashMap<Integer,UserPermission> MY_MAP = new HashMap<Integer, UserPermission>();
	  
	  static {
	    for (UserPermission myEnum : values()) {
	      MY_MAP.put(myEnum.getValue(), myEnum);
	    }
	  }
	  
	  
	  private int value;

		/**
		 * constructor for this class
		 */
	  private UserPermission(int value) {
	    this.value = value;
	  }

	  public int getValue() {
	    return value;
	  }

	  public static UserPermission getByValue(int value) {
	    return MY_MAP.get(value);
	  }

	  public String toString() {
	    return name();
	  }


}