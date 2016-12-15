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

import classes.Course;
import timetable.DbHelper;

public class CourseDAO {

	private Connection connection;
	private static CourseDAO instance;
	
	private CourseDAO() throws ClassNotFoundException, SQLException, IOException, ParseException {
		connection = DbHelper.getInstance().getConnection();
	}
	
	public static CourseDAO getInstance() throws ClassNotFoundException, SQLException, IOException, ParseException {
		if (instance == null) return (instance = new CourseDAO());
		return instance;
	}
	
	public List<Course> getAllCourses() throws Exception {
		
		List<Course> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = connection.createStatement();
			myRs = myStmt.executeQuery("select * from course");
			
			while (myRs.next()) {
				Course tempCourse = convertRowToCourse(myRs);
				list.add(tempCourse);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void addCourse(Course theCourse) throws Exception {
		
		PreparedStatement myStmt = null;
		try {
			// prepare statement
			myStmt = connection.prepareStatement("insert into course (name, semester, "
				+ "year, hours) values(?,?,?,?)");
			
			// set params
			myStmt.setString(1, theCourse.getName());
			myStmt.setString(2, theCourse.getSemester());
			myStmt.setInt(3, theCourse.getYear());
			myStmt.setInt(4, theCourse.getHours());
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}	
	}
	
	public void updateCourse(Course theCourse) throws SQLException, ClassNotFoundException, IOException, ParseException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("update course"
					+ " set name=?, semester=?, year=?, hours=? where number=?");
			
			// set params
			myStmt.setString(1, theCourse.getName());
			myStmt.setString(2, theCourse.getSemester());
			myStmt.setInt(3, theCourse.getYear());
			myStmt.setInt(4, theCourse.getHours());
			myStmt.setInt(5, theCourse.getNumber());
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}
	
	public void deleteCourse(int CourseId) throws SQLException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("delete from course where number=?");
			
			// set param
			myStmt.setInt(1, CourseId);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}
	
	private Course convertRowToCourse(ResultSet myRs) throws SQLException {
		
		int numCourse = myRs.getInt("number");
		String name = myRs.getString("name");
		String semester = myRs.getString("semester");
		int year = myRs.getInt("year");
		int hours = myRs.getInt("hours");
		
		return new Course(numCourse, name, semester, year, hours);
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
