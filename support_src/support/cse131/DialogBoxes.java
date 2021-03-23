package support.cse131;

import javax.swing.JComponent;
import javax.swing.JOptionPane;

/**
 * @author Dennis Cosgrove (http://www.cse.wustl.edu/~cosgroved/)
 */
public class DialogBoxes {
	public static boolean askUser(String message) {
		return askUser(message, null);
	}

	public static boolean askUser(String message, String title) {
		JComponent parent = null;
		int option = JOptionPane.showConfirmDialog(parent, message, title,
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		return option == JOptionPane.YES_OPTION;
	}
}
