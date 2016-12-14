package tablemodels;

import javax.swing.table.AbstractTableModel;
import java.util.List;
import classes.Phone;

public class PhoneTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private static final int OBJECT_COL = -1;
	private static final int ID_COL = 0;
	private static final int NUMBER_COL = 1;

	private String[] columnNames = { "ID", "Number" };
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
		case ID_COL:
			return tempPhone.getId();
		case NUMBER_COL:
			return tempPhone.getNumber();
		case OBJECT_COL:
			return tempPhone;
		default:
			return tempPhone.getId();
		}
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return getValueAt(0, c).getClass();
	}
}
