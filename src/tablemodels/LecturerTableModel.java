package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import classes.Lecturer;

public class LecturerTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int FIRST_NAME_COL = 1;
	private static final int LAST_NAME_COL = 2;
	private static final int ADDRESS_COL = 3;
	private static final int BIRTHDATE_COL = 4;
	private static final int AGE_COL = 5;
	
	private String[] columnNames = { "ID", "First Name", "Last Name","Address", "Birthdate","Age" };
	private List<Lecturer> lecturers;

	public LecturerTableModel(List<Lecturer> theLecturers) {
		lecturers = theLecturers;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return lecturers.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Lecturer tempLecturer = lecturers.get(rowIndex);

		switch (columnIndex) {
		case ID_COL:
			return tempLecturer.getId();
		case FIRST_NAME_COL:
			return tempLecturer.getFirstName();
		case LAST_NAME_COL:
			return tempLecturer.getLastName();
		case ADDRESS_COL:
			return tempLecturer.getAddress();
		case BIRTHDATE_COL:
			return tempLecturer.getAddress();
		case AGE_COL:
			return tempLecturer.getAge();
		case OBJECT_COL:
			return tempLecturer;
		default:
			return tempLecturer.getLastName();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}