package views;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginViewController {

	@FXML
	private TextField userField;

	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Text errorMessage;

	ButtonController butController = ButtonController.getInstance();

	@FXML
	private void returnPush() throws Exception {
		butController.startMain();
	}

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