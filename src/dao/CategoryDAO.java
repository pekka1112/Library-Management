package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.*;

public class CategoryDAO {
	
	public static List<Category> getCategories() { 
		 List<Category> list = new ArrayList<Category>();
		 Connection c = ConnectDatabase.getConnection();
		 String sql = "SELECT * FROM Category";
		 try {
			 Statement statement = c.createStatement();
	         ResultSet rs = statement.executeQuery(sql);
	         while(rs.next()){
	                Category category = new Category();
	                category.setId(rs.getInt(1));
	                category.setName(rs.getString(2));
	                category.setParent_category_id(rs.getInt(3));
	                list.add(category);
	            }
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	public static ResultSet getCategorieID() { 
		 Connection c = ConnectDatabase.getConnection();
		 String sql = "SELECT id FROM Category";
		 try {
			 Statement statement = c.createStatement();
	         ResultSet rs = statement.executeQuery(sql);
	         return rs;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public static ResultSet getCategoryIDFromBookID(int id) { 
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT top 1 category_id FROM Book WHERE id = ?";
		try {
			PreparedStatement statement = c.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			return rs;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public static void main(String[] args) {
		System.out.println(getCategories().size());
	}
}
