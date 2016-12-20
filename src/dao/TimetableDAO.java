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
import java.util.Vector;

import classes.Timetable;
import timetable.DbHelper;

public class TimetableDAO {

	private Connection connection;

	private List<Timetable> list;	//DELETE

	private static TimetableDAO instance;

	private TimetableDAO() throws ClassNotFoundException, SQLException, IOException, ParseException {
		connection = DbHelper.getInstance().getConnection();
	}

	public static TimetableDAO getInstance() throws ClassNotFoundException, SQLException, IOException, ParseException {
		if(instance == null) return (instance = new TimetableDAO());
		return instance;
	}

	public List<Timetable> getAllTimetables() throws SQLException {

		//		List<Timetable> list = new ArrayList<>();
		list = new ArrayList<>();		//DELETE

		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = connection.createStatement();
			myRs = myStmt.executeQuery("select * from timetable");

			while (myRs.next()) {
				Timetable tempTimetable = convertRowToTimetable(myRs);
				list.add(tempTimetable);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}

	public void addTimetable(Timetable theTimetable) throws SQLException {

		PreparedStatement myStmt = null;
		try {
			// prepare statement
			myStmt = connection.prepareStatement("insert into timetable "
					+ "(courseNumber, lecturerId, classNumber, day, hour) values(?,?,?,?,?)");

			// set params
			myStmt.setInt(1, theTimetable.getCourseNum());
			myStmt.setInt(2, theTimetable.getLecturerId());
			myStmt.setInt(3, theTimetable.getClassNum());
			myStmt.setString(4, theTimetable.getDay());
			myStmt.setInt(5, theTimetable.getHour());
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}	
	}

	public void updateTimetable(Timetable theTimetable) throws SQLException, ClassNotFoundException, IOException, ParseException {

		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("update timetable"
					+ " set courseNumber=?, lecturerId=?, classNumber=?, day=?, hour=?"
					+ " where id=?");

			// set params
			myStmt.setInt(1, theTimetable.getCourseNum());
			myStmt.setInt(2, theTimetable.getLecturerId());
			myStmt.setInt(3, theTimetable.getClassNum());
			myStmt.setString(4, theTimetable.getDay());
			myStmt.setInt(5, theTimetable.getHour());

			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}

	public void updateTimetableBy(String column, Object oldValue, Object newValue) throws SQLException {

		PreparedStatement myStmt = null;
		boolean isInt = true;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("update timetable"
					+ " set " +column+ "=? where " +column+ "=?");

			// figure what type we got
			if(oldValue instanceof String)
				isInt = false;

			// set params
			if(isInt) {
				myStmt.setInt(1, (int)newValue);
				myStmt.setInt(2, (int)oldValue);
			}else {
				myStmt.setString(1, (String)newValue);
				myStmt.setString(2, (String)oldValue);
			}

			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}

	public void deleteTimetable(int courseNumber, int lecturerId, int classNumber,
			String day, int hour) throws SQLException {

		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("delete from timetable "
					+ "where courseNumber=? and lecturerId=? and classNumber=? and day=? and hour=?");

			// set param
			myStmt.setInt(1, courseNumber);
			myStmt.setInt(2, lecturerId);
			myStmt.setInt(3, classNumber);
			myStmt.setString(4, day);
			myStmt.setInt(5, hour);
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}

	private Timetable convertRowToTimetable(ResultSet myRs) throws SQLException {

		int courseNumber = myRs.getInt("courseNumber");
		int lecturerId = myRs.getInt("lecturerId");
		int classNumber = myRs.getInt("classNumber");
		String day = myRs.getString("day");
		int hour = myRs.getInt("hour");

		return new Timetable(courseNumber, lecturerId, classNumber, day, hour);
	}

	public Vector<Vector<String>> getClassroomInfo(int classNumber) throws SQLException {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> row = null;
		PreparedStatement myStmt = null;
		ResultSet rs = null;
		try {
			// prepare statement
			myStmt = connection.prepareStatement(
					  "select "
					+ "lecturer.id 'lecturerId', lecturer.name_first, lecturer.name_last, "
					+ "course.number 'courseNumber', course.name 'courseName' "
					+ "from timetable "
					+ "join lecturer on timetable.lecturerId = lecturer.id "
					+ "join course on timetable.courseNumber = course.number "
					+ "where timetable.classNumber = ?");

			// set param
			myStmt.setInt(1, classNumber);

			// execute SQL
			rs = myStmt.executeQuery();
			while (rs.next()) {
				row = new Vector<String>();
				row.addElement(String.valueOf(rs.getInt("lecturerId")));
				row.addElement(rs.getString("name_first")+" "+ rs.getString("name_last"));
				row.addElement(String.valueOf(rs.getString("courseNumber")));
				row.addElement(rs.getString("courseName"));
				data.addElement(row);
			}
			return data;
		}
		finally {
			close(myStmt,rs);
		}
	}

	public Vector<Vector<String>> getLecturerInfo(int lecturerId) throws SQLException {
		Vector<Vector<String>> data = new Vector<Vector<String>>();
		Vector<String> row = null;
		PreparedStatement myStmt = null;
		ResultSet rs = null;
		
		try {
			// prepare statement
			myStmt = connection.prepareStatement(
					  "select "
					+ "classNumber, course.number 'courseNumber', name 'courseName', day, hour "
					+ "from timetable "
					+ "join classroom on timetable.classNumber = classroom.number "
					+ "join course on timetable.courseNumber = course.number "
					+ "where timetable.lecturerId = ?");

			// set param
			myStmt.setInt(1, lecturerId);

			// execute SQL
			rs = myStmt.executeQuery();
			while (rs.next()) {
				row = new Vector<String>();
				row.addElement(String.valueOf(rs.getInt("classNumber")));
				row.addElement(String.valueOf(rs.getString("courseNumber")));
				row.addElement(rs.getString("courseName"));
				row.addElement(rs.getString("day"));
				row.addElement(String.valueOf(rs.getString("hour"))+":00");
				data.addElement(row);
			}
			return data;
		}
		finally {
			close(myStmt,rs);
		}
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