package timetable;

import java.awt.Font;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.table.DefaultTableCellRenderer;

import classes.*;
import dao.*;
import dialogs.*;
import tablemodels.ClassroomTableModel;
import tablemodels.CourseTableModel;
import tablemodels.LecturerTableModel;
import tablemodels.PhoneTableModel;
import tablemodels.TimetableTableModel;

import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.JTabbedPane;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;

public class GuiClass extends JFrame {

	private static final long serialVersionUID = 1L;

	private DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();

	private JPanel contentPane;

	private JTable classroomTable;
	private JTable courseTable;
	private JTable lecturerTable;
	private JTable phoneTable;
	private JTable timetableTable;

	private ClassroomDialog classroomDialog = null;
	private PhoneDialog phoneDialog = null;
	private CourseDialog courseDialog = null;
	private LecturerDialog lecturerDialog = null;

	private ClassroomTableModel classroomTableModel = null;
	private CourseTableModel courseTableModel = null;
	private LecturerTableModel lecturerTableModel = null;
	private PhoneTableModel phoneTableModel = null;
	private TimetableTableModel timetableTableModel = null;

	private List<Classroom> classrooms = null;
	private List<Course> courses = null;
	private List<Lecturer> lecturers = null;
	private List<Phone> phones = null;
	private List<Timetable> timetables = null;

	private ClassroomDAO classroomDAO = null;
	private CourseDAO courseDAO = null;
	private LecturerDAO lecturerDAO = null;
	private PhoneDAO phoneDAO = null;
	private TimetableDAO timetableDAO = null;

	private JPanel panel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton updateButton;


	/**
	 * Create the frame.
	 */
	public GuiClass() {
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		setTitle("Timetable Managment System");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/timetablelogo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 816, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.insets = new Insets(0, 0, 5, 0);
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		contentPane.add(tabbedPane, gbc_tabbedPane);

		try {
			classrooms = (classroomDAO = ClassroomDAO.getInstance()).getAllClassrooms();
			courses = (courseDAO = CourseDAO.getInstance()).getAllCourses();
			lecturers = (lecturerDAO = LecturerDAO.getInstance()).getAllLecturers();
			phones = (phoneDAO = PhoneDAO.getInstance()).getAllPhones();
			timetables = (timetableDAO = TimetableDAO.getInstance()).getAllTimetables();
		} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
			e.printStackTrace();
		}
		classroomTableModel = new ClassroomTableModel(classrooms);
		courseTableModel = new CourseTableModel(courses);
		lecturerTableModel = new LecturerTableModel(lecturers);
		phoneTableModel = new PhoneTableModel(phones);
		timetableTableModel = new TimetableTableModel(timetables);

		classroomTable = new JTable(classroomTableModel);
		tabbedPane.addTab("Classrooms", createImageIcon("images/classroomlogo.png"),
				new JScrollPane(classroomTable), "Watch classroom table");

		classroomTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table =(JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					try {
						new ClassroomInfoDialog((int)table.getValueAt(row, ClassroomTableModel.NUMBER_COL), 
								timetableDAO);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(GuiClass.this, "Error: " + e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		courseTable = new JTable(courseTableModel);
		tabbedPane.addTab("Courses", createImageIcon("images/courselogo.png"),
				new JScrollPane(courseTable), "Watch course table");

		lecturerTable = new JTable(lecturerTableModel);
		tabbedPane.addTab("Lecturers", createImageIcon("images/lecturerlogo.png"),
				new JScrollPane(lecturerTable), "Watch lecturer table");

		lecturerTable.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				JTable table =(JTable) me.getSource();
				Point p = me.getPoint();
				int row = table.rowAtPoint(p);
				if (me.getClickCount() == 2) {
					try {
						new LecturerInfoDialog((int)table.getValueAt(row, LecturerTableModel.ID_COL), 
								(String) table.getValueAt(row, LecturerTableModel.FIRST_NAME_COL),
								(String) table.getValueAt(row, LecturerTableModel.LAST_NAME_COL),
								timetableDAO);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(GuiClass.this, "Error: " + e.getMessage(),
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		phoneTable = new JTable(phoneTableModel);
		tabbedPane.addTab("Lecturers Phone", createImageIcon("images/phonelogo.png"),
				new JScrollPane(phoneTable), "Watch phone table");

		timetableTable = new JTable(timetableTableModel);
		tabbedPane.addTab("Timetable", createImageIcon("images/timetablelogo.png"),
				new JScrollPane(timetableTable), "Watch timetable");

		panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		contentPane.add(panel, gbc_panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {

				int index = tabbedPane.getSelectedIndex();
				switch(index) {
				case 0:		//classroom tab
					addToClassroom();
					break;
				case 1:		//courses tab
					addToCourse();
					break;
				case 2:		//lecturers tab
					addToLecturer();
					break;
				case 3: 	//phones tab
					addToPhone();
					break;
				default: break;
				}
			}
		});
		addButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(addButton);

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tabbedPane.getSelectedIndex();
				try {
					switch(index) {
					case 0:		//classroom tab
						deleteFromClassroom(index);
						break;
					case 1:		//courses tab
						deleteFromCourse(index);
						break;
					case 2:		//lecturers tab
						deleteFromLecturer(index);
						break;
					case 3: 	//phones tab
						deleteFromPhone(index);
						break;
					default: break;
					} 
				} catch(SQLException ex) {
					JOptionPane.showMessageDialog(GuiClass.this, "Error: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(deleteButton);

		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int index = tabbedPane.getSelectedIndex();
				try {
					switch(index) {
					case 0:		//classroom tab
						updateClassroom();
						break;
					case 1:		//courses tab
						updateCourse();
						break;
					case 2:		//lecturers tab
						updateLecturer();
						break;
					case 3: 	//phones tab
						updatePhone();
						break;
					default: break;
					}
				} catch(SQLException ex) {
					JOptionPane.showMessageDialog(GuiClass.this, "Error: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(updateButton);

		alignAllTables();


		tabbedPane.addChangeListener((ChangeEvent e) -> {
			int index = tabbedPane.getSelectedIndex();
			if(index == 4) {		// index 4 is timetable tab
				addButton.setVisible(false);
				deleteButton.setVisible(false);
				updateButton.setVisible(false);
				
			} else {
				addButton.setVisible(true);
				deleteButton.setVisible(true);
				updateButton.setVisible(true);
			}
//			if(index == 2) panel_1.setVisible(true);
//			else panel_1.setVisible(false);
		});
	}

	private void addToClassroom() {
		if (classroomDialog == null || !classroomDialog.isVisible()) { 
			// create dialog
			classroomDialog = new ClassroomDialog(GuiClass.this, classroomDAO);

			// show dialog
			classroomDialog.setVisible(true);
		}
	}

	private void addToLecturer() {
		if (lecturerDialog == null || !lecturerDialog.isVisible()) {
			// create dialog
			lecturerDialog = new LecturerDialog(GuiClass.this, lecturerDAO);

			// show dialog
			lecturerDialog.setVisible(true);
		}
	}

	private void addToCourse() {
		if (courseDialog == null || !courseDialog.isVisible()) {
			// create dialog
			courseDialog = new CourseDialog(GuiClass.this, courseDAO);

			// show dialog
			courseDialog.setVisible(true);
		}
	}

	private void addToPhone() {
		if (phoneDialog == null || !phoneDialog.isVisible()) {
			// create dialog
			phoneDialog = new PhoneDialog(GuiClass.this, phoneDAO);

			// show dialog
			phoneDialog.setVisible(true);
		}
	}

	private void updateClassroom() throws SQLException {
		int row = 0;
		if((row = classroomTable.getSelectedRow()) == -1)
			throw new SQLException("No classroom was selected");

		Classroom tempClassroom = (Classroom)
				classroomTable.getValueAt(row, ClassroomTableModel.OBJECT_COL);

		if (classroomDialog == null || !classroomDialog.isVisible()) {
			// create dialog
			classroomDialog = new ClassroomDialog(GuiClass.this, classroomDAO,
					tempClassroom, true);

			// show dialog
			classroomDialog.setVisible(true);
		}

	}

	private void updateCourse() throws SQLException {
		int row = 0;
		if((row = courseTable.getSelectedRow()) == -1)
			throw new SQLException("No course was selected");

		Course tempCourse = (Course)courseTable.getValueAt(row, CourseTableModel.OBJECT_COL);

		if (courseDialog == null || !courseDialog.isVisible()) {
			// create dialog
			courseDialog = new CourseDialog(GuiClass.this, courseDAO,tempCourse, true);

			// show dialog
			courseDialog.setVisible(true);
		}
	}

	private void updateLecturer() throws SQLException {
		int row = 0;
		if((row = lecturerTable.getSelectedRow()) == -1)
			throw new SQLException("No lecturer was selected");

		Lecturer tempLecturer = 
				(Lecturer)lecturerTable.getValueAt(row, LecturerTableModel.OBJECT_COL);

		if (lecturerDialog == null || !lecturerDialog.isVisible()) {
			// create dialog
			lecturerDialog = new LecturerDialog(GuiClass.this, lecturerDAO,tempLecturer, true);

			// show dialog
			lecturerDialog.setVisible(true);
		}
	}

	private void updatePhone() throws SQLException {
		int row = 0;
		if((row = phoneTable.getSelectedRow()) == -1)
			throw new SQLException("No phone was selected");

		Phone tempPhone = (Phone)phoneTable.getValueAt(row, PhoneTableModel.OBJECT_COL);

		if (phoneDialog == null || !phoneDialog.isVisible()) {
			// create dialog
			phoneDialog = new PhoneDialog(GuiClass.this, phoneDAO,tempPhone, true);

			// show dialog
			phoneDialog.setVisible(true);
		}
	}


	private void deleteFromClassroom(int index) throws SQLException {
		int row = 0;
		if((row = classroomTable.getSelectedRow()) == -1)
			throw new SQLException("No classroom was selected");
		int classNumber = (int) classroomTable.getValueAt(row, 0);

		int response = confirmMessage
				("class number "+classNumber+"?"); // prompt the user
		if (response != JOptionPane.YES_OPTION) return;

		try {classroomDAO.deleteClassroom(classNumber);}
		catch(SQLException e) {throw new SQLException("Cannot delete: classroom exists in timetable");}
		//		//TODO	
		//		ArrayList<Timetable> classroomsToUpdate = timetableDAO.getSimilarClassrooms(classNumber);
		//		for (Timetable timetable : classroomsToUpdate) {
		//			System.out.println(timetable);
		//		}
		refreshClassroomView();
	}

	private void deleteFromCourse(int index) throws SQLException {
		int row = 0;
		if((row = courseTable.getSelectedRow()) == -1)
			throw new SQLException("No course was selected");
		int courseNum = (int) courseTable.getValueAt(row, 0);

		int response = confirmMessage
				("course num "+courseNum+"? (timetable will be affected)"); // prompt the user
		if (response != JOptionPane.YES_OPTION) return;
		courseDAO.deleteCourse(courseNum);
		refreshCourseView();
		refreshTimetableView();
	}

	private void deleteFromLecturer(int index) throws SQLException {
		int row = 0;
		if((row = lecturerTable.getSelectedRow()) == -1)
			throw new SQLException("No lecturer was selected");
		int lecturerId = (int) lecturerTable.getValueAt(row,0);
		int response = confirmMessage
				("lecturer who's id is "+ lecturerId+" ? (timetable will be affected)"); // prompt the user
		if (response != JOptionPane.YES_OPTION) return;

		lecturerDAO.deleteLecturer(lecturerId);
		refreshLecturerView();
		refreshPhoneView();
		refreshTimetableView();
	}

	private void deleteFromPhone(int index) throws SQLException {
		int row = 0;
		if((row = phoneTable.getSelectedRow()) == -1)
			throw new SQLException("No phone was selected");
		String number = (String) phoneTable.getValueAt(row, 0);

		int response = confirmMessage("phone "+ number+" ?"); // prompt the user
		if (response != JOptionPane.YES_OPTION) return;
		phoneDAO.deletePhone(number);
		refreshPhoneView();
	}

	private int confirmMessage(String message) {
		return JOptionPane.showConfirmDialog(
				GuiClass.this, "Delete "+message, "Confirm", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	/* Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GuiClass.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/*============================================================================*/
	/*============================================================================*/
	/*==Refreshing and updating tables (NO REASON TO GET HERE, ITS A BLACK BOX)===*/
	/*========================(Will be refactored later)==========================*/
	/*============================================================================*/

	public void refreshClassroomView() throws SQLException {
		classrooms = classroomDAO.getAllClassrooms();

		// create the model and update the "table"
		classroomTableModel = new ClassroomTableModel(classrooms);

		classroomTable.setModel(classroomTableModel);
		alignClassroomTable();
	}

	public void refreshCourseView() throws SQLException {
		courses = courseDAO.getAllCourses();

		// create the model and update the "table"
		courseTableModel = new CourseTableModel(courses);

		courseTable.setModel(courseTableModel);
		alignCourseTable();

	}

	public void refreshLecturerView() throws SQLException {
		lecturers = lecturerDAO.getAllLecturers();

		// create the model and update the "table"
		lecturerTableModel = new LecturerTableModel(lecturers);

		lecturerTable.setModel(lecturerTableModel);
		alignLecturerTable();
	}

	public void refreshPhoneView() throws SQLException {
		phones = phoneDAO.getAllPhones();

		// create the model and update the "table"
		phoneTableModel = new PhoneTableModel(phones);

		phoneTable.setModel(phoneTableModel);
		alignPhoneTable();

	}

	public void refreshTimetableView() throws SQLException {
		timetables = timetableDAO.getAllTimetables();

		// create the model and update the "table"
		timetableTableModel = new TimetableTableModel(timetables);

		timetableTable.setModel(timetableTableModel);
		alignTimetableTable();

	}

	private void alignAllTables() {
		alignClassroomTable();
		alignCourseTable();
		alignLecturerTable();
		alignPhoneTable();
		alignTimetableTable();
	}

	private void alignClassroomTable() {
		for(int i=0;i<classroomTable.getColumnCount();i++)
			classroomTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}

	private void alignCourseTable() {
		for(int i=0;i<courseTable.getColumnCount();i++)
			courseTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}

	private void alignLecturerTable() {
		for(int i=0;i<lecturerTable.getColumnCount();i++)
			lecturerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}

	private void alignPhoneTable() {
		for(int i=0;i<phoneTable.getColumnCount();i++)
			phoneTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}

	private void alignTimetableTable() {
		for(int i=0;i<timetableTable.getColumnCount();i++)
			timetableTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	}
}