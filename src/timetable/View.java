package timetable;

import javax.swing.JTabbedPane;


import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import java.awt.Toolkit;

public class View extends JPanel {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane;

	
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from
     * the event dispatch thread.
     */
    public View() {
    	setBounds(0, 0, 782, 453);
    	
        //Create and set up the window.
        JFrame frame = new JFrame("Timetable Management");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("images/timetablelogo.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initialize();
        frame.getContentPane().setLayout(null);
        //Add content to the window.
        frame.getContentPane().add(this);
        setLayout(null);
        add(tabbedPane);
        
        JButton btnDelete = new JButton("Update");
        btnDelete.setBounds(192, 457, 205, 96);
        frame.getContentPane().add(btnDelete);
        
        JButton button = new JButton("Delete");
        button.setBounds(395, 457, 205, 96);
        frame.getContentPane().add(button);
        
        JButton button_1 = new JButton("Add");
        button_1.setBounds(0, 457, 198, 96);
        frame.getContentPane().add(button_1);
        
        //Display the window.
        frame.setMinimumSize(new Dimension(618,600));
        frame.pack();
        frame.setVisible(true);
    }
	
	private void initialize() {
        
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 600, 465);
        tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
        ImageIcon classroomLogo = createImageIcon("images/classroomlogo.png");
        ImageIcon courseLogo = createImageIcon("images/courselogo.png");
        ImageIcon lecturerLogo = createImageIcon("images/lecturerlogo.png");
        ImageIcon timetableLogo = createImageIcon("images/timetablelogo.png");
        
        JTable panel1 = new JTable();
        tabbedPane.addTab("Classrooms", classroomLogo, panel1,"Watch classroom information");
        //tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
        
        JTable panel2 = new JTable();
        tabbedPane.addTab("Courses", courseLogo, panel2,"Watch courses information");
//        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        
        JTable panel3 = new JTable();
        tabbedPane.addTab("Lecturers", lecturerLogo, panel3,"Watch lecturers information");
//        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);
        
        JTable panel4 = new JTable();
//        panel4.setPreferredSize(new Dimension(410, 50));
        tabbedPane.addTab("Timetable", timetableLogo, panel4,"Watch timetable information");
        
        //The following line enables to use scrolling tabs.
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
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
}