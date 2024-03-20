package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import model.*;

public class FineCollectionDAO {
	
	public static ResultSet getTotalFineInTime() { 
		Connection c = ConnectDatabase.getConnection();
		String sql = "select MONTH(create_date) as Month, SUM(fine_amount) as TotalFine"
				+ "		from FineCollection"
				+ "		group by MONTH(create_date)";
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
}
