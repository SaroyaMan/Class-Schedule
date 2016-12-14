package timetable;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Application {

	public static void main(String[] args) {

		try {
			DbHelper db = DbHelper.getInstance();
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				new View();
			}
		});

	}

}
