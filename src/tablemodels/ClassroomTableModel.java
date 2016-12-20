package tablemodels;

import java.util.List;


import javax.swing.table.AbstractTableModel;

import classes.Classroom;

public class ClassroomTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	public static final int OBJECT_COL = -1;
	public static final int NUMBER_COL = 0;
	public static final int BUILDING_COL = 1;
	public static final int FLOOR_COL = 2;
	
	private String[] columnNames = { "Number", "Building", "Floor"};
	private List<Classroom> classrooms;

	public ClassroomTableModel(List<Classroom> theclassrooms) {
		classrooms = theclassrooms;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return classrooms.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Classroom tempClassroom = classrooms.get(rowIndex);

		switch (columnIndex) {
		case NUMBER_COL:
			return tempClassroom.getNumber();
		case BUILDING_COL:
			return tempClassroom.getBuilding();
		case FLOOR_COL:
			return tempClassroom.getFloor();
		case OBJECT_COL:
			return tempClassroom;
		default:
			return tempClassroom.getNumber();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}