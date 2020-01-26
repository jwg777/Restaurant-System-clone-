package views;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginViewController.
 */
public class LoginViewController {

	/** The user field. */
	@FXML
	private TextField userField;

	/** The password field. */
	@FXML
	private PasswordField passwordField;
	
	/** The error message. */
	@FXML
	private Text errorMessage;

	/** The but controller. */
	ButtonController butController = ButtonController.getInstance();

	/**
	 * Return push.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void returnPush() throws Exception {
		butController.startMain();
	}

	/**
	 * Login selected.
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