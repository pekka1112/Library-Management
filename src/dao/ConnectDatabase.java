package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectDatabase {
	public static void main(String[] args) {
		ConnectDatabase.getConnection();
	}
	public static Connection getConnection() { 
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:sqlserver://AX00:1433;DatabaseName=QLTV;encrypt=true;trustServerCertificate=true;?useUnicode=true&characterEncoding=UTF-8";
			String username = "sa";
			String password = "ndtp1203";
			Connection connection = DriverManager.getConnection(url, username, password);
			return connection;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
