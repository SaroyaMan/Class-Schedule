package classes;

public class Course {

	private int number;
	private String name;
	private String semester;
	private int year;
	private int hours;
	
	
	
	public Course(int number, String name, String semester, int year, int hours) {
		this.number = number;
		this.name = name;
		this.year = year;
		this.semester = semester;
		this.hours = hours;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	@Override
	public String toString() {
		return "Course [number=" + number + ", name=" + name + ", year=" + year + ", semester=" + semester + ", hours="
				+ hours + "]";
	}
	
	
	
}