package dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jdatepicker.JDateComponentFactory;
import org.jdatepicker.JDatePicker;

import classes.Lecturer;
import dao.LecturerDAO;
import timetable.GuiClass;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

public class LecturerDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private LecturerDAO LecturerDAO;
	private GuiClass guiClass;

	private Lecturer previousLecturer = null;
	private boolean updateMode = false;
	private JTextField firstNameTextField;
	private JTextField addressTextField;
	private JTextField lastNameTextField;

	private JDatePicker datePicker;
	
	public LecturerDialog(GuiClass guiClass,
			LecturerDAO theLecturerDAO, Lecturer thePreviousLecturer, boolean theUpdateMode) {

		this();
		LecturerDAO = theLecturerDAO;
		this.guiClass = guiClass;
		previousLecturer = thePreviousLecturer;

		if (updateMode = theUpdateMode) {
			setTitle("Update Lecturer");
			populateGui(previousLecturer);
		}
	}

	public LecturerDialog(GuiClass guiClass,LecturerDAO theLecturerDAO) {
		this(guiClass, theLecturerDAO, null, false);
	}

	private void populateGui(Lecturer theLecturer) {
		firstNameTextField.setText(theLecturer.getFirstName());
		lastNameTextField.setText(theLecturer.getLastName());
		addressTextField.setText(theLecturer.getAddress());
		int year = Integer.parseInt(theLecturer.getBirthdate().toString().substring(0, 4));
		datePicker.getModel().setYear(year);
		int month = Integer.parseInt(theLecturer.getBirthdate().toString().substring(5, 7))-1;
		datePicker.getModel().setMonth(month);
		int days = Integer.parseInt(theLecturer.getBirthdate().toString().substring(8, 10));
		datePicker.getModel().setDay(days);
	}

	/**
	 * Create the dialog.
	 */
	public LecturerDialog() {
		setTitle("Add Lecturer");
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
			JLabel firstNameLabel = new JLabel("First Name");
			GridBagConstraints gbc_firstNameLabel = new GridBagConstraints();
			gbc_firstNameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_firstNameLabel.anchor = GridBagConstraints.EAST;
			gbc_firstNameLabel.gridx = 1;
			gbc_firstNameLabel.gridy = 1;
			contentPanel.add(firstNameLabel, gbc_firstNameLabel);
		}
		{
			firstNameTextField = new JTextField();
			GridBagConstraints gbc_firstNameTextField = new GridBagConstraints();
			gbc_firstNameTextField.insets = new Insets(0, 0, 5, 0);
			gbc_firstNameTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_firstNameTextField.gridx = 2;
			gbc_firstNameTextField.gridy = 1;
			contentPanel.add(firstNameTextField, gbc_firstNameTextField);
			firstNameTextField.setColumns(10);
		}
		{
			JLabel lastNameLabel = new JLabel("Last Name");
			GridBagConstraints gbc_lastNameLabel = new GridBagConstraints();
			gbc_lastNameLabel.anchor = GridBagConstraints.EAST;
			gbc_lastNameLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lastNameLabel.gridx = 1;
			gbc_lastNameLabel.gridy = 2;
			contentPanel.add(lastNameLabel, gbc_lastNameLabel);
		}

		{
			lastNameTextField = new JTextField();
			GridBagConstraints gbc_lastNameTextField = new GridBagConstraints();
			gbc_lastNameTextField.insets = new Insets(0, 0, 5, 0);
			gbc_lastNameTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_lastNameTextField.gridx = 2;
			gbc_lastNameTextField.gridy = 2;
			contentPanel.add(lastNameTextField, gbc_lastNameTextField);
			lastNameTextField.setColumns(10);
		}
		{
			JLabel addressLabel = new JLabel("Address");
			GridBagConstraints gbc_addressLabel = new GridBagConstraints();
			gbc_addressLabel.anchor = GridBagConstraints.EAST;
			gbc_addressLabel.insets = new Insets(0, 0, 5, 5);
			gbc_addressLabel.gridx = 1;
			gbc_addressLabel.gridy = 3;
			contentPanel.add(addressLabel, gbc_addressLabel);
		}
		{
			addressTextField = new JTextField();
			GridBagConstraints gbc_addressTextField = new GridBagConstraints();
			gbc_addressTextField.insets = new Insets(0, 0, 5, 0);
			gbc_addressTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_addressTextField.gridx = 2;
			gbc_addressTextField.gridy = 3;
			contentPanel.add(addressTextField, gbc_addressTextField);
			addressTextField.setColumns(10);
		}
		{
			JLabel birthdateLabel = new JLabel("Birthdate");
			GridBagConstraints gbc_birthdateLabel = new GridBagConstraints();
			gbc_birthdateLabel.anchor = GridBagConstraints.EAST;
			gbc_birthdateLabel.insets = new Insets(0, 0, 0, 5);
			gbc_birthdateLabel.gridx = 1;
			gbc_birthdateLabel.gridy = 4;
			contentPanel.add(birthdateLabel, gbc_birthdateLabel);
		}
//		{
//			JComboBox comboBox = new JComboBox();
//			GridBagConstraints gbc_comboBox = new GridBagConstraints();
//			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
//			gbc_comboBox.gridx = 2;
//			gbc_comboBox.gridy = 4;
//			contentPanel.add(comboBox, gbc_comboBox);
//		}
		
		{
			datePicker = new JDateComponentFactory().createJDatePicker();
			GridBagConstraints gbc_datePicker = new GridBagConstraints();
			gbc_datePicker.fill = GridBagConstraints.HORIZONTAL;
			gbc_datePicker.gridx = 2;
			gbc_datePicker.gridy = 4;
			contentPanel.add((JComponent)datePicker, gbc_datePicker);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						saveLecturer();
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
						LecturerDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void saveLecturer() {

		// get the Lecturer info from gui
		try {
			Lecturer tempLecturer = null;
			String f_name = firstNameTextField.getText();
			String l_name = lastNameTextField.getText();
			String address = addressTextField.getText();

			int year = datePicker.getModel().getYear();
			int month = datePicker.getModel().getMonth()+1;
			int day = datePicker.getModel().getDay();
			String birth = year+"-"+month+"-"+day;
			Date birthdate = Date.valueOf(birth);

			if (updateMode) {
				tempLecturer = previousLecturer;
				tempLecturer.setFirstName(f_name);
				tempLecturer.setLastName(l_name);
				tempLecturer.setAddress(address);
				tempLecturer.setBirthdate(birthdate);

			} else {
				tempLecturer = new Lecturer(0,f_name, l_name, address, birthdate);
			}


			// save to the database
			if (updateMode) {
				LecturerDAO.updateLecturer(tempLecturer);
			} else {
				LecturerDAO.addLecturer(tempLecturer);
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			guiClass.refreshLecturerView();

			// show success message
			JOptionPane.showMessageDialog(guiClass,
					"Lecturer saved succesfully.", "Lecturer Saved",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(guiClass,
					"Error saving Lecturer: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}