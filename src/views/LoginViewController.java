package views;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * Controller for the staff login view.
 */
public class LoginViewController {

	/** The field where the user enters a username. */
	@FXML
	private TextField userField;

	/** The field where the user enters a password. */
	@FXML
	private PasswordField passwordField;
	
	/** The label used to display an error message on failed authentication. */
	@FXML
	private Text errorMessage;

	/** The button controller. */
	SceneController butController = SceneController.getInstance();

	/**
	 * When the 'Return to Menu' button is pushed, return to the main menu.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void returnPush() throws Exception {
		butController.startMain();
	}

	/**
	 * When the 'Login' button is pushed, attempt to authenticate the user with the
	 * specified username and password. If successful, go to either the waiter or kitchen view
	 * depending on which one the credentials are assigned to. If unsuccessful, display an error
	 * message.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void loginSelected() throws Exception {
	    String enteredUname = userField.getText();
	    String enteredPword = passwordField.getText();
	    
	    System.out.println("Username is " + enteredUname);
	    System.out.println("Password is " + enteredPword);
	    
	    if (enteredUname.equals("waiter") && enteredPword.equals("service")) {
	      System.out.println("Waiter successfully authenticated");
	      butController.startWaiter();
	    } else if (enteredUname.equals("kitchen") && enteredPword.equals("cooking")) {
	      System.out.println("Kitchen successfully authenticated");
	    } else {
	      errorMessage.setVisible(true);
	      System.out.println("Authentication failed");
	    }
	}

}