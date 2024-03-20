package Service;

import java.sql.ResultSet;

import dao.LoanDAO;
import dao.LoanDetailDAO;

// dung chung cho LoanDAO va LoanDetailDAO
public class LoanService {
	private LoanDAO loanDAO;
	private LoanDetailDAO loanDetailDAO;
	public LoanService() {
		loanDAO = new LoanDAO();
		loanDetailDAO = new LoanDetailDAO();
	}
	public ResultSet getLoanList() { 
		return LoanDAO.getLoanList();
	}
	public ResultSet getLoanDetail(int id) { 
		return LoanDAO.getLoanDetail(id);
	}
}
