package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoanDAO {
	public static ResultSet getLoanList() {
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT l.id , l.userTb_id , u.fullname, l.loan_date\r\n"
				+ "FROM Loan l JOIN UserTb u ON l.userTb_id = u.id ";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	public static ResultSet getLoanDetail(int loanID) {
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT l.id, b.id book_id, b.title, ld.quantity,l.loan_date, ld.due_date, ld.return_date\r\n"
				+ "FROM Loan l JOIN LoanDetail ld ON l.id = ld.loan_id\r\n"
				+ "			JOIN Book b ON b.id = ld.book_id"
				+ " WHERE l.id = ?";
		try {
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, loanID);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
