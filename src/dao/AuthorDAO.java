package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import model.*;

public class AuthorDAO {
	public static List<Author> getAuthors() { 
		 List<Author> list = new ArrayList<Author>();
		 Connection c = ConnectDatabase.getConnection();
		 String sql = "SELECT * FROM Author";
		 try {
			 Statement statement = c.createStatement();
	         ResultSet rs = statement.executeQuery(sql);
	         while(rs.next()){
	                Author a = new Author();
	                a.setId(rs.getInt(1));
	                a.setFullname(rs.getString(2));
	                a.setDob(rs.getString(3));
	                a.setDescription(rs.getString(4));
	                list.add(a);
	            }
	         
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}
	public static ResultSet getAuthorID() { 
		 Connection c = ConnectDatabase.getConnection();
		 String sql = "SELECT id FROM Author";
		 try {
			 Statement statement = c.createStatement();
	         ResultSet rs = statement.executeQuery(sql);
	         return rs;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public static ResultSet getAuthorName(int id) { 
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT fullname FROM Author WHERE id = ?";
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
	public static ResultSet getAuthorIDFromBookID(int id) { 
		Connection c = ConnectDatabase.getConnection();
		String sql = "SELECT top 1 author_id FROM Book WHERE id = ?";
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
		System.out.println(getAuthors().size());
	}
}
