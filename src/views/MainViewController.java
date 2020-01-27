package views;

import javafx.fxml.FXML;

/**
 * Controller for the main menu view.
 */
public class MainViewController {

	/** The button controller. */
	ButtonController butController = ButtonController.getInstance();

	/**
	 * When the 'View Menu' button is pressed, go to the customer view.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void customerPush() throws Exception {
		butController.startCustomer();
	}

	/**
	 * When the staff login button is pushed, go to the staff login view.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void loginPush() throws Exception {
		butController.startLogin();
	}

}