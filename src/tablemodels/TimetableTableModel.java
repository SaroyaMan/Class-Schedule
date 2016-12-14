package tablemodels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import classes.Timetable;

public class TimetableTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private static final int OBJECT_COL = -1;
	private static final int COURSENUM_COL = 0;
	private static final int LECTURERID_COL = 1;
	private static final int CLASSNUM_COL = 2;
	private static final int DAY_COL = 3;
	private static final int HOUR_COL = 4;
	
	private String[] columnNames = { "Course Number", "Lecturer Id", "Class Number","Day", "Hour"};
	private List<Timetable> Timetables;

	public TimetableTableModel(List<Timetable> theTimetables) {
		Timetables = theTimetables;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return Timetables.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Timetable tempTimetable = Timetables.get(rowIndex);

		switch (columnIndex) {
		case COURSENUM_COL:
			return tempTimetable.getCourseNum();
		case LECTURERID_COL:
			return tempTimetable.getLecturerId();
		case CLASSNUM_COL:
			return tempTimetable.getClassNum();
		case DAY_COL:
			return tempTimetable.getDay();
		case HOUR_COL:
			return tempTimetable.getHour();
		case OBJECT_COL:
			return tempTimetable;
		default:
			return tempTimetable.getCourseNum();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}