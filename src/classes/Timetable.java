package classes;

public class Timetable {

	private int courseNum;
	private int lecturerId;
	private int classNum;
	private String day;
	private int hour;
	
	public Timetable(int courseNum, int lecturerId, int classNum, String day, int hour) {
		this.courseNum = courseNum;
		this.lecturerId = lecturerId;
		this.classNum = classNum;
		this.day = day;
		this.hour = hour;
	}
	public int getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	public int getLecturerId() {
		return lecturerId;
	}
	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}
	public int getClassNum() {
		return classNum;
	}
	public void setClassNum(int classNum) {
		this.classNum = classNum;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	@Override
	public String toString() {
		return "Timetable [courseNum=" + courseNum + ", lecturerId=" + lecturerId + ", classNum=" + classNum + ", day="
				+ day + ", hour=" + hour + "]";
	}
	
	
}
