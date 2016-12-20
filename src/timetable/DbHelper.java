package timetable;

import java.io.FileInputStream;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.util.Properties;


public class DbHelper {

	private static DbHelper instance;

	private Connection connection = null;

	private ResultSet rs = null;

	private PreparedStatement statement = null;

	private DbHelper() throws ClassNotFoundException, SQLException, IOException, ParseException {

		// Connect to database
		connectToDatabase();

		// Create tables
		createTables();

		// Insert records to tables
		insertRecords();
	}

	private void connectToDatabase() throws IOException, SQLException, ClassNotFoundException {

		//Register JDBC driver
		Class.forName("com.mysql.jdbc.Driver");

//		String dburl;

		//Get database properties
		Properties props = new Properties();
		props.load(new FileInputStream("src/timetable/timetable.properties"));

		//Connect to database
		connection = DriverManager.getConnection(/*dburl = */props.getProperty("dburl"),
				props.getProperty("user"), props.getProperty("password"));
		connection.setAutoCommit(true);
//		System.out.println("DB connection successful to: " + dburl);
	}

	private void createTables() throws SQLException  {
		Statement statement = connection.createStatement();
		//clearAllTables(statement);

		//Create all the tables needed for project
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS classroom(number int, INDEX num_ind (number),"
				+ " building int, floor int NOT NULL, PRIMARY KEY(number))ENGINE=INNODB");

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS lecturer("
				+ "id int AUTO_INCREMENT, INDEX id_ind (id), "
				+ "name_first varchar(20) NOT NULL, name_last varchar(20) NOT NULL, "
				+ "address varchar(20) NOT NULL, birthdate date NOT NULL, "
				+ "PRIMARY KEY(id)) ENGINE=INNODB");

		
		statement.executeUpdate("CREATE TABLE IF NOT EXISTS course(number int AUTO_INCREMENT, "
				+ "INDEX num_ind (number), "
				+ "name varchar(20) NOT NULL, semester char NOT NULL, year int NOT NULL, "
				+ "hours int NOT NULL, PRIMARY KEY(number)) "
				+ "ENGINE=INNODB");
	

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS phone(number varchar(12), "
				+ "lecturerId int, INDEX lecturer_id_ind (lecturerId), PRIMARY KEY(number), "
				+ "CONSTRAINT FOREIGN KEY(lecturerId) REFERENCES lecturer(id) ON DELETE CASCADE) ENGINE=INNODB");

		statement.executeUpdate("CREATE TABLE IF NOT EXISTS timetable("
				+ "day ENUM('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday'), "
				+ "hour int, classNumber int, INDEX class_num_ind (classNumber), "
				+ "courseNumber int, INDEX course_num_ind (courseNumber), "
				+ "lecturerId int, INDEX lec_id_ind (lecturerId) ,PRIMARY KEY(day, hour, classNumber), "
				+ "CONSTRAINT FOREIGN KEY(courseNumber) REFERENCES course(number) ON DELETE CASCADE, "
				+ "CONSTRAINT FOREIGN KEY(classNumber) REFERENCES classroom(number) ON UPDATE CASCADE, "
				+ "CONSTRAINT FOREIGN KEY(lecturerId) REFERENCES lecturer(id) ON DELETE CASCADE) "
				+ "ENGINE=INNODB");	
		
		
		/*
		 * 		statement.executeUpdate("CREATE TABLE IF NOT EXISTS timetable("
				+ "day varchar(10), "
				+ "hour int, classNumber int, INDEX class_num_ind (classNumber), "
				+ "courseNumber int, INDEX course_num_ind (courseNumber), "
				+ "lecturerId int, INDEX lec_id_ind (lecturerId) ,PRIMARY KEY(day, hour, classNumber), "
				+ "CONSTRAINT FOREIGN KEY(courseNumber) REFERENCES course(number) ON DELETE CASCADE, "
				+ "CONSTRAINT FOREIGN KEY(classNumber) REFERENCES classroom(number) ON UPDATE CASCADE, "
				+ "CONSTRAINT FOREIGN KEY(lecturerId) REFERENCES lecturer(id) ON DELETE CASCADE) "
				+ "ENGINE=INNODB");	
		 */
	}

