/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataaccess;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import business.User;

public class UserDB {
	// Database URL
	// private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";

	public static long insert(User user) {
		System.out.println("control is within user insert");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");

			String s = "INSERT into uncctweets.USER (fullName, email, dobDay, dobMonth, dobYear, nickName, passwords, salt,usertype ) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(s);
			ps.setString(1, user.getFullName());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getDobDay());
			ps.setString(4, user.getDobMonth());
			ps.setString(5, user.getDobYear());
			ps.setString(6, user.getNickName());
			ps.setString(7, user.getPassword());
			ps.setString(8, user.getSalt());
			ps.setString(9, "user");
			ps.executeUpdate();

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
		return 0;
	}

	public static void updateLogOutTime(User userLog) {
		System.out.println("control is within user update LogOut time");
		ResultSet rs = null;
		User userData = new User();
		String pass;
		// implement update into database
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// Testing
			// conn.setAutoCommit(false);
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			// System.out.println("password" + password + " " + email);
			// String sql = "UPDATE uncctweets.USER SET Last_Login_Time ='" +
			// userLog.getLastLoginTime() + "' WHERE EMAIL ='" +
			// userLog.getEmail() + "'";
			// + user.getPassword() + "'";
			String s = "UPDATE uncctweets.USER SET Last_Login_Time = ? where EMAIL= ?";
			PreparedStatement ps = (PreparedStatement) conn.prepareStatement(s);
			ps.setString(1, userLog.getLastLoginTime());
			ps.setString(2, userLog.getEmail());
			// System.out.println("the answr is" + sql);
			ps.executeUpdate();
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
		// return userData;
	}

	public static void updatepass(String email, String password) {
		System.out.println("control is within user update");
		ResultSet rs = null;
		User userData = new User();
		String pass;
		// implement update into database
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// Testing
			// conn.setAutoCommit(false);
			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			System.out.println("password" + password + " " + email);
			String sql = "UPDATE uncctweets.USER SET passwords ='" + password + "' WHERE EMAIL ='" + email + "'";
			// + user.getPassword() + "'";

			System.out.println("the answer is" + sql);
			stmt.executeUpdate(sql);
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
		// return userData;
	}

	public static long update(User user) {
		System.out.println("control is within user update");
		// implement update into database
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// Testing
			// conn.setAutoCommit(false);

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "UPDATE uncctweets.USER SET fullName = '" + user.getFullName() + "', dobDay ='"
					+ user.getDobDay() + "', dobMonth ='" + user.getDobMonth() + "', dobYear ='" + user.getDobYear()
					+ "', passwords ='" + user.getPassword() + "', salt ='" + user.getSalt() + "' WHERE EMAIL ='"
					+ user.getEmail() + "'";

			stmt.executeUpdate(sql);

		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");
		return 0;
	}

	public static void updatepic(User user, String filepath) {
		System.out.println("control is within user update epic");
		System.out.println("filepath " + filepath);
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection

			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			// String sqlm = "UPDATE uncctweets.USER SET picfilepath = '"
			// +filepath +"' WHERE email = '" + user.getEmail() + "'";

			// stmt.executeUpdate(sqlm);

		} catch (SQLException se) {
		} catch (ClassNotFoundException e) {
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	}

	public static User select(String emailAddress, String password) {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within select method");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User userData = new User();
		// userData = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM uncctweets.USER " + "WHERE EMAIL = '" + emailAddress + "' AND passwords ='"
					+ password + "' and usertype <> 'admin'";
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
			while (rs.next()) {
				System.out.println("Result set full name " + rs.getString("fullName"));
				userData.setFullName(rs.getString("fullName"));
				userData.setNickName(rs.getString("nickName"));
				userData.setDobDay(rs.getString("dobDay"));
				userData.setDobMonth(rs.getString("dobMonth"));
				userData.setDobYear(rs.getString("dobYear"));
				userData.setEmail(rs.getString("email"));
				userData.setPassword(rs.getString("passwords"));
				userData.setUsertype(rs.getString("usertype"));
				userData.setUserId(rs.getInt("userId"));
				// java.util.Date uDate =
				// formatter.parse(rs.getString("Last_Login_Time").trim());
				// userData.setLastLoginTime(uDate);
				userData.setLastLoginTime(rs.getString("Last_Login_Time"));
				System.out.println("userData.setLastLoginTime(uDate) " + userData.getEmail());
				// userData.setLastLoginTime(rs.getDate("Last_Login_Time"));
				
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return userData;
	}

	// Logic for getting user id from the nickname
	public static String salt(String email) {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within salt method of saltUser");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User userData2 = new User();
		// userData = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT salt FROM uncctweets.USER " + "WHERE EMAIL = '" + email + "'";
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Result set salt " + rs.getString("salt"));
				userData2.setSalt(rs.getString("salt"));
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		// System.out.println("userData fullname is "+userData.getFullName());
		// if(userData.getEmail() != null)
		// return userData;
		// else
		// return null;
		return userData2.getSalt();
	}

	// Logic for getting user id from the nickname

	public static int select(String nickName) {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within select method of nickname");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User userData2 = new User();
		// userData = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM uncctweets.USER " + "WHERE NICKNAME = '" + nickName + "'";
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Result set full name " + rs.getString("fullName"));
				userData2.setUserId(rs.getInt("userId"));
				userData2.setFullName(rs.getString("fullName"));
				userData2.setNickName(rs.getString("nickName"));
				userData2.setDobDay(rs.getString("dobDay"));
				userData2.setDobMonth(rs.getString("dobMonth"));
				userData2.setDobYear(rs.getString("dobYear"));
				userData2.setEmail(rs.getString("email"));
				userData2.setPassword(rs.getString("passwords"));

			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		// System.out.println("userData fullname is "+userData.getFullName());
		// if(userData.getEmail() != null)
		// return userData;
		// else
		// return null;
		return userData2.getUserId();
	}

	public static ArrayList selectAll() {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within allUsers method");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected SELECTALL...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT fullName, nickName, email FROM uncctweets.USER where usertype <>'admin' ";
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Result set full name " + rs.getString("fullName"));
				User eachUser = new User();
				eachUser.setFullName(rs.getString("fullName"));
				eachUser.setNickName(rs.getString("nickName"));
				// eachUser.setDobDay(rs.getString("dobDay"));
				// eachUser.setDobMonth(rs.getString("dobMonth"));
				// eachUser.setDobYear(rs.getString("dobYear"));
				eachUser.setEmail(rs.getString("email"));

				// eachUser.setPassword(rs.getString("password"));
				userList.add(eachUser);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return userList;
	}

	public static User selectemail(String emailAddress) {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within select method");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User userData = new User();
		// userData = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM uncctweets.USER " + "WHERE EMAIL = '" + emailAddress + "'";
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Result set full name " + rs.getString("fullName"));
				userData.setFullName(rs.getString("fullName"));
				userData.setNickName(rs.getString("nickName"));
				userData.setDobDay(rs.getString("dobDay"));
				userData.setDobMonth(rs.getString("dobMonth"));
				userData.setDobYear(rs.getString("dobYear"));
				userData.setEmail(rs.getString("email"));
				userData.setPassword(rs.getString("passwords"));
				userData.setUserId(rs.getInt("userId"));
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		// System.out.println("userData fullname is "+userData.getFullName());
		// if(userData.getEmail() != null)
		// return userData;
		// else
		// return null;
		return userData;
	}

	// Logic for getting the user details by userid
	public static User selectUser_Id(int userIdFollower) {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within selectUser_Id method");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User userData = new User();
		// userData = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM uncctweets.USER " + "WHERE userID = " + userIdFollower;
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Result set full name " + rs.getString("fullName"));
				userData.setFullName(rs.getString("fullName"));
				userData.setNickName(rs.getString("nickName"));
				userData.setDobDay(rs.getString("dobDay"));
				userData.setDobMonth(rs.getString("dobMonth"));
				userData.setDobYear(rs.getString("dobYear"));
				userData.setEmail(rs.getString("email"));
				userData.setPassword(rs.getString("passwords"));

			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null) {
					conn.close();
				}
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		// System.out.println("userData fullname is "+userData.getFullName());
		// if(userData.getEmail() != null)
		// return userData;
		// else
		// return null;
		return userData;
	}

	// Logic to select all the other users
	public static ArrayList selectOtherUsers(User logInUser) {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within selectOtherUsers method");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList userList = new ArrayList();
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM uncctweets.USER WHERE usertype<>'admin' and userID <>" + logInUser.getUserId();
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("Result set full name " + rs.getString("fullName"));
				User eachUser = new User();
				eachUser.setFullName(rs.getString("fullName"));
				eachUser.setNickName(rs.getString("nickName"));
				// eachUser.setDobDay(rs.getString("dobDay"));
				// eachUser.setDobMonth(rs.getString("dobMonth"));
				// eachUser.setDobYear(rs.getString("dobYear"));
				eachUser.setEmail(rs.getString("email"));

				eachUser.setUserId(rs.getInt("userID"));
				System.out.println("eachUser.setPicfilePath " + eachUser.getPicfilepath());
				// eachUser.setPassword(rs.getString("password"));
				userList.add(eachUser);
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		return userList;
	}

	public static User selectAdmin(String emailAddress, String password) {
		// search in the database and find the User, if does not exist return
		// null; if exist make a User object and return it.
		System.out.println("control is within select method");
		// implement insert into database
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		User userData = new User();
		// userData = null;
		try {
			// STEP 2: Register JDBC driver

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			stmt = conn.createStatement();
			String sql = "SELECT * FROM uncctweets.USER " + "WHERE EMAIL = '" + emailAddress + "' AND passwords ='"
					+ password + "' and usertype <> 'user'";
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
			while (rs.next()) {
				System.out.println("Result set full name " + rs.getString("fullName"));
				userData.setFullName(rs.getString("fullName"));
				userData.setNickName(rs.getString("nickName"));
				userData.setDobDay(rs.getString("dobDay"));
				userData.setDobMonth(rs.getString("dobMonth"));
				userData.setDobYear(rs.getString("dobYear"));
				userData.setEmail(rs.getString("email"));
				userData.setPassword(rs.getString("passwords"));

				userData.setUserId(rs.getInt("userId"));
				// java.util.Date uDate =
				// formatter.parse(rs.getString("Last_Login_Time").trim());
				// userData.setLastLoginTime(uDate);
				userData.setLastLoginTime(rs.getString("Last_Login_Time"));
				System.out.println("userData.setLastLoginTime(uDate) " + userData.getLastLoginTime());
				// userData.setLastLoginTime(rs.getDate("Last_Login_Time"));
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

		// System.out.println("userData fullname is "+userData.getFullName());
		// if(userData.getEmail() != null)
		// return userData;
		// else
		// return null;
		return userData;
	}

	public static void deleteUser(String email) {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {

			System.out.println("Delete urs Connecting to a selected database...");

			conn = ConnectionPool.getConnection();
			System.out.println("Connected database successfully...");
			stmt = conn.createStatement();
			String sql = "SELECT userID FROM uncctweets.USER " + "WHERE EMAIL = '" + email + "'";
			System.out.println("sql is " + sql);
			// User userData = stmt.executeUpdate(sql);
			// int resultSet = stmt.executeUpdate(sql);
			rs = stmt.executeQuery(sql);
			int i = 0;
			if (rs.next()) {
				i = rs.getInt("userID");
				System.out.println(i);
			}

			// delete from USER where userID=2;

			// STEP 4: Execute a query
			System.out.println("Creating statement...");
			if (i != 0) {
				String sqlm = "delete from uncctweets.USER  WHERE userID =" + i;

				stmt.executeUpdate(sqlm);

				String sqlfollow = "delete from uncctweets.Follow  WHERE userID = " + i;
				stmt.executeUpdate(sqlfollow);

			}

		} catch (SQLException se) {
		} catch (ClassNotFoundException e) {
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
		System.out.println("Goodbye!");

	}
}
