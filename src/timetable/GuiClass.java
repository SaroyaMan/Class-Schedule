package timetable;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import classes.*;
import dao.*;
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
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class GuiClass extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable classroomTable;
	private JTable courseTable;
	private JTable lecturerTable;
	private JTable phoneTable;
	private JTable timetableTable;


	List<Classroom> classrooms = null;
	List<Course> courses = null;
	List<Lecturer> lecturers = null;
	List<Phone> phones = null;
	List<Timetable> timetables = null;
	private JPanel panel;
	private JButton addButton;
	private JButton deleteButton;
	private JButton updateButton;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GuiClass frame = new GuiClass();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GuiClass() {
		setTitle("Timetable Managment System");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/timetablelogo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 816, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
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
			classrooms = ClassroomDAO.getInstance().getAllClassrooms();
			courses = CourseDAO.getInstance().getAllCourses();
			lecturers = LecturerDAO.getInstance().getAllLecturers();
			phones = PhoneDAO.getInstance().getAllPhones();
			timetables = TimetableDAO.getInstance().getAllTimetables();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ClassroomTableModel classroomTableModel = new ClassroomTableModel(classrooms);
		CourseTableModel courseTableModel = new CourseTableModel(courses);
		LecturerTableModel lecturerTableModel = new LecturerTableModel(lecturers);
		PhoneTableModel phoneTableModel = new PhoneTableModel(phones);
		TimetableTableModel timetableTableModel = new TimetableTableModel(timetables);

		classroomTable = new JTable(classroomTableModel);
		tabbedPane.addTab("Classrooms", createImageIcon("images/classroomlogo.png"),
				new JScrollPane(classroomTable), "Watch classroom table");

//		tabbedPane.getTabComponentAt(0).add(new JScrollPane(classroomTable)));
		
		courseTable = new JTable(courseTableModel);
		tabbedPane.addTab("Courses", createImageIcon("images/courselogo.png"),
				new JScrollPane(courseTable), "Watch course table");
		
		lecturerTable = new JTable(lecturerTableModel);
		tabbedPane.addTab("Lecturers", createImageIcon("images/lecturerlogo.png"),
				new JScrollPane(lecturerTable), "Watch lecturer table");

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
		addButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(addButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(deleteButton);
		
		updateButton = new JButton("Update");
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel.add(updateButton);

		alignValuesInTables();
	}


	/** Returns an ImageIcon, or null if the path was invalid. */
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = View.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	private void alignValuesInTables() {

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for(int i=0;i<classroomTable.getColumnCount();i++)
			classroomTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		for(int i=0;i<courseTable.getColumnCount();i++)
			courseTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		for(int i=0;i<lecturerTable.getColumnCount();i++)
			lecturerTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		for(int i=0;i<phoneTable.getColumnCount();i++)
			phoneTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

		for(int i=0;i<timetableTable.getColumnCount();i++)
			timetableTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);

	}
}