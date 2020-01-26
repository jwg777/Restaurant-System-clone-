package views;

import javafx.fxml.FXML;

// TODO: Auto-generated Javadoc
/**
 * The Class MainViewController.
 */
public class MainViewController {

	/** The but controller. */
	ButtonController butController = ButtonController.getInstance();

	/**
	 * Customer push.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void customerPush() throws Exception {
		butController.startCustomer();
	}

	/**
	 * Login push.
	 *
	 * @throws Exception the exception
	 */
	@FXML
	private void loginPush() throws Exception {
		butController.startLogin();
	}

}