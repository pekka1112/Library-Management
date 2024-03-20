package dao;

import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class BookDAO {
	public List<MyBook> getBookList() {
		List<MyBook> l = new ArrayList<MyBook>();
		Connection c = ConnectDatabase.getConnection();
		String sql = "select * from Book";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MyBook b = new MyBook();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("stock_quantity"));
				l.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return l;
	}
	public static MyBook getBookByID(int id) {
		Connection c = ConnectDatabase.getConnection();
		String sql = "select * from Book where id = ?";
		try {
			PreparedStatement statement = c.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			MyBook b = new MyBook();
			while (rs.next()) {
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("stock_quantity"));
			}
			return b;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public static ResultSet getDetailOfBook(int id) {
		Connection c = ConnectDatabase.getConnection();
		String sql = "select b.*, a.fullname author_name, a.dob author_dob, c.name category_name "
				   + " from Book b join Author a on b.author_id = a.id "
				   + "            join Category c on b.category_id = c.id "
				   + " where b.id = ? ";
		try {
			PreparedStatement statement = c.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public static List<MyBook> getLoanBookListOfUser(int id) {
		List<MyBook> l = new ArrayList<MyBook>();
		Connection c = ConnectDatabase.getConnection();
		String sql = " select b.* "
				   + " from Book b join LoanDetail ld on b.id = ld.book_id "
				   + "             join Loan l on ld.loan_id = l.id "
				   + "			   join UserTb u on u.id = l.userTb_id "
				   + " where u.id = ? ";
		try {
			PreparedStatement statement = c.prepareStatement(sql);
			statement.setInt(1,id);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				MyBook b = new MyBook();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("stock_quantity"));
				l.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return l;
	}

	public static List<MyBook> getBookListByAuthorName(String authorName) {
		List<MyBook> l = new ArrayList<MyBook>();
		Connection c = ConnectDatabase.getConnection();
		String sql = "select b.* from Book b join Author a ON b.author_id = a.id where a.id = ?";
		try {
			PreparedStatement statement = c.prepareStatement(sql);
			statement.setString(1, authorName);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				MyBook b = new MyBook();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("stock_quantity"));
				l.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return l;
	}

	public List<MyBook> getBookListByCategory(String category) {
		List<MyBook> l = new ArrayList<MyBook>();
		Connection c = ConnectDatabase.getConnection();
		String sql = "select b.* from Book b join Category c ON b.category_id = c.id where c.id = ?";
		try {
			PreparedStatement statement = c.prepareStatement(sql);
			statement.setString(1, category);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				MyBook b = new MyBook();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("stock_quantity"));
				l.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return l;
	}

	public static ResultSet get5BookMostBorrowed() {
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT TOP 5 b.id, b.title , b.author_id, b.category_id , COUNT(b.id) So_lan_muon"
				+ "	FROM Loan l JOIN LoanDetail ld ON l.id = ld.loan_id"
				+ "				JOIN UserTb u ON l.userTb_id = u.id"
				+ "				JOIN Book b ON ld.book_id = b.id "
				+ "	GROUP BY b.id, b.title , b.author_id, b.category_id";
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

	public static List<MyBook> getBookIsNotBorrowingByUser() {
		List<MyBook> l = new ArrayList<MyBook>();
		Connection c = ConnectDatabase.getConnection();
		String sql = "select * from Book\r\n" + "		where NOT EXISTS (\r\n"
				+ "							select book.*\r\n" + "							from LoanDetail \r\n"
				+ "							where LoanDetail.book_id = Book.id\r\n" + "							)";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MyBook b = new MyBook();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("stock_quantity"));
				l.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return l;
	}

	public static List<MyBook> getBookRegistration() {
		List<MyBook> l = new ArrayList<MyBook>();
		Connection c = ConnectDatabase.getConnection();
		String sql = "select * from BookRegistration";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MyBook b = new MyBook();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description_book"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("quantity"));
				l.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return l;
	}

	public static List<MyBook> getLongestBookInLib() {
		List<MyBook> l = new ArrayList<MyBook>();
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT * \r\n" + "	FROM Book \r\n"
				+ "	WHERE import_date = (SELECT MIN(import_date) FROM Book)";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MyBook b = new MyBook();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setAuthor_id(rs.getInt("author_id"));
				b.setCategory_id(rs.getInt("category_id"));
				b.setDescription(rs.getString("description"));
				b.setPublished_date(rs.getString("published_date"));
				b.setImport_date(rs.getString("import_date"));
				b.setStock_quantity(rs.getInt("stock_quantity"));
				l.add(b);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return l;
	}

	public static ResultSet getCategory(int id) {
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT c.name FROM Category c JOIN Book b ON c.id = b.category_id " + "WHERE b.category_id = ?";
		try {
			PreparedStatement statement = c.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	public static ResultSet getBooksWithCateAuthorName() {
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT b.id, b.title, c.name, a.fullname, b.published_date, b.stock_quantity, b.import_date, b.description\r\n"
				+ "FROM Book b JOIN Category c ON b.category_id = c.id \r\n"
				+ "			JOIN Author a ON b.author_id = a.id";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static ResultSet getBookIDListNotBorrowed() {
		Connection c = ConnectDatabase.getConnection();
		String sql = "select id from Book\r\n"
				+ "		where NOT EXISTS (\r\n"
				+ "							select book.*\r\n"
				+ "							from LoanDetail \r\n"
				+ "							where LoanDetail.book_id = Book.id )";
		try {
			Statement statement = c.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static void main(String[] args) {
		List<MyBook> l = getBookListByAuthorName("401");
		printList(l);
	}

	public static void printList(List<MyBook> list) {
		for (MyBook b : list) {
			System.out.println(b.getAuthor_id());
		}
	}
}
