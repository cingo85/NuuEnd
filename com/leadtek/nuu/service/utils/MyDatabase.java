package com.leadtek.nuu.service.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDatabase {

	public static void main(String[] args) throws SQLException {

		String sql = "SELECT * FROM stuinfo_personal";

		String url = "jdbc:mysql://203.64.173.61:3306/nuu?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
		String user = "Leadtek";
		String pass = "Leadtek21191";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pass);

			stmt = con.createStatement();

			stmt.execute(sql);
			rs = stmt.getResultSet();
			rs.last();
			System.out.println(rs.getRow());
			
			
		} catch (ClassNotFoundException ex) {

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (con != null) {
				con.close();
			}

			if (stmt != null) {
				stmt.close();
			}

		}

	}

	public ResultSet conn(String sql) throws SQLException {

		String url = "jdbc:mysql://203.64.173.61:3306/nuu?serverTimezone=GMT%2B8&useUnicode=true&characterEncoding=utf-8";
		String user = "Leadtek";
		String pass = "Leadtek21191";
		ResultSet rs = null;
		Connection con = null;
		Statement stmt = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(url, user, pass);

			stmt = con.createStatement();

			stmt.execute(sql);
			rs = stmt.getResultSet();
		} catch (ClassNotFoundException ex) {

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (con != null) {
				con.close();
			}

			if (stmt != null) {
				stmt.close();
			}

		}
		return rs;
	}

}
