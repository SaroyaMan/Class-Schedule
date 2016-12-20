package dialogs;

import java.awt.BorderLayout;


import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import dao.TimetableDAO;
import timetable.Application;

import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.GridBagConstraints;

public class LecturerInfoDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * Create the dialog.
	 * @throws SQLException 
	 */
	public LecturerInfoDialog(int lecturerId, String fName, String lName, TimetableDAO _timetableDAO, 
			Application guiClass)
			throws SQLException {
		setTitle("Lecturer Info: "+lecturerId+" - "+fName+" "+lName);
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/infologo.png"));
		TimetableDAO timetableDAO = _timetableDAO;

		setBounds(100, 100, 613, 300);
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

		Vector<String> columns = new Vector<>();
		columns.add("Class Number"); columns.add("Course Number"); 
		columns.add("Course Name"); columns.add("Day"); columns.add("Hour");
		Vector<Vector<String>> rows = timetableDAO.getLecturerInfo(lecturerId);
		if(rows.isEmpty()) {
			JOptionPane.showMessageDialog(guiClass,
					"Error:" + "This lecturer doesn't teaching", "Error",
					JOptionPane.ERROR_MESSAGE);
			this.dispose();
			return;
		}
		table = new JTable(rows, columns) {

			private static final long serialVersionUID = -8543155634392823341L;
			public boolean isCellEditable(int data, int columns) {return false;}

		};
		scrollPane.setViewportView(table);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int x=0;x<table.getColumnCount();x++)
			table.getColumnModel().getColumn(x).setCellRenderer( centerRenderer );

		
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setVisible(true);
	}
}
