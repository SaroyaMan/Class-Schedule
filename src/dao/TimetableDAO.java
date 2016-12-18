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

	//	public ArrayList<Timetable> getSimilarClassrooms(int ClassNum) {
	//
	//		ArrayList<Timetable> timewithOldClass = new ArrayList<>();
	//
	//		//			// prepare statement
	//		//			myStmt = connection.prepareStatement("select * from timetable where classNumber=?");
	//		//			
	//		//			// set param
	//		//			myStmt.setInt(1, oldClassNum);
	//		//
	//		//			// execute SQL
	//		//			rs = myStmt.executeQuery();		
	//		for (Timetable timetable : list) {
	//			if(timetable.getClassNum() == ClassNum) {
	//				timewithOldClass.add(timetable);
	//			}
	//		}
	//		return timewithOldClass;
	//	}

	private Timetable convertRowToTimetable(ResultSet myRs) throws SQLException {

		int courseNumber = myRs.getInt("courseNumber");
		int lecturerId = myRs.getInt("lecturerId");
		int classNumber = myRs.getInt("classNumber");
		String day = myRs.getString("day");
		int hour = myRs.getInt("hour");

		return new Timetable(courseNumber, lecturerId, classNumber, day, hour);
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

	public String[][] getClassroomInfo(int classNumber) throws SQLException {
		//TODO
		String[][] data;
		ArrayList<Timetable> classInfoNeeded = new ArrayList<>();
		PreparedStatement myStmt = null;

//		for (Timetable timetable : list) {
//			if(timetable.getClassNum() == classNumber)
//				classInfoNeeded.add(timetable);
//		}

		try {
			// prepare statement
			myStmt = connection.prepareStatement("select (id, name_first, name_last) from lecturer "
					+ "where lecturerId = "
					+ "(select lecturerId from timetable where classNumber=?)");

			// set param
			myStmt.setInt(1, classNumber);

			// execute SQL
			myStmt.executeUpdate();		
			return null;
		}
		finally {
			close(myStmt,null);
		}
	}

	public String[][] getLecturerInfo(int lecturerId) {
		//	TODO
		return null;
	}
}
