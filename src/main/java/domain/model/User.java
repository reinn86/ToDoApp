package domain.model;

import java.io.Serializable;

public class User implements Serializable{
	private String userName = null;
	
	public User() {}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
