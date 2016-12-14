package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import classes.Course;

public class CourseTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private static final int OBJECT_COL = -1;
	private static final int NUMBER_COL = 0;
	private static final int NAME_COL = 1;
	private static final int SEMESTER_COL = 2;
	private static final int YEAR_COL = 3;
	private static final int HOURS_COL = 4;
	
	private String[] columnNames = { "Number", "Name", "Semester","Year", "Hours"};
	private List<Course> Courses;

	public CourseTableModel(List<Course> theCourses) {
		Courses = theCourses;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return Courses.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Course tempCourse = Courses.get(rowIndex);

		switch (columnIndex) {
		case NUMBER_COL:
			return tempCourse.getNumber();
		case NAME_COL:
			return tempCourse.getName();
		case SEMESTER_COL:
			return tempCourse.getSemester();
		case YEAR_COL:
			return tempCourse.getYear();
		case HOURS_COL:
			return tempCourse.getHours();
		case OBJECT_COL:
			return tempCourse;
		default:
			return tempCourse.getName();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}