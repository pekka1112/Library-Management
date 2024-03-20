package Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.AuthorDAO;
import model.*;

public class AuthorService {
	private static AuthorDAO authorDAO;

	public AuthorService() {
		authorDAO = new AuthorDAO();
	}
	public List<Author> getAuthors() { 
		return AuthorDAO.getAuthors();
	}
	public List<String> getAuthorsName() { 
		List<String> s = new ArrayList<String>();
		for (Author a : AuthorDAO.getAuthors()) {
			s.add(a.getFullname());
		}
		return s;
	}
	public static ResultSet getAuthorID() {  
		return authorDAO.getAuthorID();
	}
	public static ResultSet getAuthorName(int id) {  
		return authorDAO.getAuthorName(id);
	}
	public static ResultSet getAuthorIDFromBookID(int id) {  
		return authorDAO.getAuthorIDFromBookID(id);
	}
}
