package dialogs;

import java.awt.BorderLayout;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classes.Classroom;
import dao.ClassroomDAO;
import timetable.GuiClass;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;


public class ClassroomDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private final JPanel contentPanel = new JPanel();

	private ClassroomDAO classroomDAO;
	private GuiClass guiClass;

	private Classroom previousClassroom = null;
	private boolean updateMode = false;
	private JTextField classNumTextField;

	public ClassroomDialog(GuiClass guiClass,
			ClassroomDAO theClassroomDAO, Classroom thePreviousClassroom, boolean theUpdateMode) {

		this();
		classroomDAO = theClassroomDAO;
		this.guiClass = guiClass;
		previousClassroom = thePreviousClassroom;

		if (updateMode = theUpdateMode) {
			setTitle("Update Classroom");
			populateGui(previousClassroom);
		}
	}

	public ClassroomDialog(GuiClass guiClass,ClassroomDAO theClassroomDAO) {
		this(guiClass, theClassroomDAO, null, false);
	}

	private void populateGui(Classroom theClassroom) {

		classNumTextField.setText(String.valueOf(theClassroom.getNumber()));
	}

	/**
	 * Create the dialog.
	 */
	public ClassroomDialog() {
//		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Add Classroom");
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
			JLabel lblClassNumber = new JLabel("Class Number");
			GridBagConstraints gbc_lblClassNumber = new GridBagConstraints();
			gbc_lblClassNumber.insets = new Insets(0, 0, 5, 5);
			gbc_lblClassNumber.anchor = GridBagConstraints.EAST;
			gbc_lblClassNumber.gridx = 1;
			gbc_lblClassNumber.gridy = 1;
			contentPanel.add(lblClassNumber, gbc_lblClassNumber);
		}
		{
			classNumTextField = new JTextField();
			GridBagConstraints gbc_classNumTextField = new GridBagConstraints();
			gbc_classNumTextField.insets = new Insets(0, 0, 5, 0);
			gbc_classNumTextField.fill = GridBagConstraints.HORIZONTAL;
			gbc_classNumTextField.gridx = 2;
			gbc_classNumTextField.gridy = 1;
			contentPanel.add(classNumTextField, gbc_classNumTextField);
			classNumTextField.setColumns(10);
		}
		{
			JLabel validateLabel = new JLabel("(Must have at least 2 digits)");
			GridBagConstraints gbc_validateLabel = new GridBagConstraints();
			gbc_validateLabel.insets = new Insets(0, 0, 5, 0);
			gbc_validateLabel.gridx = 2;
			gbc_validateLabel.gridy = 2;
			contentPanel.add(validateLabel, gbc_validateLabel);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						saveClassroom();
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
						ClassroomDialog.this.dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void saveClassroom() {

		// get the Classroom info from gui

		Classroom tempClassroom = null;

		try {
			int classNum = Integer.parseInt(classNumTextField.getText());
			int oldClassNum = 0;
			if (updateMode) {
				oldClassNum = previousClassroom.getNumber();
				tempClassroom = previousClassroom;
				tempClassroom.setNumber(classNum);

			} else {
				tempClassroom = new Classroom(classNum);
			}


			// save to the database
			if (updateMode) {
				classroomDAO.updateClassroom(tempClassroom, oldClassNum);
			} else {
				classroomDAO.addClassroom(tempClassroom);
			}

			// close dialog
			setVisible(false);
			dispose();

			// refresh gui list
			guiClass.refreshClassroomView();

			// show success message
			JOptionPane.showMessageDialog(guiClass,
					"Classroom saved succesfully.", "Classroom Saved",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception exc) {
			JOptionPane.showMessageDialog(guiClass,
					"Error saving Classroom: " + exc.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}