package com.study.groupware;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class OracleConnectionTest {

	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String USER = "groupware";
	private static final String PW = "groupware12#";
	
	@Test
	public void testConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		
		Connection conn = DriverManager.getConnection(URL, USER, PW);
		
		try{
			
			System.out.println("OracleConnectionTest.java : " + conn);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally {
			
			try {
				conn.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
