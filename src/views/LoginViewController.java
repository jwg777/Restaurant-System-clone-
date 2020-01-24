package views;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController{
  
    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;
	
	ButtonController butController = ButtonController.getInstance();
	
	@FXML
	private void returnPush() throws Exception{
		System.out.println("Return Pushed");
		butController.startMain();
	}
	
	@FXML
	private void loginSelected() throws Exception {
	    System.out.println("Username is " + userField.getText());
	    System.out.println("Password is " + passwordField.getText());
	}

}