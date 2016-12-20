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

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class Application extends JFrame {

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

	private JPanel buttonPanel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton updateButton;
	private JPanel filterLecturerPanel;
	private JLabel dayFromLabel;
	private JLabel dayToLabel;
	private JLabel hourToLabel;
	private JLabel hourFromLabel;
	private JButton filterLecturerButton;
	private JComboBox<String> toDayOfWeekComBox;
	private JComboBox<String> fromDayOfWeekComBox;
	private JComboBox<String> fromHourComboBox;
	private JComboBox<String> toHourComboBox;
	private JLabel fromLabel;
	private JLabel toLabel;
	private JButton resetLecturerButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> new Application().setVisible(true));
	}

	/**
	 * Create the frame.
	 */
	public Application() {
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
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
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
								timetableDAO, Application.this);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(Application.this, "Error: " + e.getMessage(),
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
								timetableDAO, Application.this);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(Application.this, "Error: " + e.getMessage(),
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

		buttonPanel = new JPanel();
		GridBagConstraints gbc_buttonPanel = new GridBagConstraints();
		gbc_buttonPanel.insets = new Insets(0, 0, 5, 0);
		gbc_buttonPanel.fill = GridBagConstraints.BOTH;
		gbc_buttonPanel.gridx = 0;
		gbc_buttonPanel.gridy = 1;
		contentPane.add(buttonPanel, gbc_buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

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
		buttonPanel.add(addButton);

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
					JOptionPane.showMessageDialog(Application.this, "Error: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		buttonPanel.add(deleteButton);

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
					JOptionPane.showMessageDialog(Application.this, "Error: " + ex.getMessage(),
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		buttonPanel.add(updateButton);
		
		filterLecturerPanel = new JPanel();
		filterLecturerPanel.setVisible(false);
		GridBagConstraints gbc_filterLecturerPanel = new GridBagConstraints();
		gbc_filterLecturerPanel.gridheight = 2;
		gbc_filterLecturerPanel.insets = new Insets(0, 0, 5, 0);
		gbc_filterLecturerPanel.fill = GridBagConstraints.VERTICAL;
		gbc_filterLecturerPanel.gridx = 0;
		gbc_filterLecturerPanel.gridy = 2;
		contentPane.add(filterLecturerPanel, gbc_filterLecturerPanel);
		GridBagLayout gbl_filterLecturerPanel = new GridBagLayout();
		gbl_filterLecturerPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_filterLecturerPanel.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_filterLecturerPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_filterLecturerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		filterLecturerPanel.setLayout(gbl_filterLecturerPanel);
		
		fromLabel = new JLabel("From:");
		fromLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_fromLabel = new GridBagConstraints();
		gbc_fromLabel.gridwidth = 2;
		gbc_fromLabel.insets = new Insets(0, 0, 5, 5);
		gbc_fromLabel.gridx = 5;
		gbc_fromLabel.gridy = 0;
		filterLecturerPanel.add(fromLabel, gbc_fromLabel);
		
		toLabel = new JLabel("To:");
		toLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		GridBagConstraints gbc_toLabel = new GridBagConstraints();
		gbc_toLabel.gridwidth = 2;
		gbc_toLabel.insets = new Insets(0, 0, 5, 5);
		gbc_toLabel.gridx = 12;
		gbc_toLabel.gridy = 0;
		filterLecturerPanel.add(toLabel, gbc_toLabel);
		
		dayFromLabel = new JLabel("Day");
		GridBagConstraints gbc_dayFromLabel = new GridBagConstraints();
		gbc_dayFromLabel.anchor = GridBagConstraints.EAST;
		gbc_dayFromLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dayFromLabel.gridx = 5;
		gbc_dayFromLabel.gridy = 1;
		filterLecturerPanel.add(dayFromLabel, gbc_dayFromLabel);
		
		fromDayOfWeekComBox = new JComboBox<>();
		fromDayOfWeekComBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}));
		GridBagConstraints gbc_fromDayOfWeekComBox = new GridBagConstraints();
		gbc_fromDayOfWeekComBox.insets = new Insets(0, 0, 5, 5);
		gbc_fromDayOfWeekComBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_fromDayOfWeekComBox.gridx = 8;
		gbc_fromDayOfWeekComBox.gridy = 1;
		filterLecturerPanel.add(fromDayOfWeekComBox, gbc_fromDayOfWeekComBox);
		
		dayToLabel = new JLabel("Day");
		GridBagConstraints gbc_dayToLabel = new GridBagConstraints();
		gbc_dayToLabel.anchor = GridBagConstraints.NORTHEAST;
		gbc_dayToLabel.insets = new Insets(0, 0, 5, 5);
		gbc_dayToLabel.gridx = 12;
		gbc_dayToLabel.gridy = 1;
		filterLecturerPanel.add(dayToLabel, gbc_dayToLabel);
		
		toDayOfWeekComBox = new JComboBox<>();
		toDayOfWeekComBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"}));
		toDayOfWeekComBox.setSelectedIndex(5);
		GridBagConstraints gbc_toDayOfWeekComBox = new GridBagConstraints();
		gbc_toDayOfWeekComBox.gridwidth = 4;
		gbc_toDayOfWeekComBox.insets = new Insets(0, 0, 5, 5);
		gbc_toDayOfWeekComBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_toDayOfWeekComBox.gridx = 13;
		gbc_toDayOfWeekComBox.gridy = 1;
		filterLecturerPanel.add(toDayOfWeekComBox, gbc_toDayOfWeekComBox);
		
		hourFromLabel = new JLabel("Hour");
		GridBagConstraints gbc_hourFromLabel = new GridBagConstraints();
		gbc_hourFromLabel.anchor = GridBagConstraints.EAST;
		gbc_hourFromLabel.insets = new Insets(0, 0, 5, 5);
		gbc_hourFromLabel.gridx = 5;
		gbc_hourFromLabel.gridy = 2;
		filterLecturerPanel.add(hourFromLabel, gbc_hourFromLabel);
		
		fromHourComboBox = new JComboBox<>();
		fromHourComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"}));
		GridBagConstraints gbc_fromHourComboBox = new GridBagConstraints();
		gbc_fromHourComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_fromHourComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_fromHourComboBox.gridx = 8;
		gbc_fromHourComboBox.gridy = 2;
		filterLecturerPanel.add(fromHourComboBox, gbc_fromHourComboBox);
		
		hourToLabel = new JLabel("Hour");
		GridBagConstraints gbc_hourToLabel = new GridBagConstraints();
		gbc_hourToLabel.anchor = GridBagConstraints.EAST;
		gbc_hourToLabel.insets = new Insets(0, 0, 5, 5);
		gbc_hourToLabel.gridx = 12;
		gbc_hourToLabel.gridy = 2;
		filterLecturerPanel.add(hourToLabel, gbc_hourToLabel);
		
		toHourComboBox = new JComboBox<>();
		toHourComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"}));
		toHourComboBox.setSelectedIndex(14);
		GridBagConstraints gbc_toHourComboBox = new GridBagConstraints();
		gbc_toHourComboBox.gridwidth = 4;
		gbc_toHourComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_toHourComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_toHourComboBox.gridx = 13;
		gbc_toHourComboBox.gridy = 2;
		filterLecturerPanel.add(toHourComboBox, gbc_toHourComboBox);
		
		filterLecturerButton = new JButton("Filter");
		filterLecturerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int fromDayOfWeek = fromDayOfWeekComBox.getSelectedIndex()+1;
				int fromHour = Integer.parseInt(((String) fromHourComboBox.getItemAt(fromHourComboBox.getSelectedIndex())).substring(0, 2));
				
				int toDayOfWeek = toDayOfWeekComBox.getSelectedIndex()+1;
				int toHour = Integer.parseInt(((String) toHourComboBox.getItemAt(toHourComboBox.getSelectedIndex())).substring(0, 2));
				
				try {
					lecturers = lecturerDAO.filterLecturers(fromDayOfWeek, fromHour, toDayOfWeek, toHour);
					lecturerTableModel = new LecturerTableModel(lecturers);
					lecturerTable.setModel(lecturerTableModel);
					alignLecturerTable();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		filterLecturerButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_filterLecturerButton = new GridBagConstraints();
		gbc_filterLecturerButton.insets = new Insets(0, 0, 0, 5);
		gbc_filterLecturerButton.gridx = 10;
		gbc_filterLecturerButton.gridy = 3;
		filterLecturerPanel.add(filterLecturerButton, gbc_filterLecturerButton);
		
		resetLecturerButton = new JButton("Reset");
		resetLecturerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fromDayOfWeekComBox.setSelectedIndex(0);
				fromHourComboBox.setSelectedIndex(0);
				toDayOfWeekComBox.setSelectedIndex(5);
				toHourComboBox.setSelectedIndex(14);
				try {
					refreshLecturerView();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		resetLecturerButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		GridBagConstraints gbc_resetLecturerButton = new GridBagConstraints();
		gbc_resetLecturerButton.insets = new Insets(0, 0, 0, 5);
		gbc_resetLecturerButton.gridx = 11;
		gbc_resetLecturerButton.gridy = 3;
		filterLecturerPanel.add(resetLecturerButton, gbc_resetLecturerButton);

		alignAllTables();

		tabbedPane.addChangeListener((ChangeEvent e) -> {
			int index = tabbedPane.getSelectedIndex();
			if(index == 4) {		// index 4 is timetable tab
				buttonPanel.setVisible(false);
				
			} else {
				buttonPanel.setVisible(true);
			}
			if(index == 2) filterLecturerPanel.setVisible(true);
			else filterLecturerPanel.setVisible(false);
		});
	}

	private void addToClassroom() {
		if (classroomDialog == null || !classroomDialog.isVisible()) { 
			// create dialog
			classroomDialog = new ClassroomDialog(Application.this, classroomDAO);

			// show dialog
			classroomDialog.setVisible(true);
		}
	}

	private void addToLecturer() {
		if (lecturerDialog == null || !lecturerDialog.isVisible()) {
			// create dialog
			lecturerDialog = new LecturerDialog(Application.this, lecturerDAO);

			// show dialog
			lecturerDialog.setVisible(true);
		}
	}

	private void addToCourse() {
		if (courseDialog == null || !courseDialog.isVisible()) {
			// create dialog
			courseDialog = new CourseDialog(Application.this, courseDAO);

			// show dialog
			courseDialog.setVisible(true);
		}
	}

	private void addToPhone() {
		if (phoneDialog == null || !phoneDialog.isVisible()) {
			// create dialog
			phoneDialog = new PhoneDialog(Application.this, phoneDAO);

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
			classroomDialog = new ClassroomDialog(Application.this, classroomDAO,
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
			courseDialog = new CourseDialog(Application.this, courseDAO,tempCourse, true);

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
			lecturerDialog = new LecturerDialog(Application.this, lecturerDAO,tempLecturer, true);

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
			phoneDialog = new PhoneDialog(Application.this, phoneDAO,tempPhone, true);

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
				Application.this, "Delete "+message, "Confirm", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	}

	/* Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Application.class.getResource(path);
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