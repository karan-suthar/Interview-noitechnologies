package com.noitechnologies.interview.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.noitechnologies.interview.model.MultiKeyMap;
import com.noitechnologies.interview.model.User;

public class DaoClass {

	private static final String URL = "";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";
	private static final String CLASSNAME = "com.mysql.cj.jdbc.Driver";

	// function to store user data into database return true of not exception occurs
	public static boolean insertUserDataIntoDB(MultiKeyMap<String, Long, User> users) {

		final String query = "INSERT INTO user(`first_name`,`last_name`,`age`,`dob`,`email`,`mobile`) values(?,?,?,?,?,?)";
		int[] result = new int[0];
		try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {

			connection.setAutoCommit(false);
			for (User user : users.getMap().values()) {
				pstmt.setString(1, user.getFirstName());
				pstmt.setString(2, user.getLastName());
				pstmt.setInt(3, user.getAge());
				pstmt.setDate(4, Date.valueOf(user.getDob()));
				pstmt.setString(5, user.getEmail());
				pstmt.setLong(6, user.getMobileNumber());
				pstmt.addBatch();
			}
			result = pstmt.executeBatch();
			System.out.println("The number of rows inserted: " + result.length);
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return result.length == users.getMap().size();
	}

	// function to store user's address data into database return true of not exception occurs
	public static boolean insertAddressDataIntoDB(MultiKeyMap<String, Long, User> users) {

		final String query = "INSERT INTO address(`line1`,`line2`,`city`,`state`,`country`,`pincode`,`email`) values(?,?,?,?,?,?,?)";
		int[] result = new int[0];
		try (Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {

			connection.setAutoCommit(false);
			for (User user : users.getMap().values()) {
				pstmt.setString(1, user.getAddress().getAddressLine1());
				pstmt.setString(2, user.getAddress().getAddressLine2());
				pstmt.setString(3, user.getAddress().getCity());
				pstmt.setString(4, user.getAddress().getState());
				pstmt.setString(5, user.getAddress().getCountry());
				pstmt.setInt(6, user.getAddress().getPinCode());
				pstmt.setString(7, user.getEmail());
				pstmt.addBatch();
			}
			result = pstmt.executeBatch();
			System.out.println("The number of rows inserted: " + result.length);
			connection.commit();
		} catch (

		Exception e) {
			e.printStackTrace();
			return false;
		}

		return result.length == users.getMap().size();
	}

	private static Connection getConnection() throws SQLException, ClassNotFoundException {
		Connection con = null;
		try {
			Class.forName(CLASSNAME);
			con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			throw e;
		}
		return con;
	}

}
