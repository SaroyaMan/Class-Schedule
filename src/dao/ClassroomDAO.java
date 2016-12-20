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

import classes.Classroom;
import timetable.DbHelper;

public class ClassroomDAO {

	private Connection connection;
	private static ClassroomDAO instance;
	
	private ClassroomDAO() throws ClassNotFoundException, SQLException, IOException, ParseException {
		connection = DbHelper.getInstance().getConnection();
	}
	
	public static ClassroomDAO getInstance() throws ClassNotFoundException, SQLException, IOException, ParseException {
		if (instance == null) return (instance = new ClassroomDAO());
		return instance;
	}
	
	public List<Classroom> getAllClassrooms() throws SQLException {
		
		List<Classroom> list = new ArrayList<>();
		
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myStmt = connection.createStatement();
			myRs = myStmt.executeQuery("select * from classroom");
			
			while (myRs.next()) {
				Classroom tempClassroom = convertRowToClassroom(myRs);
				list.add(tempClassroom);
			}
			return list;		
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	public void addClassroom(Classroom theClassroom) throws Exception {
		
		PreparedStatement myStmt = null;
		try {
			// prepare statement
			myStmt = connection.prepareStatement("insert into classroom values(?,?,?)");
			
			// set params
			myStmt.setInt(1, theClassroom.getNumber());
			myStmt.setInt(2, theClassroom.getBuilding());
			myStmt.setInt(3, theClassroom.getFloor());
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}	
	}
	
	public void updateClassroom(Classroom theClassroom, int oldNum) throws SQLException, ClassNotFoundException, IOException, ParseException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("update classroom"
					+ " set number=?, building=?, floor=? where number=?");
			
			// set params
			myStmt.setInt(1, theClassroom.getNumber());
			myStmt.setInt(2, theClassroom.getBuilding());
			myStmt.setInt(3, theClassroom.getFloor());
			myStmt.setInt(4, oldNum);
			
			// execute SQL
			myStmt.executeUpdate();			
		}
		finally {
			close(myStmt,null);
		}
	}
	
	public void deleteClassroom(int ClassroomId) throws SQLException {
		
		PreparedStatement myStmt = null;

		try {
			// prepare statement
			myStmt = connection.prepareStatement("delete from classroom where number=?");
			
			// set param
			myStmt.setInt(1, ClassroomId);
			
			// execute SQL
			myStmt.executeUpdate();
		}
		finally {
			close(myStmt,null);
		}
	}
	
	private Classroom convertRowToClassroom(ResultSet myRs) throws SQLException {
		
		int numClassroom = myRs.getInt("number");
		
		return new Classroom(numClassroom);
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