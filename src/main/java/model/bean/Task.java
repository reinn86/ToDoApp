package model.bean;

import java.io.Serializable;

public class Task implements Serializable{
	private boolean isComplete = false;
	private String contents = null;
	private String term = null;
	private int id  = 0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Task() {}
	
	public String getContents() {
		return contents;
	}
	
	public String getTerm() {
		return term;
	}
	
	public boolean isComplete() {
		return isComplete;
	}
	
	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public void setTerm(String term) {
		this.term = term;
	}
}
