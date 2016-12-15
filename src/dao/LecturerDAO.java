package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import classes.Lecturer;
import timetable.DbHelper;

public class LecturerDAO {

	private Connection connection;
	private static LecturerDAO instance;
	
	private LecturerDAO() throws ClassNotFoundException, SQLException, IOException, ParseException {
		connection = DbHelper.getInstance().getConnection();
	}
	
	public static LecturerDAO getInstance() throws ClassNotFoundException, SQLException, IOException, ParseException {
		if(instance == null) return (instance = new LecturerDAO());
		return instance;
	}
	
	public List<Lecturer> getAllLecturers() throws Exception {
		
		List<Lecturer> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = connection.createStatement();
			myRs = myStmt.executeQuery("select * from lecturer");
			
			while (myRs.next()) {
				Lecturer tempLecturer = convertRowToLecturer(myRs);
				list.add(tempLecturer);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void addLecturer(Lecturer theLecturer) throws Exception {
		
		PreparedStatement myStmt = null;
		try {
			// prepare statement
			myStmt = connection.prepareStatement("insert into lecturer (name_first, name_last,"
				+ "address, birthdate, age) values(?,?,?,?,?)");
			
			// set params
			myStmt.setString(1, theLecturer.getFirstName());
			myStmt.setString(2, theLecturer.getLastName());
			myStmt.setString(3, theLecturer.getAddress());
			myStmt.setDate(4, theLecturer.getBirthdate());
			myStmt.setInt(5, theLecturer.getAge());
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}	
	}
	
	public void updateLecturer(Lecturer theLecturer) throws SQLException, ClassNotFoundException, IOException, ParseException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("update lecturer"
					+ " set first_name=?, last_name=?, address=?, birthdate=? where id=?");
			
			// set params
			myStmt.setString(1, theLecturer.getFirstName());
			myStmt.setString(2, theLecturer.getLastName());
			myStmt.setString(3, theLecturer.getAddress());
			myStmt.setDate(4, theLecturer.getBirthdate());
			myStmt.setInt(5, theLecturer.getId());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}
	
	public void deleteLecturer(int LecturerId) throws SQLException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("delete from lecturer where id=?");
			
			// set param
			myStmt.setInt(1, LecturerId);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}
	
	private Lecturer convertRowToLecturer(ResultSet myRs) throws SQLException {
		
		int id = myRs.getInt("id");
		String firstName = myRs.getString("name_first");
		String lastName = myRs.getString("name_last");
		String address = myRs.getString("address");
		Date birthdate = myRs.getDate("birthdate");
		return new Lecturer(id, firstName, lastName, address, birthdate);
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
