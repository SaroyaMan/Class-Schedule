package tablemodels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import classes.Phone;

public class PhoneTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	public static final int OBJECT_COL = -1;
	public static final int NUMBER_COL = 0;
	public static final int ID_COL = 1;

	private String[] columnNames = { "Number", "Lecturer ID" };
	private List<Phone> phones;

	public PhoneTableModel(List<Phone> thephones) {
		phones = thephones;
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		return phones.size();
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Phone tempPhone = phones.get(rowIndex);

		switch (columnIndex) {
		case NUMBER_COL:
			return tempPhone.getNumber();
		case ID_COL:
			return tempPhone.getIdLecturer();
		case OBJECT_COL:
			return tempPhone;
		default:
			return tempPhone.getNumber();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