	private void insertRecords() throws SQLException, ParseException {

		// Insert records into classroom table
		statement = connection.prepareStatement("INSERT INTO classroom values(?,?,?)");
		insertRecordToClassroom(2104);
		insertRecordToClassroom(2105);
		insertRecordToClassroom(2207);
		insertRecordToClassroom(3015);
		insertRecordToClassroom(4313);
		insertRecordToClassroom(2102);
		insertRecordToClassroom(2255);
		insertRecordToClassroom(426);
		insertRecordToClassroom(247);
		insertRecordToClassroom(3315);

		// Insert records into lecturer table

		statement = connection.prepareStatement("INSERT INTO lecturer (name_first, name_last,"
				+ "address, birthdate) values(?,?,?,?)");
		insertRecordToLecturer("Marcelo","Shihman","Bney Brak","1991-04-03");
		insertRecordToLecturer("Amit","Shmuel","Ramat Hasharon","1991-08-25");
		insertRecordToLecturer("Yftah","Livny","Petah Tikva","1988-03-01");
		insertRecordToLecturer("Anat","Tal","Givaatiam","1991-10-01");
		insertRecordToLecturer("Riva","Shalom","Ramat Hasharon","1965-08-25");
		insertRecordToLecturer("Avivit","Levy","Ramat Hasharon","1973-08-25");
		insertRecordToLecturer("Ytzhak","Nudler","Hod Hasharon","1964-08-25");
		insertRecordToLecturer("Netzher","Zaidneberg","Tel Aviv","1983-08-25");
		insertRecordToLecturer("Yonit","Rusho","Holon","1981-08-25");
		insertRecordToLecturer("Amit","Rash","Ramat Hasharon","1983-08-25");
		
		// Insert records into course table
		statement = connection.prepareStatement("INSERT INTO course (name, semester, year, hours) "
				+ "values(?,?,?,?)");
		insertRecordToCourse("Computer Sience A", "a", 2016, 4);
		insertRecordToCourse("Computer Sience B", "b", 2016, 4);
		insertRecordToCourse("Calculus A", "a", 2016, 5);
		insertRecordToCourse("Calculus B", "b", 2016, 5);
		insertRecordToCourse("Unix System", "c", 2016, 4);
		insertRecordToCourse("Physics A", "a", 2015, 3);
		insertRecordToCourse("Physics B", "b", 2015, 3);
		insertRecordToCourse("Java", "a", 2016, 4);
		insertRecordToCourse("Android", "a", 2016, 4);
		insertRecordToCourse("Algorithms", "a", 2013, 4);
		insertRecordToCourse("OOP", "a", 2012, 3);
		
		// Insert records into course table
		statement = connection.prepareStatement("INSERT INTO phone values(?,?)");
		insertRecordToPhone(1,"054-3456569");
		insertRecordToPhone(2,"052-2548161");
		insertRecordToPhone(2,"053-3456521");
		insertRecordToPhone(3,"051-4388383");
		insertRecordToPhone(4,"051-4968484");
		insertRecordToPhone(5,"052-4543211");
		insertRecordToPhone(1,"057-4343222");
		insertRecordToPhone(9,"058-2423123");
		insertRecordToPhone(3,"051-4325231");
		insertRecordToPhone(2,"058-3123125");

		statement.executeUpdate("SET foreign_key_checks = 0;");
		// Insert records into timetable
		statement = connection.prepareStatement("INSERT INTO timetable values(?,?,?,?,?)");
		insertRecordToTimetable(1,3,247,"Sunday", 10);
		insertRecordToTimetable(3,4,2104,"Monday", 11);
		insertRecordToTimetable(4,5,2105,"Tuesday", 12);
		insertRecordToTimetable(6,6,2255,"Friday", 12);
		insertRecordToTimetable(7,7,3315,"Thursday", 8);
		insertRecordToTimetable(8,8,247,"Wednesday", 15);
		insertRecordToTimetable(9,9,2207,"Sunday", 12);
		insertRecordToTimetable(3,1,4313,"Friday", 12);
		insertRecordToTimetable(2,3,2104,"Monday", 13);
		insertRecordToTimetable(5,2,4313,"Tuesday", 14);

		statement.executeUpdate("SET foreign_key_checks = 1;");
	}

	private void insertRecordToTimetable(int courseNum, int lecturerId,
			int classNum, String day, int hour) throws SQLException {

		statement.setString(1, day);
		statement.setInt(2, hour);
		statement.setInt(3, classNum);
		statement.setInt(4, courseNum);
		statement.setInt(5, lecturerId);
		statement.addBatch();
		statement.executeBatch();
	}

	private void insertRecordToClassroom(int num) throws SQLException {
		int building = Integer.parseInt(String.valueOf(String.valueOf(num).charAt(0)));
		int floor = Integer.parseInt(String.valueOf(String.valueOf(num).charAt(1)));
		statement.setInt(1, num);
		statement.setInt(2, building);
		statement.setInt(3, floor);
		statement.addBatch();
		statement.executeBatch();
	}

	private void insertRecordToCourse(String name, String semester, int year, int hours)
			throws SQLException {
		statement.setString(1,name);
		statement.setString(2,semester);
		statement.setInt(3,year);
		statement.setInt(4,hours);
		statement.addBatch();
		statement.executeBatch();
	}

	private void insertRecordToLecturer(String fname, String lname, String address,
			String birthdate) throws SQLException {

		Date birth = Date.valueOf(birthdate);
		statement.setString(1, fname);
		statement.setString(2, lname);
		statement.setString(3, address);
		statement.setDate(4,birth);
		statement.addBatch();
		statement.executeBatch();
	}

	private void insertRecordToPhone(int idLecturer, String phoneNum) throws SQLException {
		statement.setString(1, phoneNum);
		statement.setInt(2, idLecturer);
		statement.addBatch();
		statement.executeBatch();
	}

	public static DbHelper getInstance() throws ClassNotFoundException, SQLException, IOException, ParseException {
		if (instance != null) return instance;
		return (instance = new DbHelper());
	}

	public void closeResources() {
		if(statement!=null) try{statement.close();}catch(SQLException e){}
		if(connection!=null) try{connection.close();}catch(SQLException e){}
		if(rs!=null) try{rs.close();}catch(SQLException e){}
	}

	public Connection getConnection() {return connection;}
}