package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class UserTBDAO {
	public static List<UserTB> getUserTBList() {
		List<UserTB> l  = new ArrayList<UserTB>();
		Connection connection = ConnectDatabase.getConnection();
		String sql = "SELECT * FROM UserTb";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				UserTB u = new UserTB();
				u.setId(rs.getInt(1));
				u.setFullname(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setEmail(rs.getString(4));
				u.setPhone(rs.getString(5));
				u.setDob(rs.getString(6));
				l.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	} 
	public static List<Account> getAccountList() { 
		List<Account> listAcc = new ArrayList<Account>();
		Connection connection = ConnectDatabase.getConnection();
		String sql = "SELECT id, passwd FROM UserTb";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Account acc = new Account();
				acc.setUsername(rs.getString(1));
				acc.setPassword(rs.getString(2));
				listAcc.add(acc);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listAcc;
	}
	public static List<Account> getAdminList() { 
		List<Account> listAdmin = new ArrayList<Account>();
		Connection connection = ConnectDatabase.getConnection();
		String sql = "SELECT u.id, u.passwd FROM UserTb u JOIN Admin a ON u.id = a.userTb_id";
		try {
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Account admin = new Account();
				admin.setUsername(rs.getString(1));
				admin.setPassword(rs.getString(2));
				listAdmin.add(admin);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listAdmin;
	}
	public static List<UserTB> getUserHasntReturnedBook() { 
		List<UserTB> users = new ArrayList<UserTB>();
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT u.*"
				+ "	FROM Loan l JOIN LoanDetail ld ON l.id = ld.loan_id\r\n"
				+ "				JOIN UserTb u ON u.id = l.userTb_id\r\n"
				+ "	WHERE  ( ld.return_date > ld.due_date or return_date IS NULL);";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				UserTB u = new UserTB();
				u.setId(rs.getInt(1));
				u.setFullname(rs.getString(2));
				u.setPassword(rs.getString(3));
				u.setEmail(rs.getString(4));
				u.setPhone(rs.getString(5));
				u.setDob(rs.getString(6));
				users.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	public static ResultSet getUserStillInDebt() { 
		
		Connection c = ConnectDatabase.getConnection();
		String sql = "select us.id, us.fullname, us.email, us.phone, us.dob, fc.create_date, fc.fine_amount\r\n"
				+ "	from UserTb us JOIN FineCollection fc ON us.id = fc.userTb_id\r\n"
				+ "	where fc.payment_status = 0 \r\n"
				+ "	group by us.id, us.fullname, us.email, us.phone, us.dob, fc.create_date, fc.fine_amount";
		try {
			Statement s = c.createStatement();
			return s.executeQuery(sql);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public static ResultSet getMostBorrowedUser() { 
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT TOP 5 u.id , u.fullname, u.email, u.phone, COUNT(ld.book_id) solanmuon\r\n"
				+ "	FROM Loan l JOIN LoanDetail ld ON l.id = ld.loan_id\r\n"
				+ "				JOIN UserTb u ON l.userTb_id = u.id\r\n"
				+ "				JOIN Book b ON ld.book_id = b.id \r\n"
				+ "	GROUP BY u.id, u.fullname, u.email, u.phone\r\n"
				+ "	ORDER BY COUNT(ld.book_id) ";
		try {
			Statement statement = c.createStatement();
			return statement.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		System.out.println(getAdminList().size());
	}
}
