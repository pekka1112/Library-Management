package Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.CategoryDAO;
import model.*;

public class CategoryService {
	private static CategoryDAO categoryDAO ;

	public CategoryService() {
		categoryDAO = new CategoryDAO();
	}
	public static List<Category> getCategories() {  
		return categoryDAO.getCategories();
	}
	public static ResultSet getCategorieID() {  
		return categoryDAO.getCategorieID();
	}
	public static ResultSet getCategoryIDFromBookID(int id) {  
		return CategoryDAO.getCategoryIDFromBookID(id);
	}
	public List<String> getCategoryNames() {
		List<String> s = new ArrayList<String>() ;
		for (Category c : categoryDAO.getCategories()) {
			s.add(c.getName());
		}
		return s;
	}
}
