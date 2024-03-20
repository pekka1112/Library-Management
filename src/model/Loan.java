package model;

public class Loan {
	private int id;
	private int userID;
	private String loanDate;
	public Loan() {
		super();
		this.id = id;
		this.userID = userID;
		this.loanDate = loanDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	
}
