package dialogs;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.TimetableDAO;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.awt.GridBagConstraints;

public class ClassroomInfoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public ClassroomInfoDialog(int classNumber,TimetableDAO _timetableDAO) throws SQLException {
		setTitle("Classroom Info: "+classNumber);
		TimetableDAO timetableDAO = _timetableDAO;

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0};
		gbl_contentPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		contentPanel.add(scrollPane, gbc_scrollPane);

		String[] columns = {"Lecturer ID", "Lecturer Name", "Course Number", "Course Name"};
		String[][] rows = null;
		table = new JTable((new DefaultTableModel(rows, columns))) {

			private static final long serialVersionUID = -8543155634392823341L;
			public boolean isCellEditable(int data, int columns) {return false;}

		};
		scrollPane.setViewportView(table);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int x=0;x<table.getColumnCount();x++)
			table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );


		/*Start*/
//		rows = timetableDAO.getClassroomInfo(classNumber);


		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
