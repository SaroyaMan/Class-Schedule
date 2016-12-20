package dialogs;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Phone;
import dao.LecturerDAO;
import dao.PhoneDAO;
import timetable.Application;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JComboBox;

public class PhoneDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();
	private JComboBox<?> comboBox = null;
	
	private PhoneDAO phoneDAO;
	private Application guiClass;

	private Phone previousPhone = null;
	private boolean updateMode = false;
	private JTextField phoneNumTextField;

	public PhoneDialog(Application guiClass,
			PhoneDAO thePhoneDAO, Phone thePreviousPhone, boolean theUpdateMode) {

		this();
		phoneDAO = thePhoneDAO;
		this.guiClass = guiClass;
		previousPhone = thePreviousPhone;

		if (updateMode = theUpdateMode) {
			setTitle("Update Phone");
			populateGui(previousPhone);
		}
	}

	public PhoneDialog(Application guiClass,PhoneDAO thePhoneDAO) {
		this(guiClass, thePhoneDAO, null, false);
	}

	private void populateGui(Phone thePhone) {

		phoneNumTextField.setText(String.valueOf(thePhone.getNumber()));
		comboBox.setSelectedIndex(thePhone.getIdLecturer()-1);
	}

	/**
	 * Create the dialog.
	 */
	public PhoneDialog() {
		setTitle("Add Phone");
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
			JLabel lblClassNumber = new JLabel("Phone Number");
			GridBagConstraints gbc_lblClassNumber = new GridBagConstraints();
			gbc_lblClassNumber.insets = new Insets(0, 0, 5, 5);
			gbc_lblClassNumber.anchor = GridBagConstraints.EAST;
			gbc_lblClassNumber.gridx = 1;
			gbc_lblClassNumber.gridy = 1;
			contentPanel.add(lblClassNumber, gbc_lblClassNumber);
		}
		{
			phoneNumTextField = new JTextField();
			GridBagConstraints gbc_phoneNumTextField = new GridBagConstraints();
			gbc_phoneNumTextField.insets = new Insets(0, 0, 5, 0);
			gbc_phoneNumTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_phoneNumTextField.gridx = 2;
			gbc_phoneNumTextField.gridy = 1;
			contentPanel.add(phoneNumTextField, gbc_phoneNumTextField);
			phoneNumTextField.setColumns(10);
		}
		{
			JLabel lblIdLecturer = new JLabel("ID Lecturer");
			GridBagConstraints gbc_lblIdLecturer = new GridBagConstraints();
			gbc_lblIdLecturer.anchor = GridBagConstraints.EAST;
			gbc_lblIdLecturer.insets = new Insets(0, 0, 5, 5);
			gbc_lblIdLecturer.gridx = 1;
			gbc_lblIdLecturer.gridy = 2;
			contentPanel.add(lblIdLecturer, gbc_lblIdLecturer);
		}
		{
			try {
				comboBox = new JComboBox<>(LecturerDAO.getInstance().getAllLecturersId().toArray());
			} catch (ClassNotFoundException | SQLException | IOException | ParseException e) {
				e.printStackTrace();
			}
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 2;
			gbc_comboBox.gridy = 2;
			contentPanel.add(comboBox, gbc_comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						savePhone();
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
						PhoneDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void savePhone() {

		// get the Phone info from gui

		Phone tempPhone = null;

		try {
			String number = phoneNumTextField.getText();
			int idLecturer = (int) comboBox.getSelectedItem();
			if (updateMode) {
				tempPhone = previousPhone;
				tempPhone.setNumber(number);

			} else {
				tempPhone = new Phone(number, idLecturer);
			}


			// save to the database
			if (updateMode) {
				phoneDAO.updatePhone(tempPhone);
			} else {
				phoneDAO.addPhone(tempPhone);
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			guiClass.refreshPhoneView();

			// show success message
			JOptionPane.showMessageDialog(guiClass,
					"Phone saved succesfully.", "Phone Saved",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(guiClass,
					"Error saving Phone: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}