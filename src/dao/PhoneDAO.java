package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import classes.Phone;
import timetable.DbHelper;

public class PhoneDAO {

	private Connection connection;
	private static PhoneDAO instance;
	
	private PhoneDAO() throws ClassNotFoundException, SQLException, IOException, ParseException {
		connection = DbHelper.getInstance().getConnection();
	}
	
	public static PhoneDAO getInstance() throws ClassNotFoundException, SQLException, IOException, ParseException {
		if (instance == null) return (instance = new PhoneDAO());
		return instance;
	}
	
	public List<Phone> getAllPhones() throws Exception {
		
		List<Phone> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = connection.createStatement();
			myRs = myStmt.executeQuery("select * from phone");
			
			while (myRs.next()) {
				Phone tempPhone = convertRowToPhone(myRs);
				list.add(tempPhone);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void addPhone(Phone thePhone) throws Exception {
		
		PreparedStatement myStmt = null;
		try {
			// prepare statement
			myStmt = connection.prepareStatement("insert into phone values(?,?)");
			
			// set params
			myStmt.setInt(1, thePhone.getId());
			myStmt.setString(2, thePhone.getNumber());

			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}	
	}
	
	public void updatePhone(Phone thePhone) throws SQLException, ClassNotFoundException, IOException, ParseException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("update phone"
					+ " set number=? where lecturerId=? and number=?");
			
			// set params
			myStmt.setString(1, thePhone.getNumber());
			myStmt.setInt(2, thePhone.getId());
			myStmt.setString(3, thePhone.getNumber());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}
	
	public void deletePhone(int phoneId, int number) throws SQLException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("delete from phone where lecturerId=? and number=?");
			
			// set param
			myStmt.setInt(1, phoneId);
			myStmt.setInt(2, number);
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}
	
	private Phone convertRowToPhone(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("lecturerId");
		String number = myRs.getString("number");
		return new Phone(id, number);
	}
	
	private static void close(Statement myStmt, ResultSet myRs)
			throws SQLException {

		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}
	}
}
