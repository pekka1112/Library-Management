package Service;

import java.sql.ResultSet;
import java.util.List;

import dao.UserTBDAO;
import model.UserTB;

public class UserTBService {
	private UserTBDAO userTBDAO;

	public UserTBService() {
		userTBDAO = new UserTBDAO();
	}
	public List<UserTB> getUserHasntReturnedBook() { 
		return UserTBDAO.getUserHasntReturnedBook();
	}
	public static List<UserTB> getUserTBList() { 
		return UserTBDAO.getUserTBList();
	}
	public static ResultSet getUserStillInDebt() { 
		return UserTBDAO.getUserStillInDebt();
	}
	public static ResultSet getMostBorrowedUser() { 
		return UserTBDAO.getMostBorrowedUser();
	}
}
