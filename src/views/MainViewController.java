package views;

import javafx.fxml.FXML;

public class MainViewController {

	ButtonController butController = ButtonController.getInstance();

	@FXML
	private void customerPush() throws Exception {
		butController.startCustomer();
	}

	@FXML
	private void loginPush() throws Exception {
		butController.startLogin();
	}

}