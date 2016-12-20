package dialogs;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Course;
import dao.CourseDAO;
import timetable.Application;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JComboBox;

public class CourseDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private CourseDAO CourseDAO;
	private Application guiClass;

	private Course previousCourse = null;
	private boolean updateMode = false;
	private JTextField nameTextField;
	private JTextField yearTextField;
	private JComboBox<String> semesterComboBox;
	private JComboBox<Integer> hoursComboBox;

	public CourseDialog(Application guiClass,
			CourseDAO theCourseDAO, Course thePreviousCourse, boolean theUpdateMode) {

		this();
		CourseDAO = theCourseDAO;
		this.guiClass = guiClass;
		previousCourse = thePreviousCourse;

		if (updateMode = theUpdateMode) {
			setTitle("Update Course");
			populateGui(previousCourse);
		}
	}

	public CourseDialog(Application guiClass,CourseDAO theCourseDAO) {
		this(guiClass, theCourseDAO, null, false);
	}

	private void populateGui(Course theCourse) {


		nameTextField.setText(theCourse.getName());
		if(!theCourse.getSemester().equals("a") && theCourse.getSemester() != null) {
			if(theCourse.getSemester().equals("b"))
				semesterComboBox.setSelectedIndex(1);
			else
				semesterComboBox.setSelectedIndex(2);
		}
				//		semesterComboBox.setSelectedIndex(anIndex);
				yearTextField.setText(String.valueOf(theCourse.getYear()));
				hoursComboBox.setSelectedIndex(theCourse.getHours()-1);
	}

	/**
	 * Create the dialog.
	 */
	public CourseDialog() {
		setTitle("Add Course");
		setIconImage(Toolkit.getDefaultToolkit().getImage("images/addlogo.png"));
		setBounds(100, 100, 405, 204);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[]{0, 0, 0, 0};
		gbl_contentPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel nameLabel = new JLabel("Name");
			GridBagConstraints gbc_nameLabel = new GridBagConstraints();
			gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_nameLabel.anchor = GridBagConstraints.EAST;
			gbc_nameLabel.gridx = 1;
			gbc_nameLabel.gridy = 1;
			contentPanel.add(nameLabel, gbc_nameLabel);
		}
		{
			nameTextField = new JTextField();
			GridBagConstraints gbc_nameTextField = new GridBagConstraints();
			gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
			gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_nameTextField.gridx = 2;
			gbc_nameTextField.gridy = 1;
			contentPanel.add(nameTextField, gbc_nameTextField);
			nameTextField.setColumns(10);
		}
		{
			JLabel semesterLabel = new JLabel("Semester");
			GridBagConstraints gbc_semesterLabel = new GridBagConstraints();
			gbc_semesterLabel.anchor = GridBagConstraints.EAST;
			gbc_semesterLabel.insets = new Insets(0, 0, 5, 5);
			gbc_semesterLabel.gridx = 1;
			gbc_semesterLabel.gridy = 2;
			contentPanel.add(semesterLabel, gbc_semesterLabel);
		}
		{
			String[] semesterOptions = {"a","b","c"};
			semesterComboBox = new JComboBox<>(semesterOptions);

			GridBagConstraints gbc_semesterComboBox = new GridBagConstraints();
			gbc_semesterComboBox.insets = new Insets(0, 0, 5, 0);
			gbc_semesterComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_semesterComboBox.gridx = 2;
			gbc_semesterComboBox.gridy = 2;
			contentPanel.add(semesterComboBox, gbc_semesterComboBox);
		}
		{
			JLabel yearLabel = new JLabel("Year");
			GridBagConstraints gbc_yearLabel = new GridBagConstraints();
			gbc_yearLabel.anchor = GridBagConstraints.EAST;
			gbc_yearLabel.insets = new Insets(0, 0, 5, 5);
			gbc_yearLabel.gridx = 1;
			gbc_yearLabel.gridy = 3;
			contentPanel.add(yearLabel, gbc_yearLabel);
		}
		{
			yearTextField = new JTextField();
			GridBagConstraints gbc_yearTextField = new GridBagConstraints();
			gbc_yearTextField.insets = new Insets(0, 0, 5, 0);
			gbc_yearTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_yearTextField.gridx = 2;
			gbc_yearTextField.gridy = 3;
			contentPanel.add(yearTextField, gbc_yearTextField);
			yearTextField.setColumns(10);
		}
		{
			JLabel hoursLabel = new JLabel("Hours");
			GridBagConstraints gbc_hoursLabel = new GridBagConstraints();
			gbc_hoursLabel.anchor = GridBagConstraints.EAST;
			gbc_hoursLabel.insets = new Insets(0, 0, 0, 5);
			gbc_hoursLabel.gridx = 1;
			gbc_hoursLabel.gridy = 4;
			contentPanel.add(hoursLabel, gbc_hoursLabel);
		}
		{
			Integer[] hourOptions = {1,2,3,4,5,6};
			hoursComboBox = new JComboBox<>(hourOptions);
			GridBagConstraints gbc_hoursComboBox = new GridBagConstraints();
			gbc_hoursComboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_hoursComboBox.gridx = 2;
			gbc_hoursComboBox.gridy = 4;
			contentPanel.add(hoursComboBox, gbc_hoursComboBox);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						saveCourse();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						CourseDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void saveCourse() {

		// get the Course info from gui
		try {
			Course tempCourse = null;
			String name = nameTextField.getText();
			String semester = (String) semesterComboBox.getSelectedItem();
			int year = Integer.parseInt(yearTextField.getText());
			if(year < 1900 || year > 2999)
				throw new NumberFormatException("Year must be 1900-2999");
			int hours = (int) hoursComboBox.getSelectedItem();

			if (updateMode) {
				tempCourse = previousCourse;
				tempCourse.setName(name);
				tempCourse.setSemester(semester);
				tempCourse.setYear(year);
				tempCourse.setHours(hours);

			} else {
				tempCourse = new Course(0,name, semester, year, hours);
			}


			// save to the database
			if (updateMode) {
				CourseDAO.updateCourse(tempCourse);
			} else {
				CourseDAO.addCourse(tempCourse);
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			guiClass.refreshCourseView();

			// show success message
			JOptionPane.showMessageDialog(guiClass,
					"Course saved succesfully.", "Course Saved",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(guiClass,
					"Error saving Course: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}