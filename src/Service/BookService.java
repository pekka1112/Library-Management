package Service;

import java.sql.ResultSet;
import java.util.List;

import dao.BookDAO;
import model.MyBook;

public class BookService {
	private BookDAO bookDAO;

	public BookService() {
		super();
		bookDAO = new BookDAO();
	} 
	public ResultSet get5BookMostBorrowed() {  
		return BookDAO.get5BookMostBorrowed();
	}
	public List<MyBook> getBookIsNotBorrowingByUser() { 
		return BookDAO.getBookIsNotBorrowingByUser();
	}
	public List<MyBook> getBookRegistration() { 
		return BookDAO.getBookRegistration();
	}
	public List<MyBook> getLongestBookInLib() { 
		return BookDAO.getLongestBookInLib();
	}
	public ResultSet getCategory(int id) {  
		return BookDAO.getCategory(id);
	}
	public ResultSet getBooksWithCateAuthorName() {  
		return BookDAO.getBooksWithCateAuthorName();
	}
	public ResultSet getBookIDListNotBorrowed() {  
		return BookDAO.getBookIDListNotBorrowed();
	}
	public MyBook getBookByID(int id) { 
		return bookDAO.getBookByID(id);
	}
	public ResultSet getDetailOfBook(int id) { 
		return bookDAO.getDetailOfBook(id);
	}
	public List<MyBook> getLoanBookListOfUser(int id) { 
		return bookDAO.getLoanBookListOfUser(id);
	}
}
